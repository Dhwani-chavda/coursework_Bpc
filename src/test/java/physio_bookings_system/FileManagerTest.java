package physio_bookings_system;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileManagerTest {

    // Use a dedicated test file to avoid clashing with production files.
    private final String testFileName = "testFile.txt";

    @BeforeEach
    void setUp() {
        // Ensure a clean slate before each test by deleting the test file if it exists.
        File file = new File(testFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test.
        File file = new File(testFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testWriteAndReadFile() {
        // Define sample data that we expect to write and read back.
        List<String> expectedLines = Arrays.asList(
                "Hello, World!",
                "This is a test file.",
                "FileManager works!"
        );

        // Write the expected lines to the test file.
        FileManager.writeFile(testFileName, expectedLines);

        // Read back from the file.
        List<String> actualLines = FileManager.readFile(testFileName);

        // The read data should exactly match the data we wrote.
        assertEquals(expectedLines, actualLines,
                "The lines read from the file should match the lines written.");
    }

    @Test
    void testReadFileNonExistent() {
        // When trying to read a file that doesn't exist, an empty list should be returned.
        String nonExistentFile = "nonexistent.txt";
        // Ensure the file doesn't exist.
        File file = new File(nonExistentFile);
        if (file.exists()) {
            file.delete();
        }

        List<String> lines = FileManager.readFile(nonExistentFile);
        assertTrue(lines.isEmpty(),
                "Reading a non-existent file should return an empty list.");
    }
}
