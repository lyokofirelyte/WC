package com.github.lyokofirelyte.WC.Staff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class WCTele {

	WCMain pl;
	public WCTele(WCMain instance){
    this.pl = instance;
	}
	
	String checks = ":tp: :tphere: :tpa: :tpahere: :tpaall: :tpall:";
	String que;
	WCPlayer wcp;
	WCPlayer wcpCurrent;
	Player q;
	
	@WCCommand(aliases = {"tp", "tphere", "tpa", "tpahere"}, desc = "WC TP Command", help = "/tp, /tphere, /tpa, /tpahere",  name = "TP", player = true)
	public void onTP(Player p, String[] args, String cmd){

			wcp = pl.wcm.getWCPlayer(p.getName());
			que = cmd.toLowerCase();
			
			
			if (que.equals("tpa")){
				if (!wcp.getTpaRequest().equals("none")){
					if (Bukkit.getPlayer(wcp.getTpaRequest()) == null){
						s(p, "The person who sent the request is no longer online.");
					} else {
						WCPlayer wcpCurrent = pl.wcm.getWCPlayer(Bukkit.getPlayer(wcp.getTpaRequest()).getName());
						wcpCurrent.setLastLocation(Bukkit.getPlayer(wcp.getTpaRequest()).getLocation());
						Bukkit.getPlayer(wcp.getTpaRequest()).teleport(p.getLocation());
						q = p; pl.api.wcutils.effects(Bukkit.getPlayer(wcp.getTpaRequest()), q.getLocation()); s(q, "Teleporting!"); s(p, "Accepted.");
						pl.wcm.updatePlayerMap(wcp.getTpaRequest(), wcpCurrent);
						wcp.setTpaRequest("none");
						pl.wcm.updatePlayerMap(p.getName(), wcp);
						return;
					}
				}
			}
			if (que.equals("tpahere")){
				if (!wcp.getTpahereRequest().equals("none")){
					if (Bukkit.getPlayer(wcp.getTpahereRequest()) == null){
						s(p, "The person who sent the request is no longer online.");
					} else {
					    wcp.setLastLocation(p.getLocation());
						p.teleport(Bukkit.getPlayer(wcp.getTpahereRequest()));
						q = p; pl.api.wcutils.effects(q, q.getLocation()); s(p, "Teleporting!"); s(q, "Accepted.");
						wcp.setTpahereRequest("none");
						pl.wcm.updatePlayerMap(p.getName(), wcp);
						return;
					}
				}
			}
			if (que.equals("tpa") && !p.hasPermission("wa.continental")){
				return;
			}
			if (que.equals("tpahere") && !p.hasPermission("wa.immortal")){
				return;
			}
			if (!argsCheck(1, "/" + cmd.toLowerCase() + " &d<player>", p, args) || !playerCheck(args[0])){
				return;
			}
			
		switch (cmd.toLowerCase()){
		
			case "tp":
				
				String tpWorlds = "WACP Keopi Tripolis Syracuse Alliance";
				
				if (p.hasPermission("wa.mod2") || (tpWorlds.contains(q.getWorld().getName()) && tpWorlds.contains(q.getWorld().getName()))){
				
					wcp.setLastLocation(p.getLocation());
					pl.wcm.updatePlayerMap(p.getName(), wcp);
					s(p, "Teleporting!");
					p.teleport(q.getLocation());
					if (!pl.wcm.getWCSystem("system").getVanishedPlayers().contains(p.getName())){
						pl.api.wcutils.effects(q, q.getLocation());
					}
				}

			break;
			
			case "tphere":
				
				if (p.hasPermission("wa.mod2")){
					wcpCurrent.setLastLocation(q.getLocation());
					pl.wcm.updatePlayerMap(q.getName(), wcpCurrent);
					q.teleport(p.getLocation());
					s(q, "Teleporting!");
					pl.api.wcutils.effects(q, q.getLocation());
				}
				
			break;
			
			case "tpa":
				
				wcpCurrent.setTpaRequest(p.getName());
				pl.wcm.updatePlayerMap(q.getName(), wcpCurrent);
				s(q, p.getDisplayName() + " &dhas requested to teleport to you. Type &6/tpa &dto allow it.");
				s(p, "Request sent!");
				
			break;
			
			case "tpahere":
				
				wcpCurrent = pl.wcm.getWCPlayer(q.getName());
				wcpCurrent.setTpahereRequest(p.getName());
				pl.wcm.updatePlayerMap(q.getName(), wcpCurrent);
				s(q, p.getDisplayName() + " &dhas requested for you to teleport to them. Type &6/tpahere &dto allow it.");
				s(p, "Request sent!");
				
			break;
			
			case "tpaall":
				
				if (p.hasPermission("wa.mod2")){
					for (Player r : Bukkit.getOnlinePlayers()){
						wcpCurrent = pl.wcm.getWCPlayer(r.getName());
						wcpCurrent.setTpahereRequest(p.getName());
						pl.wcm.updatePlayerMap(r.getName(), wcpCurrent);
						s(r, p.getDisplayName() + " &dhas requested that you teleport to them. Type &6/tpahere &dto allow it.");
						s(p, "Request sent!");
					}
				}
	
			break;
			
			case "tpall":
				
				if (p.hasPermission("wa.admin")){
					for (Player r : Bukkit.getOnlinePlayers()){
						wcpCurrent = pl.wcm.getWCPlayer(r.getName());
						wcpCurrent.setLastLocation(r.getLocation());
						pl.wcm.updatePlayerMap(r.getName(), wcpCurrent);
						r.teleport(p.getLocation());
						s(r, "Teleporting!");
					}
					
						
					pl.api.wcutils.effects(q, q.getLocation());
				}
				
			break;
		
		}
		
		return;
	}
	
	public Boolean argsCheck(int valid, String error, Player p, String[] args){
		if (args.length == valid){
			return true;
		}
		s(p, error);
		return false;
	}
	
	public Boolean playerCheck(String player){
		for (Player p : Bukkit.getOnlinePlayers()){
			if (p.getName().toLowerCase().contains(player.toLowerCase())){
				q = Bukkit.getPlayer(player);
				wcpCurrent = pl.wcm.getWCPlayer(q.getName());
				return true;
			}
		}
		return false;
	}
}
