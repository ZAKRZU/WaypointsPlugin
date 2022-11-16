package com.zakrzu.waypoints.Command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.zakrzu.waypoints.SQLiteDatabase;
import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class ListCommand extends BaseCommand {

    private int pageSize;

    public ListCommand() {
        super();
        this.pageSize = 5;
        this.setCmd("list");
        this.setDescription("displays a list of waypoints");
        this.addOptionalArg("page");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        int allPages = (int) Math.ceil((double) (Waypoint.count) / (double) this.pageSize);
        int page = 0;
        try {
            if (args.length > 0)
                page = Integer.parseInt(args[0]) - 1;
        } catch (NumberFormatException e) {
            sender.sendMessage("Page must be a number!");
            return false;
        }
        if (page >= (allPages - 1))
            page = allPages - 1;
        if (page < 0)
            page = 0;

        ArrayList<Waypoint> waypoints = ((SQLiteDatabase) WaypointsPlugin.getDatabase()).getWaypoints(page * this.pageSize, this.pageSize);
        
        if (waypoints == null) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "Error occurred. Please contact server operator.");
            return true;
        }

        if (waypoints.size() < 1) {
            sender.sendMessage("No waypoints has been saved!");
            return true;
        }
        sender.sendMessage(ChatColor.YELLOW + "--------------" + ChatColor.RESET 
                        + " Waypoints (Page: " + (page + 1) + " / " + (allPages) + ") " 
                        + ChatColor.YELLOW + "--------------");
        for (Waypoint waypoint : waypoints) {
            sender.sendMessage(ChatColor.RED + "" +  waypoint.getId() 
                            + ChatColor.RESET + ". " + waypoint.getName() + " " + ChatColor.GRAY + waypoint.getRawCoordinates());
        }
        return true;
    }
}
