package Client.Components;

import Client.Api.AppointmentApiClient;
import Client.Api.PatientApiClient;
import Client.Api.UserApiClient;
import Client.CurrentUser ;
import com.example.HospitalInfoSystem.Entities.Appointment;
import com.example.HospitalInfoSystem.Entities.Patient;
import com.example.HospitalInfoSystem.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class AppointmentPatientManagementPanel extends JPanel {

    private AppointmentApiClient appointmentApiClient;
    private PatientApiClient patientApiClient;
    private UserApiClient userApiClient;
    private JPanel appointmentsPanel;
    private JScrollPane scrollPane;

    public AppointmentPatientManagementPanel(AppointmentApiClient appointmentApiClient, PatientApiClient patientApiClient, UserApiClient userApiClient) {
        this.appointmentApiClient = appointmentApiClient;
        this.patientApiClient = patientApiClient;
        this.userApiClient = userApiClient;

        setLayout(new BorderLayout());

        // Получаем текущего пользователя
        CurrentUser  currentUser  = CurrentUser .getInstance();
        Long userId = currentUser .getId();

        User user = userApiClient.getUserById(userId);
        Patient patient = patientApiClient.getPatientById(user.getPatient().getPatientId());

        // Создаем панель для встреч
        appointmentsPanel = new JPanel();
        appointmentsPanel.setLayout(new BoxLayout(appointmentsPanel, BoxLayout.Y_AXIS));

        // Инициализируем список встреч
        updateAppointments(user.getPatient().getPatientId());

        // Оборачиваем панель встреч в JScrollPane
        scrollPane = new JScrollPane(appointmentsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Создаем кнопку "Обновить"
        JButton refreshButton = new JButton("Обновить");
        refreshButton.addActionListener(e -> updateAppointments(user.getPatient().getPatientId())); // Обработчик события

        // Добавляем кнопку в нижнюю часть панели
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateAppointments(Long patientId) {
        // Получаем обновленный список встреч
        List<Appointment> appointments = appointmentApiClient.getAppointmentsByPatientId(patientId);

        // Сортируем встречи по дате
        appointments.sort(Comparator.comparing(Appointment::getDateTime));

        // Очищаем текущую панель встреч
        appointmentsPanel.removeAll();

        // Выводим информацию о каждой встрече
        for (Appointment appointment : appointments) {
            JPanel appointmentPanel = new JPanel();
            appointmentPanel.setLayout(new BoxLayout(appointmentPanel, BoxLayout.Y_AXIS));
            appointmentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            appointmentPanel.setBackground(Color.WHITE);
            appointmentPanel.setPreferredSize(new Dimension(380, 100));
            appointmentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            appointmentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel statusLabel = new JLabel(appointment.getStatus());
            statusLabel.setFont(new Font(statusLabel.getFont().getName(), Font.BOLD, 16));
            if (appointment.getStatus() != null) {
                if (appointment.getStatus().equalsIgnoreCase("Назначена")) {
                    statusLabel.setForeground(new Color(0, 128, 0));
                }
                else if (appointment.getStatus().equalsIgnoreCase("В процессе")) {
                    statusLabel.setForeground(Color.BLUE);
                } else {
                    statusLabel.setForeground(Color.RED);
                }
            }

            appointmentPanel.add(statusLabel);
            JLabel doctorLabel = new JLabel("Доктор: " + appointment.getDoctor().getName() + " " + appointment.getDoctor().getSurname());
            appointmentPanel.add(doctorLabel);
            JLabel specialtyLabel = new JLabel("Специальность: " + appointment.getDoctor().getSpecialty());
            appointmentPanel.add(specialtyLabel);
            JLabel roomLabel = new JLabel("Комната: " + appointment.getDoctor().getRoomNumber());
            appointmentPanel.add(roomLabel);
            JLabel dateLabel = new JLabel("Дата: " + appointment.getDateTime());
            appointmentPanel.add(dateLabel);

            appointmentsPanel.add(appointmentPanel);
            appointmentsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Обновляем интерфейс
        appointmentsPanel.revalidate();
        appointmentsPanel.repaint();
    }
}
