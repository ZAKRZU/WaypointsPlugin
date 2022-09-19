package com.zakrzu.waypoints.Command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class WaypointCmdList extends WaypointCmdBase {

    private int pageSize;

    public WaypointCmdList() {
        super();
        this.pageSize = 5;
        this.setCmd("list");
        this.setDescription("displays a list of waypoints");
        this.addOptionalArg("page", "Integer");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        int page = 0;
        try {
            if (args.length > 0)
                page = Integer.parseInt(args[0]) - 1;
        } catch (NumberFormatException e) {
            sender.sendMessage("Page must be a number!");
            return false;
        }
        if (page < 1)
            page = 0;
        ArrayList<Waypoint> waypoints = WaypointsPlugin.getDatabase().getWaypoints();
        int allPages = (int) Math.ceil((float) waypoints.size() / (float) this.pageSize);
        if (waypoints.size() < 1) {
            sender.sendMessage("No waypoints has been saved!");
            return true;
        }
        if (page > allPages - 1) {
            sender.sendMessage("There are only " + (allPages) + " page(s). Showing last one.");
            page = allPages - 1;
        }
        sender.sendMessage("-- Waypoints (Page: " + (page + 1) + " / " + (allPages) + ") --");
        sender.sendMessage("ID  NAME  X   Y   Z");
        for (int i = 0; i < pageSize; i++) {
            Waypoint waypoint = null;
            try {
                waypoint = waypoints.get(page * this.pageSize + i);
            } catch (Exception e) {
            }
            if (waypoint == null)
                break;
            sender.sendMessage(waypoint.getId() + ". " + waypoint.getName() + " " + waypoint.getRawCoordinates());
        }
        return true;
    }
}
