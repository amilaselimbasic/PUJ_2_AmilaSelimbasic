package com.amilaselimbasic.lifemanagement.financeapp.db;

import com.amilaselimbasic.lifemanagement.financeapp.model.Transaction;
import com.amilaselimbasic.lifemanagement.session.CurrentUser;
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
        mongoClient = MongoClients.create("mongodb://localhost:27017");

        // baza 'financeapp'
        MongoDatabase db = mongoClient.getDatabase("financeapp");

        // kolekcija 'transactions'
        collection = db.getCollection("transactions");
    }

    // uzimam sve transakcije jednog ulogovanog usera
    public List<Transaction> getAllByUser() {
        List<Transaction> list = new ArrayList<>();
        String userId = CurrentUser.getUserId(); // trenutni user

        // ✅ nedostajao tačka-zarez
        FindIterable<Document> docs = collection.find(Filters.eq("userId", userId));

        for (Document d : docs) {
            ObjectId id = d.getObjectId("_id");
            double amount = d.getDouble("amount");
            String desc = d.getString("description");
            String type = d.getString("type");
            String category = d.getString("category");

            list.add(new Transaction(id.toHexString(), amount, desc, type, category));
        }

        return list;
    }

    // unos nove transakcije
    public String insertTransaction(double amount, String description, String type, String category) {
        String userId = CurrentUser.getUserId();

        Document doc = new Document("userId", userId)
                .append("amount", amount)
                .append("description", description)
                .append("type", type)
                .append("category", category);

        collection.insertOne(doc);
        return doc.getObjectId("_id").toHexString();
    }

    // update postojećeg dokumenta (samo za trenutnog usera)
    public void updateTransaction(String id, double amount, String description, String type, String category) {
        String userId = CurrentUser.getUserId();

        collection.updateOne(
                Filters.and(
                        Filters.eq("_id", new ObjectId(id)),
                        Filters.eq("userId", userId)
                ),
                Updates.combine(
                        Updates.set("amount", amount),
                        Updates.set("description", description),
                        Updates.set("type", type),
                        Updates.set("category", category)
                )
        );
    }

    // brisanje po ID-u (samo za trenutnog usera)
    public void deleteTransaction(String id) {
        String userId = CurrentUser.getUserId();

        collection.deleteOne(
                Filters.and(
                        Filters.eq("_id", new ObjectId(id)),
                        Filters.eq("userId", userId)
                )
        );
    }

    // zatvaram konekciju na kraju programa
    public void close() {
        mongoClient.close();
    }
}
