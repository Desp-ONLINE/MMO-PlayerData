package com.binggre.mmoplayerdata.objects;

import com.binggre.mmoplayerdata.config.Config;
import com.binggre.mongolibraryplugin.base.MongoData;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class MMOPlayerData implements MongoData<UUID> {

    private final UUID id;
    private String nickname;
    private String serverName;
    private LocalDateTime lastJoinDate;
    private LocalDateTime lastQuitDate;

    public MMOPlayerData(Player player) {
        this.id = player.getUniqueId();
        this.nickname = player.getName();
        updateLastJoinDate();
        updateLastQuitDate();
    }

    public void updateServerName(int port) {
        this.serverName = Config.getInstance().getServerName(Bukkit.getServer().getPort());
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