package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

public class WCSuicide {

	WCMain pl;
	
	public WCSuicide(WCMain instance){
		pl = instance;
    }

	  @WCCommand(aliases = {"suicide"}, help = "/suicide", desc = "Goodbye world!", player = true)
	  public void onSuicide(Player sender, String[] args){
		  
		  Player p = ((Player)sender);
			  
			  p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 3F);
			  Utils.effects(p);
			  p.setHealth((double)0);
		  
		  return;
	  }
}
