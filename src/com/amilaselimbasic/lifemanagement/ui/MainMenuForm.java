package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.financeapp.ui.MainForm;

import javax.swing.*;

public class MainMenuForm {

    private JPanel mainPanel;
    private JLabel MainMenu;
    private JButton btnFinanceApp;
    private JButton btnStudyPlanner;
    private JButton btnHabitTracker;
    private JButton btnSleepTracker;

    public MainMenuForm() {

        btnFinanceApp.addActionListener(e -> {
            MainForm financeForm = new MainForm();

            JFrame frame = new JFrame("Finance App");
            frame.setContentPane(financeForm.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        btnHabitTracker.addActionListener(e -> {
            HabitTrackerForm form=new HabitTrackerForm();
            JFrame frame = new JFrame("Habit Tracker");
            frame.setContentPane(form.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
                }
        );

        btnSleepTracker.addActionListener(e -> {
            SleepTrackerForm form = new SleepTrackerForm();
            JFrame frame = new JFrame("Sleep Tracker");
            frame.setContentPane(form.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
                }
        );

        btnStudyPlanner.addActionListener(e -> {
               StudyPlannerForm form = new StudyPlannerForm ();
        JFrame frame = new JFrame("Study Planner");
        frame.setContentPane(form.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
        );
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
