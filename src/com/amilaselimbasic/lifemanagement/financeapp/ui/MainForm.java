package com.amilaselimbasic.lifemanagement.financeapp.ui;


import com.amilaselimbasic.lifemanagement.financeapp.db.MongoService;
import com.amilaselimbasic.lifemanagement.financeapp.model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainForm {

    // sve ovo mora ostati ovako
    private JPanel mainPanel;
    private JTextField txtAmount;         // unos iznosa
    private JTextField txtDescription;    // opis
    private JComboBox<String> cmbType;    // ovdje biram prihod/rashod
    private JComboBox<String> cmbCategory; // kategorija rashoda (ili prihoda?)
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnExport;
    private JTable tblTransactions;
    private JScrollPane tblScroll;

    // lbl
    private JLabel lblAmount;
    private JLabel lblDescription;
    private JLabel lblType;
    private JLabel lblCategory;

    private MongoService mongoService;  // konekcija za bazu
    private String selectedId = null;

    public MainForm() {

        mongoService = new MongoService(); // spajanje na mongo

        loadTableData(); // odmah učitavam tabelu kad se otvori forma

        // ovdje pravim evente
        btnAdd.addActionListener(e -> addTransaction());
        btnUpdate.addActionListener(e -> updateTransaction());
        btnDelete.addActionListener(e -> deleteTransaction());
        btnExport.addActionListener(e -> exportToTXT());

        // kad kliknem u tabeli, da mi prebaci podatke u inpute
        tblTransactions.getSelectionModel().addListSelectionListener(e -> {
            int selRow = tblTransactions.getSelectedRow();
            if (selRow >= 0) {
                // uzimam vrijednosti iz tabele
                selectedId = tblTransactions.getValueAt(selRow, 0).toString();
                txtAmount.setText(tblTransactions.getValueAt(selRow, 1).toString());
                txtDescription.setText(tblTransactions.getValueAt(selRow, 2).toString());
                cmbType.setSelectedItem(tblTransactions.getValueAt(selRow, 3).toString());
                cmbCategory.setSelectedItem(tblTransactions.getValueAt(selRow, 4).toString());
            }
        });
    }

    private void addTransaction() {
        try {
            // pretvori tekst u double
            double amount = Double.parseDouble(txtAmount.getText());
            String desc = txtDescription.getText();
            String type = cmbType.getSelectedItem().toString();
            String category = cmbCategory.getSelectedItem().toString();

            // ubacujem u bazu
            mongoService.insertTransaction(amount, desc, type, category);

            loadTableData(); // refresh tabele
            clearFields();   // da ne ostanu prethodni podaci

        } catch (NumberFormatException ex) {
            // ako korisnik unese slova umjesto broja
            JOptionPane.showMessageDialog(mainPanel, "Iznos mora biti broj!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTransaction() {

        // ako nije selektovan red, neće raditi
        if (selectedId == null) {
            JOptionPane.showMessageDialog(mainPanel, "Odaberite transakciju prvo!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(txtAmount.getText());
            String desc = txtDescription.getText();
            String type = cmbType.getSelectedItem().toString();
            String category = cmbCategory.getSelectedItem().toString();

            // update u bazi
            mongoService.updateTransaction(selectedId, amount, desc, type, category);

            loadTableData(); // opet punim tabelu
            clearFields();
            selectedId = null; // resetujem ID jer više nije selektovan

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Iznos mora biti broj!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTransaction() {

        // mora biti selektovan neki red
        if (selectedId == null) {
            JOptionPane.showMessageDialog(mainPanel, "Prvo izabrati red!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // da se potvrdi radnja
        int confirm = JOptionPane.showConfirmDialog(mainPanel, "Sigurno obrisati?", "Potvrdi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            mongoService.deleteTransaction(selectedId); // brisanje u bazi
            loadTableData();
            clearFields();
            selectedId = null;
        }
    }

    private void loadTableData() {

        // lista svih transakcija  ovo dobijam iz MongoService
        List<Transaction> all = mongoService.getAll();

        // pravim običnu 2D matricu jer nisam našla bolji način za JTable
        String[][] data = new String[all.size()][5];

        for (int i = 0; i < all.size(); i++) {
            Transaction t = all.get(i);

            // popunjavam ručno
            data[i][0] = t.getId();
            data[i][1] = String.valueOf(t.getAmount());
            data[i][2] = t.getDescription();
            data[i][3] = t.getType();
            data[i][4] = t.getCategory();
        }

        String[] columns = {"ID","Iznos","Opis","Vrsta","Kategorija"};

        // koristim DefaultTableModel
        tblTransactions.setModel(new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column){
                return false; // da ne može direktno mijenjati u tabeli
            }
        });
    }

    private void clearFields() {
        // resetujem sve unose nakon dodavanja/update/brisanja
        txtAmount.setText("");
        txtDescription.setText("");
        cmbType.setSelectedIndex(0);
        cmbCategory.setSelectedIndex(0);
        tblTransactions.clearSelection();
    }

    private void exportToTXT() {

        // uzimam sve transakcije opet
        List<Transaction> all = mongoService.getAll();

        double totalIncome = 0;
        double totalExpense = 0;

        // grupišem rashode po kategorijama
        Map<String, Double> expensesByCategory = new HashMap<>();

        // prolazim kroz listu ručno
        for (Transaction t : all){
            if (t.getType().equalsIgnoreCase("Prihod")) {

                totalIncome += t.getAmount();

            } else {

                totalExpense += t.getAmount();

                expensesByCategory.put(
                        t.getCategory(),
                        expensesByCategory.getOrDefault(t.getCategory(),0.0) + t.getAmount()
                );
            }
        }

        // zapisujem u fajl
        try(FileWriter writer = new FileWriter("export.txt")){

            writer.append(String.format("Ukupni prihod: %.2f%n", totalIncome));
            writer.append(String.format("Ukupni rashod: %.2f%n", totalExpense));
            writer.append(String.format("Stanje: %.2f%n", totalIncome-totalExpense));

            writer.append("Rashodi po kategorijama:\n");

            for (Map.Entry<String, Double> e : expensesByCategory.entrySet()){
                writer.append(String.format("%s: %.2f%n", e.getKey(), e.getValue()));
            }

            JOptionPane.showMessageDialog(mainPanel,"Export završen: export.txt","Info",JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e){
            // ako nešto pukne, samo poruka
            JOptionPane.showMessageDialog(mainPanel,"Greška pri exportu!","Greška",JOptionPane.ERROR_MESSAGE);
        }
    }

    // ovo mora ostati ovako
    public JPanel getMainPanel(){ return mainPanel; }
}
