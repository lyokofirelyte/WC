package com.github.lyokofirelyte.WC.Staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

import static com.github.lyokofirelyte.WC.WCMain.s;

public class WCTele implements CommandExecutor {

	WCMain pl;
	public WCTele(WCMain instance){
    this.pl = instance;
	}
	
	String checks = ":tp: :tphere: :tpa: :tpahere:";
	String que;
	WCPlayer wcp;
	WCPlayer wcpCurrent;
	Player q;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = ((Player)sender);
		
		if (checks.contains(":" + cmd.getName().toLowerCase() + ":")){
			wcp = pl.wcm.getWCPlayer(p.getName());
			que = cmd.getName().toLowerCase();
			if (que.equals("tpa")){
				if (!wcp.getTpaRequest().equals("none")){
					if (Bukkit.getPlayer(wcp.getTpaRequest()) == null){
						s(p, "The person who sent the request is no longer online.");
					} else {
						WCPlayer wcpCurrent = pl.wcm.getWCPlayer(Bukkit.getPlayer(wcp.getTpaRequest()).getName());
						wcpCurrent.setLastLocation(Bukkit.getPlayer(wcp.getTpaRequest()).getLocation());
						Bukkit.getPlayer(wcp.getTpaRequest()).teleport(p.getLocation());
						q = p; Utils.effects(q); s(q, "Teleporting!"); s(p, "Accepted.");
						pl.wcm.updatePlayerMap(wcp.getTpaRequest(), wcpCurrent);
						wcp.setTpaRequest("none");
						pl.wcm.updatePlayerMap(p.getName(), wcp);
					}
					return true;
				}
			}
			if (que.equals("tpahere")){
				if (!wcp.getTpahereRequest().equals("none")){
					if (Bukkit.getPlayer(wcp.getTpahereRequest()) == null){
						s(p, "The person who sent the request is no longer online.");
					} else {
					    wcp.setLastLocation(p.getLocation());
						p.teleport(Bukkit.getPlayer(wcp.getTpahereRequest()));
						q = p; Utils.effects(q); s(p, "Teleporting!"); s(q, "Accepted.");
						wcp.setTpahereRequest("none");
						pl.wcm.updatePlayerMap(p.getName(), wcp);
					}
					return true;
				}
			}
			if (que.equals("tpa") && !p.hasPermission("wa.continental")){
				return true;
			}
			if (que.equals("tpahere") && !p.hasPermission("wa.immortal")){
				return true;
			}
			if (!argsCheck(1, "/" + cmd.getName().toLowerCase() + " &d<player>", p, args) || !playerCheck(args[0])){
				return true;
			}
		}
		  
		switch (cmd.getName().toLowerCase()){
		
			case "tp":
				
				wcp.setLastLocation(p.getLocation());
				pl.wcm.updatePlayerMap(p.getName(), wcp);
				p.teleport(q.getLocation());
				s(p, "Teleporting!");
				Utils.effects(q);
				
			break;
			
			case "tphere":
				
				wcpCurrent.setLastLocation(q.getLocation());
				pl.wcm.updatePlayerMap(q.getName(), wcpCurrent);
				q.teleport(p.getLocation());
				s(q, "Teleporting!");
				Utils.effects(q);
				
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
				
				for (Player r : Bukkit.getOnlinePlayers()){
					wcpCurrent = pl.wcm.getWCPlayer(r.getName());
					wcpCurrent.setTpahereRequest(p.getName());
					pl.wcm.updatePlayerMap(r.getName(), wcpCurrent);
					s(r, p.getDisplayName() + " &dhas requested that you teleport to them. Type &6/tpahere &dto allow it.");
					s(p, "Request sent!");
				}
	
			break;
			
			case "tpall":
				
				for (Player r : Bukkit.getOnlinePlayers()){
					wcpCurrent = pl.wcm.getWCPlayer(r.getName());
					wcpCurrent.setLastLocation(r.getLocation());
					pl.wcm.updatePlayerMap(r.getName(), wcp);
					r.teleport(p.getLocation());
					s(r, "Teleporting!");
				}
				
				q = p;
				Utils.effects(q);;
				
			break;
		
		}
		
		return true;
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
