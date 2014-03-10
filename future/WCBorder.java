package com.github.lyokofirelyte.WC;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

public class WCBorder implements Listener {

	WCMain pl;
	
	public WCBorder(WCMain i){
		pl = i;
	}
	
	@WCCommand(aliases = {"setcenter"}, perm = "wa.staff", help = "/setcenter", desc = "WC Border Command")
	public void setCenter(Player p, String[] args){
		
		Location l = p.getLocation();
		pl.wcm.getWCSystem("system").setBorder(l.getWorld().getName() + " " + l.getX() + " " + l.getY() + " " + l.getZ());
		pl.insideBorder = WCUtils.circle(l, 100, 1, true, false, 0);
		WCUtils.s(p, "Updated border!");
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		
		if (pl.insideBorder.size() <= 0){
			return;
		}
		
		for (Location l : pl.insideBorder){
			if (!checkIt(e.getTo(), l)){
				p.teleport(e.getFrom());
				WCUtils.effects(p);
				WCUtils.s(p, "You've reached the border!");
				return;
			}
		}
	}
	
	public Boolean checkIt(Location p, Location l){
		
		World w = l.getWorld();
		
		if (p.getWorld() == w){
			
			double x = Math.round(l.getX());
			double z = Math.round(l.getZ());
			double meX = Math.round(p.getX());
			double meZ = Math.round(p.getZ());
			
			if (x == meX || z == meZ){
				return false;
			}
		}
		return true;
	}
}