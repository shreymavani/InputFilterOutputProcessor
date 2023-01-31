import agent.config.Configuration;
import agent.filter.FilterProcessor;
import agent.input.InputProcessor;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
public class TestingForInputProcessor {

    private FilterProcessor filterProcessor;
    private Configuration configuration;
    @Autowired
    InputProcessor inputProcessor;
    InputStream input = null;
    String testFilePath ,expectedFilePath ,line,destination,data;
    File file;
    BufferedReader br;
    @BeforeEach
    public void ini() throws FileNotFoundException {

    }

    @Test
    public void inputFileTestingByComparingOutputFile() throws IOException {
        configuration =new Configuration();
        Configuration.fetchPaths();
        testFilePath = configuration.getDirectoryPath("inputFilePathInputProTesting");
        expectedFilePath = configuration.getDirectoryPath("outputFilePathInputProTesting");
        destination = configuration.getDirectoryPath("outputFilePath");

        Configuration configuration = new Configuration();
        Configuration.fetchPaths();
        InputProcessor inputProcessor=new InputProcessor(configuration);
        FilterProcessor filterProcessor = new FilterProcessor(inputProcessor,configuration);
        inputProcessor.register(filterProcessor);
//        inputProcessor.process();
//        System.out.println(1000);
        file = new File(testFilePath);
        br = new BufferedReader(new FileReader(file));
        configuration.putRegex("(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+");
        data="";
        while ((line = br.readLine()) != null) {
            data += (line)+"\n";
        }
//        filterProcessor.sendMessage(data);
//        filterProcessor.process();
        file = new File(expectedFilePath);
        br = new BufferedReader(new FileReader(file));
        String expectedData = "";

        while ((line = br.readLine()) != null){
            expectedData += line;
        }

        file = new File(destination);
        br = new BufferedReader(new FileReader(file));
        String actualData = "";

        while ((line = br.readLine()) != null){
            actualData += line;
        }

        assertEquals(expectedData,actualData);
    }

    @AfterEach
    public void clear() throws IOException {
        input.close();
    }
}

