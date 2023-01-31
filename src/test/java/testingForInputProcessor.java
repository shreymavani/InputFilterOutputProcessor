import agent.config.Configuration;
import agent.filter.FilterProcessor;
import agent.input.InputProcessor;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
public class testingForInputProcessor {

    private static FilterProcessor filterProcessor;
    private static Configuration configuration;
    @Autowired
    private static InputProcessor inputProcessor;
    InputStream input = null;
    static String testFilePath ,expectedFilePath ,line,destination,data;
    File file;
    BufferedReader br;
    @BeforeAll
    public void ini() throws FileNotFoundException {
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
    }

    @Test
    public void inputFileTestingByComparingOutputFile() throws IOException {

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

