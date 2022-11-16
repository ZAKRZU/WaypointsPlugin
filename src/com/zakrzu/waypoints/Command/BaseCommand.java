package com.zakrzu.waypoints.Command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCommand {

    protected String cmd;
    protected String description;
    protected ArrayList<String> args;
    protected ArrayList<String> argsOptional;

    public BaseCommand() {
        this.args = new ArrayList<>();
        this.argsOptional = new ArrayList<>();
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

    public void addArg(String argName) {
        this.args.add(argName);
    }

    public ArrayList<String> getArgs() {
        return this.args;
    }

    public void addOptionalArg(String argName) {
        this.argsOptional.add(argName);
    }

    public ArrayList<String> getOptionalArgs() {
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
