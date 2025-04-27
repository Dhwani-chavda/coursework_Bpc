package physio_bookings_system;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManagerTest {

    @BeforeEach
    void setUp() throws Exception {
        // Delete the Appointment and Schedule files to start fresh.
        File appointmentFile = new File(AppointmentManager.appointmentFile);
        if (appointmentFile.exists()) {
            appointmentFile.delete();
        }
        File scheduleFile = new File(AppointmentManager.scheduleFile);
        if (scheduleFile.exists()) {
            scheduleFile.delete();
        }
    }

    @Test
    void testCancelAppointment() throws Exception {
        // Prepare a sample appointment with Status=Booked
        FileWriter fw = new FileWriter(AppointmentManager.appointmentFile, true);

        // Example appointment record (note the expected format as built in bookAppointment):
        String appointmentData = "{AppointmentID=APPT001, PatientID=P001, Treatment=Checkup, PhysiotherapistID=PHY001, "
                + "Schedule={Day=Monday, Date=1/1/2025, Time=10:00}, Status=Booked}";
        fw.write(appointmentData + "\n");
        fw.close();

        File appointmentFile = new File(AppointmentManager.appointmentFile);
        assertTrue(appointmentFile.exists(), "Appointment file should exist.");

        // Call cancelAppointment for appointment APPT001.
        AppointmentManager.cancelAppointment("APPT001");

        // Verify the appointment record was updated to have Status=Cancelled.
        Scanner scanner = new Scanner(appointmentFile);
        boolean foundUpdatedAppointment = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("AppointmentID=APPT001")) {
                foundUpdatedAppointment = true;
                assertTrue(line.contains("Status=Cancelled"),
                        "Appointment status should be updated to 'Cancelled'.");
            }
        }
        scanner.close();
        assertTrue(foundUpdatedAppointment, "Updated appointment record was not found in the file.");

        // Verify that the schedule has been restored.
        File scheduleFile = new File(AppointmentManager.scheduleFile);
        assertTrue(scheduleFile.exists(), "Schedule file should exist after cancellation.");
        Scanner scheduleScanner = new Scanner(scheduleFile);
        boolean foundRestoredSchedule = false;
        while (scheduleScanner.hasNextLine()) {
            String scheduleLine = scheduleScanner.nextLine().trim();
            // The restored schedule is written as: {restoredSchedule}
            // In our sample record, restoredSchedule should be: Day=Monday, Date=1/1/2025, Time=10:00
            if (scheduleLine.equals("{Day=Monday, Date=1-1-2025, Time=10:00}")) {
                foundRestoredSchedule = true;
                break;
            }
        }
        scheduleScanner.close();
        assertTrue(foundRestoredSchedule, "Restored schedule was not found in the schedule file.");
}
}
