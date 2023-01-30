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
    String testFilePath ,expectedFilePath ,line,destination;
    File file;
    BufferedReader br;
    @BeforeEach
    public void ini() throws IOException {
        configuration =new Configuration();
        filterProcessor=new FilterProcessor();
        testFilePath = configuration.getDataFromPropertyFileWithKey("/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/InputProcessorTest.properties","testFilePath");
        expectedFilePath = configuration.getDataFromPropertyFileWithKey("/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/InputProcessorTest.properties","outputFilePath");
        destination = configuration.getDataFromPropertyFileWithKey("/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/InputOutputPathOfFile.properties","outputFilePath");
        file = new File(testFilePath);
        br = new BufferedReader(new FileReader(testFilePath));
    }

    @Test
    public void inputFileTestingByComparingOutputFile() throws IOException {
        String data="";
        while ((line = br.readLine()) != null) {
            data += (line)+"\n";
        }
        filterProcessor.sendMessage(data,"(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+");
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

