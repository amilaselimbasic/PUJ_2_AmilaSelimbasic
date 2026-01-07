package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.db.TaskService;
import com.amilaselimbasic.lifemanagement.model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudyPlannerForm {

    private JPanel mainPanel;
    private JPanel btnPanel;
    private JTable table1;
    private JButton btnAddTask;
    private JButton btnDeleteTask;
    private JButton btnMarkDone;
    private JScrollPane scrollPane;

    private TaskService taskService;

    public StudyPlannerForm() {

        taskService = new TaskService();

        initTable();
        refreshTable();

        btnAddTask.addActionListener(e -> addTask());
        btnDeleteTask.addActionListener(e -> deleteTask());
        btnMarkDone.addActionListener(e -> markTaskDone());
    }

    // inicijalizacija tabele
    private void initTable() {
        table1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Zadatak", "Status", "ID"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // sakrivamo ID kolonu
        table1.getColumnModel().getColumn(2).setMinWidth(0);
        table1.getColumnModel().getColumn(2).setMaxWidth(0);
        table1.getColumnModel().getColumn(2).setWidth(0);
    }

    // dodavanje zadatka
    private void addTask() {
        String name = JOptionPane.showInputDialog(
                mainPanel,
                "Unesite naziv zadatka"
        );

        if (name != null && !name.isEmpty()) {
            taskService.insertTask(name);
            refreshTable();
        }
    }

    // brisanje zadatka
    private void deleteTask() {
        int row = table1.getSelectedRow();

        if (row >= 0) {
            String id = table1.getValueAt(row, 2).toString();
            taskService.deleteTask(id);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Odaberite zadatak",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // označavanje zadatka kao završenog
    private void markTaskDone() {
        int row = table1.getSelectedRow();

        if (row >= 0) {
            String id = table1.getValueAt(row, 2).toString();
            taskService.updateTaskDone(id, true);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Odaberite zadatak",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // osvježavanje tabele iz MongoDB
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);

        for (Task t : taskService.getAll()) {
            model.addRow(new Object[]{
                    t.getName(),
                    t.isDone() ? "Završeno" : "Nije završeno",
                    t.getId()
            });
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
