package com.zakrzu.waypoints.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class VersionCommand extends BaseCommand {

    private PluginDescriptionFile pdf;

    public VersionCommand(PluginDescriptionFile pdf) {
        super();
        this.pdf = pdf;
        this.setCmd("version");
        this.setDescription("displays plugin information");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "--------------" + ChatColor.RESET 
                        + " [" + ChatColor.GOLD + "Waypoints"+ ChatColor.RESET +"] " 
                        + ChatColor.YELLOW + "--------------");
        sender.sendMessage("Version: " + ChatColor.RED + this.pdf.getVersion());
        sender.sendMessage("API Version: " + ChatColor.DARK_AQUA + ChatColor.MAGIC + "##" 
                        + ChatColor.RESET + this.pdf.getAPIVersion() 
                        + ChatColor.DARK_RED + ChatColor.MAGIC + "##");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Created by talented ZAKRZU (●'◡'●)");
        return true;
    }
}
