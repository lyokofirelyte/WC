package com.github.lyokofirelyte.WC.Staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WC.WCMain.s;

import com.github.lyokofirelyte.WC.Util.Utils;

public class WCSudo implements CommandExecutor {

	 WCMain pl;
	 public WCSudo(WCMain instance){
	 this.pl = instance;
	 }
	 
	 public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
			  
		if (cmd.getName().equalsIgnoreCase("sudo")){
			
			Player p = ((Player)sender);
			
			if (args.length < 2){
				s(p, "/sudo <player> <command>");
			} else if (Bukkit.getPlayer(args[0]) == null){
				s(p, "Player not found!");
			} else {
				Bukkit.getPlayer(args[0]).performCommand(Utils.createString(args, 1));
				s(p, "Forced " + Bukkit.getPlayer(args[0]).getDisplayName() + " &dto run &7&o" + Utils.createString(args, 1));
			}
		}
		
		return true;
	 }
}
