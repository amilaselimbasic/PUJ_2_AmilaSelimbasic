package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.financeapp.ui.MainForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuForm {
    private JPanel mainPanel;
    private JLabel MainMenu;
    private JButton btnFinanceApp;
    private JButton btnStudyPlanner;
    private JButton btnHabitTracker;
    private JButton btnSleepTracker;


    public MainMenuForm () {
        /// Ovo je sa zad samo inf

        btnFinanceApp.addActionListener(e -> {
            MainForm financeForm = new MainForm();

            JFrame frame = new JFrame("Finance App");
            frame.setContentPane(financeForm.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }    );

        btnHabitTracker.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        mainPanel,
                        "modul će biti naknadno implementian",
                        "info",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        btnSleepTracker.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        mainPanel,
                        "modul ću implementirati kasnije",
                        "info",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        btnStudyPlanner.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        mainPanel,
                "modul cu dodati kasnije",
                "info",
                JOptionPane.INFORMATION_MESSAGE
                )
        );
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
