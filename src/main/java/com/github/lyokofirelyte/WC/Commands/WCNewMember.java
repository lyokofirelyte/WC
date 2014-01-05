package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

public class WCNewMember{
	
	public WCMain main;
	
	public WCNewMember(WCMain main){
		
		this.main = main;
		
	}
	
	@WCCommand(aliases={"newmember", "nm"}, help = "Initiate a new member", min = 1, max = 1, perm = "wa.staff")
	public void onNewMember(Player sender, String[] args){
			
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
			
			return;
		
	}
	
}
