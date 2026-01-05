package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class StudyPlannerForm {

    private JPanel mainPanel;
    private JTable table1;
    private JButton btnAddTask;
    private JButton btnMarkDone;
    private JButton btnDeleteTask;
    private JPanel btnPanel;

    private List<Task> tasks = new ArrayList<>();

    public StudyPlannerForm() {

        initTable();

        btnAddTask.addActionListener(e -> addTask());
        btnDeleteTask.addActionListener(e -> deleteTask());
        btnMarkDone.addActionListener(e -> markTaskDone());
    }

    private void initTable() {
        table1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Zadatak", "Status"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void addTask() {
        String taskName = JOptionPane.showInputDialog(
                mainPanel,
                "Unesite naziv zadatka"
        );

        if (taskName != null && !taskName.isEmpty()) {
            tasks.add(new Task(taskName));
            refreshTable();
        }
    }

    private void deleteTask() {
        int row = table1.getSelectedRow();

        if (row >= 0) {
            tasks.remove(row);
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

    private void markTaskDone() {
        int row = table1.getSelectedRow();

        if (row >= 0) {
            tasks.get(row).setDone(true);
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

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);

        for (Task t : tasks) {
            model.addRow(new Object[]{
                    t.getName(),
                    t.isDone() ? "Završeno" : "U toku"
            });
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
