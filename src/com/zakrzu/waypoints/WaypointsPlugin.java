package com.zakrzu.waypoints;

import org.bukkit.plugin.java.JavaPlugin;

import com.zakrzu.waypoints.Command.WaypointMainCommand;

public class WaypointsPlugin extends JavaPlugin {

    private static Database db;

    @Override
    public void onEnable() {
        db = new ConfigDatabase(this, this.getConfig());
        WaypointMainCommand mainCmd = new WaypointMainCommand(this);
        this.getCommand("waypoint").setExecutor(mainCmd);
        this.getCommand("waypoint").setTabCompleter(mainCmd.getTabCompleter());
    }

    @Override
    public void onDisable() {

    }

    public static Database getDatabase() {
        return WaypointsPlugin.db;
    }

}
