package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.lyokofirelyte.WC.WCMain;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCPowerTool implements Listener {

	WCMain pl;
	public WCPowerTool(WCMain instance){
	pl = instance;
    }
	
	@WCCommand(aliases = {"powertool", "pt"}, help = "Bind a command to a tool", perm = "wa.regional")
	public void onPowerTool(Player sender, String[] args){
					
			Player p = ((Player)sender);		
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			String name = p.getItemInHand().getType().name();
			List<String> pts = wcp.getPowertools();
			List<String> toRemove = new ArrayList<String>();
			
			if (args.length == 0){
				for (String s : pts){
					if (s.startsWith(name)){
						toRemove.add(s);
						s(p, "Powertool command removed.");
					}
				}
				for (String s : toRemove){
					pts.remove(s);
				}
				pl.wcm.updatePlayerMap(p.getName(), wcp);
			} else if (args[0].startsWith(":a")){
				wcp.setPowertools(new ArrayList<String>());
				pl.wcm.updatePlayerMap(p.getName(), wcp);
				s(p, "All powertools removed!");
			} else {
				for (String s : pts){
					if (s.startsWith(name)){
						if (!p.hasPermission("wa.eternal")){
							s(p, "You need eternal to add more commands. Use &6/pt &dto remove the current one first.");
							return;
						}
					}
				}			
				pts.add(name + "%%%%%%" + createString(args, 0));
				s(p, "Powertool added. Type &6/pt &dto remove it, or &6/pt :a &dto remove all of your powertools.");
				pl.wcm.updatePlayerMap(p.getName(), wcp);
			}
		
		return;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR){
		
			Player p = e.getPlayer();	
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			String name = p.getItemInHand().getType().name();
			List<String> pts = wcp.getPowertools();
			
			for (String s : pts){
				if (s.startsWith(name)){
					String[] ss = s.split("%%%%%%");
					p.performCommand(ss[1]);
				}
			}
		}
	}
}
