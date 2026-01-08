package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.db.HabitService;
import com.amilaselimbasic.lifemanagement.model.Habit;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Prozor koji prikazuje jednostavnu analitiku za Habit Tracker
public class HabitAnalyticsFrame extends JFrame {

    private JLabel lblTotal;      // labela za ukupan broj navika
    private JLabel lblCompleted;  // labela za broj završenih navika
    private JLabel lblPercentage; // labela za procenat završetka

    private HabitService habitService; // servis za rad sa Habit podacima

    public HabitAnalyticsFrame() {

        habitService = new HabitService(); // kreiramo servis

        // Osnovna podešavanja prozora
        setTitle("Habit Analytics");
        setSize(350, 200);
        setLocationRelativeTo(null); // centriramo prozor
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // zatvara samo ovaj frame

        // Kreiranje panela i rasporeda
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 reda, 1 kolona, razmak 10px
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // margine

        // Naslov prozora unutar panela
        JLabel title = new JLabel("Habit Analytics", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        // Inicijalizacija labela za prikaz statistike
        lblTotal = new JLabel();
        lblCompleted = new JLabel();
        lblPercentage = new JLabel();

        // Dodavanje komponenti u panel
        panel.add(title);
        panel.add(lblTotal);
        panel.add(lblCompleted);
        panel.add(lblPercentage);

        // Dodavanje panela u frame
        add(panel);

        // Učitavanje i prikaz analitike
        loadAnalytics();
    }

    // Metoda koja izračunava i prikazuje statistiku navika
    private void loadAnalytics() {

        List<Habit> habits = habitService.getAllByUser(); // uzimamo sve navike za trenutno prijavljenog korisnika

        int total = habits.size();  // ukupan broj navika
        int completed = 0;          // broj završenih navika

        // Prolazimo kroz sve navike i brojimo završene
        for (Habit h : habits) {
            if (h.isDone()) {
                completed++;
            }
        }

        // Izračunavamo procenat završenih
        double percentage = total == 0 ? 0 : (completed * 100.0 / total);

        // Postavljamo tekst labela
        lblTotal.setText("Ukupan broj navika: " + total);
        lblCompleted.setText("Završene navike: " + completed);
        lblPercentage.setText(String.format("Uspješnost: %.2f %%", percentage));
    }
}
