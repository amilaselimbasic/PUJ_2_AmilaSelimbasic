package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.model.Habit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class HabitTrackerForm {
    private JTable tblHabits;
    private JButton btnAddHabit;
    private JButton btnDeleteHabit;
    private JButton btnMarkDone;
    private JPanel mainPanel;
    private JScrollPane scrollPane;

    private List<Habit> habits=new ArrayList<>();

    public HabitTrackerForm () {
        initTable();
        btnAddHabit.addActionListener(e -> addHabit());
        btnDeleteHabit.addActionListener(e -> deleteHabit());
        btnMarkDone.addActionListener(e -> markHabitDone());

    }

    private void initTable() {
        tblHabits.setModel(new DefaultTableModel(
                new Object[][]{},
                new String []{"Navika","Status"}
        ) {
            @Override
        public boolean isCellEditable(int row, int column){
            return false;
            }
        } );
    }

    private void addHabit() {
        String name=JOptionPane.showInputDialog(
                mainPanel,
                "Unesite naziv navike"
        );

        if(name !=null && !name.isEmpty()) {
            habits.add(new Habit(name));
            refreshTable();
        }
    }

    private void deleteHabit() {
        int row = tblHabits.getSelectedRow();

        if (row>=0) {
            habits.remove(row);
            refreshTable();
        }
        else {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Odaberite naviku",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void markHabitDone(){
        int row=tblHabits.getSelectedRow();

        if (row>=0) {
            habits.get(row).setDone(true);
            refreshTable();
        }
        else {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Odaberite naviku",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void refreshTable(){
        DefaultTableModel model=(DefaultTableModel) tblHabits.getModel();
        model.setRowCount(0);

        for (Habit h:habits) {
            model.addRow(new Object[]{
                    h.getName(),
                    h.isDone() ? "Završeno" : "Nije završeno"
            });
        }
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}
