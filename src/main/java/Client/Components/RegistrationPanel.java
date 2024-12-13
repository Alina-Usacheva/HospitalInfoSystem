package Client.Components;

import Client.Api.UserApiClient;
import com.example.HospitalInfoSystem.Dto.UserRegistrationDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrationPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserApiClient userApiClient;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public RegistrationPanel(CardLayout cardLayout, JPanel mainPanel, UserApiClient userApiClient) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.userApiClient = userApiClient;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Имя
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Имя:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        JTextField firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(300, 30));
        add(firstNameField, gbc);

        // Фамилия
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Фамилия:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JTextField lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(300, 30));
        add(lastNameField, gbc);

        // Роль
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Выберите роль:"), gbc);
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Пациент", "Врач"});
        gbc.gridx = 1; gbc.gridy = 2;
        add(roleComboBox, gbc);

        // Поля для пациента
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel dobLabel = new JLabel("Дата рождения:");
        add(dobLabel, gbc);
        JSpinner dobSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dobEditor = new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd");
        dobSpinner.setEditor(dobEditor);
        dobSpinner.setPreferredSize(new Dimension(300, 30)); // Установка размера

        // Отключаем редактирование текстового поля
        JTextField dobTextField = ((JSpinner.DateEditor) dobSpinner.getEditor()).getTextField();
        dobTextField.setEditable(false); // Запретить редактирование

        // Установка минимальной и максимальной даты
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, Calendar.JANUARY, 1); // Минимальная дата
        dobSpinner.setValue(calendar.getTime());
        calendar.setTime(new Date()); // Максимальная дата - сегодня
        dobSpinner.setModel(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH));

        gbc.gridx = 1; gbc.gridy = 3;
        add(dobSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel addressLabel = new JLabel("Адрес:");
        add(addressLabel, gbc);
        JTextField addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(300, 30)); // Установка размера
        gbc.gridx = 1; gbc.gridy = 4;
        add(addressField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        JLabel phoneLabel = new JLabel("Номер телефона:");
        add(phoneLabel, gbc);
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(300, 30)); // Установка размера
        gbc.gridx = 1; gbc.gridy = 5;
        add(phoneField, gbc);

        // Поля для врача
        gbc.gridx = 0; gbc.gridy = 6;
        JLabel specialityLabel = new JLabel("Специальность:");
        add(specialityLabel, gbc);
        JTextField specialityField = new JTextField();
        specialityField.setPreferredSize(new Dimension(300, 30)); // Установка размера
        gbc.gridx = 1; gbc.gridy = 6;
        add(specialityField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        JLabel roomNumberLabel = new JLabel("Номер комнаты:");
        add(roomNumberLabel, gbc);
        JTextField roomNumberField = new JTextField();
        roomNumberField.setPreferredSize(new Dimension(300, 30)); // Установка размера
        gbc.gridx = 1; gbc.gridy = 7;
        add(roomNumberField, gbc);

        // Поля для логина и пароля
        gbc.gridx = 0; gbc.gridy = 8;
        add(new JLabel("Логин:"), gbc);
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 30)); // Установка размера
        gbc.gridx = 1; gbc.gridy = 8;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        add(new JLabel("Пароль:"), gbc);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 30)); // Установка размера
        gbc.gridx = 1; gbc.gridy = 9;
        add(passwordField, gbc);

        // Кнопки
        gbc.gridx = 0; gbc.gridy = 10;
        JButton registerButton = new JButton("Зарегистрироваться");
        add(registerButton, gbc);

        gbc.gridx = 1; gbc.gridy = 10;
        JButton switchToLoginButton = new JButton("Войти");
        add(switchToLoginButton, gbc);

        // Скрываем поля для врача по умолчанию
        toggleFields(roleComboBox, dobSpinner, dobLabel, addressField, addressLabel, phoneField, phoneLabel,
                specialityField, specialityLabel, roomNumberField, roomNumberLabel);

        // Добавляем слушатель для изменения роли
        roleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleFields(roleComboBox, dobSpinner, dobLabel, addressField, addressLabel, phoneField, phoneLabel,
                        specialityField, specialityLabel, roomNumberField, roomNumberLabel);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String dob = dateFormat.format((Date) dobSpinner.getValue()); // Получаем дату из JSpinner
                String address = addressField.getText();
                String phone = phoneField.getText();
                String specialty = specialityField.getText();
                String roomNumber = roomNumberField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();

                // Валидация полей
                StringBuilder errorMessage = new StringBuilder();
                if (firstName.isEmpty()) {
                    errorMessage.append("Имя обязательно.\n");
                }
                if (lastName.isEmpty()) {
                    errorMessage.append("Фамилия обязательна.\n");
                }
                if (username.isEmpty()) {
                    errorMessage.append("Логин обязателен.\n");
                }
                if (password.isEmpty()) {
                    errorMessage.append("Пароль обязателен.\n");
                }

                if ("Пациент".equals(role)) {
                    if (dob == null || dob.isEmpty()) {
                        errorMessage.append("Дата рождения обязательна.\n");
                    }
                    if (address.isEmpty()) {
                        errorMessage.append("Адрес обязателен.\n");
                    }
                    if (phone.isEmpty()) {
                        errorMessage.append("Номер телефона обязателен.\n");
                    }
                } else if ("Врач".equals(role)) {
                    if (specialty.isEmpty()) {
                        errorMessage.append("Специальность обязательна.\n");
                    }
                    if (roomNumber.isEmpty()) {
                        errorMessage.append("Номер комнаты обязателен.\n");
                    }
                }

                // Если есть ошибки, показываем сообщение и выходим
                if (errorMessage.length() > 0) {
                    JOptionPane.showMessageDialog(RegistrationPanel.this, errorMessage.toString(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Создание DTO для регистрации
                UserRegistrationDto registrationDto = new UserRegistrationDto();
                registrationDto.setFirstName(firstName);
                registrationDto.setLastName(lastName);
                registrationDto.setDob(dob);
                registrationDto.setAddress(address);
                registrationDto.setPhone(phone);
                registrationDto.setSpecialty(specialty);
                registrationDto.setRoomNumber(roomNumber);
                registrationDto.setUsername(username);
                registrationDto.setPassword(password);
                registrationDto.setRole(role);

                // Логика для регистрации пользователя
                String response = userApiClient.registerUser (registrationDto);
                if (response != null && response.equals("User  registered successfully!")) {
                    JOptionPane.showMessageDialog(RegistrationPanel.this, "Регистрация успешна!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Login"); // Переключение на панель входа
                } else {
                    JOptionPane.showMessageDialog(RegistrationPanel.this, "Ошибка регистрации: " + response, "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        switchToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });
    }

    private void toggleFields(JComboBox<String> roleComboBox, JSpinner dobSpinner, JLabel dobLabel,
                              JTextField addressField, JLabel addressLabel, JTextField phoneField, JLabel phoneLabel,
                              JTextField specialityField, JLabel specialityLabel, JTextField roomNumberField,
                              JLabel roomNumberLabel) {
        String selectedRole = (String) roleComboBox.getSelectedItem();
        boolean isPatient = "Пациент".equals(selectedRole);



        dobSpinner.setVisible(isPatient);
        addressField.setVisible(isPatient);
        phoneField.setVisible(isPatient);
        specialityField.setVisible(!isPatient);
        roomNumberField.setVisible(!isPatient);
        dobLabel.setVisible(isPatient);
        addressLabel.setVisible(isPatient);
        phoneLabel.setVisible(isPatient);
        specialityLabel.setVisible(!isPatient);
        roomNumberLabel.setVisible(!isPatient);
    }
}
