package com.zakrzu.waypoints.Command;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WaypointCmdBase {

    protected String cmd;
    protected String description;
    protected HashMap<String, String> args;
    protected HashMap<String, String> argsOptional;

    public WaypointCmdBase() {
        this.args = new HashMap<>();
        this.argsOptional = new HashMap<>();
    }

    public void setCmd(String newCmd) {
        this.cmd = newCmd;
    }

    public String getCmd() {
        return this.cmd;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void addArg(String argName, String argType) {
        this.args.put(argName, argType);
    }

    public HashMap<String, String> getArgs() {
        return this.args;
    }

    public void addOptionalArg(String argName, String argType) {
        this.argsOptional.put(argName, argType);
    }

    public HashMap<String, String> getOptionalArgs() {
        return this.argsOptional;
    }

    public boolean execute(Player sender, String[] args) {
        sender.sendMessage("You need to implement this command");
        return true;
    }

    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("You need to implement this command");
        return true;
    }

}
