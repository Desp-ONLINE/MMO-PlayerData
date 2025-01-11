package com.binggre.mmoplayerdata.commands;

import com.binggre.binggreapi.command.BetterCommand;
import com.binggre.binggreapi.command.CommandArgument;
import com.binggre.mmoplayerdata.commands.arguments.ReloadArgument;

import java.util.List;

public class AdminCommand extends BetterCommand {

    @Override
    public String getCommand() {
        return "mmoplayerdata";
    }

    @Override
    public boolean isSingleCommand() {
        return false;
    }

    @Override
    public List<CommandArgument> getArguments() {
        return List.of(new ReloadArgument());
    }
}