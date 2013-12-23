package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;

public class WCSuicide implements CommandExecutor {

	WCMain pl;
	public WCSuicide(WCMain instance){
	pl = instance;
    }

	  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		  Player p = ((Player)sender);
		  
		  if (cmd.getName().toLowerCase().equals("suicide")){
			  
			  p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 3F);
			  Utils.effects(p);
			  p.setHealth(0);
		  }
		  
		  return true;
	  }
}
