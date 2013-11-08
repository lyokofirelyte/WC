package com.github.lyokofirelyte.WC.Listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import com.github.lyokofirelyte.WC.WCCommands;
import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.FireworkShenans;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCMiscEvents implements Listener {
	
	WCMain plugin;
	WCPlayer wcp;
	WCPlayer wcp2;
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
		
		if (ent instanceof Player){
			wcp = plugin.wcm.getWCPlayer(((Player)ent).getName());
			wcp2 = plugin.wcm.getWCPlayer(p.getName());
			
			if (!wcp.getAllowPokes() || !wcp2.getAllowPokes()){
				WCMain.s(p, "That person or yourself have disabled pokes.");
			} else {
				WCMain.s2(p, "Poked " + (((Player) ent).getDisplayName()));
				WCMain.s2((Player)ent, "You were poked by " + p.getDisplayName() + "&d!");
			}	
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEnderDragonSpawnPORTALZ(EntityDamageByEntityEvent e){
		
		if (e.getEntity() instanceof Player){
			if (e.getDamager() instanceof Snowball){
				final Player p = ((Player)e.getEntity());
		        List<Location> circleblocks = Utils.circle(p, p.getLocation(), 5, 1, true, false, 1);
		        long delay =  0L;
		        	for (final Location l : circleblocks){
		        		delay = delay + 2L;
		        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		        	    {
		        	      public void run()
		        	      {
		        	        	FireworkShenans fplayer = new FireworkShenans();
		        	        	try {
									fplayer.playFirework(p.getWorld(), l,
									FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Color.WHITE).build());
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (Exception e) {
									e.printStackTrace();
								}        	      }
		        	    }
		        	    , delay);
		        	}
		        	
		        	return;
		        	
			} else if (e.getDamager() instanceof Player){
			
				WCPlayer wcp = plugin.wcm.getWCPlayer(((Player)e.getEntity()).getName());
				WCPlayer wcp2 = plugin.wcm.getWCPlayer(((Player)e.getDamager()).getName());
				
				if (!wcp.getPVP() || !wcp2.getPVP()){
					e.setCancelled(true);
					WCMain.s((Player)e.getDamager(), "That player has PVP mode turned off!");
				}
			}
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
