package com.binggre.mmoplayerdata.objects;

import com.binggre.mongolibraryplugin.base.MongoData;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class MMOPlayerData implements MongoData<UUID> {

    private final UUID id;
    private String nickname;
    private final String serverName;
    private LocalDateTime lastJoinDate;
    private LocalDateTime lastQuitDate;

    public MMOPlayerData(Player player) {
        this.id = player.getUniqueId();
        this.nickname = player.getName();
        this.serverName = player.getServer().getName();
        updateLastJoinDate();
        updateLastQuitDate();
    }

    public void updateNickname(Player player) {
        if (player.getUniqueId() != this.id) return;
        if (player.getName().equals(nickname)) return;
        this.nickname = player.getName();
    }

    public void updateLastJoinDate() {
        lastJoinDate = LocalDateTime.now();
    }

    public void updateLastQuitDate() {
        lastQuitDate = LocalDateTime.now();
    }
}