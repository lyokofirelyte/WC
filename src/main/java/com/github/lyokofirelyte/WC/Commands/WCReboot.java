package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;

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
				WCSystem wcs = pl.wcm.getWCSystem("system");
				wcs.setRebooting(true);
				pl.wcm.updateSystem("system", wcs);
				
				for (Player p : Bukkit.getOnlinePlayers()){
					Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
				}

			}
		}
		return true;
	}
}
