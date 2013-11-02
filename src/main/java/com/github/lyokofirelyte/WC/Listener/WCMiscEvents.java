package com.github.lyokofirelyte.WC.Listener;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.github.lyokofirelyte.WC.WCCommands;
import com.github.lyokofirelyte.WC.WCMain;

public class WCMiscEvents implements Listener {
	
	WCMain plugin;
	WCCommands wc;
	
	public WCMiscEvents(WCMain plugin){
		
		this.plugin = plugin;
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerTouchMob(PlayerInteractEntityEvent e){
		
		Player p = e.getPlayer();
		Entity ent = e.getRightClicked();
		boolean ifCommand = plugin.datacore.getBoolean("Users." + p.getName() + ".commandUsed");
		
		if (ifCommand){
			
			ent.setPassenger(p);
			plugin.datacore.set("Users." + p.getName() + ".commandUsed", false);
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEnderDragonSpawnPORTALZ(EntityCreatePortalEvent e){
		
		if (e.isCancelled()){
			
			return;
			
		}
		
		EntityType ent = e.getEntity().getType();
		
		if (ent == EntityType.ENDER_DRAGON){
			
			e.setCancelled(true);
			
		}
		
	}
	
	  @EventHandler(priority = EventPriority.LOW)
	  public void onEntityExplode(EntityExplodeEvent event)
	  {
	    Entity e = event.getEntity();

	    List <Entity> entity_list = e.getNearbyEntities(7.0D, 7.0D, 7.0D);
	    List <Block> block_list = event.blockList();

	    for (Entity entity : entity_list)
	    {
	      if ((entity.getType().equals(EntityType.MINECART_TNT)) || (entity.getType().equals(EntityType.PRIMED_TNT)))
	      {
	        event.setCancelled(true);
	        break;
	      }
	    }

	    for (Block block : block_list)
	    {
	      if (block.getType().equals(Material.TNT))
	      {
	        event.setCancelled(true);
	        break;
	      }
	    }
	 }
	
}
