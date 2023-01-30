package agent.filter;

import agent.Configuration;
import agent.output.OutputProcessor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import agent.Configuration;

public class FilterProcessor implements Processor {

    private String filterData,regex;
    private OutputProcessor outputProcessor;
    private Queue<String> queue = new LinkedList<>();
    public FilterProcessor(Configuration configuration)
    {
        regex = configuration.getRegex();
        outputProcessor = new OutputProcessor(configuration);
    }
    public void process() {

        try {
            String currentData = receiveMessage();
            filterData = stringFiltering(currentData, regex);
            outputProcessor.sendMessage(filterData);
            outputProcessor.process();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
