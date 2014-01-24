package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;

public class WCReboot{
	
	WCMain pl;
	
	public WCReboot(WCMain instance){
    this.pl = instance;
	}
	
	@WCCommand(aliases = {"reboot"}, help = "Reboot the server", perm = "wa.admin")
	public void onReboot(Player sender, String[] args){
		  
			if (!sender.hasPermission("wa.admin")){
				WCUtils.s(((Player)sender), "You don't have permission!");
			} else {
				pl.rm.wcReboot();
				WCSystem wcs = pl.wcm.getWCSystem("system");
				wcs.setRebooting(true);
				pl.wcm.updateSystem("system", wcs);
				
				for (Player p : Bukkit.getOnlinePlayers()){
					Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
				}

			}
			
		return;
	}
}
