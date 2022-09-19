package com.zakrzu.waypoints;

import org.bukkit.plugin.java.JavaPlugin;

import com.zakrzu.waypoints.Command.WaypointMainCommand;

public class WaypointsPlugin extends JavaPlugin {

    private static Database db;

    @Override
    public void onEnable() {
        db = new ConfigDatabase(this, this.getConfig());
        this.getCommand("waypoint").setExecutor(new WaypointMainCommand(this));
    }

    @Override
    public void onDisable() {

    }

    public static Database getDatabase() {
        return WaypointsPlugin.db;
    }

}
