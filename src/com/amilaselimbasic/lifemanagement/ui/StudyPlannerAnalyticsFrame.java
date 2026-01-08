package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.db.TaskService;
import com.amilaselimbasic.lifemanagement.model.Task;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Prozor koji prikazuje analitiku za Study Planner
public class StudyPlannerAnalyticsFrame extends JFrame {

    public StudyPlannerAnalyticsFrame() {

        // Osnovna podešavanja prozora
        setTitle("Study Planner Analytics"); // naslov prozora
        setSize(400, 220);                    // veličina prozora
        setLocationRelativeTo(null);          // centriramo prozor
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // zatvara samo ovaj prozor

        // Kreiranje servisa za čitanje zadataka iz baze
        TaskService taskService = new TaskService();
        String userId = CurrentUser.getUserId();          // trenutno prijavljeni korisnik
        List<Task> tasks = taskService.getAllByUser(userId); // svi zadaci za usera

        // Statističke varijable
        int totalTasks = tasks.size(); // ukupno zadataka
        int completed = 0;             // broj završenih zadataka

        // Prolazimo kroz sve zadatke i brojimo zavrsene
        for (Task t : tasks) {
            if (t.isDone()) completed++;
        }

        int pending = totalTasks - completed; // broj nezavršenih zadataka
        double completedPercent = totalTasks == 0 ? 0 : ((double) completed / totalTasks) * 100; // procenat završenih

        // Kreiranje panela i rasporeda za prikaz statistike
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10)); // 5 redova, 1 kolona
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // margine

        // Naslov
        JLabel title = new JLabel("Study Planner Analytics", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        // Dodavanje komponenti u panel
        panel.add(title);
        panel.add(new JLabel("Ukupan broj zadataka: " + totalTasks));
        panel.add(new JLabel("Završeni zadaci: " + completed));
        panel.add(new JLabel("Nezavršeni zadaci: " + pending));
        panel.add(new JLabel("Procenat završenih: " + String.format("%.2f", completedPercent) + "%"));

        // Dodavanje panela u frame
        add(panel);
    }
}
