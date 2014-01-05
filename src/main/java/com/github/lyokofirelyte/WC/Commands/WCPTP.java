package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.WCMain;

import static com.github.lyokofirelyte.WC.WCMain.s;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPatrol;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCPTP{

	WCMain pl;
	public WCPTP(WCMain instance){
	pl = instance;
    }
	
	@WCCommand(aliases = {"ptp"}, help = "WC Patrol TP command")
	public void onPTP(Player sender, String[] args){
					
			Player p = ((Player)sender);
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			
			if (args.length != 1){
				s(p, "/ptp <player>");
				return;
			}
			
			for (ItemStack i : wcp.getPatrolActives()){
				if (i != null && Utils.dispName(i, "Teleport Cube")){
					if (wcp.getPatrol() != null){
						final WCPatrol wcpp = pl.wcm.getWCPatrol(wcp.getPatrol());
						if (Bukkit.getPlayer(args[0]) != null){
							WCPlayer wcpCurrent = pl.wcm.getWCPlayer(args[0]);
							if (wcpCurrent.getPatrol() != null && wcpCurrent.getPatrol().equals(wcp.getPatrol())){
								if (wcpp.getCanTp()){
									wcpp.setCanTp(false);
									p.teleport(Bukkit.getPlayer(args[0]));
									Utils.effects(p);
									Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
									public void run() { tpAllow(wcpp);} }, 6000L);
									pl.wcm.updatePatrol(wcpp.getName(), wcpp);
								} else {
									s(p, "5 minute cooldown is active (patrol wide).");
								}
							}
						}
					}
					return;
				}
			}
			s(p, "You're missing either a teleport cube, being in a patrol, or the other player being online.");

		return;
	}
	
	public void tpAllow(WCPatrol wcpp){
		wcpp.setCanTp(true);
		pl.wcm.updatePatrol(wcpp.getName(), wcpp);
		for (String s : wcpp.getMembers()){
			if (Bukkit.getPlayer(s) != null){
				s(Bukkit.getPlayer(s), "Your patrol's TP cooldown is finished!");
			}
		}
	}
}
