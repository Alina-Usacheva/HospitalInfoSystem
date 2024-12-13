package Client.Components;

import Client.Api.*;
import Client.CurrentUser ;
import com.example.HospitalInfoSystem.Entities.Appointment;
import com.example.HospitalInfoSystem.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AppointmentDoctorManagementPanel extends JPanel {

    private AppointmentApiClient appointmentApiClient;
    private MedicationApiClient medicationApiClient;
    private MedicalRecordApiClient medicalRecordApiClient;
    private PrescriptionApiClient prescriptionApiClient;
    private UserApiClient userApiClient;

    private JTable appointmentTable;
    private AppointmentTableModel appointmentTableModel;

    public AppointmentDoctorManagementPanel(AppointmentApiClient appointmentApiClient, MedicationApiClient medicationApiClient, MedicalRecordApiClient medicalRecordApiClient, PrescriptionApiClient prescriptionApiClient, UserApiClient userApiClient) {
        this.appointmentApiClient = appointmentApiClient;
        this.medicationApiClient = medicationApiClient;
        this.medicalRecordApiClient = medicalRecordApiClient;
        this.prescriptionApiClient = prescriptionApiClient;
        this.userApiClient = userApiClient;

        setLayout(new BorderLayout());

        // Заголовок
        JLabel titleLabel = new JLabel("Управление записями на прием", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Таблица для отображения записей
        appointmentTableModel = new AppointmentTableModel();
        appointmentTable = new JTable(appointmentTableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Панель для кнопок
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Обновить");
        JButton updateStatusButton = new JButton("Изменить статус");
        JButton assignTreatmentButton = new JButton("Назначить лечение");

        buttonPanel.add(refreshButton);
        buttonPanel.add(updateStatusButton);
        buttonPanel.add(assignTreatmentButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Обработчик кнопки назначения лечения
        assignTreatmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Appointment selectedAppointment = appointmentTableModel.getAppointmentAt(selectedRow);
                    new TreatmentAssignmentDialog(selectedAppointment, appointmentApiClient, medicationApiClient, medicalRecordApiClient, prescriptionApiClient).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(AppointmentDoctorManagementPanel.this, "Пожалуйста, выберите запись для назначения лечения.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        buttonPanel.add(refreshButton);
        buttonPanel.add(updateStatusButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Обработчик кнопки обновления
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAppointments();
            }
        });

        // Обработчик кнопки изменения статуса
        updateStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Appointment selectedAppointment = appointmentTableModel.getAppointmentAt(selectedRow);
                    String newStatus = (String) JOptionPane.showInputDialog(
                            AppointmentDoctorManagementPanel.this,
                            "Выберите новый статус:",
                            "Изменить статус",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"Назначена", "В процессе", "Закрыта"},
                            selectedAppointment.getStatus()
                    );

                    if (newStatus != null) {
                        selectedAppointment.setStatus(newStatus);
                        try {
                            appointmentApiClient.updateAppointment(selectedAppointment.getAppointmentId(), selectedAppointment);
                            JOptionPane.showMessageDialog(AppointmentDoctorManagementPanel.this, "Статус успешно изменен.");
                            loadAppointments(); // Обновляем список записей
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(AppointmentDoctorManagementPanel.this, "Ошибка при изменении статуса: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(AppointmentDoctorManagementPanel.this, "Пожалуйста, выберите запись для изменения статуса.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Загружаем записи при инициализации
        loadAppointments();
    }

    private void loadAppointments() {
        try {
            CurrentUser  currentUser = CurrentUser.getInstance();
            Long userId = currentUser.getId();

            User user = userApiClient.getUserById(userId);

            List<Appointment> appointments = appointmentApiClient.getAppointmentsByDoctorId(user.getDoctor().getDoctorId());
            appointmentTableModel.setAppointments(appointments);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка при загрузке записей: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
