package physio_bookings_system;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Scanner;

public class ReportTest {

    private final String appointmentFile = "Appointment.txt";
    private final String patientFile = "Patient.txt";
    private final String physiotherapistFile = "Physiotherapist.txt";

    // Capture the console output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws Exception {
        // Redirect System.out to capture the report output.
        System.setOut(new PrintStream(outContent));

        // Clean up any existing files.
        new File(appointmentFile).delete();
        new File(patientFile).delete();
        new File(physiotherapistFile).delete();

        // Create and write sample data to Patient.txt
        FileWriter fw = new FileWriter(patientFile);
        fw.write("{ID=P001, Name=John Doe, Address=123 Main St, Phone=555-1111}\n");
        fw.close();

        // Create and write sample data to Physiotherapist.txt
        fw = new FileWriter(physiotherapistFile);
        fw.write("{ID=PHY001, Name=Dr. Smith, Address=Some Addr, Phone=555-2222, Expertise=[Physio]}\n");
        fw.close();

        // Create and write sample data to Appointment.txt
        fw = new FileWriter(appointmentFile);
        fw.write("{AppointmentID=APPT001, PatientID=P001, Treatment=Checkup, PhysiotherapistID=PHY001, "
                + "Schedule={Day=Monday, Date=1/1/2025, Time=10:00}, Status=Attended}\n");
        fw.close();
    }

    @AfterEach
    void tearDown() {
        // Restore original System.out.
        System.setOut(originalOut);

        // Delete the created test files.
        new File(appointmentFile).delete();
        new File(patientFile).delete();
        new File(physiotherapistFile).delete();

        // Clear the captured output.
        outContent.reset();
    }

    @Test
    void testGenerateWeeklyAppointmentReport() {
        // Generate the report.
        Report.generateWeeklyAppointmentReport();

        // Convert the captured output to a string.
        String output = outContent.toString();

        // Verify that essential parts of the report output are present.
        assertTrue(output.contains("4-WEEK APPOINTMENT REPORT"), "Report header missing.");
        assertTrue(output.contains("Physiotherapist: Dr. Smith"), "Physiotherapist details missing.");
        assertTrue(output.contains("Patient Name       : John Doe"), "Patient details missing.");
        assertTrue(output.contains("Treatment          : Checkup"), "Treatment details missing.");
        assertTrue(output.contains("Schedule           : Monday, 1/1/2025, 10:00"), "Schedule details missing.");
        assertTrue(output.contains("Status             : Attended"), "Status details missing.");
        assertTrue(output.contains("Dr. Smith - Attended: 1"), "Attended count details missing.");
}
}