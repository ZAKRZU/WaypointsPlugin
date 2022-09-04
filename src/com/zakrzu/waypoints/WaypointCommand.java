package com.zakrzu.waypoints;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class WaypointCommand implements CommandExecutor {

    private int pageSize = 5;
    private WaypointsPlugin pluginInstance;

    public WaypointCommand(WaypointsPlugin instance) {
        this.pluginInstance = instance;
    }

    public void addWaypoint(String name, Player creator, Location loc) {
        Waypoint newWaypoint = new Waypoint(name, creator, loc);
        pluginInstance.db.addWaypoint(newWaypoint);
        creator.sendMessage("Waypoint \"" + name + "\" added with id: " + newWaypoint.getId());
        creator.sendMessage(newWaypoint.getCoordinates());
    }

    public void displayWaypoints(Player player) {
        this.displayWaypoints(player, 0);
    }

    public void displayWaypoints(Player player, int page) {
        page = page - 1;
        if (page < 1)
            page = 0;
        ArrayList<Waypoint> waypoints = pluginInstance.db.getWaypoints();
        if (waypoints.size() < 1) {
            player.sendMessage("No waypoints has been saved!");
            return;
        }
        player.sendMessage("-- Waypoints (Page: " + (page + 1) + ") --");
        player.sendMessage("ID  NAME  X   Y   Z");
        for (int i = 0; i < pageSize; i++) {
            Waypoint waypoint = null;
            try {
                waypoint = waypoints.get(page * this.pageSize + i);
            } catch (Exception e) {
            }
            if (waypoint == null)
                break;
            player.sendMessage(waypoint.getId() + ". " + waypoint.getName() + " " + waypoint.getRawCoordinates());
        }
    }

    public void displayWaypoints(Player player, ArrayList<Waypoint> list) {
        if (list.size() < 1) {
            player.sendMessage("Waypoints not found");
            return;
        }
        player.sendMessage("-- Waypoints --");
        for (Waypoint waypoint : list) {
            player.sendMessage(waypoint.getId() + ". " + waypoint.getName() + " " + waypoint.getCoordinates());
        }
    }

    public void displayWaypointDetails(Player player, Waypoint saved) {
        if (saved == null) {
            player.sendMessage("Waypoint not found");
            return;
        }
        player.sendMessage("-- Waypoint --");
        player.sendMessage("Id: " + saved.getId());
        player.sendMessage("Name: " + saved.getName());
        player.sendMessage("Location: " + saved.getCoordinates());
        player.sendMessage("Creator: " + saved.getCreator());
    }

    public void displayHelp(Player player) {
        PluginDescriptionFile pdf = pluginInstance.getDescription();
        player.sendMessage("[Waypoints " + pdf.getVersion() + " by ZAKRZU] -- Showing help");
        player.sendMessage("/waypoint list <page>- displays a list of waypoints");
        player.sendMessage("/waypoint get <id|name> - displays details about the waypoint");
        player.sendMessage("/waypoint filter <word> - lists waypoints containing the word");
        player.sendMessage("/waypoint add <name> - adds current position to the list");
        player.sendMessage("/waypoint delete <id> - removes a waypoint from the list");
        player.sendMessage("/waypoint version - Displays the plugin version");
    }

    public void displayVersion(Player player) {
        PluginDescriptionFile pdf = pluginInstance.getDescription();
        player.sendMessage("[Waypoints plugins]");
        player.sendMessage("Version: "+pdf.getVersion());
        player.sendMessage("API Version: "+pdf.getAPIVersion());
        player.sendMessage("Created by talented ZAKRZU (●'◡'●)");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only ingame use!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length < 1)
            displayHelp(player);

        else if (args.length < 2) {
            if (args[0].contains("list")) {
                this.displayWaypoints(player);
            } else if (args[0].equals("version")) {
                this.displayVersion(player);
            } else {
                player.sendMessage("Not enough arguments!");
                return false;
            }
        } else if (args.length < 3) {
            int id = -1;
            String name = "";
            switch (args[0]) {
                case "add":
                    name = args[1];
                    this.addWaypoint(name, player, player.getLocation());
                    break;
                case "get":
                    try {
                        id = Integer.parseInt(args[1]);
                        this.displayWaypointDetails(player, pluginInstance.db.getById(id));
                    } catch (NumberFormatException e) {
                        name = args[1];
                        this.displayWaypoints(player, pluginInstance.db.getByName(name));
                    }
                    break;
                case "delete":
                    try {
                        id = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        return true;
                    }
                    if (pluginInstance.db.removeWaypoint(id)) {
                        player.sendMessage("Waypoint " + id + " has been deleted");
                    } else {
                        player.sendMessage("Waypoint " + id + " cannot be deleted.");
                    }
                    break;
                case "list":
                    int page = 0;
                    try {
                        page = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        return true;
                    }
                    this.displayWaypoints(player, page);
                    break;
                case "filter":
                    String word = args[1];
                    this.displayWaypoints(player, pluginInstance.db.getFiltred(word));
                    break;
                default:
                    break;
            }
        }

        return true;
    }

}
