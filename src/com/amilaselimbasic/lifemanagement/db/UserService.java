package com.amilaselimbasic.lifemanagement.db;

import com.amilaselimbasic.lifemanagement.model.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserService {
private final MongoCollection<Document> collection;
public UserService () {
    collection = MongoConfig.getDatabase().getCollection("user");
}

//dohvati sve korisnike iz baze

public List<User> getAll () {
    List<User> list = new ArrayList<>();
    for (Document doc : collection.find()) {
        ObjectId id = doc.getObjectId("_id");
        String username = doc.getString("username");
        String password = doc.getString("password");
        String role = doc.getString("role");
        list.add(new User(id.toHexString(),username,password, role));
    }
    return list;
}

//dodaj novog korisnika

public String insertUser (String username, String password, String role) {
    Document doc = new Document("username",username)
            .append("password",password)
                    .append("role", role);

    collection.insertOne(doc);
    return doc.getObjectId("_id").toHexString();
}

// pronađi korisnika
    public User findUser(String username, String password) {
    Document doc = collection.find(eq("username", username)).first();
    if (doc != null && doc.getString("password").equals(password)) {
        ObjectId id=doc.getObjectId("_id");
        String role= doc.getString("role");
        return new User(id.toHexString(), username, password, role);
    }
    return null;
    }

public void deleteUser (String id) {
    collection.deleteOne(eq("id", new ObjectId(id)));
}

}
