package com.amilaselimbasic.lifemanagement.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuForm {
    private JPanel mainPanel;
    private JLabel MainMenu;
    private JButton btnFinanceApp;


    public MainMenuForm () {
        /// Ovo je sa zad samo inf

        btnFinanceApp.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                JOptionPane.showMessageDialog(
                                                        mainPanel,
                                                        "FinanceApp cu otvoriti kasnije",
                                                        "info",
                                                        JOptionPane.INFORMATION_MESSAGE
                                                );
                                            }
                                        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
