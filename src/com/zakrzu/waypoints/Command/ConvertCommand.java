package com.zakrzu.waypoints.Command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.zakrzu.waypoints.ConfigDatabase;
import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class ConvertCommand extends BaseCommand {

    private WaypointsPlugin plugin;

    public ConvertCommand(WaypointsPlugin instance) {
        super();
        this.setCmd("convert");
        this.setDescription("converts from config database engine to sqlite");
        this.plugin = instance;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Are you sure you want to convert ConfigDatabase to SQLite?");
            sender.sendMessage("Please make a backup before running this command");
            sender.sendMessage("Type  /wp convert yes  to continue");
            return true;
        } else if (!args[0].equals("yes")) {
            sender.sendMessage("Are you sure you want to convert ConfigDatabase to SQLite?");
            sender.sendMessage("Please make a backup before running this command");
            sender.sendMessage("Type  /wp convert yes  to continue");
            return true;
        }

        ConfigDatabase oldDb = new ConfigDatabase(this.plugin, this.plugin.getConfig());
        ArrayList<Waypoint> waypoints = oldDb.getWaypoints();
        for (Waypoint waypoint : waypoints) {
            if (!WaypointsPlugin.getDatabase().addWaypoint(waypoint))
                return false;
        }
        
        sender.sendMessage("Convertion has been completed!");
        return true;
    }
}
