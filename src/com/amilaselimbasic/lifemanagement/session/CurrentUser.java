package com.amilaselimbasic.lifemanagement.session;

import com.amilaselimbasic.lifemanagement.model.User;

// Klasa koja čuva trenutno prijavljenog korisnika
// Static polja i metode omogućavaju globalni pristup u aplikaciji
public class CurrentUser {

    private static User loggedUser; // trenutno prijavljeni korisnik

    // Postavljanje prijavljenog korisnika
    public static void set(User user) {
        loggedUser = user;
    }

    // Dohvatanje trenutno prijavljenog korisnika
    public static User get() {
        return loggedUser;
    }

    // Dohvatanje ID-a trenutno prijavljenog korisnika
    // Vraća null ako niko nije prijavljen
    public static String getUserId() {
        return loggedUser != null ? loggedUser.getId() : null;
    }

    // Odjava korisnika (resetovanje trenutno prijavljenog)
    public static void logout() {
        loggedUser = null;
    }
}
