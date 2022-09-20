package com.zakrzu.waypoints.Command;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class WaypointCmdTrack extends WaypointCmdBase {

    private HashMap<Player, Waypoint> tracker;

    public WaypointCmdTrack() {
        super();
        this.tracker = new HashMap<>();
        this.setCmd("track");
        this.setDescription("compass will show the waypoint");
        this.addOptionalArg("id | restore");
    }

    @Override
    public boolean execute(Player sender, String[] args) {
        Waypoint wp = null;
        if (args.length < this.argsOptional.size()) {
            wp = this.tracker.get(sender);
            if (wp != null) {
                sender.sendMessage("Compass is tracking " + wp.getName() + " (" + wp.getId() + ") ");
            } else {
                sender.sendMessage("No tracking information. (But your compass may indicate a waypoint)");
                sender.sendMessage("Type 'track restore' to reset compass");
            }
            return false;
        }
        int id = -1;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            if (args[0].equals("restore")) {
                sender.setCompassTarget(sender.getWorld().getSpawnLocation());
                this.tracker.remove(sender);
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
        this.tracker.put(sender, wp);
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
