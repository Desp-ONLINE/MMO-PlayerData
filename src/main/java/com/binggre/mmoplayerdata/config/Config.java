package com.binggre.mmoplayerdata.config;

import com.binggre.binggreapi.utils.file.FileManager;
import com.binggre.mmoplayerdata.MMOPlayerDataPlugin;
import com.binggre.mongolibraryplugin.MongoLibraryPlugin;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public String getServerName(int bukkitServerPort) {
        return serverNames.get(bukkitServerPort);
    }

    public void init() {
        MongoCollection<Document> collection = MongoLibraryPlugin.getInst().getMongoClient()
                .getDatabase(MMOPlayerDataPlugin.DATA_BASE_NAME)
                .getCollection("Config");

        Document document = collection.find().first();
        instance = FileManager.toObject(Objects.requireNonNull(document).toJson(), Config.class);
    }
}