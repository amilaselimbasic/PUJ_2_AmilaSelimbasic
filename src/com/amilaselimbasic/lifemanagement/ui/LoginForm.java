package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.model.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LoginForm {

    private JPanel mainPanel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel Username;
    private JLabel Password;

    // lista korisnika u memoriji
    private static List<User> users = new ArrayList<>();

    public LoginForm() {
        // dodajemo jednog početnog korisnika
        users.add(new User("user1", "1234"));

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            User found = users.stream()
                    .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);

            if (found != null) {
                // otvori glavni meni
                MainMenuForm menu = new MainMenuForm();
                JFrame frame = new JFrame("Main Menu");
                frame.setContentPane(menu.getMainPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // zatvori login prozor
                SwingUtilities.getWindowAncestor(mainPanel).dispose();
            } else {
                JOptionPane.showMessageDialog(mainPanel,
                        "Neispravno korisničko ime ili lozinka",
                        "Greška",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegister.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(mainPanel, "Unesite korisničko ime:");
            String password = JOptionPane.showInputDialog(mainPanel, "Unesite lozinku:");

            if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
                users.add(new User(username, password));
                JOptionPane.showMessageDialog(mainPanel, "Registracija uspješna!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
