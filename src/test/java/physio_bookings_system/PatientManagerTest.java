package physio_bookings_system;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

public class PatientManagerTest {

    @BeforeEach
    void setUp() {
        // Remove the Patient.txt file before each test run, if it exists,
        // to ensure the test starts with a fresh state.
        File file = new File(PatientManager.patientFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddPatient() throws Exception {
        // Prepare simulated input for addPatient method:
        // Order of input: Patient ID, Full Name, Address, Phone Number.
        String simulatedInput = "P001\nJohn Doe\n123 Main Street\n555-1234\n";
        Scanner scanner = new Scanner(simulatedInput);

        // Call the static addPatient method.
        PatientManager.addPatient(scanner);

        // Construct the expected data string exactly as it will be saved.
        String expectedData = "{ID=P001, Name=John Doe, Address=123 Main Street, Phone=555-1234}";

        // Verify that the file exists.
        File file = new File(PatientManager.patientFile);
        assertTrue(file.exists(), "Patient file should exist after adding a record.");

        // Read the file and confirm it contains the expected data.
        boolean found = false;
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();
            if (line.equals(expectedData)) {
                found = true;
                break;
            }
        }
        fileScanner.close();

        assertTrue(found, "Expected patient data was not found in the file.");
}
}