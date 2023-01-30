import agent.Configuration;
import agent.filter.FilterProcessor;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.*;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
public class TestingForInputProcessor {

    private FilterProcessor filterProcessor;
    private Configuration configuration;
    InputStream input = null;
    String testFilePath ,expectedFilePath ,line,destination,data;
    File file;
    BufferedReader br;
    @BeforeEach
    public void ini() throws IOException {
        configuration =new Configuration();
        Configuration.fetchPaths();
        testFilePath = configuration.getDirectoryPath("inputFilePathInputProTesting");
        expectedFilePath = configuration.getDirectoryPath("outputFilePathInputProTesting");
        destination = configuration.getDirectoryPath("outputFilePath");
        file = new File(testFilePath);
        br = new BufferedReader(new FileReader(file));
        configuration.putRegex("(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+");
        filterProcessor=new FilterProcessor(configuration);
    }

    @Test
    public void inputFileTestingByComparingOutputFile() throws IOException {
        data="";
        while ((line = br.readLine()) != null) {
            data += (line)+"\n";
        }
        filterProcessor.sendMessage(data);
        filterProcessor.process();
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

        assertEquals(actualData, expectedData);
    }

    @AfterEach
    public void clear() throws IOException {
        input.close();
    }
}

