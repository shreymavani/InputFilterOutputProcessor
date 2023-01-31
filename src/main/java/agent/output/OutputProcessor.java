package agent.output;

import agent.config.Configuration;
import agent.message.Message;
import agent.messageHandler.MessageHandler;
import agent.processor.Processor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

public class OutputProcessor implements Processor, MessageHandler {
    private String destination = null,inputData="";
    private Configuration configuration;
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private ExecutorService executorService;
    public OutputProcessor(ExecutorService executorService,Configuration configuration)
    {
//        System.out.println(101);
        this.configuration = configuration;
        this.executorService = executorService;
        executorService.submit(()-> {
            try {
//                System.out.println(102);
                process();
//                System.out.println(106);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void process() throws IOException {
        while (true) {
            try {
//                System.out.println(104);
                Message message = queue.take();
//                  System.out.println(105);
                inputData = message.getData();

                destination = configuration.getDirectoryPath("outputFilePath");
                FileWriter fw = new FileWriter(destination, true);
                fw.write(inputData);
                fw.close();

            } catch (InterruptedException e) {
                throw new IOException();
            }
        }
    }

    @Override
    public void handle(Message message){
//        System.out.println(103);
        queue.offer(message);
    }

    @Override
    public void run() {

    }
}
