package Client.Components;

import com.example.HospitalInfoSystem.Dto.PrescriptionDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrescriptionDialog extends JDialog {
    private JTextField nameField;
    private JTextField dosageField;
    private JTextField durationField;
    private PrescriptionDto prescription;

    public PrescriptionDialog() {
        setTitle("Добавить рецепт");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new GridLayout(0, 1));

        nameField = new JTextField();
        dosageField = new JTextField();
        durationField = new JTextField();

        add(new JLabel("Название:"));
        add(nameField);
        add(new JLabel("Дозировка:"));
        add(dosageField);
        add(new JLabel("Длительность:"));
        add(durationField);

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String dosage = dosageField.getText();
                String duration = durationField.getText();
                if (!name.isEmpty() && !dosage.isEmpty() && !duration.isEmpty()) {
                    prescription = new PrescriptionDto(name, dosage, duration);
                    dispose(); // Закрываем диалог
                } else {
                    JOptionPane.showMessageDialog(PrescriptionDialog.this, "Пожалуйста, заполните все поля.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        add(saveButton);
    }

    public PrescriptionDto getPrescription() {
        return prescription;
    }
}
