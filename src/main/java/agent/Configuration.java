package agent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Configuration {
    private Properties prop = new Properties();
    private InputStream input = null;
    private String regex;
    private Scanner in;
    public String getDataFromPropertyFileWithKey(String pathToPropertyFile, String key) throws IOException {
        input = new FileInputStream(pathToPropertyFile);
        prop.load(input);
        return prop.getProperty(key);
    }

    public String getregex()
    {
        return regex;
    }

    public void putregex()
    {
        in=new Scanner(System.in);
        regex = in.nextLine();
    }

}
