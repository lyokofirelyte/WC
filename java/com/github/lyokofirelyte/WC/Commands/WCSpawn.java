package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;

public class WCSpawn implements CommandExecutor {
	
	WCMain plugin;
	public WCSpawn(WCMain instance){
	this.plugin = instance;
	}
	
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		if (cmd.getName().equalsIgnoreCase("spawn") || cmd.getName().equalsIgnoreCase("s")){
			Player p = ((Player)sender);
			
			p.performCommand("warp spawn");
		}
		
		return true;
	}

}
