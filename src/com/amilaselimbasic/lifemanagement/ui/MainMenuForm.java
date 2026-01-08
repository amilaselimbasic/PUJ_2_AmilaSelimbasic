package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.db.UserService;
import com.amilaselimbasic.lifemanagement.financeapp.ui.MainForm;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;

import javax.swing.*;
import java.awt.*;

// Glavni meni nakon logina, odavde korisnik može pristupiti svim modulima
public class MainMenuForm {

    private JPanel mainPanel;     // glavni panel
    private JPanel topPanel;      // panel za naslov i temu
    private JPanel themePanel;    // panel za odabir teme
    private JPanel buttonPanel;   // panel za glavna dugmad

    private JLabel lblMainMenu;   // naslov forme
    private JLabel lblTheme;      // labela za temu

    private JComboBox<String> cmbTheme; // izbor teme
    private JButton btnApplyTheme;      // dugme za primjenu teme

    private JButton btnFinanceApp;      // dugme za Finance App
    private JButton btnStudyPlanner;    // dugme za Study Planner
    private JButton btnHabitTracker;    // dugme za Habit Tracker
    private JButton btnSleepTracker;    // dugme za Sleep Tracker

    private final UserService userService; // servis za korisnike (promjena teme)

    public MainMenuForm() {

        userService = new UserService(); // inicijalizacija servisa

        // kreiranje glavnih panela
        mainPanel = new JPanel(new BorderLayout());
        topPanel = new JPanel(new GridLayout(2, 1));
        themePanel = new JPanel();
        buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2x2 grid za dugmad

        // naslov
        lblMainMenu = new JLabel("Main Menu", SwingConstants.CENTER);
        lblMainMenu.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblMainMenu);

        // odabir teme
        lblTheme = new JLabel("Tema:");
        String[] themes = {"zelena", "plava", "roza", "narandžasta", "dark", "cyberpunk"};
        cmbTheme = new JComboBox<>(themes);
        btnApplyTheme = new JButton("Primijeni");

        themePanel.add(lblTheme);
        themePanel.add(cmbTheme);
        themePanel.add(btnApplyTheme);
        topPanel.add(themePanel);

        // kreiranje dugmadi za module
        btnFinanceApp = new JButton("Finance App");
        btnStudyPlanner = new JButton("Study Planner");
        btnHabitTracker = new JButton("Habit Tracker");
        btnSleepTracker = new JButton("Sleep Tracker");

        buttonPanel.add(btnFinanceApp);
        buttonPanel.add(btnStudyPlanner);
        buttonPanel.add(btnHabitTracker);
        buttonPanel.add(btnSleepTracker);

        // dodavanje panela na glavni panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // postavljanje trenutne teme korisnika
        String currentTheme = "zelena"; // default
        if (CurrentUser.get() != null && CurrentUser.get().getTheme() != null) {
            currentTheme = CurrentUser.get().getTheme();
        }
        cmbTheme.setSelectedItem(currentTheme);
        applyTheme(currentTheme); // odmah primijeni temu

        // akcija za primjenu teme
        btnApplyTheme.addActionListener(e -> {
            String selectedTheme = (String) cmbTheme.getSelectedItem();
            if (selectedTheme != null && CurrentUser.get() != null) {
                // update u bazi i u sesiji
                userService.updateTheme(CurrentUser.getUserId(), selectedTheme);
                CurrentUser.get().setTheme(selectedTheme);
                applyTheme(selectedTheme);

                // obavijest korisniku
                JOptionPane.showMessageDialog(
                        mainPanel,
                        "Tema promijenjena u: " + selectedTheme,
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        // akcije za otvaranje modula
        btnFinanceApp.addActionListener(e -> openFinanceApp());
        btnHabitTracker.addActionListener(e -> openHabitTracker());
        btnSleepTracker.addActionListener(e -> openSleepTracker());
        btnStudyPlanner.addActionListener(e -> openStudyPlanner());
    }

    // metoda za primjenu boja teme
    private void applyTheme(String theme) {
        Color background;
        Color text;

        // jednostavan switch za boje
        switch (theme.toLowerCase()) {
            case "zelena":
                background = new Color(180, 238, 180);
                text = Color.BLACK;
                break;
            case "plava":
                background = new Color(173, 216, 230);
                text = Color.BLACK;
                break;
            case "roza":
                background = new Color(255, 182, 193);
                text = Color.BLACK;
                break;
            case "narandžasta":
                background = new Color(255, 200, 120);
                text = Color.BLACK;
                break;
            case "dark":
                background = Color.DARK_GRAY;
                text = Color.WHITE;
                break;
            case "cyberpunk":
                background = Color.BLACK;
                text = Color.MAGENTA;
                break;
            default:
                background = Color.LIGHT_GRAY;
                text = Color.BLACK;
        }

        // primjena boja na sve panele i elemente
        mainPanel.setBackground(background);
        topPanel.setBackground(background);
        themePanel.setBackground(background);
        buttonPanel.setBackground(background);

        lblMainMenu.setForeground(text);
        lblTheme.setForeground(text);

        btnFinanceApp.setBackground(background);
        btnStudyPlanner.setBackground(background);
        btnHabitTracker.setBackground(background);
        btnSleepTracker.setBackground(background);
        btnApplyTheme.setBackground(background);

        btnFinanceApp.setForeground(text);
        btnStudyPlanner.setForeground(text);
        btnHabitTracker.setForeground(text);
        btnSleepTracker.setForeground(text);
        btnApplyTheme.setForeground(text);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // otvaranje Finance App prozora
    private void openFinanceApp() {
        MainForm financeForm = new MainForm();
        JFrame frame = new JFrame("Finance App");
        frame.setContentPane(financeForm.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // otvaranje Habit Tracker prozora
    private void openHabitTracker() {
        HabitTrackerForm form = new HabitTrackerForm();
        JFrame frame = new JFrame("Habit Tracker");
        frame.setContentPane(form.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // otvaranje Sleep Tracker prozora
    private void openSleepTracker() {
        SleepTrackerForm form = new SleepTrackerForm();
        JFrame frame = new JFrame("Sleep Tracker");
        frame.setContentPane(form.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // otvaranje Study Planner prozora
    private void openStudyPlanner() {
        StudyPlannerForm form = new StudyPlannerForm();
        JFrame frame = new JFrame("Study Planner");
        frame.setContentPane(form.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // getter za glavni panel (potreban za prikaz u JFrame)
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
