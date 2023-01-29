package agent.readfilterstore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    Properties prop = new Properties();
    InputStream input = null;
    String getDataFromPropertyFileWithKey(String pathToPropertyFile,String key) throws IOException {
        input = new FileInputStream(pathToPropertyFile);
        prop.load(input);
        return prop.getProperty(key);
    }
}
