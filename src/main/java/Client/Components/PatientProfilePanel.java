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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PatientProfilePanel extends JPanel {

    private PatientApiClient patientApiClient;
    private UserApiClient userApiClient;

    private JTextField nameField;
    private JTextField surnameField;
    private JSpinner dateOfBirthSpinner;
    private JTextField phoneNumberField;
    private JTextField addressField;

    private boolean isEditing = false;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public PatientProfilePanel(PatientApiClient patientApiClient, UserApiClient userApiClient) {
        this.patientApiClient = patientApiClient;
        this.userApiClient = userApiClient;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Получаем текущего пользователя
        CurrentUser  currentUser  = CurrentUser .getInstance();
        Long userId = currentUser .getId();

        User user = userApiClient.getUserById(userId);
        Patient patient = patientApiClient.getPatientById(user.getPatient().getPatientId());

        // Имя
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Имя:"), gbc);
        nameField = new JTextField(patient.getName());
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(nameField, gbc);

        // Фамилия
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Фамилия:"), gbc);
        surnameField = new JTextField(patient.getSurname());
        surnameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(surnameField, gbc);

        // Дата рождения
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Дата рождения:"), gbc);
        try {
            Date dateOfBirth = dateFormat.parse(patient.getDateOfBirth());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOfBirth);
            dateOfBirthSpinner = new JSpinner(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH));
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateOfBirthSpinner, "yyyy-MM-dd");
            dateOfBirthSpinner.setEditor(dateEditor);

            // Получаем текстовое поле и отключаем редактирование
            JTextField dateTextField = ((JSpinner.DateEditor) dateOfBirthSpinner.getEditor()).getTextField();
            dateTextField.setEditable(false); // Запретить редактирование
        } catch (ParseException e) {
            e.printStackTrace();
            dateOfBirthSpinner = new JSpinner();
        }
        dateOfBirthSpinner.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(dateOfBirthSpinner, gbc);

        // Контакты
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Контакты:"), gbc);
        phoneNumberField = new JTextField(patient.getPhoneNumber());
        phoneNumberField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(phoneNumberField, gbc);

        // Адрес
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Адрес:"), gbc);
        addressField = new JTextField(patient.getAddress());
        addressField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        add(addressField, gbc);

        // Кнопка "Редактировать"
        gbc.gridx = 0;
        gbc.gridy = 5;
        JButton editButton = new JButton("Изменить");
        editButton.setPreferredSize(new Dimension(100, 30));
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEditing = !isEditing;
                setEditable(isEditing);
                editButton.setText(isEditing ? "Отменить" : "Редактировать");
            }
        });
        add(editButton, gbc);

        // Кнопка "Сохранить"
        gbc.gridx = 1;
        JButton saveButton = new JButton("Сохранить");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isEditing) {
                    Patient updatedPatient = new Patient();
                    updatedPatient.setName(nameField.getText());
                    updatedPatient.setSurname(surnameField.getText());
                    updatedPatient.setDateOfBirth(dateFormat.format((Date) dateOfBirthSpinner.getValue()));
                    updatedPatient.setPhoneNumber(phoneNumberField.getText());
                    updatedPatient.setAddress(addressField.getText());

                    patientApiClient.updatePatient(user.getPatient().getPatientId(), updatedPatient);
                    JOptionPane.showMessageDialog(PatientProfilePanel.this, "Данные успешно обновлены!");
                    isEditing = false;
                    setEditable(false);
                    editButton.setText("Редактировать");
                }
            }
        });
        add(saveButton, gbc);

        setEditable(false);
    }

    private void setEditable(boolean editable) {
        nameField.setEditable(editable);
        surnameField.setEditable(editable);
        dateOfBirthSpinner.setEnabled(editable);
        phoneNumberField.setEditable(editable);
        addressField.setEditable(editable);
    }
}
