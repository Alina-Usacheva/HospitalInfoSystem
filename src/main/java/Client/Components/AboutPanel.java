package Client.Components;

import javax.swing.*;
import java.awt.*;

public class AboutPanel extends JPanel {

    public AboutPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Отступы между элементами
        gbc.anchor = GridBagConstraints.CENTER; // Центрирование элементов

        setBackground(new Color(240, 240, 240));

        JLabel nameLabel = createLabel("ФИО:", "Усачева Алина Константиновна");
        JLabel universityLabel = createLabel("Университет:", "Финансовый университет при правительстве РФ");
        JLabel groupLabel = createLabel("Группа:", "ПИ22-4");
        JLabel projectLabel = createLabel("Проект:", "Информационно-справочная система поликлиники");
        JLabel supervisorLabel = createLabel("Научный руководитель:", "Е. П. Догадина");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);
        gbc.gridy++;
        add(universityLabel, gbc);
        gbc.gridy++;
        add(groupLabel, gbc);
        gbc.gridy++;
        add(projectLabel, gbc);
        gbc.gridy++;
        add(supervisorLabel, gbc);

        setBorder(BorderFactory.createTitledBorder("Об авторе"));
    }

    private JLabel createLabel(String title, String content) {
        JLabel label = new JLabel("<html><b>" + title + "</b> " + content + "</html>");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(50, 50, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER); // Центрирование текста
        return label;
    }
}