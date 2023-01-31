import agent.config.Configuration;
import agent.output.OutputProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static junit.framework.TestCase.assertEquals;

public class TestingForOutputprocessor {

   private OutputProcessor outputProcessor;
    private Configuration configuration;
    String testingFile,destination,expectedData,actualData,line;
    File file;
    BufferedReader br;
    @BeforeEach
    public void ini() throws IOException {
        configuration =new Configuration();
        configuration.fetchPaths();
        outputProcessor=new OutputProcessor(configuration);
        destination = configuration.getDirectoryPath("outputFilePath");
        testingFile = configuration.getDirectoryPath("inputFilePathOutputProTesting");
    }

    @Test
    public void comparingOutputContent() throws Exception {

        //Testing File Reading

        file = new File(testingFile);
        br = new BufferedReader(new FileReader(file));
        expectedData = "";
        actualData = "";

        while ((line = br.readLine()) != null) {            //Writing Content of Test file in other demo file using outputProcessor Store method.
            outputProcessor.sendMessage(line);
            outputProcessor.process();
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
