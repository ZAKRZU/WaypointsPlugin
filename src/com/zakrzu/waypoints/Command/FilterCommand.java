package com.zakrzu.waypoints.Command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class FilterCommand extends BaseCommand {

    public FilterCommand() {
        super();
        this.setCmd("filter");
        this.setDescription("lists waypoints containing the word");
        this.addArg("word");
        this.addOptionalArg("case sensitive");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        boolean caseSensitive = true;
        if (args.length < this.args.size()) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "No filter word has been specified!");
            return false;
        }
        if (args.length >= this.args.size() + this.argsOptional.size()) {
            caseSensitive = Boolean.parseBoolean(args[1]);
        }
        String word = args[0];
        ArrayList<Waypoint> list = WaypointsPlugin.getDatabase().getFiltred(word, caseSensitive);

        if (list.size() < 1) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "Waypoints not found");
            return true;
        }
        sender.sendMessage(ChatColor.YELLOW + "--------------" + ChatColor.RESET 
                        + " [" + ChatColor.GOLD + "Waypoint"+ ChatColor.RESET +"] " 
                        + ChatColor.YELLOW + "--------------");
        for (Waypoint waypoint : list) {
            sender.sendMessage(ChatColor.DARK_RED + "" + waypoint.getId() + ". " 
                            + ChatColor.RESET + waypoint.getName() + " " 
                            + ChatColor.GRAY + waypoint.getCoordinates());
        }
        return true;
    }
}
