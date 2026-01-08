package com.amilaselimbasic.lifemanagement.financeapp.ui;

import com.amilaselimbasic.lifemanagement.financeapp.db.MongoService;
import com.amilaselimbasic.lifemanagement.financeapp.model.Transaction;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FinanceAnalyticsFrame extends JFrame {

    public FinanceAnalyticsFrame() {
        setTitle("Finance Analytics");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        MongoService mongoService = new MongoService();
        List<Transaction> transactions = mongoService.getAllByUser();

        double totalIncome = 0;
        double totalExpense = 0;
        Map<String, Double> expensesByCategory = new HashMap<>();

        for (Transaction t : transactions) {
            if ("Prihod".equalsIgnoreCase(t.getType())) {
                totalIncome += t.getAmount();
            } else {
                totalExpense += t.getAmount();
                expensesByCategory.put(
                        t.getCategory(),
                        expensesByCategory.getOrDefault(t.getCategory(), 0.0) + t.getAmount()
                );
            }
        }

        double balance = totalIncome - totalExpense;

        JPanel panel = new JPanel(new GridLayout(6 + expensesByCategory.size(), 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Finance Analytics", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title);

        panel.add(new JLabel(String.format("Ukupni prihod: %.2f", totalIncome)));
        panel.add(new JLabel(String.format("Ukupni rashod: %.2f", totalExpense)));
        panel.add(new JLabel(String.format("Stanje: %.2f", balance)));
        panel.add(new JLabel("Rashodi po kategorijama:"));

        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            panel.add(new JLabel(String.format("%s: %.2f", entry.getKey(), entry.getValue())));
        }

        add(panel);
    }
}
