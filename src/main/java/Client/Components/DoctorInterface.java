package Client.Components;

import Client.Api.*;

import javax.swing.*;

public class DoctorInterface {

    public DoctorInterface(DoctorApiClient doctorApiClient, PatientApiClient patientApiClient,
                           AppointmentApiClient appointmentApiClient,
                           MedicationApiClient medicationApiClient,
                           MedicalRecordApiClient medicalRecordApiClient,
                           PrescriptionApiClient prescriptionApiClient,
                           UserApiClient userApiClient) {
        JFrame doctorFrame = new JFrame("Система для врачей");
        doctorFrame.setSize(800, 600);
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Информация о пациентах", new PatientsInfoPanel(patientApiClient, userApiClient));
        tabbedPane.addTab("Расписание", new AppointmentDoctorManagementPanel(appointmentApiClient, medicationApiClient, medicalRecordApiClient, prescriptionApiClient, userApiClient));
        tabbedPane.addTab("Об Авторе", new AboutPanel());

        doctorFrame.add(tabbedPane);
        doctorFrame.setVisible(true);
    }
}
