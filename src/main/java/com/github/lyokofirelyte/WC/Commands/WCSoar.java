package com.github.lyokofirelyte.WC.Commands;


import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;

import static com.github.lyokofirelyte.WC.WCMain.s;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCSoar{

	WCMain pl;
	public WCSoar(WCMain instance){
	pl = instance;
	}
	
	@WCCommand(aliases = {"soar"}, help = "Soar for a short period of time", max = 0, perm = "wa.elysian")
	public void onSoar(Player sender, String[] args){
			
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
		
		return;
	}
	
}
