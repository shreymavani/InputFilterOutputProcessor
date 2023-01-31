package agent.filter;

import agent.config.Configuration;
import agent.message.Message;
import agent.messageHandler.MessageHandler;
import agent.output.OutputProcessor;
import agent.processor.Processor;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterProcessor implements MessageHandler,Processor {

    private String filterData,regex,rawData;
    private OutputProcessor outputProcessor;
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();;
    private  final ExecutorService executorService;
    public FilterProcessor(ExecutorService executorService,Configuration configuration)
    {
        this.executorService = executorService;
        regex = configuration.getRegex();
        outputProcessor = new OutputProcessor(executorService,configuration);
        executorService.submit(this::run);
    }

    public void run() {
        try {
            process();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void process() throws InterruptedException {

        while (true) {
            try {
                Message message = queue.take();
                rawData = message.getData();
//                System.out.println("98" + rawData);
                filterData = stringFiltering(rawData, regex);
//                System.out.println(98.1);
                Message filteredMessage = new Message(filterData);
//                System.out.println(99);
                outputProcessor.handle(filteredMessage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String stringFiltering(@NotNull String inputData, String regex) {
        String[] splitData = inputData.split("\n");
        filterData = "";
        System.out.println(splitData.length);
        for (String line : splitData) {

            Pattern pattern = Pattern.compile(regex);            // Compile the pattern

            Matcher matcher = pattern.matcher(line);             // Replace the password with the string ""

            filterData += (matcher.replaceAll("*****") + "\n");     // Replace the word "password" and password details with the string ""
        }

        return filterData;

    }

    @Override
    public void handle(Message message) {
        queue.offer(message);
    }

}
//    String removePrivateInformation(String InputData) {
//
//        String regex = "(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+";
//        String[] splitData = InputData.split("\n");
//        filterData = "";
//        System.out.println(splitData.length);
//        for (String line : splitData) {
//
//            Pattern pattern = Pattern.compile(regex);            // Compile the pattern
//
//            Matcher matcher = pattern.matcher(line);             // Replace the password with the string ""
//
//            filterData += (matcher.replaceAll("")+"\n");     // Replace the word "password" and password details with the string ""
//
//        }
//
//        return filterData;
//
//    }

//    String maskingPrivateInformation(String InputData) {
//
//        String regex = "(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+";
//        String[] splitData = InputData.split("\n");
//        filterData = "";
//        System.out.println(splitData.length);
//        for (String line : splitData) {
//
//            Pattern pattern = Pattern.compile(regex);               // Compile the pattern
//
//            Matcher matcher = pattern.matcher(line);                // Replace the password with the string ""
//
//            filterData += matcher.replaceAll("password: *****\n");
//        }
//        return filterData;
//    }
//}
