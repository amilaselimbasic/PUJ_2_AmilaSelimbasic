package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.model.Habit;
import com.amilaselimbasic.lifemanagement.db.HabitService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HabitTrackerForm {
    private JTable tblHabits;
    private JButton btnAddHabit;
    private JButton btnDeleteHabit;
    private JButton btnMarkDone;
    private JPanel mainPanel;
    private JScrollPane scrollPane;

    private HabitService habitService;

    public HabitTrackerForm () {

        habitService=new HabitService();

        initTable();
        refreshTable();

        btnAddHabit.addActionListener(e -> addHabit());
        btnDeleteHabit.addActionListener(e -> deleteHabit());
        btnMarkDone.addActionListener(e -> markHabitDone());

    }

    //inicijalizacija tabele

    private void initTable() {
        tblHabits.setModel(new DefaultTableModel(
                new Object[][]{},
                new String []{"Navika","Status", "ID"}
        ) {
            @Override
        public boolean isCellEditable(int row, int column){
            return false;
            }
        } );

        //sakrivanje ID kolone

        tblHabits.getColumnModel().getColumn(2).setMaxWidth(0);
        tblHabits.getColumnModel().getColumn(2).setMaxWidth(0);
        tblHabits.getColumnModel().getColumn(2).setWidth(0);

    }

    //dodavanje navike

    private void addHabit() {
        String name=JOptionPane.showInputDialog(
                mainPanel,
                "Unesite naziv navike"
        );

        if(name !=null && !name.isEmpty()) {
            habitService.insertHabit(name);
            refreshTable();
        }
    }

    //brisanje navike

    private void deleteHabit() {
        int row = tblHabits.getSelectedRow();

        if (row>=0) {
            String id = tblHabits.getValueAt(row,2).toString();
            habitService.deleteHabit(id);
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

    //označavanje navike završenom

    private void markHabitDone(){
        int row=tblHabits.getSelectedRow();

        if (row>=0) {
            String id = tblHabits.getValueAt(row, 2).toString();
            habitService.markDone(id);
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

    //učitavanje iz baze u tabelu

    private void refreshTable(){
        DefaultTableModel model=(DefaultTableModel) tblHabits.getModel();
        model.setRowCount(0);

        for (Habit h: habitService.getAll()) {
            model.addRow(new Object[]{
                    h.getName(),
                    h.isDone() ? "Završeno" : "Nije završeno",
                    h.getId()
            });
        }
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}
