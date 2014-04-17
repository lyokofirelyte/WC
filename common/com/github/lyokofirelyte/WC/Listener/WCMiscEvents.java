package com.github.lyokofirelyte.WC.Listener;

import static com.github.lyokofirelyte.WCAPI.WCUtils.AS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
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

import com.dsh105.holoapi.HoloAPI;
import com.dsh105.holoapi.api.Hologram;
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
	
	/*@SuppressWarnings("deprecation")
	@EventHandler
	public void onCombatAbility(PlayerInteractEntityEvent e){
		
		List<Material> swords = Arrays.asList(Material.WOOD_SWORD, Material.DIAMOND_SWORD, Material.IRON_SWORD, Material.STONE_SWORD);
		WCPlayer wcp = plugin.wcm.getWCPlayer(e.getPlayer().getName());
		Player p = e.getPlayer();
		Location l = p.getLocation();
		
		if (swords.contains(p.getItemInHand().getType()) && e.getRightClicked() instanceof Player == false){
			for (Location ll : circle(l, 2, 60, false, false, 0)){
				if (ll.getBlock().getType() != Material.AIR){
					s(p, "You need to be in a more open spot for this ability.");
					return;
				}
			}
			float walkSpeed = new Float(p.getWalkSpeed());
			float flySpeed = new Float(p.getFlySpeed());
			p.setWalkSpeed(0);
			p.setFlySpeed(0);
			p.setAllowFlight(true);
			p.setVelocity(new Vector(0, 5, 0));
			wcp.setUsingInstaKill(true);
			plugin.wcm.updatePlayerMap(p.getName(), wcp);
			plugin.api.ls.callDelay("jumpDown", this, p, walkSpeed, flySpeed);
		}
	}
	
	@WCDelay(time = 40L)
	public void jumpDown(Player p, float ws, float fs){
		
		p.getLocation().setPitch(0);
		p.getLocation().setYaw(180);
		p.setVelocity(new Vector(0, -7, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
		p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0);
		plugin.api.ls.callDelay("killMonsters", this, p, ws, fs);
	}
	
	@SuppressWarnings("deprecation")
	@WCDelay(time = 18L)
	public void killMonsters(Player p, float ws, float fs){
		
		p.setWalkSpeed(ws);
		p.setFlySpeed(fs);
		p.setAllowFlight(false);
		p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 3F);
		Location center = p.getLocation();
		
		for (int x = 0; x < 20; x++){
			if (!new Location(center.getWorld(), center.getX(), center.getY()-x, center.getZ()).getBlock().getType().equals(Material.AIR)){
				center = new Location(center.getWorld(), center.getX(), center.getY()-x, center.getZ());
				break;
			}
		}
		
		for (Location l : circle(center, 10, 1, false, false, 0)){
			switch (l.getBlock().getType()){
				case GRASS: case DIRT: case STONE: case COBBLESTONE:
					FallingBlock b = l.getWorld().spawnFallingBlock(new Location(l.getWorld(), l.getX(), l.getY()+1, l.getZ()), l.getBlock().getType(), (byte)0);
					b.setVelocity(new Vector(0, 1.3, 0));
					l.getBlock().setType(Material.AIR);
				break;
				default: break;
			}
		}
		
		for (org.bukkit.entity.Entity e : p.getNearbyEntities(10D, 10D, 10D)){
			if (e instanceof Monster && !e.isDead()){
				e.getWorld().playEffect(e.getLocation(), Effect.STEP_SOUND, Material.FIRE.getId());
				e.remove();
			}
		}
		
		plugin.wcm.getWCPlayer(p.getName()).setUsingInstaKill(false);
		s(p, "KERSPLASH!");
	}*/
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCraft(CraftItemEvent e){
		e.setCancelled(false);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemPrepare(PrepareItemCraftEvent e){
		e.getInventory().setResult(e.getRecipe().getResult());
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
			}else if(e.getDamager() instanceof Projectile){
	        		Projectile proj = (Projectile) e.getDamager();		
	        		if(proj.getShooter() instanceof Player){
	        			Player shooter = (Player) proj.getShooter();
	        			if(shooter.getName() != ((Player)e.getEntity()).getName()){
		    				WCPlayer wcp = plugin.wcm.getWCPlayer(((Player)e.getEntity()).getName());
		    				WCPlayer wcp2 = plugin.wcm.getWCPlayer(shooter.getName());
		    				
		    				if (!wcp.getPVP() || !wcp2.getPVP()){
		    					e.setCancelled(true);
		    					WCUtils.s(shooter, "That player has PVP mode turned off!");
		    				}
	        			}
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
      public void onVehicleUpdate(VehicleUpdateEvent e) {
              if (e.getVehicle() instanceof Minecart) {
                      Minecart minecart = ((Minecart)e.getVehicle());
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
	
	public Location getCardinalMove(Player p) {
		
		double rotation = (p.getEyeLocation().getYaw() - 180) % 360;
		
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return new Location(p.getWorld(), p.getEyeLocation().getX(), p.getEyeLocation().getY(), p.getEyeLocation().getZ()-4, p.getEyeLocation().getPitch(), p.getEyeLocation().getYaw());
        } else if (22.5 <= rotation && rotation < 67.5) {
        	return new Location(p.getWorld(), p.getEyeLocation().getX()+4, p.getEyeLocation().getY(), p.getEyeLocation().getZ()-4, p.getEyeLocation().getPitch(), p.getEyeLocation().getYaw());
        } else if (67.5 <= rotation && rotation < 112.5) {
        	return new Location(p.getWorld(), p.getEyeLocation().getX()+4, p.getEyeLocation().getY(), p.getEyeLocation().getZ(), p.getEyeLocation().getPitch(), p.getEyeLocation().getYaw());
        } else if (112.5 <= rotation && rotation < 157.5) {
        	return new Location(p.getWorld(), p.getEyeLocation().getX()+4, p.getEyeLocation().getY(), p.getEyeLocation().getZ()+4, p.getEyeLocation().getPitch(), p.getEyeLocation().getYaw());
        } else if (157.5 <= rotation && rotation < 202.5) {
        	return new Location(p.getWorld(), p.getEyeLocation().getX(), p.getEyeLocation().getY(), p.getEyeLocation().getZ()+4, p.getEyeLocation().getPitch(), p.getEyeLocation().getYaw());
        } else if (202.5 <= rotation && rotation < 247.5) {
        	return new Location(p.getWorld(), p.getEyeLocation().getX()-4, p.getEyeLocation().getY(), p.getEyeLocation().getZ()+4, p.getEyeLocation().getPitch(), p.getEyeLocation().getYaw());
        } else if (247.5 <= rotation && rotation < 292.5) {
        	return new Location(p.getWorld(), p.getEyeLocation().getX()-4, p.getEyeLocation().getY(), p.getEyeLocation().getZ(), p.getEyeLocation().getPitch(), p.getEyeLocation().getYaw());
        } else if (292.5 <= rotation && rotation < 337.5) {
        	return new Location(p.getWorld(), p.getEyeLocation().getX()-4, p.getEyeLocation().getY(), p.getEyeLocation().getZ()-4, p.getEyeLocation().getPitch(), p.getEyeLocation().getYaw());
        } else if (337.5 <= rotation && rotation < 360.0) {
        	 return new Location(p.getWorld(), p.getEyeLocation().getX(), p.getEyeLocation().getY(), p.getEyeLocation().getZ()-4, p.getEyeLocation().getPitch(), p.getEyeLocation().getYaw());
        } else {
            return null;
        }
	}
      
      @EventHandler
      public void onMove(PlayerMoveEvent e){
    	  
    	  Player p = e.getPlayer();
    	  WCPlayer wcp = plugin.wcm.getWCPlayer(e.getPlayer().getName());
    	  
    	  if (plugin.wcm.getWCSystem("system").getHolograms().containsKey(e.getPlayer().getName())){
    		  Hologram holo = HoloAPI.getManager().getHologram(plugin.wcm.getWCSystem("system").getHolograms().get(p.getName()));
    		  holo.move(getCardinalMove(p));
    		  holo.updateLine(0, AS("&7" + WCUtils.getTime() + " " + p.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ()));
    	  }
    	  
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