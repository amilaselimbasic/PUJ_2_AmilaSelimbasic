package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.model.SleepRecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerForm {
    private JPanel mainPanel;
    private JTable tblSleep;
    private JButton btnAdd;
    private JButton btnDelete;
    private JScrollPane scrollPane;

    private List<SleepRecord> records=new ArrayList<>();

    public SleepTrackerForm () {

        initTable ();

        btnAdd.addActionListener(e -> addRecord());
        btnDelete.addActionListener(e -> deleteRecord());

    }

    private void initTable () {
        tblSleep.setModel(new DefaultTableModel(
                new Object[][] {},
                new String []{"Datum", "Sati sna"}
        )
                          {
                              @Override public boolean isCellEditable (int row, int column) {
                                  return false;
                              }
                          }
        );
    }
    private void addRecord() {
        String date =JOptionPane.showInputDialog(
                mainPanel,
                "Unesite datum"
        );

        if (date==null || date.isEmpty()) return;

        String hoursStr = JOptionPane.showInputDialog(
                mainPanel,
                "Unesite broj sati sna"
        );

        try {
            int hours = Integer.parseInt(hoursStr);
            records.add(new SleepRecord(date,hours));
            refreshTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Broj sati mora biti cijeli broj",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deleteRecord() {
        int row = tblSleep.getSelectedRow();
        if (row>=0) {
            records.remove(row);
            refreshTable();
        }
        else {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Odaberite unos",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblSleep.getModel();
        model.setRowCount(0);

        for (SleepRecord r : records) {
            model.addRow(new Object[]{
                    r.getDate(),
                    r.getHours()
            });
        }
    }

    public JPanel getMainPanel () {
        return mainPanel;
    }
}
