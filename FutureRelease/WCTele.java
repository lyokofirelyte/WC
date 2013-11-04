package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.lyokofirelyte.WC.WCMain;

public class WCTele implements CommandExecutor {

	WCMain pl;
	public WCTele(WCMain instance){
    this.pl = instance;
	}
	
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		switch (cmd.getName()){
		
			case "tp":
				
			break;
			
			case "tphere":
				
			break;
			
			case "tpa":
				
			break;
			
			case "tpahere":
				
			break;
			
			case "tpaall":
				
			break;
			
			case "tpall":
				
			break;
		
		}
		
		return true;
	}
}
