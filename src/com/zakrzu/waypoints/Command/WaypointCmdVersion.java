package com.zakrzu.waypoints.Command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class WaypointCmdVersion extends WaypointCmdBase {

    private PluginDescriptionFile pdf;

    public WaypointCmdVersion(PluginDescriptionFile pdf) {
        super();
        this.pdf = pdf;
        this.setCmd("version");
        this.setDescription("displays plugin information");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("[Waypoints plugin]");
        sender.sendMessage("Version: " + this.pdf.getVersion());
        sender.sendMessage("API Version: " + this.pdf.getAPIVersion());
        sender.sendMessage("Created by talented ZAKRZU (●'◡'●)");
        return false;
    }
}
