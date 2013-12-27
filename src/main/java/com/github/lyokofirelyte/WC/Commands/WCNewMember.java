package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;

public class WCNewMember implements CommandExecutor {
	
	public WCMain main;
	
	public WCNewMember(WCMain main){
		
		this.main = main;
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if ((label.equalsIgnoreCase("newmember") || label.equalsIgnoreCase("nm")) && sender.hasPermission("wa.staff")){
			
			Player p = (Player) sender;
			
			if (args.length != 1){
				
				WCMain.s(p, "/newmember <player>");
				
			} else {
				
				/*if (args[0].equals("test")){
					
					this.main.wcm.displayGui(p, new GuiPlayerSelection(this.main, new Runnable(){
						
						public void run(){
							
							// Nothing
							
						}
						
					}, true, args[1]));
					
					return true;
					
				}*/
				
				String player = args[0];
				
				Utils.bc("Welcome &7" + player + " &dto Worlds Apart Members!");
				p.performCommand("pex user " + player + " group add Member");
				p.performCommand("pex user " + player + " group remove Guest");
				
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
}
