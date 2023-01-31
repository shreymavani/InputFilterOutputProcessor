package agent.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
public class Configuration {
    private Properties prop = new Properties();
    private InputStream input = null;
    private static String regex;
    private Scanner in;
    private static HashMap<String,String>filePaths = new HashMap<>();
    public static void fetchPaths()
    {
        regex = System.getenv("REGEX");
//        regex = "(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+";
        String directoryPath = "/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/propertyFiles";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String [] keyValue = line.split(" = ");
                        filePaths.put(keyValue[0],keyValue[1]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getDirectoryPath(String key)
    {
        return filePaths.get(key);
    }

    public String getRegex()
    {
        return regex;
    }
    public void putRegex(String regex){this.regex = regex;}
}
