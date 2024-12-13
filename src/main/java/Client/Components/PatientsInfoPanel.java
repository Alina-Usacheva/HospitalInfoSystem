package Client.Components;

import Client.Api.PatientApiClient;
import Client.Api.UserApiClient;
import Client.CurrentUser ;
import com.example.HospitalInfoSystem.Entities.Patient;
import com.example.HospitalInfoSystem.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PatientsInfoPanel extends JPanel {

    private PatientApiClient patientApiClient;
    private UserApiClient userApiClient;
    private JPanel patientsPanel; // Панель для отображения пациентов
    private JScrollPane scrollPane;

    public PatientsInfoPanel(PatientApiClient patientApiClient, UserApiClient userApiClient) {
        this.patientApiClient = patientApiClient;
        this.userApiClient = userApiClient;

        setLayout(new BorderLayout());

        // Поле для ввода имени или фамилии
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Поиск");

        // Панель для ввода и кнопки поиска
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Введите имя или фамилию:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Создаем панель для пациентов
        patientsPanel = new JPanel();
        patientsPanel.setLayout(new BoxLayout(patientsPanel, BoxLayout.Y_AXIS));

        // Оборачиваем панель пациентов в JScrollPane
        scrollPane = new JScrollPane(patientsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Обработчик кнопки поиска
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                if (!searchText.isEmpty()) {
                    searchPatients(searchText);
                } else {
                    JOptionPane.showMessageDialog(PatientsInfoPanel.this, "Пожалуйста, введите имя или фамилию для поиска.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void searchPatients(String searchText) {
        try {
            // Получаем список пациентов по части имени или фамилии
            List<Patient> patients = patientApiClient.searchPatientsByNameOrSurname(searchText);
            displayPatients(patients);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при поиске пациентов: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayPatients(List<Patient> patients) {
        // Очищаем текущую панель пациентов
        patientsPanel.removeAll();

        // Выводим информацию о каждом пациенте
        for (Patient patient : patients) {
            JPanel patientPanel = new JPanel();
            patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));
            patientPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            patientPanel.setBackground(Color.WHITE);
            patientPanel.setPreferredSize(new Dimension(380, 100));
            patientPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            patientPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Добавляем информацию о пациенте
            JLabel nameLabel = new JLabel("Пациент: " + patient.getName() + " " + patient.getSurname());
            patientPanel.add(nameLabel);

            JLabel dobLabel = new JLabel("Дата рождения: " + patient.getDateOfBirth());
            patientPanel.add(dobLabel);

            JLabel addressLabel = new JLabel("Адрес: " + patient.getAddress());
            patientPanel.add(addressLabel);

            JLabel phoneLabel = new JLabel("Телефон: " + patient.getPhoneNumber());
            patientPanel.add(phoneLabel);

            patientsPanel.add(patientPanel);
            patientsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Обновляем интерфейс
        patientsPanel.revalidate();
        patientsPanel.repaint();
    }
}
