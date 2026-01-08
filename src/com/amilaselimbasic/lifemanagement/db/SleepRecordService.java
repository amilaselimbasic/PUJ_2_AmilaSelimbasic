package com.amilaselimbasic.lifemanagement.db;

import com.amilaselimbasic.lifemanagement.model.SleepRecord;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class SleepRecordService {

    // Kolekcija iz MongoDB za čuvanje zapisa o spavanju
    private final MongoCollection<Document> collection;

    public SleepRecordService() {
        // Dohvata kolekciju "sleeprecords" iz baze
        collection = MongoConfig.getDatabase().getCollection("sleeprecords");
    }

    // Metoda za dohvatanje svih zapisa trenutnog korisnika
    public List<SleepRecord> getAllByUser(String userId) {
        List<SleepRecord> list = new ArrayList<>();

        // Prolazimo kroz sve dokumente koji imaju userId jednak trenutnom korisniku
        for (Document doc : collection.find(eq("userId", userId))) {
            ObjectId id = doc.getObjectId("_id"); // MongoDB ID
            String date = doc.getString("date"); // Datum spavanja
            int hours = doc.getInteger("hours", 0); // Broj sati, default 0
            list.add(new SleepRecord(id.toHexString(), date, hours));
        }
        return list;
    }

    // Dodavanje novog zapisa o spavanju za trenutno ulogovanog korisnika
    public void insertRecord(String date, int hours) {
        String userId = CurrentUser.getUserId(); // Dohvata trenutno ulogovanog korisnika

        Document doc = new Document("userId", userId) // Kreiranje novog dokumenta
                .append("date", date)
                .append("hours", hours);

        collection.insertOne(doc); // Ubacivanje u bazu
    }

    // Brisanje zapisa po ID-u
    public void deleteRecord(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id))); // Traži po MongoDB ID-u i briše
    }
}
