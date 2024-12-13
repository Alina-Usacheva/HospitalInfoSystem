package Client.Components;

import Client.Api.*;
import Client.Components.DoctorInterface;
import Client.Components.PatientInterface;
import Client.CurrentUser ;
import com.example.HospitalInfoSystem.Dto.LoginDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserApiClient userApiClient;
    MedicationApiClient medicationApiClient;
    MedicalRecordApiClient medicalRecordApiClient;
    PrescriptionApiClient prescriptionApiClient;

    public LoginPanel(CardLayout cardLayout,
                      JPanel mainPanel,
                      UserApiClient userApiClient,
                      DoctorApiClient doctorApiClient,
                      PatientApiClient patientApiClient,
                      AppointmentApiClient appointmentApiClient,
                      MedicationApiClient medicationApiClient,
                      MedicalRecordApiClient medicalRecordApiClient,
                      PrescriptionApiClient prescriptionApiClient) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.userApiClient = userApiClient;
        this.medicationApiClient = medicationApiClient;
        this.medicalRecordApiClient = medicalRecordApiClient;
        this.prescriptionApiClient = prescriptionApiClient;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.weightx = 1.0;

        // Текст с именем пользователя
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        JLabel usernameLabel = new JLabel("Логин:");
        add(usernameLabel, constraints);

        // Поле ввода пользователя
        constraints.gridy++;
        JTextField usernameField = new JTextField(20);
        add(usernameField, constraints);

        // Текст с паролем
        constraints.gridy++;
        JLabel passwordLabel = new JLabel("Пароль:");
        add(passwordLabel, constraints);

        // Поле ввовда пароля
        constraints.gridy++;
        JPasswordField passwordField = new JPasswordField(20);
        add(passwordField, constraints);

        // Текст с ролью
        constraints.gridy++;
        JLabel roleLabel = new JLabel("Выберите роль:");
        add(roleLabel, constraints);

        // Выбор роли пользователя
        constraints.gridy++;
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Пациент", "Врач"});
        add(roleComboBox, constraints);

        // Кнопка логина
        constraints.gridy++;
        JButton loginButton = new JButton("Войти");
        add(loginButton, constraints);

        // Кнопка регистрации
        constraints.gridy++;
        JButton switchToRegisterButton = new JButton("Зарегистрироваться");
        add(switchToRegisterButton, constraints);

        // Action Listener для кнопки логина
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = (String) roleComboBox.getSelectedItem();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Создаем LoginDto для аутентификации
                LoginDto loginDto = new LoginDto(username, password, role);

                try {
                    // Логика для входа
                    Long userId = userApiClient.loginUser(loginDto);
                    if (userId != null) {
                        // Сохраняем текущего пользователя
                        CurrentUser currentUser  = CurrentUser .getInstance();
                        currentUser.setId(userId);
                        currentUser.setUsername(username);
                        currentUser.setRole(role);

                        // Закрываем окно авторизации
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(LoginPanel.this);
                        frame.dispose();

                        // Открыть интерфейс в зависимости от роли
                        if (role.equals("Пациент")) {
                            new PatientInterface(doctorApiClient, patientApiClient, appointmentApiClient, medicalRecordApiClient,
                                    userApiClient, prescriptionApiClient, medicationApiClient);
                        } else {
                            new DoctorInterface(doctorApiClient, patientApiClient, appointmentApiClient, medicationApiClient,
                                    medicalRecordApiClient, prescriptionApiClient, userApiClient);
                        }
                    }
                } catch (RuntimeException ex) {
                    // Обработка ошибки входа
                    JOptionPane.showMessageDialog(LoginPanel.this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action Listener для переключения на окно регистрации
        switchToRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Register");
            }
        });
    }
}
