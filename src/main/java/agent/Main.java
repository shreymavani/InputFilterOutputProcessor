package agent;

import agent.input.*;
public class Main {
    public static void main(String[] args) throws Exception {

        InputProcessor inputProcessor=new InputProcessor();         //Object of agent.readfilterstore.InputProcessor is created
        inputProcessor.process();                                   //process method is called
    }
}