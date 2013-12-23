package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WC.WCMain.s;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCPowerTool implements CommandExecutor, Listener {

	WCMain pl;
	public WCPowerTool(WCMain instance){
	pl = instance;
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().toLowerCase().equals("powertool") || cmd.getName().toLowerCase().equals("pt")){
			
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
							return true;
						}
					}
				}			
				pts.add(name + "%%%%%%" + Utils.createString(args, 0));
				s(p, "Powertool added. Type &6/pt &dto remove it, or &6/pt :a &dto remove all of your powertools.");
				pl.wcm.updatePlayerMap(p.getName(), wcp);
			}
		}
		
		return true;
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