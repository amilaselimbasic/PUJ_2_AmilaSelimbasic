package com.amilaselimbasic.lifemanagement.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {

    // Kreiranje konekcije sa lokalnim MongoDB serverom
    private static final MongoClient client = MongoClients.create("mongodb://localhost:27017");

    // Odabir baze podataka koju ćemo koristiti
    private static final MongoDatabase database = client.getDatabase("lifemanagment");

    // Getter metoda za pristup bazi iz drugih klasa
    public static MongoDatabase getDatabase() {
        return database;
    }

    // Metoda za zatvaranje konekcije sa bazom kad više nije potrebna
    public static void close() {
        client.close();
    }
}
