package Client.Components;

import Client.Api.AppointmentApiClient;
import Client.Api.MedicalRecordApiClient;
import Client.Api.MedicationApiClient;
import Client.Api.PrescriptionApiClient;
import com.example.HospitalInfoSystem.Dto.PrescriptionDto;
import com.example.HospitalInfoSystem.Entities.Appointment;
import com.example.HospitalInfoSystem.Entities.MedicalRecord;
import com.example.HospitalInfoSystem.Entities.Medication;
import com.example.HospitalInfoSystem.Entities.Prescription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TreatmentAssignmentDialog extends JDialog {
    private Appointment appointment;
    private AppointmentApiClient appointmentApiClient;
    private MedicationApiClient medicationApiClient;
    private MedicalRecordApiClient medicalRecordApiClient;
    private PrescriptionApiClient prescriptionApiClient;
    private JTextField medicationNameField;
    private JTextField diagnosisField;
    private JTextField treatmentNameField;
    private JTextArea treatmentDescriptionArea;
    private DefaultListModel<PrescriptionDto> prescriptionListModel;
    private JList<PrescriptionDto> prescriptionList;

    public TreatmentAssignmentDialog(Appointment appointment, AppointmentApiClient appointmentApiClient,
                                     MedicationApiClient medicationApiClient,
                                     MedicalRecordApiClient medicalRecordApiClient,
                                     PrescriptionApiClient prescriptionApiClient) {
        this.appointment = appointment;
        this.appointmentApiClient = appointmentApiClient;
        this.medicationApiClient = medicationApiClient;
        this.medicalRecordApiClient = medicalRecordApiClient;
        this.prescriptionApiClient = prescriptionApiClient;

        setTitle("Назначить лечение");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        // Поля для ввода
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        medicationNameField = new JTextField();
        diagnosisField = new JTextField();
        treatmentNameField = new JTextField();
        treatmentDescriptionArea = new JTextArea(8, 20);
        treatmentDescriptionArea.setLineWrap(true);
        treatmentDescriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(treatmentDescriptionArea);

        prescriptionListModel = new DefaultListModel<>();
        prescriptionList = new JList<>(prescriptionListModel);
        prescriptionList.setVisibleRowCount(8);
        JScrollPane prescriptionScrollPane = new JScrollPane(prescriptionList);

        // Добавляем элементы в inputPanel
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Название записи:"), gbc);
        gbc.gridy++;
        inputPanel.add(medicationNameField, gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Диагноз:"), gbc);
        gbc.gridy++;
        inputPanel.add(diagnosisField, gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Название лечения:"), gbc);
        gbc.gridy++;
        inputPanel.add(treatmentNameField, gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Описание:"), gbc);
        gbc.gridy++;
        inputPanel.add(descriptionScrollPane, gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Рецепты:"), gbc);
        gbc.gridy++;
        inputPanel.add(prescriptionScrollPane, gbc);

        JButton addPrescriptionButton = new JButton("Добавить рецепт");
        JButton removePrescriptionButton = new JButton("Удалить рецепт");
        JButton saveButton = new JButton("Сохранить");

        addPrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Открываем диалог для ввода данных рецепта
                PrescriptionDialog prescriptionDialog = new PrescriptionDialog();
                prescriptionDialog.setVisible(true);
                PrescriptionDto prescription = prescriptionDialog.getPrescription();
                if (prescription != null) {
                    prescriptionListModel.addElement(prescription);
                }
            }
        });

        removePrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = prescriptionList.getSelectedIndex();
                if (selectedIndex != -1) {
                    prescriptionListModel.remove(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(TreatmentAssignmentDialog.this, "Пожалуйста, выберите рецепт для удаления.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String medicationName = medicationNameField.getText();
                String diagnosis = diagnosisField.getText();
                String treatmentName = treatmentNameField.getText();
                String treatmentDescription = treatmentDescriptionArea.getText();

                // Создаем медицинскую запись
                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setPatient(appointment.getPatient());
                medicalRecord.setDate(appointment.getDateTime());
                medicalRecord.setDiagnosis(diagnosis);
                medicalRecord.setTreatment(treatmentName);

                // Создаем лечение
                Medication medication = new Medication();
                medication.setName(medicationName);
                medication.setDescription(treatmentDescription);

                List<Prescription> prescriptions = new ArrayList<>();
                for (int i = 0; i < prescriptionListModel.size(); i++) {
                    PrescriptionDto dto = prescriptionListModel.getElementAt(i);
                    Prescription prescription = new Prescription();
                    prescription.setDosage(dto.getDosage());
                    prescription.setDuration(dto.getDuration());
                    prescriptions.add(prescription);
                }

                // Сохраняем медицинскую запись и рецепты
                try {
                    // Сохраняем лечение
                    Medication newMedication = medicationApiClient.createMedication(medication);
                    // Сохраняем медицинскую запись
                    MedicalRecord newMedicalRecord = medicalRecordApiClient.createMedicalRecord(medicalRecord);

                    for (Prescription prescription : prescriptions) {
                        prescription.setMedication(newMedication);
                        prescription.setMedicalRecord(newMedicalRecord);
                        prescriptionApiClient.createPrescription(prescription);
                    }
                    JOptionPane.showMessageDialog(TreatmentAssignmentDialog.this, "Лечение успешно назначено.");
                    dispose(); // Закрываем диалог
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TreatmentAssignmentDialog.this, "Ошибка при назначении лечения: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addPrescriptionButton);
        buttonPanel.add(removePrescriptionButton);
        buttonPanel.add(saveButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
