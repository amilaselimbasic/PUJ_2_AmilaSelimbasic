package com.amilaselimbasic.lifemanagement.model;

// Klasa koja predstavlja jednog korisnika aplikacije
public class User {

    private String id;        // Jedinstveni ID korisnika iz MongoDB
    private String username;  // Korisničko ime
    private String password;  // Lozinka korisnika (u ovom projektu plain text)
    private String theme;     // Odabrana tema (zelena, plava, roza, itd.)

    // Konstruktor sa 4 parametra
    public User(String id, String username, String password, String theme) {
        this.id = id;             // Postavljanje ID-a
        this.username = username; // Postavljanje korisničkog imena
        this.password = password; // Postavljanje lozinke
        this.theme = theme;       // Postavljanje inicijalne teme
    }

    // Getter za ID korisnika
    public String getId() {
        return id;
    }

    // Setter za ID (ako bude potrebe za promjenom)
    public void setId(String id) {
        this.id = id;
    }

    // Getter za korisničko ime
    public String getUsername() {
        return username;
    }

    // Setter za korisničko ime
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter za lozinku
    public String getPassword() {
        return password;
    }

    // Setter za lozinku
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter za odabranu temu
    public String getTheme() {
        return theme;
    }

    // Setter za promjenu teme
    public void setTheme(String theme) {
        this.theme = theme;
    }
}
