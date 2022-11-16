package com.zakrzu.waypoints;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigDatabase {

    private ArrayList<Waypoint> waypoints;
    private FileConfiguration config;

    public ConfigDatabase(WaypointsPlugin instance, FileConfiguration config) {
        this.waypoints = new ArrayList<>();
        this.config = config;
        this.loadWaypoints();
        instance.saveConfig();
    }

    public void loadWaypoints() {
        Set<String> locations = null;
        try {
            locations = this.config.getConfigurationSection("WAYPOINTS").getKeys(false);
        } catch (NullPointerException e) {
            return;
        }
        for (String str_id : locations) {
            int id = Integer.parseInt(str_id);
            String name = this.config.getString("WAYPOINTS." + id + ".NAME");
            String creator = this.config.getString("WAYPOINTS." + id + ".CREATOR");
            Location location = this.config.getLocation("WAYPOINTS." + id + ".LOCATION");
            Waypoint waypoint = new Waypoint(id, name, creator, location);
            waypoints.add(waypoint);
        }

    }

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

}
