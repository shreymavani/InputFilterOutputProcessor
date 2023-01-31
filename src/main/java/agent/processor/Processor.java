package agent.processor;

import java.io.IOException;

public interface Processor {
    public void process() throws IOException, InterruptedException;
}
