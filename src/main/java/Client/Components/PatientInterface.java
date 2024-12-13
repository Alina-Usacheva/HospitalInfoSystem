package Client.Components;

import Client.Api.*;

import javax.swing.*;

public class PatientInterface {

    public PatientInterface(DoctorApiClient doctorApiClient,
                            PatientApiClient patientApiClient,
                            AppointmentApiClient appointmentApiClient,
                            MedicalRecordApiClient medicalRecordApiClient,
                            UserApiClient userApiClient,
                            PrescriptionApiClient prescriptionApiClient,
                            MedicationApiClient medicationApiClient) {
        JFrame patientFrame = new JFrame("Система для пациента");
        patientFrame.setSize(800, 600);
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Мой профиль", new PatientProfilePanel(patientApiClient, userApiClient));
        tabbedPane.addTab("Приёмы", new AppointmentPatientManagementPanel(appointmentApiClient, patientApiClient, userApiClient));
        tabbedPane.addTab("История", new MedicalHistoryPanel(medicalRecordApiClient, patientApiClient, userApiClient, prescriptionApiClient, medicationApiClient));
        //tabbedPane.addTab("Результаты анализов и исследований", new LabResultsPanel());
        tabbedPane.addTab("Записаться на приём", new AppointmentBookingPanel(doctorApiClient, appointmentApiClient, userApiClient));
        tabbedPane.addTab("Об Авторе", new AboutPanel());

        patientFrame.add(tabbedPane);
        patientFrame.setVisible(true);
    }
}
