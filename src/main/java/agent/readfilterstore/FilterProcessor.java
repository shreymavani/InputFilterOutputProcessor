package agent.readfilterstore;

import org.jetbrains.annotations.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterProcessor {

    private String filterData;
    private OutputProcessor outputProcessor;

    FilterProcessor()
    {
        outputProcessor = new OutputProcessor();
    }
    void filter(String buffer, String regex) {

        try {
            filterData = stringFiltering(buffer, regex);
            outputProcessor.store(filterData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String stringFiltering(@NotNull String inputData, String regex) {
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
