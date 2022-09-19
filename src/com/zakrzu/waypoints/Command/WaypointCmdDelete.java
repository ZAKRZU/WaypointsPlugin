package com.zakrzu.waypoints.Command;

import org.bukkit.command.CommandSender;

import com.zakrzu.waypoints.WaypointsPlugin;

public class WaypointCmdDelete extends WaypointCmdBase {
    public WaypointCmdDelete() {
        super();
        this.setCmd("delete");
        this.setDescription("removes a waypoint from the list");
        this.addArg("id", "Integer");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < this.args.size()) {
            sender.sendMessage("No ID has been specified!");
            return false;
        }
        int id = -1;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Id must be a number!");
            return false;
        }
        if (WaypointsPlugin.getDatabase().removeWaypoint(id)) {
            sender.sendMessage("Waypoint " + id + " has been deleted");
        } else {
            sender.sendMessage("Waypoint " + id + " does not exist!");
        }
        return true;
    }
}
