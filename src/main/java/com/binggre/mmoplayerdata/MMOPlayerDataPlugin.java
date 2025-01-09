package com.binggre.mmoplayerdata;

import com.binggre.binggreapi.BinggrePlugin;
import com.binggre.mmoplayerdata.listener.PlayerListener;
import com.binggre.mmoplayerdata.repository.PlayerRepository;
import lombok.Getter;

@Getter
public final class MMOPlayerDataPlugin extends BinggrePlugin {

    @Getter
    private static MMOPlayerDataPlugin instance = null;

    private PlayerRepository playerRepository;

    @Override
    public void onEnable() {
        instance = this;
        playerRepository = new PlayerRepository(this, "MMO-PlayerData", "Player", null);
        playerRepository.init();

        registerEvents(this, new PlayerListener());
    }

    @Override
    public void onDisable() {
        playerRepository.saveAll();
        playerRepository.removeAll();
    }
}
