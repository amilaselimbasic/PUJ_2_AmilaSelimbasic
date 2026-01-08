package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.db.UserService;
import com.amilaselimbasic.lifemanagement.model.User;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;

import javax.swing.*;

// Forma za login i registraciju korisnika
public class LoginForm {

    private JPanel mainPanel;          // glavni panel forme
    private JTextField txtUsername;    // polje za unos korisničkog imena
    private JPasswordField txtPassword;// polje za unos lozinke
    private JButton btnLogin;          // dugme za login
    private JButton btnRegister;       // dugme za registraciju
    private JLabel lblUsername;        // labela za username
    private JLabel lblPassword;        // labela za password

    private final UserService userService; // servis za rad sa korisnicima

    public LoginForm() {
        userService = new UserService(); // inicijalizacija servisa

        // akcija za login dugme
        btnLogin.addActionListener(e -> login());

        // akcija za register dugme
        btnRegister.addActionListener(e -> register());
    }

    // metoda za login korisnika
    private void login() {
        String username = txtUsername.getText().trim(); // uzimamo tekst iz polja
        String password = new String(txtPassword.getPassword()); // uzimamo lozinku

        // provjera da li su polja prazna
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Unesite username i password",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // provjera da li postoji user sa unesenim podacima
        User user = userService.login(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Pogrešno korisničko ime ili lozinka",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // postavljanje trenutno prijavljenog korisnika
        CurrentUser.set(user);

        // otvaranje glavnog menija nakon uspješnog logina
        MainMenuForm menu = new MainMenuForm();
        JFrame frame = new JFrame("Main Menu");
        frame.setContentPane(menu.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // centriranje prozora
        frame.setVisible(true);

        // zatvaranje login forme
        SwingUtilities.getWindowAncestor(mainPanel).dispose();
    }

    // metoda za registraciju novog korisnika
    private void register() {
        String username = JOptionPane.showInputDialog(mainPanel, "Unesite korisničko ime:");
        if (username == null || username.isEmpty()) return; // provjera praznog inputa

        String password = JOptionPane.showInputDialog(mainPanel, "Unesite lozinku:");
        if (password == null || password.isEmpty()) return; // provjera praznog inputa

        // provjera da li korisnik već postoji
        if (userService.usernameExists(username)) {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Korisnik već postoji!",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // postavljanje default teme za novog korisnika
        String defaultTheme = "zelena";
        userService.insertUser(username, password, defaultTheme); // dodavanje u bazu

        // obavijest korisniku da je registracija uspješna
        JOptionPane.showMessageDialog(
                mainPanel,
                "Registracija uspješna!",
                "Info",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // getter za glavni panel (potreban za prikaz u JFrame)
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
