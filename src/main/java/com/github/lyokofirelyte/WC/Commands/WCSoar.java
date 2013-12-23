package com.github.lyokofirelyte.WC.Commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WC.WCMain.s;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCSoar implements CommandExecutor {

	WCMain pl;
	public WCSoar(WCMain instance){
	pl = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (cmd.getName().toLowerCase().equals("soar")){
			
			Player p = ((Player)sender);
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			
			if (wcp.getCanSoar()){
				wcp.setCanSoar(false);
				wcp.setSoarTimer((System.currentTimeMillis() / 1000L)+60L);
				wcp.setCanSoarTimer((System.currentTimeMillis() / 1000L)+600L);
				pl.wcm.updatePlayerMap(p.getName(), wcp);
				p.setAllowFlight(true);
				p.setFlying(true);
				Utils.effects(p);
				s(p, "Soaring for 60 seconds!");
			} else {
				long timeLeft = (wcp.getCanSoarTimer() - (System.currentTimeMillis()/1000L));
				s(p, "You can soar again in " + timeLeft + " &dseconds.");
			}
		}
		
		return true;
	}
	
}
