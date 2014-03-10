package com.github.lyokofirelyte.WC.Staff;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCUtils;

public class WCMember implements Listener {

	WCMain pl;
	public WCMember(WCMain instance){
    pl = instance;
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onWhack(EntityDamageByEntityEvent e){
		
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player && ((Player)e.getDamager()).hasPermission("wa.staff") && ((Player) e.getDamager()).getItemInHand().getType().equals(Material.ENDER_PORTAL_FRAME)){
			((Player)e.getDamager()).performCommand("pex user " + ((Player) e.getEntity()).getName() + " group add member");
			WCUtils.s(((Player)e.getDamager()), "Added to members!");
		}
	}	
}