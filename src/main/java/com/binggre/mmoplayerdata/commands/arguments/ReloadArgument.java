package com.binggre.mmoplayerdata.commands.arguments;

import com.binggre.binggreapi.command.CommandArgument;
import com.binggre.mmoplayerdata.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadArgument implements CommandArgument {

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Config.getInstance().init();
        commandSender.sendMessage("리로드 완료");
        return false;
    }

    @Override
    public String getArg() {
        return "reload";
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "- config를 리로드합니다.";
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public String getPermissionMessage() {
        return "";
    }

    @Override
    public boolean onlyPlayer() {
        return false;
    }
}
