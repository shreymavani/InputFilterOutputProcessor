import agent.Configuration;
import agent.filter.FilterProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import static junit.framework.TestCase.assertEquals;

public class TestingForFilterProcessor {

    FilterProcessor filterProcessor;
    Configuration configuration;
    InputStream input = null;
    Properties prop = new Properties();
    @BeforeEach
    public void init() {
        filterProcessor = new FilterProcessor();
        configuration = new Configuration();
    }

    @Test
    @DisplayName("MaskingPasswordTest")
    public void maskingPassword() {

        String input = "User : Shrey,date:22,password:2001", expectedOutput = "User : Shrey,date:22,*****\n";
        String out = filterProcessor.stringFiltering(input,"(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+");
        assertEquals(expectedOutput, out);
    }

    @DisplayName("Masking Password Testing Using Multiple String")
    @ParameterizedTest
    @ValueSource(strings = {"User : Shrey,date:22,password:dhjgvhghgtctycehq)((*", "User : Shrey,date:22,password:dhaeh@", "User : Shrey,date:22,password:21", "User : Shrey,date:22,password:2010101"})
    public void removePasswordTestingUsingMultipleString(String input) {
        String expectedOutput = "User : Shrey,date:22,*****\n";
        String out = filterProcessor.stringFiltering(input,"(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+");
        assertEquals(expectedOutput, out);
    }

    @Test
    @DisplayName("Testing File Based TestCases")
    public void fileBasedTesting() throws IOException {

        String filePath = configuration.getDataFromPropertyFileWithKey("/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/FilePathForFilterTesting.properties","filePath1");
        String OutputfilePath = configuration.getDataFromPropertyFileWithKey("/Users/smavani/IdeaProjects/InputFilterOutputProcessor/src/resources/FilePathForFilterTesting.properties","outputFilePath1");

        String data = Files.readString(Paths.get(filePath));
        String expectedOutput = Files.readString(Paths.get(OutputfilePath));

        String actual=filterProcessor.stringFiltering(data,"(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+");

        assertEquals(expectedOutput, actual);
    }
}
