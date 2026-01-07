package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.db.UserService;
import com.amilaselimbasic.lifemanagement.model.User;

import javax.swing.*;
import java.util.List;

public class LoginForm {

    private JPanel mainPanel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JLabel lblUsername;
    private JLabel lblPassword;

    private final UserService userService;

    public LoginForm() {
        // inicijalizacija servisa koji komunicira sa MongoDB
        userService = new UserService();

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> register());
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        User user = userService.findUser(username, password);

        if (user != null) {
            // Otvaranje glavnog menija
            MainMenuForm menu = new MainMenuForm();
            JFrame frame = new JFrame("Main Menu");
            frame.setContentPane(menu.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // zatvaranje login prozora
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
        } else {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Neispravno korisničko ime ili lozinka",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void register() {
        String username = JOptionPane.showInputDialog(mainPanel, "Unesite korisničko ime:");
        String password = JOptionPane.showInputDialog(mainPanel, "Unesite lozinku:");
        String role = "user"; // default role

        if (username != null && password != null &&
                !username.isEmpty() && !password.isEmpty()) {

            // provjera da li korisnik već postoji
            User existingUser = userService.findUser(username, password);
            if (existingUser != null) {
                JOptionPane.showMessageDialog(mainPanel,
                        "Korisnik već postoji!",
                        "Greška",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // unos novog korisnika u MongoDB
            String id = userService.insertUser(username, password, role);

            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Registracija uspješna! ID korisnika: " + id,
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

