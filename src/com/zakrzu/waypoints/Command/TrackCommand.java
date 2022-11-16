package com.zakrzu.waypoints.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zakrzu.waypoints.Tracker;
import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class TrackCommand extends BaseCommand {

    private Tracker tracker;

    public TrackCommand() {
        super();
        this.tracker = WaypointsPlugin.getTracker();
        this.setCmd("track");
        this.setDescription("compass will show the waypoint");
        this.addOptionalArg("id | restore");
    }

    @Override
    public boolean execute(Player sender, String[] args) {
        int id = -1;
        Waypoint wp = this.tracker.getTracker(sender);

        if (args.length < this.getOptionalArgs().size()) {
            if (wp != null)
                sender.sendMessage(WaypointsPlugin.PREFIX + "The compass shows direction to the " + wp.getName() + "(" + wp.getId() + ")" + " waypoint");
            else {
                sender.sendMessage(WaypointsPlugin.PREFIX + "Compass does not track any waypoint.");
                sender.sendMessage(WaypointsPlugin.PREFIX + "Type 'track restore' to reset compass");
            }
            
            return true;
        }

        if (args[0].equals("restore")) {
            sender.setCompassTarget(sender.getWorld().getSpawnLocation());
            this.tracker.removeTracker(sender);
            sender.sendMessage(WaypointsPlugin.PREFIX + "Compass is now showing spawn location");
            return true;
        }

        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "Waypoint id must be a number!");
            return false;
        }

        wp = WaypointsPlugin.getDatabase().getById(id);
        if (wp == null) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "Waypoint with id " + id + " does not exist!");
            return false;
        }
        
        sender.setCompassTarget(wp.getLocation());
        this.tracker.addTracker(sender, wp);
        sender.sendMessage(WaypointsPlugin.PREFIX + "Compass is now showing direction to the " + wp.getName() + "(" + wp.getId() + ")" + " waypoint");
        return true;

    }

    public boolean execute2(Player sender, String[] args) {
        Waypoint wp = null;
        int id = -1;

        if (args.length < this.argsOptional.size()) {
            wp = this.tracker.getTracker(sender);

            if (wp != null) {
                sender.sendMessage("Compass is tracking " + wp.getName() + " (" + wp.getId() + ") ");
            } else {
                sender.sendMessage("No tracking information.");
                sender.sendMessage("Type 'track restore' to reset compass");
            }

            return false;
        }
        
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            if (args[0].equals("restore")) {
                sender.setCompassTarget(sender.getWorld().getSpawnLocation());
                this.tracker.removeTracker(sender);
                sender.sendMessage("Compass is now targeting spawn point");
                return true;
            }
            sender.sendMessage("ID must me a number");
            sender.sendMessage("If you want to restore spawn position type restore instead ID");
            return false;
        }
        wp = WaypointsPlugin.getDatabase().getById(id);
        if (wp == null) {
            sender.sendMessage("Waypoint "+ id + " does not exist!");
            return true;
        }
        sender.setCompassTarget(wp.getLocation());
        this.tracker.addTracker(sender, wp);
        sender.sendMessage("Compass is now showing waypoint " + wp.getName() + " ("+ wp.getId() +") ");
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return this.execute(player, args);
        } else {
            sender.sendMessage("This command is for use ingame only!");
        }
        return true;
    }
}
