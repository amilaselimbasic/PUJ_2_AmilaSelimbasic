package com.amilaselimbasic.lifemanagement.db;

import com.amilaselimbasic.lifemanagement.model.Habit;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;

public class HabitService {
    private final MongoCollection<Document> collection;

    public HabitService () {
        collection=MongoConfig.getDatabase().getCollection("habits");
    }

    public List<Habit>getAll() {
        List<Habit> list = new ArrayList<>();
        for (Document doc : collection.find()) {
            ObjectId id = doc.getObjectId("_id");
            String name = doc.getString("name");
            boolean done = doc.getBoolean("done", false);
            list.add(new Habit(id.toHexString(), name, done));
        }
        return list;
    }

    public String insertHabit(String name) {
        Document doc = new Document("name", name) .append("done", false);
        collection.insertOne(doc);
        return doc.getObjectId("_id").toHexString();
    }

    public void markDone (String id) {
        collection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", new Document("done", true)));
    }

    public void deleteHabit (String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }
}

