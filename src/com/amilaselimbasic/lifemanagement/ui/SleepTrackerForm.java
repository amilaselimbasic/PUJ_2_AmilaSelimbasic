package com.amilaselimbasic.lifemanagement.ui;

import com.amilaselimbasic.lifemanagement.model.SleepRecord;
import com.amilaselimbasic.lifemanagement.db.SleepRecordService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SleepTrackerForm {
    private JPanel mainPanel;
    private JTable tblSleep;
    private JButton btnAdd;
    private JButton btnDelete;
    private JScrollPane scrollPane;

    private SleepRecordService sleepRecordService;

    public SleepTrackerForm () {

        sleepRecordService=new SleepRecordService();

        initTable ();
        refreshTable();

        btnAdd.addActionListener(e -> addRecord());
        btnDelete.addActionListener(e -> deleteRecord());

    }

    //inicijalizacija tabele

    private void initTable () {
        tblSleep.setModel(new DefaultTableModel(
                new Object[][] {},
                new String []{"Datum", "Sati sna", "ID"}
        )
                          {
                              @Override public boolean isCellEditable (int row, int column) {
                                  return false;
                              }
                          }
        );

        //sakrivanje ID kolone

        tblSleep.getColumnModel().getColumn(2).setWidth(0);
        tblSleep.getColumnModel().getColumn(2).setMaxWidth(0);
        tblSleep.getColumnModel().getColumn(2).setMinWidth(0);

    }

    //dodavanje zapisa

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
            sleepRecordService.insertRecord(date,hours);
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Broj sati mora biti cijeli broj",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    //brisanje zapisa

    private void deleteRecord() {
        int row = tblSleep.getSelectedRow();
        if (row>=0) {
            String id=tblSleep.getValueAt(row, 2).toString();
            sleepRecordService.deleteRecord(id);
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

    //učitavanje podataka iz baze

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblSleep.getModel();
        model.setRowCount(0);

        for (SleepRecord r : sleepRecordService.getAll()) {
            model.addRow(new Object[]{
                    r.getDate(),
                    r.getHours(),
                    r.getId()
            });
        }
    }

    public JPanel getMainPanel () {
        return mainPanel;
    }
}
