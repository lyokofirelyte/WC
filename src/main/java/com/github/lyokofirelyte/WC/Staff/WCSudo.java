package com.github.lyokofirelyte.WC.Staff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;

import static com.github.lyokofirelyte.WCAPI.WCUtils.s;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

public class WCSudo {

	 WCMain pl;
	 public WCSudo(WCMain instance){
	 this.pl = instance;
	 }
	 
	 @WCCommand(aliases = {"sudo"}, desc = "You are the puppet master", help = "/sudo <playername>", perm = "wa.mod2")
	 public void onSudo(Player sender, String[] args){
			  			
			Player p = ((Player)sender);
			
			if (args.length < 2){
				s(p, "/sudo <player> <command>");
			} else if (Bukkit.getPlayer(args[0]) == null){
				s(p, "Player not found!");
			} else {
				Bukkit.getPlayer(args[0]).performCommand(Utils.createString(args, 1));
				s(p, "Forced " + Bukkit.getPlayer(args[0]).getDisplayName() + " &dto run &7&o" + Utils.createString(args, 1));
			}
		
		return;
	 }
}
