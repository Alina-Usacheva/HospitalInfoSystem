package Client;

import Client.Api.*;
import Client.Components.LoginPanel;
import Client.Components.RegistrationPanel;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;

public class HealthManagementApp {

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public HealthManagementApp() {
        // Инициализация API клиентов
        DoctorApiClient doctorApiClient = new DoctorApiClient(new RestTemplate());
        UserApiClient userApiClient = new UserApiClient(new RestTemplate());
        PatientApiClient patientApiClient = new PatientApiClient(new RestTemplate());
        AppointmentApiClient appointmentApiClient = new AppointmentApiClient(new RestTemplate());
        MedicationApiClient medicationApiClient = new MedicationApiClient(new RestTemplate());
        MedicalRecordApiClient medicalRecordApiClient = new MedicalRecordApiClient(new RestTemplate());
        PrescriptionApiClient prescriptionApiClient = new PrescriptionApiClient(new RestTemplate());

        // Создание основного окна приложения
        frame = new JFrame("Health Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Создание CardLayout для переключения между панелями
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Добавление панелей для входа и регистрации
        mainPanel.add(new LoginPanel(cardLayout, mainPanel, userApiClient, doctorApiClient, patientApiClient, appointmentApiClient, medicationApiClient, medicalRecordApiClient, prescriptionApiClient), "Login");
        mainPanel.add(new RegistrationPanel(cardLayout, mainPanel, userApiClient), "Register");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HealthManagementApp::new);
    }
}
