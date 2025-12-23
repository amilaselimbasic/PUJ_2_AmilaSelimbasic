package com.amilaselimbasic.lifemanagement;

import com.amilaselimbasic.lifemanagement.ui.LoginForm;

import javax.swing.*;

public class Main {

    public static void main (String[] args) {

        SwingUtilities.invokeLater(()-> {

            LoginForm loginForm = new LoginForm();

            JFrame frame = new JFrame("Login");
            frame.setContentPane(loginForm.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}