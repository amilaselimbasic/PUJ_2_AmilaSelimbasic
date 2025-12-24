package com.amilaselimbasic.lifemanagement.financeapp.model;

// klasa koja predstavlja jednu transakciju

public class Transaction {

    private String id;              // mongo ID string jer ga tako spremam iz baze
    private double amount;          // iznos
    private String description;     // opis transakcije
    private String type;            // prihod ili rashod
    private String category;        // npr hrana, računi itd.

    public Transaction() {
        // prazan konstruktor za ručno dodavanje
    }

    // konstruktor sa svim poljima (koristim u MongoService kad čitam iz baze)
    public Transaction(String id, double amount, String description, String type, String category) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.category = category;
    }

    // konstruktor bez ID-a kad unosim novu transakciju, jer ID pravi Mongo sam
    public Transaction(double amount, String description, String type, String category) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.category = category;
    }

    // getteri i setteri
    public String getId() {
        return id;
    }

    public void setId(String id) {
        // id dolazi iz mongo ObjectId, ali ja ga koristim kao String
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        // provjera da li je double se radi u MainForm
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        // običan tekst
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        // Prihod ili Rashod
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        // npr. Hrana, Prevoz, itd.
        this.category = category;
    }
}
