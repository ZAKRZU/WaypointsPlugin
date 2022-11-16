package com.zakrzu.waypoints;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.zakrzu.waypoints.Event.PlayerLeaveEvent;

public class WaypointsPlugin extends JavaPlugin {

    private static Database db;
    private static Tracker tracker;
    public static String PREFIX = ChatColor.YELLOW + "[Waypoints] " + ChatColor.RESET;

    @Override
    public void onEnable() {
        WaypointsPlugin.db = new SQLiteDatabase(this);
        WaypointsPlugin.tracker = new Tracker();
        WaypointCommand mainCmd = new WaypointCommand(this);
        this.getCommand("waypoint").setExecutor(mainCmd);
        this.getCommand("waypoint").setTabCompleter(mainCmd.getTabCompleter());
        this.getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(), this);
    }

    @Override
    public void onDisable() {

    }

    public static Database getDatabase() {
        return WaypointsPlugin.db;
    }

    public static Tracker getTracker() {
        return WaypointsPlugin.tracker;
    }

}
