package agent.readfilterstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static junit.framework.TestCase.assertEquals;

public class TestingForOutputprocessor {

    private final String filePath = "/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/test/test.txt";
    private OutputProcessor outputProcessor;
    private Configuration configuration;
    String testingFile,destination,expectedData,actualData,line;
    File file;
    BufferedReader br;
    @BeforeEach
    public void ini() throws IOException {
        outputProcessor=new OutputProcessor();
        configuration =new Configuration();
        testingFile = configuration.getDataFromPropertyFileWithKey("/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/OutputProcessorTest.properties","testingFilePath");
        destination = configuration.getDataFromPropertyFileWithKey("/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/InputOutputPathOfFile.properties","outputFilePath");
    }

    @Test
    public void comparingOutputContent() throws Exception {

        //Testing File Reading

        file = new File(testingFile);
        br = new BufferedReader(new FileReader(file));
        expectedData = "";
        actualData = "";

        while ((line = br.readLine()) != null) {            //Writing Content of Test file in other demo file using outputProcessor Store method.
            outputProcessor.store(line);
            actualData += line;
        }

        file = new File(destination);
        br = new BufferedReader(new FileReader(file));
        while ((line = br.readLine()) != null) {            //Reading Content from demo file
            expectedData += line;
        }

        br.close();

        assertEquals(actualData, expectedData);
    }
}
