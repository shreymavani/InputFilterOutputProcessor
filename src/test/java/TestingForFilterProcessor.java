import agent.config.Configuration;
import agent.filter.FilterProcessor;
import agent.input.InputProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import static junit.framework.TestCase.assertEquals;

public class TestingForFilterProcessor {

    FilterProcessor filterProcessor;
    Configuration configuration;
    @Autowired
    InputProcessor inputProcessor;
    InputStream input = null;
    Properties prop = new Properties();
    @BeforeEach
    public void init() {
        configuration = new Configuration();
        configuration.fetchPaths();
        filterProcessor = new FilterProcessor(inputProcessor,configuration);
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

        String filePath = configuration.getDirectoryPath("inputFilePathFilterProTesting");
        String OutputfilePath = configuration.getDirectoryPath("outputFilePathFilterProTesting");

        String data = Files.readString(Paths.get(filePath));
        String expectedOutput = Files.readString(Paths.get(OutputfilePath));

        String actual=filterProcessor.stringFiltering(data,"(?i)(password|pwd|pass)[=:\\\\b]?[^\\\\b]+");

        assertEquals(expectedOutput, actual);
    }
}
