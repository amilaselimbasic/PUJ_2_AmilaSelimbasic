package com.amilaselimbasic.lifemanagement.financeapp.db;

import com.amilaselimbasic.lifemanagement.financeapp.model.Transaction;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

// Ovdje pravim komunikaciju sa MongoDB-om.
public class MongoService {

    private final MongoClient mongoClient; // konekcija na Mongo
    private final MongoCollection<Document> collection; // tabela/collection

    public MongoService() {

        // koristim default localhost mongodb
        String connectionString = "mongodb://localhost:27017";

        // otvaram konekciju
        mongoClient = MongoClients.create(connectionString);

        // baza 'financeapp'
        MongoDatabase db = mongoClient.getDatabase("financeapp");

        // kolekcija 'transactions'
        collection = db.getCollection("transactions");
    }

    // uzimam sve transakcije iz baze i prebacujem ih u Java listu
    public List<Transaction> getAll() {
        List<Transaction> list = new ArrayList<>();

        // find bez filtera -> sve
        FindIterable<Document> docs = collection.find();

        for (Document d : docs) {
            // mongo pravi ObjectId pa ga prebacujem u string
            ObjectId id = d.getObjectId("_id");

            double amount = d.getDouble("amount");
            String desc = d.getString("description");
            String type = d.getString("type");
            String category = d.getString("category");

            // ovdje koristim konstruktor sa ID-em
            list.add(new Transaction(id.toHexString(), amount, desc, type, category));
        }
        return list;
    }

    // unos nove transakcije
    public String insertTransaction(double amount, String description, String type, String category) {

        // pravim dokument koji ide u Mongo
        Document doc = new Document("amount", amount)
                .append("description", description)
                .append("type", type)
                .append("category", category);

        collection.insertOne(doc);

        // vraćam hex id da ga GUI zna i koristi
        return doc.getObjectId("_id").toHexString();
    }

    // update postojećeg dokumenta
    public void updateTransaction(String id, double amount, String description, String type, String category) {

        // new ObjectId(id) jer je mongo ID specijalan tip
        collection.updateOne(
                Filters.eq("_id", new ObjectId(id)),
                Updates.combine(
                        Updates.set("amount", amount),
                        Updates.set("description", description),
                        Updates.set("type", type),
                        Updates.set("category", category)
                )
        );
    }

    // brisanje po ID-u
    public void deleteTransaction(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    // zatvaram konekciju na kraju programa
    public void close() {
        mongoClient.close();
    }
}
