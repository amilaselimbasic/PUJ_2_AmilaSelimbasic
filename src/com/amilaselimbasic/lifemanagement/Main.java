package com.amilaselimbasic.lifemanagement;

import com.amilaselimbasic.lifemanagement.ui.LoginForm;
import javax.swing.*;

// Glavna klasa koja pokreće aplikaciju
public class Main {

    public static void main(String[] args) {

        // Swing GUI mora da se pokreće u Event Dispatch Thread-u
        SwingUtilities.invokeLater(() -> {

            // kreiranje login forme
            LoginForm loginForm = new LoginForm();

            // kreiranje glavnog JFrame prozora
            JFrame frame = new JFrame("Login");

            // postavljanje sadržaja forme na glavni panel login forme
            frame.setContentPane(loginForm.getMainPanel());

            // zatvaranje aplikacije kada se zatvori prozor
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // automatsko prilagođavanje veličine prozora prema komponentama
            frame.pack();

            // centriranje prozora na ekranu
            frame.setLocationRelativeTo(null);

            // prikazivanje prozora
            frame.setVisible(true);
        });
    }
}
