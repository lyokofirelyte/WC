package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

public class WCSpawn{
	
	WCMain plugin;
	public WCSpawn(WCMain instance){
	this.plugin = instance;
	}
	
	@WCCommand(aliases = {"spawn"}, help = "Teleport back to the server spawn point", max = 0)
	public void onSpawn(Player sender, String[] args){
		  
			Player p = ((Player)sender);
			
			p.performCommand("warp spawn");
		
		return;
	}

}
