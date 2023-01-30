package agent;

import agent.input.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        Configuration.fetchPaths();
        InputProcessor inputProcessor=new InputProcessor(configuration);         //Object of agent.readfilterstore.InputProcessor is created
        inputProcessor.process();                                   //process method is called
    }
}