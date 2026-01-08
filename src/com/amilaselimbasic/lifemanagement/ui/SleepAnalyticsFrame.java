package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.db.SleepRecordService;
import com.amilaselimbasic.lifemanagement.model.SleepRecord;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Prozor koji prikazuje analitiku za Sleep Tracker
public class SleepAnalyticsFrame extends JFrame {

    public SleepAnalyticsFrame() {

        // Osnovna podešavanja prozora
        setTitle("Sleep Analytics"); // naslov prozora
        setSize(400, 220);           // veličina prozora
        setLocationRelativeTo(null);  // centriramo prozor
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // zatvara samo ovaj prozor

        // Kreiranje servisa za čitanje podataka iz baze
        SleepRecordService service = new SleepRecordService();
        String userId = CurrentUser.getUserId(); // trenutno prijavljeni korisnik
        List<SleepRecord> records = service.getAllByUser(userId); // uzimamo sve zapise spavanja za usera

        // Statističke varijable
        int totalRecords = records.size(); // broj zapisa
        int totalHours = 0;                // ukupni sati sna
        int minHours = Integer.MAX_VALUE;  // minimalni sati sna
        int maxHours = 0;                  // maksimalni sati sna

        // Prolazimo kroz sve zapise i računamo statistiku
        for (SleepRecord r : records) {
            int hours = r.getHours();
            totalHours += hours;                  // sabiranje ukupnih sati
            minHours = Math.min(minHours, hours); // pronalazak minimuma
            maxHours = Math.max(maxHours, hours); // pronalazak maksimuma
        }

        // Prosječni sati sna
        double avg = totalRecords == 0 ? 0 : (double) totalHours / totalRecords;
        minHours = totalRecords == 0 ? 0 : minHours; // ako nema zapisa, minimum je 0

        // Kreiranje panela i rasporeda za prikaz statistike
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10)); // 5 redova, 1 kolona
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // margine

        // Naslov
        JLabel title = new JLabel("Sleep Analytics", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        // Dodavanje komponenti u panel
        panel.add(title);
        panel.add(new JLabel("Ukupan broj zapisa: " + totalRecords));
        panel.add(new JLabel("Prosječno spavanje: " + String.format("%.2f", avg) + " h"));
        panel.add(new JLabel("Minimum sati sna: " + minHours + " h"));
        panel.add(new JLabel("Maksimum sati sna: " + maxHours + " h"));

        // Dodavanje panela u frame
        add(panel);
    }
}
