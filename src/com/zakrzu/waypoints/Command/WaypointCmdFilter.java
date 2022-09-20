package com.zakrzu.waypoints.Command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class WaypointCmdFilter extends WaypointCmdBase {

    public WaypointCmdFilter() {
        super();
        this.setCmd("filter");
        this.setDescription("lists waypoints containing the word");
        this.addArg("word");
        this.addOptionalArg("case sensitive");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        boolean caseSensitive = true;
        if (args.length < this.args.size()) {
            sender.sendMessage("No word has been specified!");
            return false;
        }
        if (args.length >= this.args.size() + this.argsOptional.size()) {
            caseSensitive = Boolean.parseBoolean(args[1]);
        }
        String word = args[0];
        ArrayList<Waypoint> list = WaypointsPlugin.getDatabase().getFiltred(word, caseSensitive);

        if (list.size() < 1) {
            sender.sendMessage("Waypoints not found");
            return true;
        }
        sender.sendMessage("-- Waypoints --");
        for (Waypoint waypoint : list) {
            sender.sendMessage(waypoint.getId() + ". " + waypoint.getName() + " " + waypoint.getCoordinates());
        }
        return true;
    }
}
