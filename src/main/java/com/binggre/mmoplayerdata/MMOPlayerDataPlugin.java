package com.binggre.mmoplayerdata;

import com.binggre.binggreapi.BinggrePlugin;
import com.binggre.mmoplayerdata.commands.AdminCommand;
import com.binggre.mmoplayerdata.config.Config;
import com.binggre.mmoplayerdata.listener.PlayerListener;
import com.binggre.mmoplayerdata.repository.PlayerRepository;
import lombok.Getter;

@Getter
public final class MMOPlayerDataPlugin extends BinggrePlugin {

    public static final String DATA_BASE_NAME = "MMO-PlayerData";

    @Getter
    private static MMOPlayerDataPlugin instance = null;

    private PlayerRepository playerRepository;

    @Override
    public void onEnable() {
        instance = this;
        Config.getInstance().init();

        playerRepository = new PlayerRepository(this, DATA_BASE_NAME, "Player", null);
        playerRepository.init();

        executeCommand(this, new AdminCommand());
        registerEvents(this, new PlayerListener());
    }

    @Override
    public void onDisable() {
        playerRepository.saveAll();
        playerRepository.removeAll();
    }
}
