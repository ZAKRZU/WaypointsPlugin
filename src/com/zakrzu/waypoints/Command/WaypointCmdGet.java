package com.zakrzu.waypoints.Command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class WaypointCmdGet extends WaypointCmdBase {
    public WaypointCmdGet() {
        super();
        this.setCmd("get");
        this.setDescription("displays details about waypoint");
        this.addArg("id | name", "Mixed");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < this.args.size()) {
            sender.sendMessage("No word has been specified!");
            return false;
        }
        ArrayList<Waypoint> waypointsList = new ArrayList<>();
        try {
            int id = Integer.parseInt(args[0]);
            Waypoint wp = WaypointsPlugin.getDatabase().getById(id);
            if (wp != null)
                waypointsList.add(WaypointsPlugin.getDatabase().getById(id));
        } catch (NumberFormatException e) {
            String word = args[0];
            waypointsList = WaypointsPlugin.getDatabase().getByName(word);
        }

        if (waypointsList.size() < 1) {
            sender.sendMessage("Waypoint not found");
            return true;
        } else if (waypointsList.size() == 1) {
            sender.sendMessage("-- Waypoint --");
            sender.sendMessage("Id: " + waypointsList.get(0).getId());
            sender.sendMessage("Name: " + waypointsList.get(0).getName());
            sender.sendMessage("Location: " + waypointsList.get(0).getCoordinates());
            sender.sendMessage("Creator: " + waypointsList.get(0).getCreator());
        } else {
            sender.sendMessage("-- Waypoints (" + waypointsList.size() + ") --");
            for (Waypoint waypoint : waypointsList) {
                sender.sendMessage(waypoint.getId() + ". " + waypoint.getName() + " " + waypoint.getRawCoordinates());
            }
        }
        return true;
    }
}
