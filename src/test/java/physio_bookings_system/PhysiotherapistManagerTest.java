package physio_bookings_system;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

public class PhysiotherapistManagerTest {

    @BeforeEach
    void setUp() {
        // Clean up before each test by deleting the file if it exists,
        // so that we start with a fresh file.
        File file = new File(PhysiotherapistManager.fileName);
        if (file.exists()) {
            file.delete();
        }
        // (Optionally, delete the schedule file as well if needed)
        File scheduleFile = new File(PhysiotherapistManager.scheduleFile);
        if (scheduleFile.exists()) {
            scheduleFile.delete();
        }
    }

    @Test
    void testAddPhysiotherapist() throws Exception {
        // Prepare the simulated input (each line corresponds to one prompt).
        // Order of input: ID, Full Name, Address, Phone, Expertise.
        String inputString = "PH001\nDr. Smith\nAddress 1\n555-1234\nSports Therapy\n";
        Scanner scanner = new Scanner(inputString);

        // Call the static method to add a physiotherapist.
        PhysiotherapistManager.addPhysiotherapist(scanner);

        // Expected data format (as constructed in the method):
        String expected = "{ID=PH001, Name=Dr. Smith, Address=Address 1, Phone=555-1234, Expertise=[Sports Therapy]}";

        // Now check that the file "Physiotherapist.txt" contains the expected string.
        File file = new File(PhysiotherapistManager.fileName);
        assertTrue(file.exists(), "Physiotherapist file should exist after adding a record.");

        boolean found = false;
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();
            if (line.equals(expected)) {
                found = true;
                break;
            }
        }
        fileScanner.close();

        assertTrue(found, "Expected physiotherapist data was not found in the file.");}
}