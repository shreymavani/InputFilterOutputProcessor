package agent.readfilterstore;

import java.io.*;
import java.util.Scanner;

public class InputProcessor {

    private static final int MAX_BATCH_SIZE = 1048576;
    private FilterProcessor filter;
    private Configuration configuration;
    Scanner in;
    String regex,directoryPath,line;

    InputProcessor() throws IOException {
        configuration = new Configuration();
        directoryPath = configuration.getDataFromPropertyFileWithKey("/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/InputOutputPathOfFile.properties","directoryPath");
        filter = new FilterProcessor();
    }
    void process() throws Exception {


        in=new Scanner(System.in);
        regex = in.nextLine();

        File directory = new File(directoryPath);         //path to directory

        File[] files = directory.listFiles();

        for (File file : files) {

            try {

                long fileSize = file.length();
                if (fileSize > MAX_BATCH_SIZE) {

                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                        int numBatches = (int) Math.ceil((double) fileSize / MAX_BATCH_SIZE);   // Calculate the number of batches

                        for (int i = 0; i < numBatches; i++) {
                            // Read the next batch
                            char[] batch = new char[MAX_BATCH_SIZE];
                            int read = reader.read(batch, 0, MAX_BATCH_SIZE);


                            String batchString = new String(batch, 0, read);    // Process the batch
                            filter.filter(batchString,regex);
                        }
                    }
                } else {

                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {      // File size is less than or equal to the maximum batch size
                        // Read the entire file in one go
                        String entireFile = "";
                        while ((line = reader.readLine()) != null) {
                            entireFile += (line + "\n");
                        }
                        filter.filter(entireFile,regex);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
