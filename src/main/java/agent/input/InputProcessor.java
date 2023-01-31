package agent.input;

import agent.config.Configuration;
import agent.message.Message;
import agent.messageHandler.MessageHandler;
import agent.processor.Processor;

import java.io.*;
import java.util.Observer;
import java.util.Observable;

public class InputProcessor implements Processor {

    private static final int MAX_BATCH_SIZE = 1048576;
    private String directoryPath,line,data;
    private final MessageHandler messageHandler;

    public InputProcessor(MessageHandler messageHandler,Configuration configuration){
        directoryPath = configuration.getDirectoryPath("inputDirectoryPath");
        this.messageHandler = messageHandler;
    }
    public void process() {

        File directory = new File(directoryPath);                                                //path to directory

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


                            String batchString = new String(batch, 0, read);              // Process the batch
                            data = batchString;

                        }
                    }
                } else {

                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {      // File size is less than or equal to the maximum batch size
                                                                                                  // Read the entire file in one go
                        String entireFile = "";
                        while ((line = reader.readLine()) != null) {
                            entireFile += (line + "\n");
                        }
                        data = entireFile;
                    }
                }
                Message msg = new Message(data);
                messageHandler.handle(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
