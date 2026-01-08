package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.model.SleepRecord;
import com.amilaselimbasic.lifemanagement.db.SleepRecordService;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Forma za praćenje sna korisnika
public class SleepTrackerForm {

    private JPanel mainPanel;      // glavni panel
    private JTable tblSleep;       // tabela sa zapisima sna
    private JButton btnAdd;        // dugme za dodavanje zapisa
    private JButton btnDelete;     // dugme za brisanje zapisa
    private JButton btnAnalytics;  // dugme za prikaz statistike
    private JButton btnExport;     // dugme za export u TXT
    private JScrollPane scrollPane; // scroll pane za tabelu

    private SleepRecordService sleepRecordService; // servis za rad sa zapisima sna

    public SleepTrackerForm() {
        sleepRecordService = new SleepRecordService(); // inicijalizacija servisa

        initTable();    // priprema tabele
        refreshTable(); // prikaz trenutnih zapisa

        // dugmad
        btnAdd.addActionListener(e -> addRecord());
        btnDelete.addActionListener(e -> deleteRecord());
        btnAnalytics.addActionListener(e -> new SleepAnalyticsFrame().setVisible(true));
        btnExport.addActionListener(e -> exportToTXT()); // export dugme
    }

    // inicijalizacija tabele i kolona
    private void initTable() {
        tblSleep.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Datum", "Sati sna", "ID"} // kolone
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ne može se mijenjati direktno u tabeli
            }
        });

        // sakrivanje ID kolone
        tblSleep.getColumnModel().getColumn(2).setMinWidth(0);
        tblSleep.getColumnModel().getColumn(2).setMaxWidth(0);
        tblSleep.getColumnModel().getColumn(2).setWidth(0);
    }

    // dodavanje novog zapisa
    private void addRecord() {
        String date = JOptionPane.showInputDialog(mainPanel, "Unesite datum");
        if (date == null || date.isEmpty()) return; // ako nije uneseno, prekini

        String hoursStr = JOptionPane.showInputDialog(mainPanel, "Unesite broj sati sna");

        try {
            int hours = Integer.parseInt(hoursStr); // pretvori u broj
            sleepRecordService.insertRecord(date, hours); // dodaj u bazu
            refreshTable(); // osvježi prikaz
        } catch (NumberFormatException ex) {
            // obavijesti korisnika ako nije broj
            JOptionPane.showMessageDialog(mainPanel, "Broj sati mora biti cijeli broj", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    // brisanje odabranog zapisa
    private void deleteRecord() {
        int row = tblSleep.getSelectedRow();
        if (row >= 0) {
            String id = tblSleep.getValueAt(row, 2).toString(); // uzmi ID iz sakrivene kolone
            sleepRecordService.deleteRecord(id); // obriši iz baze
            refreshTable(); // osvježi tabelu
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Odaberite unos", "Upozorenje", JOptionPane.WARNING_MESSAGE);
        }
    }

    // osvježavanje tabele sa svim zapisima
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblSleep.getModel();
        model.setRowCount(0); // isprazni tabelu

        String userId = CurrentUser.getUserId(); // ID trenutno prijavljenog korisnika
        List<SleepRecord> records = sleepRecordService.getAllByUser(userId);

        for (SleepRecord r : records) {
            model.addRow(new Object[]{
                    r.getDate(),
                    r.getHours(),
                    r.getId()
            });
        }
    }

    // export svih zapisa u TXT fajl
    private void exportToTXT() {
        String userId = CurrentUser.getUserId();
        List<SleepRecord> records = sleepRecordService.getAllByUser(userId);

        try (FileWriter writer = new FileWriter("sleep_export.txt")) {
            writer.append("Sleep Tracker Export\n\n");

            for (SleepRecord r : records) {
                writer.append(String.format("%s - %d sati%n", r.getDate(), r.getHours()));
            }

            JOptionPane.showMessageDialog(mainPanel, "Export završen: sleep_export.txt", "Info", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainPanel, "Greška pri exportu!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    // getter za glavni panel (potreban za prikaz u JFrame)
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
