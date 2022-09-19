package com.zakrzu.waypoints;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Waypoint {

    public static int ids = 0;
    private int id;
    private String creator;
    private Location location;
    private String name;

    public Waypoint(String name, Player creator, Location loc) {
        this(Waypoint.ids++, name, creator, loc);
    }

    public Waypoint(int id, String name, Player creator, Location loc) {
        this.id = id;
        this.name = name;
        this.creator = creator.getName();
        this.location = loc;
    }

    public Waypoint(int id, String name, String creator, Location loc) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.location = loc;
    }

    public int getId() {
        return id;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return this.creator;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinates() {
        return "X:" + String.format("%.03f", this.location.getX()) +
                " Y:" + String.format("%.03f", this.location.getY()) +
                " Z:" + String.format("%.03f", this.location.getZ());
    }

    public String getRawCoordinates() {
        return String.format("%.03f", this.location.getX()) +
                " " + String.format("%.03f", this.location.getY()) +
                " " + String.format("%.03f", this.location.getZ());
    }

}
