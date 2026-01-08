package com.amilaselimbasic.lifemanagement.model;

// Klasa koja predstavlja zapis o spavanju korisnika
public class SleepRecord {

    private String id;      // Jedinstveni ID zapisa (iz MongoDB)
    private String date;    // Datum zapisa (npr. "2026-01-08")
    private int hours;      // Broj sati sna

    // Konstruktor klase
    public SleepRecord(String id, String date, int hours) {
        this.id = id;       // Postavljanje ID-a
        this.date = date;   // Postavljanje datuma
        this.hours = hours; // Postavljanje broja sati
    }

    // Getter za ID zapisa
    public String getId() {
        return id;
    }

    // Getter za datum
    public String getDate() {
        return date;
    }

    // Getter za broj sati sna
    public int getHours() {
        return hours;
    }
}
