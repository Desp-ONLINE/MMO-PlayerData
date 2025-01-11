package com.binggre.mmoplayerdata.repository;

import com.binggre.binggreapi.utils.file.FileManager;
import com.binggre.mmoplayerdata.objects.MMOPlayerData;
import com.binggre.mongolibraryplugin.base.MongoCachedRepository;
import com.binggre.velocitysocketclient.VelocityClient;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerRepository extends MongoCachedRepository<UUID, MMOPlayerData> {

    private final String MMO_PREFIX = "MMO-PlayerData:";

    public PlayerRepository(Plugin plugin, String database, String collection, Map<UUID, MMOPlayerData> cache) {
        super(plugin, database, collection, cache);
    }

    private Jedis resource() {
        return VelocityClient.getInstance().getResource();
    }

    @Nullable
    public UUID getUUID(String nickname) {
        for (MMOPlayerData value : values()) {
            if (value.getNickname().equalsIgnoreCase(nickname)) {
                return value.getId();
            }
        }
        return null;
    }

    @Override
    public MMOPlayerData get(UUID uuid) {
        try (Jedis jedis = resource()) {
            String json = jedis.get(MMO_PREFIX + uuid.toString());
            return json == null ? null : FileManager.toObject(json, MMOPlayerData.class);
        }
    }

    @Override
    public MMOPlayerData remove(UUID uuid) {
        try (Jedis jedis = resource()) {
            String json = jedis.get(MMO_PREFIX + uuid.toString());
            jedis.del(MMO_PREFIX + uuid);
            return json == null ? null : FileManager.toObject(json, MMOPlayerData.class);
        }
    }

    @Override
    public void putIn(MMOPlayerData entity) {
        try (Jedis jedis = resource()) {
            String json = FileManager.toJson(entity);
            jedis.set(MMO_PREFIX + entity.getId(), json);
        }
    }

    @Override
    public Map<UUID, MMOPlayerData> getCache() {
        Map<UUID, MMOPlayerData> cache = new HashMap<>();
        for (MMOPlayerData value : values()) {
            cache.put(value.getId(), value);
        }
        return cache;
    }

    @Override
    public Collection<MMOPlayerData> values() {
        try (Jedis jedis = resource()) {
            return jedis.keys(MMO_PREFIX + "*").stream()
                    .map(key -> FileManager.toObject(jedis.get(key), MMOPlayerData.class))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Document toDocument(MMOPlayerData mmoPlayerData) {
        String json = FileManager.toJson(mmoPlayerData);
        return Document.parse(json);
    }

    @Override
    public MMOPlayerData toEntity(Document document) {
        return FileManager.toObject(document.toJson(), MMOPlayerData.class);
    }

    public MMOPlayerData init(Player player) {
        MMOPlayerData mmoPlayerData = findById(player.getUniqueId());
        if (mmoPlayerData == null) {
            mmoPlayerData = new MMOPlayerData(player);
            save(mmoPlayerData);
        }
        return mmoPlayerData;
    }

    public void init() {
        List<MMOPlayerData> all = findAll();
        for (MMOPlayerData mmoPlayerData : all) {
            putIn(mmoPlayerData);
        }
    }

    public void saveAll() {
        for (MMOPlayerData value : values()) {
            save(value);
        }
    }

    public void removeAll() {
        for (MMOPlayerData value : values()) {
            remove(value.getId());
        }
    }
}