package Client.Components;

import Client.Api.*;
import Client.CurrentUser ;
import com.example.HospitalInfoSystem.Entities.MedicalRecord;
import com.example.HospitalInfoSystem.Entities.Patient;
import com.example.HospitalInfoSystem.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;

public class MedicalHistoryPanel extends JPanel {

    private MedicalRecordApiClient medicalRecordApiClient;
    private PatientApiClient patientApiClient;
    private UserApiClient userApiClient;
    private PrescriptionApiClient prescriptionApiClient;
    private MedicationApiClient medicationApiClient;

    private JPanel medicalRecordsPanel; // Панель для медицинских записей
    private JScrollPane scrollPane;

    public MedicalHistoryPanel(MedicalRecordApiClient medicalRecordApiClient,
                               PatientApiClient patientApiClient,
                               UserApiClient userApiClient,
                               PrescriptionApiClient prescriptionApiClient,
                               MedicationApiClient medicationApiClient) {
        this.medicalRecordApiClient = medicalRecordApiClient;
        this.patientApiClient = patientApiClient;
        this.userApiClient = userApiClient;
        this.prescriptionApiClient = prescriptionApiClient;
        this.medicationApiClient = medicationApiClient;

        setLayout(new BorderLayout());

        // Получаем текущего пользователя
        CurrentUser  currentUser  = CurrentUser.getInstance();
        Long userId = currentUser.getId();

        User user = userApiClient.getUserById(userId);
        Patient patient = patientApiClient.getPatientById(user.getPatient().getPatientId());

        // Создаем панель для медицинских записей
        medicalRecordsPanel = new JPanel();
        medicalRecordsPanel.setLayout(new BoxLayout(medicalRecordsPanel, BoxLayout.Y_AXIS));

        // Инициализируем список медицинских записей
        updateMedicalRecords(patient.getPatientId());

        // Оборачиваем панель медицинских записей в JScrollPane
        scrollPane = new JScrollPane(medicalRecordsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateMedicalRecords(Long patientId) {
        // Получаем обновленный список медицинских записей
        List<MedicalRecord> medicalRecords = medicalRecordApiClient.getMedicalRecordsByPatientId(patientId);
        medicalRecords.sort(Comparator.comparing(MedicalRecord::getDate));
        medicalRecordsPanel.removeAll();

        // Выводим информацию о каждой медицинской записи
        for (MedicalRecord medicalRecord : medicalRecords) {
            JPanel recordPanel = new JPanel();
            recordPanel.setLayout(new BoxLayout(recordPanel, BoxLayout.Y_AXIS));
            recordPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            recordPanel.setBackground(Color.WHITE);
            recordPanel.setPreferredSize(new Dimension(380, 100));
            recordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            recordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            recordPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JLabel dateLabel = new JLabel("Дата: " + medicalRecord.getDate());
            JLabel diagnosisLabel = new JLabel("Диагноз: " + medicalRecord.getDiagnosis());
            JLabel treatmentLabel = new JLabel("Лечение: " + medicalRecord.getTreatment());

            recordPanel.add(dateLabel);
            recordPanel.add(diagnosisLabel);
            recordPanel.add(treatmentLabel);

            recordPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Открываем диалог с подробной информацией о медицинской записи
                    MedicalRecordDialog medicalRecordDialog = new MedicalRecordDialog(medicalRecord,
                            prescriptionApiClient, medicationApiClient);
                    medicalRecordDialog.setVisible(true);
                }
            });

            medicalRecordsPanel.add(recordPanel);
            medicalRecordsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        medicalRecordsPanel.revalidate();
        medicalRecordsPanel.repaint();
    }
}
