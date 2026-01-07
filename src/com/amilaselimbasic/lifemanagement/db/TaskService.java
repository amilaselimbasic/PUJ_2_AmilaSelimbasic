package com.amilaselimbasic.lifemanagement.db;

import com.amilaselimbasic.lifemanagement.model.Task;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TaskService {
    private final MongoCollection<Document> collection;

    public TaskService () {
        collection = MongoConfig.getDatabase().getCollection("tasks");
    }

    public List<Task> getAll () {
        List<Task> list=new ArrayList<>();
        for (Document doc : collection.find()) {
            ObjectId id = doc.getObjectId("_id");
            String name = doc.getString("name");
            boolean done = doc.getBoolean("done", false);
            list.add(new Task(id.toHexString(), name, done));
        }

        return list;
    }

    public String insertTask (String name) {
        Document doc = new Document("name", name)
                .append("done",false);
        collection.insertOne(doc);
        return doc.getObjectId("_id").toHexString();
    }

    public void updateTaskDone (String id, boolean done) {
        collection.updateOne(eq("_id", new ObjectId(id)),
        new Document("$set", new Document("done", done)));
    }

    public void deleteTask (String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
