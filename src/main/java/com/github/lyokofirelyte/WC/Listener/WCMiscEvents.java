package com.github.lyokofirelyte.WC.Listener;

import static com.github.lyokofirelyte.WC.Util.Utils.AS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WC.WCCommands;
import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;

public class WCMiscEvents implements Listener {
	
	WCMain plugin;
	WCPlayer wcp;
	WCPlayer wcp2;
	WCCommands wc;
	
	int timer = 0;
	double mult = 1;
	int timerTask;
	
	public WCMiscEvents(WCMain plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onControl(final PlayerInteractEvent e){
		
		if (e.getPlayer().isSneaking() && plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".onMob")) {
			plugin.datacore.set("Users." + e.getPlayer().getName() + ".onMob", false);
			e.getPlayer().setAllowFlight(false);
			if (e.getPlayer().isInsideVehicle() && e.getPlayer().getVehicle() instanceof Player){
				((Player)e.getPlayer().getVehicle()).setAllowFlight(false);
			}
			return;
		}
		
		if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".onMob")){
			
			if (e.getPlayer().getVehicle() instanceof Player){
				((Player)e.getPlayer().getVehicle()).setAllowFlight(true);
			}
			
			if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getItemInHand().getType().equals(Material.STICK)){
				Entity ent = e.getPlayer().getVehicle();
				ent.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(1.5));
				return;
			}
			
			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
				Entity ent = e.getPlayer().getVehicle();
				ent.setVelocity(new Vector(0, 2, 0));
				return;
			}
			
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
				Entity ent = e.getPlayer().getVehicle();
				ent.setVelocity(new Vector(0, 0, 0));
				return;
			}
			
			
		}
		
		if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".controlCarts")){
			
			if (e.getPlayer().getItemInHand().getType().equals(Material.STICK)){
				
				if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){

					for (Entity ent : plugin.carts){
						ent.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(mult));
					}
					
				} else {
					
					for (Entity ent : plugin.carts){
						ent.setVelocity(new Vector(0, 0, 0));
					}
					
				}
			}
			
			if (e.getPlayer().getItemInHand().getType().equals(Material.GOLD_SWORD)){
				
				if (mult >= 5){
					mult = 1;
				} else {
					mult++;
				}
				
				WCUtils.s(e.getPlayer(), mult + "");
			}
		}
		
		if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".ridePanel") && !e.getPlayer().isInsideVehicle()){
			
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
				
				if (plugin.links.get(e.getPlayer().getName()) == null){
					plugin.links.put(e.getPlayer().getName(), new ArrayList<Location>());
				}
				
				plugin.links.get(e.getPlayer().getName()).add(e.getClickedBlock().getLocation());
				WCUtils.s(e.getPlayer(), "Added!");
				timer = 30;
				return;
			}
		}
		
		if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".ridePanel2") && e.getPlayer().isInsideVehicle()){
			
			List<Entity> eee = new ArrayList<Entity>();
			
			if (timer == 30){
				timer = 0;
				timerTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
				public void run() { timer++;} }, 0L, 20L);
			}
			
			if (timer >= 20){
				timer = 0;
				int xXx = 1;
				
				for (Entity ent : plugin.links2.get(e.getPlayer().getName())){
					Entity newEnt = ent.getWorld().spawnFallingBlock(new Location(ent.getWorld(), ent.getLocation().getX(), ent.getLocation().getY() + 10, ent.getLocation().getZ()), Material.GLASS, (byte)0);
					ent.setPassenger(null);
					ent.remove();
					eee.add(newEnt);
				}
				
				plugin.links2 = new HashMap<String, List<Entity>>();
				plugin.links2.put(e.getPlayer().getName(), new ArrayList<Entity>());
				
				for (Entity ent : eee){
					plugin.links2.get(e.getPlayer().getName()).add(ent);
				}
					
				plugin.links2.get(e.getPlayer().getName()).get(0).setPassenger(e.getPlayer());
				
				for (Player pp : plugin.playerRide.get(e.getPlayer().getName())){
					plugin.links2.get(e.getPlayer().getName()).get(xXx).setPassenger(pp);
					xXx++;
				}
			}

			if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getItemInHand().getType().equals(Material.STICK)){
				
				for (Entity ent : plugin.links2.get(e.getPlayer().getName())){
					ent.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(2));
				}
				
			} else {
				
				for (Entity ent : plugin.links2.get(e.getPlayer().getName())){
					ent.setVelocity(new Vector(0, 0, 0));
				}
			}
			
		} else if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".ridePanel2") && e.getPlayer().isSneaking()){
			
			plugin.datacore.set("Users." + e.getPlayer().getName() + ".ridePanel", false);
			plugin.datacore.set("Users." + e.getPlayer().getName() + ".ridePanel2", false);
			plugin.playerRide.put(e.getPlayer().getName(), new ArrayList<Player>());
			plugin.links.put(e.getPlayer().getName(), new ArrayList<Location>());
			plugin.links2.put(e.getPlayer().getName(), new ArrayList<Entity>());
			Bukkit.getServer().getScheduler().cancelTask(timerTask);
			WCUtils.s(e.getPlayer(), "Cleared links!");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerTouchMob(PlayerInteractEntityEvent e){
		
		Player p = e.getPlayer();
		Entity ent = e.getRightClicked();
		boolean ifCommand = plugin.datacore.getBoolean("Users." + p.getName() + ".commandUsed");
		
		if (ifCommand){
			ent.setPassenger(p);
			p.setAllowFlight(true);
			plugin.datacore.set("Users." + p.getName() + ".commandUsed", false);
			plugin.datacore.set("Users." + p.getName() + ".onMob", true);
			WCUtils.s(p, "Right click = down, left click = up, hold right click w/ stick for directional controls, evac = shift");
		}
		
		if (ent instanceof Player){
			wcp = plugin.wcm.getWCPlayer(((Player)ent).getName());
			wcp2 = plugin.wcm.getWCPlayer(p.getName());
			
			if (!wcp.getAllowPokes() || !wcp2.getAllowPokes()){
				WCUtils.s(p, "That person or yourself have disabled pokes.");
			} else {
				WCUtils.s2(p, "Poked " + (((Player) ent).getDisplayName()));
				WCUtils.s2((Player)ent, "You were poked by " + p.getDisplayName() + "&d!");
			}	
		}
		
		if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".controlCarts") && e.getPlayer().getItemInHand().getType().equals(Material.IRON_SWORD)){
			plugin.carts.add(e.getRightClicked());
			WCUtils.s(p, "Added");
		} else if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".controlCarts") && e.getPlayer().getItemInHand().getType().equals(Material.WOOD_SWORD)){
			plugin.carts = new ArrayList<>();
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPVP(EntityDamageByEntityEvent e){
		
		if (e.getEntity() instanceof Player){
			if (e.getDamager() instanceof Snowball){
				final Player p = ((Player)e.getEntity());
		        List<Location> circleblocks = Utils.circle(p.getLocation(), 5, 1, true, false, 1);
		        long delay =  0L;
		        	for (final Location l : circleblocks){
		        		delay = delay + 2L;
		        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		        	    {
		        	      public void run()
		        	      {
		        	        	try {
									plugin.fw.playFirework(p.getWorld(), l,
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
					WCUtils.s((Player)e.getDamager(), "That player has PVP mode turned off!");
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
	  public void onEntityExplode(EntityExplodeEvent event) {
	    Entity e = event.getEntity();
	    
	    if (e.getType().equals(EntityType.ENDER_CRYSTAL) && e.getWorld().equals(Bukkit.getWorld("world"))){
	    	event.setCancelled(true);
	    }

	    List <Entity> entity_list = e.getNearbyEntities(7.0D, 7.0D, 7.0D);
	    List <Block> block_list = event.blockList();

	    for (Entity entity : entity_list) {
	    	if ((entity.getType().equals(EntityType.MINECART_TNT)) || (entity.getType().equals(EntityType.PRIMED_TNT))){
	    		event.setCancelled(true);
	    		break;
	    	}
	    }

	    for (Block block : block_list) {
	    	if (block.getType().equals(Material.TNT)){
	    		event.setCancelled(true);
	    		break;
	    	}
	    }
	 }
	  
      @EventHandler
      public void onVehicleUpdate(VehicleUpdateEvent event) {
              if (event.getVehicle() instanceof Minecart) {
                      Minecart minecart = ((Minecart)event.getVehicle());
                      if (!(minecart instanceof RideableMinecart && minecart.getPassenger() == null)) {
                    	  if (plugin.datacore.getBoolean("cartSpeed")){
                              minecart.setMaxSpeed(mult);
                    	  }
                      }
              }
      }
  	
  	@EventHandler
  	public void onFlyAttempt(PlayerToggleFlightEvent e){
  		
  		Player p = e.getPlayer();
  		
  		if (p.getGameMode() != GameMode.CREATIVE && plugin.wcm.getWCPlayer(p.getName()).isDoubleJumping()){
  			plugin.wcm.getWCPlayer(p.getName()).setDoubleJumping(false);
  			plugin.wcm.getWCPlayer(p.getName()).setDoubleJumpTimer(System.currentTimeMillis() + 5000);
  			p.setAllowFlight(false);
  			p.setFlying(false);
  			p.setVelocity(p.getLocation().getDirection().multiply(1.2D).setY(1.0D));
  			WCUtils.lowerEffects(p.getLocation());
  		}
  	}
  	
	@WCDelay(time = 20L)
	public void jumpEvac(Player p){
		p.setAllowFlight(false);
		p.setFlying(false);
	}
      
      @EventHandler
      public void onMove(PlayerMoveEvent e){
    	  
    	  Player p = e.getPlayer();
    	  WCPlayer wcp = plugin.wcm.getWCPlayer(e.getPlayer().getName());
    	  
    		if (p.getGameMode() != GameMode.CREATIVE && e.getFrom().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR){
    			if (plugin.wcm.getWCPlayer(p.getName()).getDoubleJumpTimer() <= System.currentTimeMillis()){
    				p.setAllowFlight(true);
    				plugin.wcm.getWCPlayer(p.getName()).setDoubleJumping(true);
    				plugin.api.ls.callDelay(plugin, this, "jumpEvac", p);
    			}
    		}
    		
    		if (plugin.wcm.getWCPlayer(p.getName()).isUsingInstaKill()){
    			WCUtils.lowerEffects(p.getLocation());
    		}
    	  
    	  if (p.getWalkSpeed() != 0.4 && plugin.wcm.getWCPlayer(p.getName()).getPatrol() != null){
    		  Boolean found = false;
    		  for (ItemStack i : plugin.wcm.getWCPlayer(p.getName()).getPatrolActives()){
    			  if (!found && Utils.dispName(i, "Quick Bone")){
    				  p.setWalkSpeed((float) 0.4);
    				  found = true;
    			  }
    		  }
    		  if (!found && p.getWalkSpeed() != 0.2 && p.getWalkSpeed() != 0){
    			  p.setWalkSpeed((float)0.2);
    		  }
    	  }
    	  
    	  if (plugin.wcm.getWCPlayer(p.getName()).getScoreboard() && plugin.wcm.getWCPlayer(p.getName()).getScoreboardCoords() && p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null){
    		  Objective o1 = e.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR);
  			  String disp = "§3" + Math.round(p.getLocation().getX()) + " " + Math.round(p.getLocation().getY()) + " " + Math.round(p.getLocation().getZ());
  			  if (disp.length() >= 16){
  				  disp = ("§3" + Math.round(p.getLocation().getX()) + " " + Math.round(p.getLocation().getY()) + " " + Math.round(p.getLocation().getZ())).substring(0, 16);
  			  }
  			  o1.setDisplayName(disp);
    	  }
    	  
    	  if (!plugin.afkTimer.containsKey(p.getName())){
    		  plugin.afkTimer.put(p.getName(), 0);
    	  }
    	  
		  if (plugin.afkers.contains(p) && !wcp.getAfkFreeze()){
			  WCUtils.blankB(AS("&7&o" + p.getDisplayName() + " &7&ois no longer afk. (" + Math.round(plugin.afkTimer.get(p.getName()) / 60) + " &7&ominutes)"));
			  if ((p.getDisplayName()).length() > 16){
				  p.setPlayerListName(AS(p.getDisplayName()).substring(0, 16));
			  } else {
				  p.setPlayerListName(AS(p.getDisplayName()));
			  }
			  plugin.afkers.remove(p);
			  Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
		  } 
		  
		  if (wcp.isSuperAfk() && !wcp.getAfkFreeze()){
			  wcp.setSuperAfk(false);
			  p.teleport(wcp.getAfkSpot());
			  WCUtils.effects(p);
		  }
		  
		  if (!wcp.getAfkFreeze()){
			  plugin.afkTimer.put(p.getName(), 0);
		  }
      }
      
    @EventHandler (priority = EventPriority.HIGHEST)
  	public void onTP(PlayerTeleportEvent e){
  		
  		if (!e.getPlayer().hasPermission("wa.enderpearl") && e.getCause().equals(TeleportCause.ENDER_PEARL)){
  			e.setCancelled(true);
  			return;
  		}
  	}
    
    @EventHandler
    public void onTheItemFrameDamage(EntityDamageByEntityEvent e){
    	
    	if (e.getDamager() instanceof Player){
    		
    		Player p = (Player) e.getDamager();
    		Location s = new Location(p.getWorld(), -186, 63, 234);
    		
    		if (p.getLocation().distance(s) < 150 && !(p.hasPermission("wa.staff"))){
    			
    			e.setCancelled(true);
    			
    		}
    		
    	}
    	
    	if (e.getDamager() instanceof Projectile){
    		
    		Projectile p = (Projectile) e.getDamager();
    		Location s = new Location(p.getWorld(), -186, 63, 234);
    		
    		if (p.getLocation().distance(s) < 150){
    			
    			e.setCancelled(true);
    			
    		}
    		
    	}
    	
    }
    
    @EventHandler
    public void onThePotionSplash(PotionSplashEvent e){
    	
    	if (e.getPotion().getShooter() instanceof Player){
    		
    		Player p = (Player) e.getPotion().getShooter();
    		List<String> worlds = Arrays.asList("Syracuse", "Keopi", "WACP", "Tripolis", "not_cylum");
    		
    		if (worlds.contains(p.getWorld().getName()) && !(p.hasPermission("wa.staff"))){
    			
    			e.setCancelled(true);
    			
    		}
    		
    	}
    	
    }
    
}