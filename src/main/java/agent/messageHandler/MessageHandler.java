package agent.messageHandler;

import agent.message.Message;

import java.io.IOException;

public interface MessageHandler extends Runnable {
    void handle(Message message) throws IOException;
}
