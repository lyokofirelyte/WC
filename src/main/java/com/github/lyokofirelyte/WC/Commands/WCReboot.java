package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;

public class WCReboot implements CommandExecutor {
	
	WCMain pl;
	
	public WCReboot(WCMain instance){
    this.pl = instance;
	}
	
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		if (cmd.getName().equalsIgnoreCase("reboot")){
			if (!sender.hasPermission("wa.admin")){
				WCMain.s(((Player)sender), "You don't have permission!");
			} else {
				pl.rm.wcReboot();
			}
		}
		return true;
	}
}
