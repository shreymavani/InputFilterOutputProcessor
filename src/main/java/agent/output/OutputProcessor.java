package agent.output;

import agent.config.Configuration;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class OutputProcessor implements Processor {
    private String destination = null,inputData="";
    private Queue<String> queue = new LinkedList<>();
    private Configuration configuration;
    public OutputProcessor(Configuration configuration)
    {
        this.configuration = configuration;
    }
    public void process() throws Exception {

        destination = configuration.getDirectoryPath("outputFilePath");
        FileWriter fw = new FileWriter(destination, true);
        inputData=receiveMessage();
        fw.write(inputData);
        fw.close();
    }

    public void sendMessage(String message) {
        queue.offer(message);
    }

    public String receiveMessage() {
        return queue.poll();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return null;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return null;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return null;
    }

    @Override
    public void init(ProcessingEnvironment processingEnv) {

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return null;
    }
}
