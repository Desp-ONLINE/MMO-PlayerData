package com.binggre.mmoplayerdata.listener;

import com.binggre.mmoplayerdata.MMOPlayerDataPlugin;
import com.binggre.mmoplayerdata.objects.MMOPlayerData;
import com.binggre.mmoplayerdata.repository.PlayerRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final PlayerRepository repository = MMOPlayerDataPlugin.getInstance().getPlayerRepository();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MMOPlayerData init = repository.init(player);
        init.updateLastJoinDate();
        init.updateNickname(player);
        repository.putIn(init);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uniqueId = event.getPlayer().getUniqueId();
        MMOPlayerData mmoPlayerData = repository.remove(uniqueId);
        if (mmoPlayerData != null) {
            mmoPlayerData.updateLastQuitDate();
            repository.saveAsync(mmoPlayerData);
        }
    }
}