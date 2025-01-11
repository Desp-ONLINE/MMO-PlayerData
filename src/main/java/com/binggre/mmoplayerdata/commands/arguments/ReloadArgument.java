package com.binggre.mmoplayerdata.commands.arguments;

import com.binggre.binggreapi.command.CommandArgument;
import com.binggre.binggreapi.command.annotations.ArgumentOption;
import com.binggre.mmoplayerdata.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@ArgumentOption(
        arg = "reload",
        description = "/mmoplayerdata reload - config를 리로드합니다."
)
public class ReloadArgument implements CommandArgument {

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Config.getInstance().init();
        commandSender.sendMessage("리로드 완료");
        return false;
    }
}
