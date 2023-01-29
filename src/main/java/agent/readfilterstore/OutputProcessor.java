package agent.readfilterstore;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OutputProcessor {
    private String destination = null;
    private Configuration configuration;
    OutputProcessor()
    {
        configuration = new Configuration();
    }
    void store(String data) throws Exception {

        destination = configuration.getDataFromPropertyFileWithKey("/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/InputOutputPathOfFile.properties","outputFilePath");

        FileWriter fw = new FileWriter(destination, true);
        fw.write(data);
        fw.close();

    }
}
