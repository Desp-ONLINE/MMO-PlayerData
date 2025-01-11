package com.binggre.mmoplayerdata.config;

import com.binggre.binggreapi.utils.file.FileManager;
import com.binggre.mmoplayerdata.MMOPlayerDataPlugin;
import com.binggre.mongolibraryplugin.MongoLibraryPlugin;
import com.binggre.velocitysocketclient.VelocityClient;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Config {

    private static Config instance = null;

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    // K port, V name
    private Map<Integer, String> serverNames;

    public void init() {
        MongoCollection<Document> collection = MongoLibraryPlugin.getInst().getMongoClient()
                .getDatabase(MMOPlayerDataPlugin.DATA_BASE_NAME)
                .getCollection("Config");

        Document document = collection.find().first();
        if (document == null) {
            document = new Document();
            Map<Integer, String> serverNames = new HashMap<>();
            serverNames.put(30066, "로비");
            serverNames.put(30067, "던전");
            document.append("serverNames", serverNames);
            collection.insertOne(document);
        }

        instance = FileManager.toObject(document.toJson(), Config.class);
    }
}