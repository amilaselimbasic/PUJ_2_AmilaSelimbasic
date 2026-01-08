package com.amilaselimbasic.lifemanagement.db;

import com.amilaselimbasic.lifemanagement.model.User;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class UserService {

    // Kolekcija iz MongoDB za čuvanje korisnika
    private final MongoCollection<Document> collection;

    public UserService() {
        // Povezivanje na MongoDB lokalni server i baza "lifemanagement"
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("lifemanagement");
        collection = db.getCollection("users"); // Kolekcija "users"
    }

    // Provjera da li korisnik već postoji po username-u
    public boolean userExists(String username) {
        return collection.find(eq("username", username)).first() != null;
    }

    // Alias metode za kompatibilnost sa LoginForm
    public boolean usernameExists(String username) {
        return userExists(username);
    }

    // Registracija novog korisnika
    public boolean register(String username, String password, String theme) {
        if (userExists(username)) {
            return false; // Ako korisnik postoji, ne radi ništa
        }

        // Kreiranje MongoDB dokumenta za novog korisnika
        Document doc = new Document()
                .append("username", username)
                .append("password", password)
                .append("theme", theme);

        collection.insertOne(doc); // Ubacivanje u kolekciju
        return true;
    }

    // Alias metode da LoginForm ne bi imao grešku
    public void insertUser(String username, String password, String theme) {
        register(username, password, theme);
    }

    // Login provjera
    public User login(String username, String password) {
        Document doc = collection.find(
                and(
                        eq("username", username),
                        eq("password", password)
                )
        ).first();

        if (doc == null) return null; // Ako nema podudaranja, vraća null

        // Vraća User objekt sa podacima iz baze
        return new User(
                doc.getObjectId("_id").toHexString(),
                doc.getString("username"),
                doc.getString("password"),
                doc.getString("theme")
        );
    }

    // Dohvatanje korisnika po ID-u
    public User getById(String userId) {
        Document doc = collection.find(eq("_id", new ObjectId(userId))).first();
        if (doc == null) return null;

        return new User(
                doc.getObjectId("_id").toHexString(),
                doc.getString("username"),
                doc.getString("password"),
                doc.getString("theme")
        );
    }

    // Ažuriranje teme korisnika
    public void updateTheme(String userId, String theme) {
        collection.updateOne(
                eq("_id", new ObjectId(userId)),
                new Document("$set", new Document("theme", theme))
        );
    }

    // Brisanje korisnika
    public void delete(String userId) {
        collection.deleteOne(eq("_id", new ObjectId(userId)));
    }
}
