package com.amilaselimbasic.lifemanagement.db;

import com.amilaselimbasic.lifemanagement.model.SleepRecord;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class SleepRecordService {
    private final MongoCollection<Document> collection;

    public SleepRecordService() {
        collection = MongoConfig.getDatabase().getCollection("sleeprecords");
    }

    public List<SleepRecord> getAll() {
        List<SleepRecord> list=new ArrayList<>();
        for (Document doc : collection.find()) {
            ObjectId id = doc.getObjectId("_id");
            String date = doc.getString("date");
            int hours = doc.getInteger("hours", 0);
            list.add(new SleepRecord(id.toHexString(), date, hours));
        }
        return list;
    }

    public String insertRecord (String date, int hours) {
        Document doc = new Document("date", date) .append("hours",hours);
        collection.insertOne(doc);
        return doc.getObjectId("_id").toHexString();
    }

    public void deleteRecord (String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
