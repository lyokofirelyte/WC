package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

public abstract class WCSuicide implements CommandExecutor {

	WCMain pl;
	public WCSuicide(WCMain instance){
	pl = instance;
    }

	  @WCCommand(aliases = {"suicide"}, help = "Goodbye cruel world...")
	  public void onSuicide(Player sender, String[] args){
		  
		  Player p = ((Player)sender);
			  
			  p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 3F);
			  Utils.effects(p);
			  p.setHealth(0);
		  
		  return;
	  }
}
