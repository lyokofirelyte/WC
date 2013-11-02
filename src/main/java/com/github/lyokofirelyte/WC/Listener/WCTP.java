package com.github.lyokofirelyte.WC.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.github.lyokofirelyte.WC.WCMain;


public class WCTP implements Listener {
	
	WCMain plugin;
	public WCTP(WCMain instance){
	plugin = instance;
    }
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onTP(PlayerTeleportEvent e){
		
		if (!e.getPlayer().hasPermission("wa.enderpearl") && e.getCause().equals(TeleportCause.ENDER_PEARL)){
			e.setCancelled(true);
			return;
		}
	}

}
