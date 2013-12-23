package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;

public class WCWB implements CommandExecutor {

	WCMain pl;
	public WCWB(WCMain instance){
	pl = instance;
    }

	  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  switch (cmd.getName().toLowerCase()){
		  		case "workbench": case "wb": ((Player)sender).openWorkbench(null, true); break;
		  }
		  return true;
	  }
}
