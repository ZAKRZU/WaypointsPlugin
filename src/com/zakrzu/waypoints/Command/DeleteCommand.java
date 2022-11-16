package com.zakrzu.waypoints.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.zakrzu.waypoints.WaypointsPlugin;

public class DeleteCommand extends BaseCommand {
    public DeleteCommand() {
        super();
        this.setCmd("delete");
        this.setDescription("removes a waypoint from the list");
        this.addArg("id");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < this.args.size()) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "No ID has been specified!");
            return false;
        }
        int id = -1;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "Id must be a number!");
            return false;
        }
        if (WaypointsPlugin.getDatabase().removeWaypoint(id)) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.DARK_GREEN + "Waypoint " + id + " has been deleted");
        } else {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "Waypoint " + id + " does not exist!");
        }
        return true;
    }
}
