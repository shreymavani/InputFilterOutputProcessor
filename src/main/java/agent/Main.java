package agent;

import agent.config.Configuration;
import agent.filter.FilterProcessor;
import agent.input.*;
import agent.output.OutputProcessor;

import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        Configuration.fetchPaths();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        FilterProcessor filterProcessor = new FilterProcessor(executorService,configuration);
        InputProcessor inputProcessor=new InputProcessor(filterProcessor,configuration);
        inputProcessor.process();
        executorService.shutdown();
    }
}