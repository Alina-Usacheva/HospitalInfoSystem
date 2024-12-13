package Client.Components;

import com.example.HospitalInfoSystem.Entities.MedicalRecord;
import com.example.HospitalInfoSystem.Entities.Prescription;
import com.example.HospitalInfoSystem.Entities.Medication;
import Client.Api.PrescriptionApiClient;
import Client.Api.MedicationApiClient;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MedicalRecordDialog extends JDialog {
    private MedicalRecord medicalRecord;
    private PrescriptionApiClient prescriptionApiClient;
    private MedicationApiClient medicationApiClient;

    public MedicalRecordDialog(MedicalRecord medicalRecord, PrescriptionApiClient prescriptionApiClient, MedicationApiClient medicationApiClient) {
        this.medicalRecord = medicalRecord;
        this.prescriptionApiClient = prescriptionApiClient;
        this.medicationApiClient = medicationApiClient;

        setTitle("Подробная информация о медицинской записи");
        setModal(true);
        setSize(450, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Заголовок с информацией о медицинской записи
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.add(new JLabel("Дата: " + medicalRecord.getDate()));
        headerPanel.add(new JLabel("Диагноз: " + medicalRecord.getDiagnosis()));
        headerPanel.add(new JLabel("Лечение: " + medicalRecord.getTreatment()));
        add(headerPanel, BorderLayout.NORTH);

        // Панель для рецептов
        JPanel prescriptionsPanel = new JPanel();
        prescriptionsPanel.setLayout(new BoxLayout(prescriptionsPanel, BoxLayout.Y_AXIS));
        List<Prescription> prescriptions = prescriptionApiClient.getPrescriptionsByMedicalRecordId(medicalRecord.getMedicalRecordId());

        for (Prescription prescription : prescriptions) {
            Medication medication = medicationApiClient.getMedicationById(prescription.getMedication().getMedicationId());
            JPanel prescriptionPanel = new JPanel();
            prescriptionPanel.setLayout(new BoxLayout(prescriptionPanel, BoxLayout.Y_AXIS));
            prescriptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            prescriptionPanel.setBackground(Color.WHITE);
            prescriptionPanel.setPreferredSize(new Dimension(380, 100));
            prescriptionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            JLabel medicationLabel = new JLabel("Название: " + medication.getName());
            JLabel descriptionLabel = new JLabel("Описание: " + medication.getDescription());
            JLabel dosageLabel = new JLabel("Дозировка: " + prescription.getDosage());
            JLabel durationLabel = new JLabel("Продолжительность: " + prescription.getDuration());

            prescriptionPanel.add(medicationLabel);
            prescriptionPanel.add(descriptionLabel);
            prescriptionPanel.add(dosageLabel);
            prescriptionPanel.add(durationLabel);

            prescriptionsPanel.add(prescriptionPanel);
            prescriptionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        add(new JScrollPane(prescriptionsPanel), BorderLayout.CENTER);

        // Кнопка закрытия
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}
