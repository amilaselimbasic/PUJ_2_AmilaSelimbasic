package com.amilaselimbasic.lifemanagement.db;

import com.amilaselimbasic.lifemanagement.model.Habit;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class HabitService {

    // MongoDB kolekcija u kojoj čuvamo navike
    private final MongoCollection<Document> collection;

    // Konstruktor, povezivanje sa kolekcijom "habits"
    public HabitService() {
        collection = MongoConfig.getDatabase().getCollection("habits");
    }

    // Metoda koja vraća sve navike trenutno prijavljenog korisnika
    public List<Habit> getAllByUser() {
        List<Habit> list = new ArrayList<>();

        // dohvat ID-a trenutno prijavljenog korisnika
        String userId = CurrentUser.getUserId();

        // prolazak kroz sve dokumente u kolekciji koji pripadaju tom korisniku
        for (Document doc : collection.find(eq("userId", userId))) {
            ObjectId id = doc.getObjectId("_id"); // dohvat MongoDB ID-a
            String name = doc.getString("name"); // dohvat imena navike
            boolean done = doc.getBoolean("done", false); // dohvat statusa završetka

            // dodavanje navike u listu
            list.add(new Habit(
                    id.toHexString(), // ID kao string
                    name,
                    done
            ));
        }

        return list; // vraćamo listu navika
    }

    // Metoda za dodavanje nove navike za trenutno prijavljenog korisnika
    public void insertHabit(String name) {
        String userId = CurrentUser.getUserId();

        // kreiranje MongoDB dokumenta sa userId, imenom i default statusom "nije završeno"
        Document doc = new Document("userId", userId)
                .append("name", name)
                .append("done", false);

        // ubacivanje dokumenta u kolekciju
        collection.insertOne(doc);
    }

    // Metoda za označavanje navike kao završene
    public void markDone(String id) {
        // pronalazak dokumenta po ID-u i postavljanje "done" na true
        collection.updateOne(
                eq("_id", new ObjectId(id)),
                new Document("$set", new Document("done", true))
        );
    }

    // Metoda za brisanje navike po ID-u
    public void deleteHabit(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id))); // brisanje dokumenta
    }
}
