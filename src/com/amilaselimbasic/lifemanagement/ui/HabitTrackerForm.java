package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.db.HabitService;
import com.amilaselimbasic.lifemanagement.model.Habit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Glavni prozor za praćenje navika
public class HabitTrackerForm {

    private JTable tblHabits;           // tabela u kojoj prikazujemo navike
    private JButton btnAddHabit;        // dugme za dodavanje nove navike
    private JButton btnDeleteHabit;     // dugme za brisanje odabrane navike
    private JButton btnMarkDone;        // dugme za označavanje navike kao završene
    private JButton btnAnalytics;       // dugme za otvaranje analitike
    private JButton btnExport;          // dugme za eksport u TXT fajl
    private JPanel mainPanel;           // glavni panel
    private JScrollPane scrollPane;     // scroll panel za tabelu

    private HabitService habitService;  // servis za rad sa bazom navika

    public HabitTrackerForm() {
        habitService = new HabitService(); // inicijalizacija servisa

        initTable();   // inicijalizacija tabele
        refreshTable(); // popunjavanje tabele podacima

        // dodavanje akcija za dugmad
        btnAddHabit.addActionListener(e -> addHabit());
        btnDeleteHabit.addActionListener(e -> deleteHabit());
        btnMarkDone.addActionListener(e -> markHabitDone());
        btnAnalytics.addActionListener(e -> {
            new HabitAnalyticsFrame().setVisible(true); // otvara novi prozor sa statistikama
        });
        btnExport.addActionListener(e -> exportToTXT()); // eksport u TXT
    }

    // metoda koja inicijalizuje tabelu i postavlja kolone
    private void initTable() {
        tblHabits.setModel(new DefaultTableModel(
                new Object[][]{}, // pocetno prazna tabela
                new String[]{"Navika", "Status", "ID"} // kolone
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // zabranjujemo editovanje direktno u tabeli
            }
        });

        // sakrivamo ID kolonu jer je samo za internu upotrebu
        tblHabits.getColumnModel().getColumn(2).setMinWidth(0);
        tblHabits.getColumnModel().getColumn(2).setMaxWidth(0);
        tblHabits.getColumnModel().getColumn(2).setWidth(0);
    }

    // metoda za dodavanje nove navike
    private void addHabit() {
        String name = JOptionPane.showInputDialog(mainPanel, "Unesite naziv navike");

        if (name != null && !name.isEmpty()) {
            habitService.insertHabit(name); // dodavanje u bazu
            refreshTable(); // osvježavamo tabelu
        }
    }

    // metoda za brisanje odabrane navike
    private void deleteHabit() {
        int row = tblHabits.getSelectedRow();

        if (row >= 0) {
            String id = tblHabits.getValueAt(row, 2).toString(); // uzimamo ID
            habitService.deleteHabit(id); // brisanje iz baze
            refreshTable(); // osvježavanje tabele
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Odaberite naviku", "Upozorenje", JOptionPane.WARNING_MESSAGE);
        }
    }

    // metoda za označavanje navike kao završenog
    private void markHabitDone() {
        int row = tblHabits.getSelectedRow();

        if (row >= 0) {
            String id = tblHabits.getValueAt(row, 2).toString(); // uzimamo ID
            habitService.markDone(id); // update baze
            refreshTable(); // refresh tabele
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Odaberite naviku", "Upozorenje", JOptionPane.WARNING_MESSAGE);
        }
    }

    // metoda za osvježavanje tabele sa podacima iz baze
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblHabits.getModel();
        model.setRowCount(0); // praznimo tabelu

        List<Habit> habits = habitService.getAllByUser(); // uzimamo sve navike za ulogovanog usera

        // dodajemo redove u tabelu
        for (Habit h : habits) {
            model.addRow(new Object[]{
                    h.getName(),
                    h.isDone() ? "Završeno" : "Nije završeno",
                    h.getId()
            });
        }
    }

    // metoda za eksport svih navika u TXT fajl
    private void exportToTXT() {
        List<Habit> habits = habitService.getAllByUser();

        try (FileWriter writer = new FileWriter("habits_export.txt")) {

            writer.append("Habit Tracker Export\n\n"); // naslov u fajlu

            // prolazimo kroz sve navike i zapisujemo ih
            for (Habit h : habits) {
                writer.append(String.format("%s - %s%n", h.getName(), h.isDone() ? "Završeno" : "Nije završeno"));
            }

            // poruka korisniku da je export uspješan
            JOptionPane.showMessageDialog(mainPanel, "Export završen: habits_export.txt", "Info", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            // poruka u slučaju greške
            JOptionPane.showMessageDialog(mainPanel, "Greška pri exportu!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    // getter za glavni panel (potreban za prikaz u drugim frame-ovima)
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
