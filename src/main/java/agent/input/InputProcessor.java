package agent.input;

import agent.config.Configuration;
import agent.filter.FilterProcessor;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.*;
import java.util.Observer;
import java.util.Set;
import java.util.Observable;

public class InputProcessor extends Observable implements Processor {

    private static final int MAX_BATCH_SIZE = 1048576;
    private String directoryPath,line,data;
    private Observer observer;

    public InputProcessor(Configuration configuration){
        directoryPath = configuration.getDirectoryPath("inputDirectoryPath");
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
                notifyObservers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void register(Observer observer)
    {
        this.observer = observer;
    }

    @Override
    public void notifyObservers() {

        observer.update(this,null);                                  //synchronization is used to make sure any observer registered after message is received is not notified
    }
    public Object getUpdate(Observer observer)
    {
        return this.data;
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
