package com.amilaselimbasic.lifemanagement.db;

import com.amilaselimbasic.lifemanagement.model.Task;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TaskService {

    // Kolekcija iz MongoDB za čuvanje zadataka
    private final MongoCollection<Document> collection;

    public TaskService() {
        // Dohvata kolekciju "tasks" iz baze
        collection = MongoConfig.getDatabase().getCollection("tasks");
    }

    // Metoda za dohvatanje svih zadataka trenutno ulogovanog korisnika
    public List<Task> getAllByUser(String userId) {
        List<Task> list = new ArrayList<>();

        // Prolazimo kroz sve dokumente koji pripadaju userId
        for (Document doc : collection.find(eq("userId", userId))) {
            ObjectId id = doc.getObjectId("_id"); // MongoDB ID
            String name = doc.getString("name"); // Naziv zadatka
            boolean done = doc.getBoolean("done", false); // Status zadatka, default false
            list.add(new Task(id.toHexString(), name, done));
        }

        return list;
    }

    // Dodavanje novog zadatka za trenutno ulogovanog korisnika
    public void insertTask(String name) {
        String userId = CurrentUser.getUserId(); // Dohvata trenutno ulogovanog korisnika

        Document doc = new Document("userId", userId) // Kreiranje novog dokumenta
                .append("name", name)
                .append("done", false);

        collection.insertOne(doc); // Ubacivanje u bazu
    }

    // Oznaka zadatka kao završen ili nezavršen
    public void updateTaskDone(String id, boolean done) {
        collection.updateOne(
                eq("_id", new ObjectId(id)), // Traži zadatak po ID-u
                new Document("$set", new Document("done", done)) // Ažurira polje "done"
        );
    }

    // Brisanje zadatka po ID-u
    public void deleteTask(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id))); // Briše zadatak iz baze
    }
}
