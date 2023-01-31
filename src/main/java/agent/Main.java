package agent;

import agent.config.Configuration;
import agent.filter.FilterProcessor;
import agent.input.*;

import java.util.Observer;

public class Main {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        Configuration.fetchPaths();
        InputProcessor inputProcessor=new InputProcessor(configuration);
        FilterProcessor filterProcessor = new FilterProcessor(inputProcessor,configuration);
        inputProcessor.register(filterProcessor);
        inputProcessor.process();
    }
}