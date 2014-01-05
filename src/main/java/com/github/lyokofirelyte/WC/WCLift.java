package com.github.lyokofirelyte.WC;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;

import static com.github.lyokofirelyte.WCAPI.WCUtils.AS;
import static com.github.lyokofirelyte.WCAPI.WCUtils.isInteger;
import static com.github.lyokofirelyte.WCAPI.WCUtils.s;

public class WCLift implements Listener {
	
	WCMain pl;
	
	public WCLift(WCMain i){
		pl = i;
	}
	
	@EventHandler							// 0          1            2                 3
	public void onSign(SignChangeEvent e){ // WCLift - SPEED - DESTINATION FLOOR - CURRENT FLOOR
		
		Player p = e.getPlayer();
		if (e.getLine(0).contains(AS("&dWCLift"))){ 
			if (p.hasPermission("wa.staff") && isInteger(e.getLine(1)) && isInteger(e.getLine(2)) && isInteger(e.getLine(3))){
				e.setLine(0, AS(e.getLine(0)));
				e.setLine(1, AS("&aSpeed: " + e.getLine(1)));
				e.setLine(2, AS("&aDest: " + e.getLine(2)));
				e.setLine(3, AS("&aCurrent: " + e.getLine(3)));
				s(p, "Shift-right click the sign to change which floor it should go to!");
			} else {
				e.setLine(0, AS("&4INVALID!"));
			}
		}
	}
	
	@EventHandler
	public void onClickSign(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		
		if (e.getClickedBlock().getType().equals(Material.WALL_SIGN)){
			Sign sign = (Sign) e.getClickedBlock().getState();
		}
	}
}