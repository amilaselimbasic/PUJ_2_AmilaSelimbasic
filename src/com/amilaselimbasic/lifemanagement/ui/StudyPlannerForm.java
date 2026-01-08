package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.db.TaskService;
import com.amilaselimbasic.lifemanagement.model.Task;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Forma za upravljanje zadacima korisnika (Study Planner)
public class StudyPlannerForm {

    private JPanel mainPanel;      // glavni panel forme
    private JPanel btnPanel;       // panel za dugmad
    private JTable table1;         // tabela sa zadacima
    private JButton btnAddTask;    // dugme za dodavanje zadatka
    private JButton btnDeleteTask; // dugme za brisanje zadatka
    private JButton btnMarkDone;   // dugme za označavanje zadatka kao završenog
    private JButton btnAnalytics;  // dugme za prikaz statistike zadataka
    private JButton btnExport;     // dugme za export u TXT fajl
    private JScrollPane scrollPane; // scroll pane za tabelu

    private TaskService taskService; // servis za rad sa zadacima u MongoDB

    public StudyPlannerForm() {

        taskService = new TaskService(); // inicijalizacija servisa

        initTable();    // priprema tabele
        refreshTable(); // prikaz trenutnih zadataka

        // eventi za dugmad
        btnAddTask.addActionListener(e -> addTask());
        btnDeleteTask.addActionListener(e -> deleteTask());
        btnMarkDone.addActionListener(e -> markTaskDone());
        btnAnalytics.addActionListener(e -> new StudyPlannerAnalyticsFrame().setVisible(true));
        btnExport.addActionListener(e -> exportToTXT()); // export dugme
    }

    // inicijalizacija tabele i kolona
    private void initTable() {
        table1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Zadatak", "Status", "ID"} // kolone
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ćelije se ne mogu uređivati direktno
            }
        });

        // sakrivanje ID kolone
        table1.getColumnModel().getColumn(2).setMinWidth(0);
        table1.getColumnModel().getColumn(2).setMaxWidth(0);
        table1.getColumnModel().getColumn(2).setWidth(0);
    }

    // dodavanje novog zadatka
    private void addTask() {
        String name = JOptionPane.showInputDialog(mainPanel, "Unesite naziv zadatka");
        if (name != null && !name.isEmpty()) {
            taskService.insertTask(name); // dodaj u bazu
            refreshTable();              // osvježi prikaz
        }
    }

    // brisanje odabranog zadatka
    private void deleteTask() {
        int row = table1.getSelectedRow();
        if (row >= 0) {
            String id = table1.getValueAt(row, 2).toString(); // uzmi ID iz sakrivene kolone
            taskService.deleteTask(id); // obriši iz baze
            refreshTable();             // osvježi tabelu
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Odaberite zadatak", "Upozorenje", JOptionPane.WARNING_MESSAGE);
        }
    }

    // označavanje zadatka kao završenog
    private void markTaskDone() {
        int row = table1.getSelectedRow();
        if (row >= 0) {
            String id = table1.getValueAt(row, 2).toString();
            taskService.updateTaskDone(id, true); // update statusa u bazi
            refreshTable();                        // osvježi tabelu
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Odaberite zadatak", "Upozorenje", JOptionPane.WARNING_MESSAGE);
        }
    }

    // osvježavanje tabele sa svim zadacima korisnika
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // isprazni tabelu

        String userId = CurrentUser.getUserId(); // ID trenutno prijavljenog korisnika
        List<Task> tasks = taskService.getAllByUser(userId);

        for (Task t : tasks) {
            model.addRow(new Object[]{
                    t.getName(),
                    t.isDone() ? "Završeno" : "Nije završeno",
                    t.getId()
            });
        }
    }

    // export svih zadataka u TXT fajl
    private void exportToTXT() {
        String userId = CurrentUser.getUserId();
        List<Task> tasks = taskService.getAllByUser(userId);

        try (FileWriter writer = new FileWriter("study_export.txt")) {
            writer.append("Study Planner Export\n\n");

            for (Task t : tasks) {
                writer.append(String.format("%s - %s%n", t.getName(), t.isDone() ? "Završeno" : "Nije završeno"));
            }

            JOptionPane.showMessageDialog(mainPanel, "Export završen: study_export.txt", "Info", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainPanel, "Greška pri exportu!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    // getter za glavni panel (potreban za prikaz u JFrame)
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
