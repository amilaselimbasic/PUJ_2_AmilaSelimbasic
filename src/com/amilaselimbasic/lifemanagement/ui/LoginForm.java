package com.amilaselimbasic.lifemanagement.ui;

import javax.swing.*;

public class LoginForm {

    private JPanel mainPanel;
    private JTextField txtUsername;
    private JLabel Username;
    private JLabel Password;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginForm () {

        btnLogin.addActionListener(e -> {
            //ZA SAD SAMO DA SE OTVORI MAIN MENU
            MainMenuForm menu = new MainMenuForm ();

            JFrame frame = new JFrame("MainMenu");
            frame.setContentPane(menu.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            //ovdje zatvaram login
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
        });

        btnRegister.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Registracija ce biti dodana kasnije",
                    "info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    public JPanel getMainPanel () {
        return mainPanel;
    }
}
