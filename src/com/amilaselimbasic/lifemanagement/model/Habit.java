package com.amilaselimbasic.lifemanagement.model;

// Klasa koja predstavlja jednu naviku korisnika
public class Habit {

    private String id;      // Jedinstveni ID navike (iz MongoDB)
    private String name;    // Naziv navike
    private boolean done;   // Da li je navika završena ili ne

    // Konstruktor klase
    public Habit(String id, String name, boolean done) {
        this.id = id;       // Postavljanje ID-a
        this.name = name;   // Postavljanje naziva
        this.done = done;   // Postavljanje statusa
    }

    // Getter za ID
    public String getId() {
        return id;
    }

    // Getter za naziv navike
    public String getName() {
        return name;
    }

    // Getter za status navike
    public boolean isDone() {
        return done;
    }

    // Setter za status navike (da označimo kao završeno ili ne)
    public void setDone(boolean done) {
        this.done = done;
    }
}
