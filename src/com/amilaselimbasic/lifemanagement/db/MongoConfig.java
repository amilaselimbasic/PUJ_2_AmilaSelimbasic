package com.amilaselimbasic.lifemanagement.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {

    private static final MongoClient client = MongoClients.create ("mongodb://localhost:27017");
    private static final MongoDatabase database = client.getDatabase("lifemanagment");
    public static MongoDatabase getDatabase () {
        return database;
    }

    public static void close () {
        client.close();
    }
}