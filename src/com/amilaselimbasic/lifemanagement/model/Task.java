package com.amilaselimbasic.lifemanagement.model;

// Klasa koja predstavlja jedan zadatak u Study Planner modulu
public class Task {

    private String id;      // Jedinstveni ID zadatka iz MongoDB
    private String name;    // Naziv zadatka
    private boolean done;   // Status zadatka (završeno/nezavršeno)

    // Konstruktor klase
    public Task(String id, String name, boolean done) {
        this.id = id;       // Postavljanje ID-a
        this.name = name;   // Postavljanje naziva zadatka
        this.done = done;   // Postavljanje početnog statusa
    }

    // Getter za ID zadatka
    public String getId() {
        return id;
    }

    // Getter za naziv zadatka
    public String getName() {
        return name;
    }

    // Provjera da li je zadatak završen
    public boolean isDone() {
        return done;
    }

    // Setter za promjenu statusa zadatka
    public void setDone(boolean done) {
        this.done = done;
    }
}
