package Client.Components;

import Client.Api.AppointmentApiClient;
import Client.Api.DoctorApiClient;
import Client.Api.UserApiClient;
import Client.CurrentUser;
import com.example.HospitalInfoSystem.Entities.Appointment;
import com.example.HospitalInfoSystem.Entities.Doctor;
import com.example.HospitalInfoSystem.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class AppointmentBookingPanel extends JPanel {

    private DoctorApiClient doctorApiClient;
    private AppointmentApiClient appointmentApiClient;
    private UserApiClient userApiClient;

    public AppointmentBookingPanel(DoctorApiClient doctorApiClient, AppointmentApiClient appointmentApiClient, UserApiClient userApiClient) {
        this.doctorApiClient = doctorApiClient;
        this.appointmentApiClient = appointmentApiClient;
        this.userApiClient = userApiClient;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel specialtyLabel = new JLabel("Выберите специальность:");
        JComboBox<String> specialtyComboBox = new JComboBox<>();
        specialtyComboBox.setPreferredSize(new Dimension(200, 30));

        JLabel doctorLabel = new JLabel("Выберите врача:");
        JComboBox<Doctor> doctorComboBox = new JComboBox<>();
        doctorComboBox.setPreferredSize(new Dimension(200, 30));

        try {
            List<String> specialties = doctorApiClient.getAllSpecialties();
            for (String specialty : specialties) {
                specialtyComboBox.addItem(specialty);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при получении списка специальностей: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

        specialtyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSpecialty = (String) specialtyComboBox.getSelectedItem();
                updateDoctorListBySpecialty(selectedSpecialty, doctorComboBox);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(specialtyLabel, gbc);

        gbc.gridx = 1;
        add(specialtyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(doctorLabel, gbc);

        gbc.gridx = 1;
        add(doctorComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Дата и время:"), gbc);

        JSpinner dateTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateTimeSpinner, "yyyy-MM-dd HH:mm");
        dateTimeSpinner.setEditor(dateEditor);
        dateTimeSpinner.setValue(new Date());

        // Отключаем редактирование текстового поля
        JTextField dateTextField = ((JSpinner.DateEditor) dateTimeSpinner.getEditor()).getTextField();
        dateTextField.setEditable(false);

        gbc.gridx = 1;
        add(dateTimeSpinner, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton bookButton = new JButton("Записаться на приём");
        buttonPanel.add(bookButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();
                Date dateTime = (Date) dateTimeSpinner.getValue();

                if (dateTime.before(new Date())) {
                    JOptionPane.showMessageDialog(AppointmentBookingPanel.this, "Выберите дату и время, которые не являются задним числом.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (selectedDoctor == null) {
                    JOptionPane.showMessageDialog(AppointmentBookingPanel.this, "Пожалуйста, выберите врача.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Appointment appointment = new Appointment();
                    appointment.setDoctor(selectedDoctor);

                    CurrentUser currentUser  = CurrentUser.getInstance();
                    Long userId = currentUser .getId();
                    User user = userApiClient.getUserById(userId);
                    appointment.setPatient(user.getPatient());
                    appointment.setDateTime(dateTime.toString());
                    appointment.setStatus("Назначена");

                    // Вызов API для создания записи
                    appointmentApiClient.createAppointment(appointment);

                    JOptionPane.showMessageDialog(AppointmentBookingPanel.this, "Запись на приём к " + selectedDoctor.getName() + " на " + dateTime + " успешно создана.");

                    specialtyComboBox.setSelectedIndex(-1);
                    doctorComboBox .removeAllItems();
                    dateTimeSpinner.setValue(new Date());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AppointmentBookingPanel.this, "Ошибка при записи на приём: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void updateDoctorListBySpecialty(String specialty, JComboBox<Doctor> doctorComboBox) {
        doctorComboBox.removeAllItems();

        try {
            List<Doctor> doctors = doctorApiClient.getDoctorsBySpecialty(specialty);
            for (Doctor doctor : doctors) {
                doctorComboBox.addItem(doctor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при получении списка врачей: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
