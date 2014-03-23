package com.github.lyokofirelyte.WC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WCAPI.WCPatrol;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;
import com.github.lyokofirelyte.WCAPI.Manager.WCMessageType;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class WCPatrols implements Listener {

	WCMain pl;
	
	public WCPatrols(WCMain instance){
		this.pl = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onClick(InventoryClickEvent e){
		
		if (e.getInventory().getTitle().substring(2).contains("ACTIVE GEAR")){
			e.setCancelled(false);
		}
		
		if (e.getInventory().getTitle().substring(2).contains("LOCATIONS")){
			if (e.getCurrentItem() != null){
				s((Player)e.getWhoClicked(), e.getCurrentItem().getItemMeta().getLore().get(0));
				return;
			}
		}
		
		if (e.getInventory().getTitle().contains("§6LVL:")){
			e.setCancelled(true);
			WCPlayer wcp = pl.wcm.getWCPlayer(e.getWhoClicked().getName());
			Player p = ((Player)e.getWhoClicked());
			if (dispName(e.getCurrentItem(), "Destruction Ward")){
				if (wcp.getPatrolLevel() >= 25){
					if (p.getInventory().firstEmpty() == -1){
						s(p, "Your inventory is full!");
					} else {
						p.getInventory().addItem(e.getCurrentItem());
					}
				} else {
					s(p, "You need Patrol Level 25 or higher!");
				}
			}
			if (dispName(e.getCurrentItem(), "Majjykk Stick")){
				if (wcp.getPatrolLevel() >= 20){
					if (p.getInventory().firstEmpty() == -1){
						s(p, "Your inventory is full!");
					} else {
						p.getInventory().addItem(e.getCurrentItem());
					}
				} else {
					s(p, "You need Patrol Level 20 or higher!");
				}
			}
			if (dispName(e.getCurrentItem(), "Quick Bone")){
				if (wcp.getPatrolLevel() >= 10){
					if (p.getInventory().firstEmpty() == -1){
						s(p, "Your inventory is full!");
					} else {
						p.getInventory().addItem(e.getCurrentItem());
					}
				} else {
					s(p, "You need Patrol Level 10 or higher!");
				}
			}
			p.updateInventory();
			return;
		}
		
		if (e.getInventory().getTitle().substring(2).equals("PATROLS")){
			
			Player p = ((Player)e.getWhoClicked());
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			
			if (dispName(e.getCurrentItem(), "ACTIVE GEAR") && p.getWorld().getName().equals("world")){
				p.openInventory(wcp.getPatrolActives());
				return;
			}

			if (dispName(e.getCurrentItem(), "REWARDS")){
				int neededXp = (int) (100 + (Math.pow(wcp.getPatrolLevel(), 2) + wcp.getPatrolLevel()));
				Inventory inv = Bukkit.createInventory(null, 9, "§6LVL: " + wcp.getPatrolLevel() + " §f// §6XP: " + wcp.getPatrolExp() + "/" + neededXp);
				for (ItemStack i : WCMenus.invs.get("patrolRewardMenu")){
					if (i != null){
						inv.addItem(i);
					}
				}
				p.openInventory(inv);
				return;
			}
			
			if (dispName(e.getCurrentItem(), "FORM PATROL")){
				
				if (wcp.getPatrol() != null){
					s(p, "You're already in a patrol.");
					return;
				}
				
				if (pl.api.wcPatrols.size() >= 18){
					s(p, "The patrol limit is full. Why not join one? THERE'S 18 OF THEM!");
					return;
				}
				
				wcp.setPatrolFormCmd(true);
				pl.wcm.updatePlayerMap(p.getName(), wcp);
				p.closeInventory();
				
				s(p, "&4>> &dType a name for your patrol &6without &da slash. &4<<");
			}
			
			if (dispName(e.getCurrentItem(), "LEAVE PATROL")){
				if (wcp.getPatrol() == null){
					s(p, "You're not in a patrol.");
				} else {
					WCPatrol wcpp = pl.wcm.getWCPatrol(wcp.getPatrol());
					
					for (String s : wcpp.getMembers()){
						if (Bukkit.getPlayer(s) != null){
							s(Bukkit.getPlayer(s), p.getDisplayName() + " &dhas left the patrol.");
						}
					}
					
					List<String> members = wcpp.getMembers();
					members.remove(p.getName());
					wcpp.setMembers(members);
					pl.wcm.updatePatrol(wcp.getPatrol(), wcpp);
					wcp.setPatrol(null);
					pl.wcm.updatePlayerMap(p.getName(), wcp);
					e.setCancelled(true);
				}
			}
		}
		
		if (e.getInventory().getTitle().substring(2).equals("JOIN PATROL")){
			
			Player p = ((Player)e.getWhoClicked());
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			
			if (wcp.getPatrol() != null){
				s(p, "You're already in a patrol!");
				return;
			}
			
			if (!dispName(e.getCurrentItem(), "PATROLS")){
				
				WCPatrol wcpp = pl.wcm.getWCPatrol(e.getCurrentItem().getItemMeta().getDisplayName());

					for (String s : wcpp.getMembers()){
						if (Bukkit.getPlayer(s) != null){
							s(Bukkit.getPlayer(s), p.getDisplayName() + " &dhas joined the patrol.");
						}
					}
				
				List<String> members = wcpp.getMembers();
				members.add(p.getName());
				wcpp.setMembers(members);
				pl.wcm.updatePatrol(wcp.getPatrol(), wcpp);
				wcp.setPatrol(e.getCurrentItem().getItemMeta().getDisplayName());
				pl.wcm.updatePlayerMap(p.getName(), wcp);
				e.setCancelled(true);
				
				s(p, "Joined! Type in patrol chat with /p <message>.");
			}
		}	
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
			if (pl.wcm.getWCSystem("system").canPatrolAware() && e.getPlayer().isSneaking()){
				pl.wcm.getWCPlayer(e.getPlayer().getName()).setPatrolAware(true);
			}
		}
	}
	
	@EventHandler
	public void onCrystal(EntityExplodeEvent e){
		if (e.getEntity().getLocation().getWorld().equals(Bukkit.getWorld("world")) && e.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onCrystal(EntityInteractEvent e){
		if (e.getEntity().getLocation().getWorld().equals(Bukkit.getWorld("world")) && e.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onCrystal(EntityDamageEvent e){
		if (e.getEntity().getLocation().getWorld().equals(Bukkit.getWorld("world")) && e.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHeart(PlayerPickupItemEvent e){
		if (dispName(e.getItem().getItemStack(), "HEART")){
			if (((Damageable)e.getPlayer()).getHealth() > ((Damageable)e.getPlayer()).getMaxHealth()-4){ // bullshit bukkit, complete bullshit
				e.setCancelled(true);
				return;
			}
			try {
			e.getPlayer().setHealth(((Damageable)e.getPlayer()).getHealth() + 4); // Casting damagable because of int/double conflictions gah
			} catch (IllegalArgumentException iae){}
			e.getItem().remove();
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e){
		if (e.getEntity() instanceof Player == false && e.getEntity().getKiller() instanceof Player){
			WCSystem wcs = pl.wcm.getWCSystem("system");
			for (Location l : wcs.getPatrolHotSpotAreas()){
				Player killer = e.getEntity().getKiller();
				Location loc = killer.getLocation();
				if (Math.round(loc.getX()) == Math.round(l.getX()) && Math.round(loc.getY()) == Math.round(l.getY()) && Math.round(loc.getZ()) == Math.round(l.getZ())){
					List<LivingEntity> ents = wcs.getPatrolEnts();
					wcs.setPatrolKills(wcs.getPatrolKills() + 1);
					ents.remove(e.getEntity());
					pl.wcm.updateSystem("system", wcs);
					hearts(loc);
					if (wcs.getPatrolKills() >= (wcs.getPatrolDiff()*100)){
						Bukkit.getServer().getScheduler().cancelTask(wcs.getPatrolCheckTask());
						wcs.setPatrolHotSpotAreas(new ArrayList<Location>());
						callChat(WCMessageType.BROADCAST, AS(WC + "The active hotspot has been deactivated! Only the boss remains!"));
						bossTime();
					}
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		
		if (e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			WCSystem wcs = pl.wcm.getWCSystem("system");
			if (pl.wcm.getWCPlayer(p.getName()).getPatrolLevel() >= 20){
				for (ItemStack i : pl.wcm.getWCPlayer(p.getName()).getPatrolActives()){
					if (dispName(i, "Majjykk Stick")){
						Random rand = new Random();
						int chance = rand.nextInt(15);
						if (chance == 15){
							e.setCancelled(true);
							return;
						}
					}
				}
			}
			for (Location l : wcs.getPatrolHotSpotAreas()){
				if (pl.wcm.getWCPlayer(p.getName()).getPatrol() == null && l.getWorld() == p.getWorld() && Math.round(l.getX()) == Math.round(p.getLocation().getX()) && Math.round(l.getY()) == Math.round(p.getLocation().getY()) && Math.round(l.getZ()) == Math.round(p.getLocation().getZ())){
					e.setCancelled(true);
					return;
				}
			}
		}
		
		if (e.getDamager() instanceof Player){
			Player p = (Player)e.getDamager();
			WCSystem wcs = pl.wcm.getWCSystem("system");
			for (Location l : wcs.getPatrolHotSpotAreas()){
				if (pl.wcm.getWCPlayer(p.getName()).getPatrol() == null && l.getWorld() == p.getWorld() && Math.round(l.getX()) == Math.round(p.getLocation().getX()) && Math.round(l.getY()) == Math.round(p.getLocation().getY()) && Math.round(l.getZ()) == Math.round(p.getLocation().getZ())){
					e.setCancelled(true);
					s(p, "You're not in a patrol - action cancelled.");
					return;
				}
			}
		}
		
		List<Double> hps = new ArrayList<Double>();
		
		if (e.getEntity() instanceof Player == false && e.getDamager() instanceof Player){
			Player p = (Player)e.getDamager();
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			if (wcp.getPatrol() != null){
				WCPatrol wcpp = pl.wcm.getWCPatrol(wcp.getPatrol());
				if (wcpp.getMembers().size() > 0){
					for (ItemStack i : wcp.getPatrolActives()){
						if (dispName(i, "Soul Split")){
							wcp.setPatrolHeal(wcp.getPatrolHeal() + (e.getDamage()*0.05));	
							if (wcp.getPatrolHeal() >= 1){
								for (String s : wcpp.getMembers()){
									if (Bukkit.getPlayer(s) != null){
										hps.add(((Damageable)Bukkit.getPlayer(s)).getHealth());
									} 
								}
								Collections.sort(hps);
								for (String s : wcpp.getMembers()){
									if (Bukkit.getPlayer(s) != null && ((Damageable)Bukkit.getPlayer(s)).getHealth() == hps.get(0)){
										if (((Damageable)Bukkit.getPlayer(s)).getHealth() < ((Damageable)Bukkit.getPlayer(s)).getMaxHealth() && wcp.getPatrolHeal() >= 1){
											try{
												Bukkit.getPlayer(s).setHealth(((Damageable)Bukkit.getPlayer(s)).getHealth() + 1);
											} catch (IllegalArgumentException iae){}
											wcp.setPatrolHeal(wcp.getPatrolHeal() - 1);
											pl.wcm.updatePlayerMap(p.getName(), wcp);
										}
									}
								}
							}
							pl.wcm.updatePlayerMap(p.getName(), wcp);
						} else if (dispName(i, "Defender Shield")){
							Random rand = new Random();
							int chance = rand.nextInt(19);
							if (chance == 15){
								for (Entity ee : p.getNearbyEntities(10D, 10D, 10D)){
									if (ee instanceof Player == false){
										ee.setVelocity(ee.getLocation().getDirection().multiply(-3));
									}
								}
							}
						} else if (dispName(i, "Destruction Ward") && wcp.getPatrolLevel() >= 25){
							Random rand = new Random();
							Boolean hit = false;
							int chance = rand.nextInt(30);
							if (chance == 15){
								for (Entity ee : p.getNearbyEntities(10D, 10D, 10D)){
									if (ee instanceof Player == false && !hit){
										effects(ee.getLocation());
										p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 3F);
										ee.remove();
										hit = true;
									}
								}
							}
						} 
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		
		WCSystem wcs = pl.wcm.getWCSystem("system");
		Player p = e.getPlayer();
		
		if (e.getBlock().getWorld().getName().equals("world") && pl.wcm.getWCPlayer(e.getPlayer().getName()).getPatrol() != null){
			for (Location l : wcs.getPatrolHotSpotAreas()){
				if (l.getWorld() == p.getWorld() && Math.round(l.getX()) == Math.round(p.getLocation().getX()) && Math.round(l.getY()) == Math.round(p.getLocation().getY()) && Math.round(l.getZ()) == Math.round(p.getLocation().getZ())){
					for (ItemStack i : pl.wcm.getWCPlayer(e.getPlayer().getName()).getPatrolActives()){
						if (dispName(i, "Build Charm")){
							return;
						}
					}
					e.setCancelled(true);
					s(p, "You can't build in hotzones without the build charm equipped!");
				}
			}
		}
	}
	  
	public void startPatrol(){
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				
				final WCSystem wcs = pl.wcm.getWCSystem("system");
				
				if(wcs.getPatrolCrystal() != null){
					wcs.getPatrolCrystal().remove();
				}
				
				for (LivingEntity e : wcs.getPatrolEnts()){
					e.remove();
				}
				
				Random rand = new Random();
				int xDir = rand.nextInt(2);
				int zDir = rand.nextInt(2);
				double xR = rand.nextInt(2500);
				double zR = rand.nextInt(2500);
				Boolean rawr = false;
				wcs.setPatrolKills(0);
				wcs.setPatrolDiff((rand.nextInt(3)+1));
				wcs.setPatrolEnts(new ArrayList<LivingEntity>());
				wcs.setPatrolParticipants(new ArrayList<String>());
				List<ItemStack> items = new ArrayList<ItemStack>();
				items.add(new ItemStack(Material.WOOD_SWORD));
				items.add(new ItemStack(Material.STONE_SWORD));
				items.add(new ItemStack(Material.IRON_SWORD));
				wcs.setPatrolItems(items);
				  
				if (xDir == 0){
					xR = xR - (xR*2);
				}
				  
				if (zDir == 0){
					zR = zR - (zR*2);
				}
		
				for (int y = 255; y > 0; y--){
					if (!rawr){
						Location current = new Location(Bukkit.getWorld("world"), xR, y, zR);
						if (current.getBlock().getType() != Material.AIR && current.getBlock().getType() != Material.WATER &&  current.getBlock().getType() != Material.STATIONARY_WATER && current.getBlock().getType() != Material.STATIONARY_LAVA && current.getBlock().getType() != Material.LAVA && current.getBlock() != null){
							if (new Location(current.getWorld(), current.getX(), current.getY()+1, current.getZ()).getBlock().getType() != Material.WATER && new Location(current.getWorld(), current.getX(), current.getY()+1, current.getZ()).getBlock().getType() != Material.LAVA && new Location(current.getWorld(), current.getX(), current.getY()+1, current.getZ()).getBlock().getType() != Material.STATIONARY_WATER && new Location(current.getWorld(), current.getX(), current.getY()+1, current.getZ()).getBlock().getType() != Material.STATIONARY_LAVA){
								wcs.setPatrolHotSpot(new Location(Bukkit.getWorld("world"), xR, y+1, zR));
								callChat(WCMessageType.BROADCAST, AS(WC + "New hotspot at &6" + xR + "&f, &6" + (y+1) + "&f, &6" + zR + "&d! (Diff &6" + wcs.getPatrolDiff() + "&d)"));
								rawr = true;
							}
						}
					}
				}
				
				if (!rawr){
					startPatrol();
					return;
				}
						
		    	wcs.setPatrolHotSpotAreas(circle(wcs.getPatrolHotSpot(), 50, 50, false, true, 0));

		    	wcs.setPatrolCrystal(Bukkit.getWorld("world").spawnEntity(wcs.getPatrolHotSpot(), EntityType.ENDER_CRYSTAL));
		
		    	
				int checkTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
				public void run() { checkPlayers();} }, 0L, 400L);
				
				wcs.setPatrolCheckTask(checkTask);
				pl.wcm.updateSystem("system", wcs);
				
				WCMenus.invs.get("patrolLocationMenu").setItem(0, pl.invManager.makeItem("§3ACTIVE! LVL " + wcs.getPatrolDiff(), "§6" + xR + "§6, " + wcs.getPatrolHotSpot().getY() + "§6, " + zR, true, Enchantment.DURABILITY, 10, 0, Material.BOW, 1));
				
			}}).start();
	}
	
	public void checkBoss(LivingEntity le){
		
		WCSystem wcs = pl.wcm.getWCSystem("system");
		
		if (le.isDead()){
			for (Player p : Bukkit.getOnlinePlayers()){
				BarAPI.setMessage(p, "DEFEATED!", 3);
			}
			for (LivingEntity ee : wcs.getPatrolEnts()){
				if (!ee.isDead()){
					ee.remove();
				}
			}
			if (!wcs.getPatrolCrystal().isDead()){
				wcs.getPatrolCrystal().remove();
			}
			dropChance(wcs.getPatrolDiff());
			Bukkit.getServer().getScheduler().cancelTask(wcs.getPatrolHealthTimerTask());
			callChat(WCMessageType.BROADCAST, AS(WC + "The hotspot boss has been defeated! All participants were awarded!"));
			WCMenus.invs.get("patrolLocationMenu").setItem(0, pl.invManager.makeItem("§cINACTIVE!", "§4...", false, Enchantment.DURABILITY, 10, 0, Material.BOW, 1));
			rewardPlayers();
        	List<Location> circleblocks = circle(wcs.getPatrolHotSpot(), 20, 1, true, false, 1);
	        long delay =  0L;
		        for (final Location l : circleblocks){
		        	delay = delay + 2L;
		        	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable() {
		        	public void run() {
		        	    try {
		        	    	pl.fw.playFirework(l.getWorld(), l,
							FireworkEffect.builder().with(Type.BURST).withColor(getRandomColor()).build());
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}}
		        	}, delay);
		        }
			if (!wcs.getPatrolCrystal().isDead()){
				wcs.getPatrolCrystal().remove();
			}
		} else {
			for (Player p : Bukkit.getOnlinePlayers()){ // well shit
				BarAPI.setMessage(p, "§cHOTSPOT BOSS", (float)(((Damageable)le).getHealth()/((Damageable)le).getMaxHealth())*100);
			}
		}
	}
	
	public void hearts(Location l){
		
		Random rand = new Random();
		int chance = rand.nextInt(10) + 1;
		
		if (chance == 5){
			l.getWorld().dropItemNaturally(l, pl.invManager.makeItem("§3HEART", "§3Heals you!", true, Enchantment.DURABILITY, 10, 0, Material.CAKE, 1));
		}
	}
	
	public void dropChance(int lv){
		
		WCSystem wcs = pl.wcm.getWCSystem("system");
		Location l = wcs.getPatrolHotSpot();
		Random rand = new Random();
		int soulSplitChance = rand.nextInt(15) + 1;
		int defenderShield = rand.nextInt(15) + 1;
		int teleportCube = rand.nextInt(20) + 1;
		int buildCharm = rand.nextInt(10) + 1;
		
		switch (lv){
			
			case 2:
				
				soulSplitChance = rand.nextInt(10) + 1;
				defenderShield = rand.nextInt(10) + 1;
				teleportCube = rand.nextInt(15) + 1;
				buildCharm = rand.nextInt(5) + 1;
				break;
				
			case 3:
				
				soulSplitChance = rand.nextInt(5) + 1;
				defenderShield = rand.nextInt(5) + 1;
				teleportCube = rand.nextInt(10) + 1;
				buildCharm = rand.nextInt(5) + 1;
				break;
		}

		
		if (soulSplitChance == 3){
			ItemStack i = pl.invManager.makeItem("§3Soul Split", "", true, Enchantment.DURABILITY, 10, 0, Material.ARROW, 1);
			ItemMeta im = i.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add("§b§lPATROL ITEM");
			lore.add("§3Steals life from monsters");
			lore.add("§3and adds to patrol members");
			im.setLore(lore);
			i.setItemMeta(im);
			l.getWorld().dropItemNaturally(l, i);
			callChat(WCMessageType.BROADCAST, AS(WC + "The boss has dropped something at the hotspot origin!"));
		}
		
		if (defenderShield == 3){
			ItemStack i = pl.invManager.makeItem("§3Defender Shield", "", true, Enchantment.DURABILITY, 10, 0, Material.BOWL, 1);
			ItemMeta im = i.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add("§b§lPATROL ITEM");
			lore.add("§3The wearer will randomly");
			lore.add("§3knock back nearby monsters");
			lore.add("§3during combat");
			im.setLore(lore);
			i.setItemMeta(im);
			l.getWorld().dropItemNaturally(l, i);
			callChat(WCMessageType.BROADCAST, AS(WC + "The boss has dropped something at the hotspot origin!"));
		}
		
		if (teleportCube == 3){
			ItemStack i = pl.invManager.makeItem("§3Teleport Cube", "", true, Enchantment.DURABILITY, 10, 0, Material.GLASS, 1);
			ItemMeta im = i.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add("§b§lPATROL ITEM");
			lore.add("§3Allows teleportation to");
			lore.add("§3other patrol members while");
			lore.add("§3worn. /ptp <member>");
			im.setLore(lore);
			i.setItemMeta(im);
			l.getWorld().dropItemNaturally(l, i);
			callChat(WCMessageType.BROADCAST, AS(WC + "The boss has dropped something at the hotspot origin!"));
		}
		
		if (buildCharm == 3){
			ItemStack i = pl.invManager.makeItem("§3Build Charm", "", true, Enchantment.DURABILITY, 10, 0, Material.APPLE, 1);
			ItemMeta im = i.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add("§b§lPATROL ITEM");
			lore.add("§3Allows building in");
			lore.add("§3hotspot zones while worn");
			im.setLore(lore);
			i.setItemMeta(im);
			l.getWorld().dropItemNaturally(l, i);
			callChat(WCMessageType.BROADCAST, AS(WC + "The boss has dropped something at the hotspot origin!"));
		}
	}
	
	public void rewardPlayers(){
		
		WCSystem wcs = pl.wcm.getWCSystem("system");
		
		for (String s : wcs.getPatrolHotSpotParticipants()){
			WCPlayer wcp = pl.wcm.getWCPlayer(s);
			int expGive = ((int)Math.floor(wcp.getPatrolExp()*0.06)) + 120 + (wcs.getPatrolDiff()*5) + wcs.getPatrolDiff();
			wcp.setPatrolExp(wcp.getPatrolExp() + expGive);
			if (Bukkit.getPlayer(s) != null){
				s(Bukkit.getPlayer(s), "&4>> &dYou were awarded &6" + expGive + " &dpatrol exp! &4<<");
			}
			pl.wcm.updatePlayerMap(s, wcp);
		}
		
		for (LivingEntity e : wcs.getPatrolEnts()){
			if (!e.isDead()){
				e.remove();
			}
		}
		
		wcs.getPatrolCrystal().remove();
	}
	
	@SuppressWarnings("deprecation")
	public void bossTime(){
	
		WCSystem wcs = pl.wcm.getWCSystem("system");
		final LivingEntity le;
			
		switch (wcs.getPatrolDiff()){
		
			case 1:
				le = (LivingEntity) wcs.getPatrolHotSpot().getWorld().spawnEntity(wcs.getPatrolHotSpot(), EntityType.GIANT);
				le.setMaxHealth(300); le.setHealth(300); 
				le.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 9999, 1));
				le.setCustomName("§aPAPA GRUMPS"); le.setCustomNameVisible(true);
				int healthTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
				public void run() { checkBoss(le);} }, 0L, 20L);
				wcs.setPatrolHealthTimerTask(healthTask);
				
			break;
			
			case 2:
				le = (LivingEntity) wcs.getPatrolHotSpot().getWorld().spawnEntity(wcs.getPatrolHotSpot(), EntityType.PIG_ZOMBIE);
				le.setMaxHealth(350); le.setHealth(350);
				le.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 9999, 1));
				le.setCustomName("§aPIGGLY WIGGLY"); le.setCustomNameVisible(true);	
				le.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
				le.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
				le.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
				healthTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
				public void run() { checkBoss(le);} }, 0L, 20L);
				wcs.setPatrolHealthTimerTask(healthTask);
				
			break;
			
			case 3:
				le = (LivingEntity) wcs.getPatrolHotSpot().getWorld().spawnEntity(wcs.getPatrolHotSpot(), EntityType.CAVE_SPIDER);
				le.setMaxHealth(450); le.setHealth(450);
				le.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 9999, 1));
				le.setCustomName("§aCharlotte"); le.setCustomNameVisible(true);		
				healthTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
				public void run() { checkBoss(le);} }, 0L, 20L);
				wcs.setPatrolHealthTimerTask(healthTask);
				
			break;
				
		}
		
		pl.wcm.updateSystem("system", wcs);
	}
	
	@WCDelay(time = 60L)
	public void awareCheck(Player p){
		
		if (!pl.wcm.getWCPlayer(p.getName()).isPatrolAware()){
			p.setVelocity(new Vector(0, 1.3, 0));
			s(p, "You didn't save yourself!");
		} else {
			pl.wcm.getWCPlayer(p.getName()).setPatrolAware(false);
			s(p, "Safe from the wave!");
		}
		
	}
	
	@WCDelay(time = 100L)
	public void stopAware(){
		pl.wcm.getWCSystem("system").setCanPatrolAware(false);
	}
	
	@SuppressWarnings("deprecation")
	public void checkPlayers(){
		
		WCSystem wcs = pl.wcm.getWCSystem("system");
		List<Player> used = new ArrayList<Player>();
		List<LivingEntity> ents = wcs.getPatrolEnts();
		
		for (Player p : Bukkit.getOnlinePlayers()){
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			for (Location l : wcs.getPatrolHotSpotAreas()){
				if (wcp.getPatrol() != null && !used.contains(p) && l.getWorld() == p.getWorld() && Math.round(l.getX()) == Math.round(p.getLocation().getX()) && Math.round(l.getY()) == Math.round(p.getLocation().getY()) && Math.round(l.getZ()) == Math.round(p.getLocation().getZ())){
					try {
						Damageable dmg = p;
						dmg.setHealth(dmg.getHealth()+2);
					} catch (Exception e){}
					pl.api.ls.callDelay(pl, this, "awareCheck", p);
					used.add(p);
					s(p, "&4>> &aA wave is coming! Shift-left click to be safe! &4<<");
					if (p.getNearbyEntities(25D, 30D, 25D).size() <= 120){
						for (int x = 0; x <= wcs.getPatrolDiff(); x++){
							LivingEntity le = (LivingEntity) wcs.getPatrolHotSpot().getWorld().spawnEntity(wcs.getPatrolHotSpot(), EntityType.ZOMBIE);
							le.setMaxHealth(30); le.setHealth(30);
							ents.add(le);
							le.setCustomName(AS(getRandomChatColor() + "Hotspot Guardian")); le.setCustomNameVisible(true);
							Location ploc = p.getLocation();
							le = (LivingEntity) p.getWorld().spawnEntity(new Location(p.getWorld(), ploc.getX()+5, ploc.getY()+2, ploc.getZ()-3), getRandomEntity());
							p.getWorld().playEffect(le.getLocation(), Effect.STEP_SOUND, Material.EMERALD_BLOCK.getId());
							le.getEquipment().setItemInHand(wcs.getPatrolItems().get(wcs.getPatrolDiff()-1));
							if (wcs.getPatrolDiff() == 3){
								ItemStack i = wcs.getPatrolItems().get(wcs.getPatrolDiff()-1);
								ItemMeta im = i.getItemMeta();
								im.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
								i.setItemMeta(im);
								le.getEquipment().setItemInHand(i);
							}
							le.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
							le.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
							le.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
							le.setCustomName(AS(getRandomChatColor() + "Hotspot Minion")); le.setCustomNameVisible(true);
							ents.add(le);
						}
						if (!wcs.getPatrolHotSpotParticipants().contains(p.getName())){
							List<String> pr = wcs.getPatrolHotSpotParticipants();
							pr.add(p.getName());
							wcs.setPatrolParticipants(pr);
							pl.wcm.updateSystem("system", wcs);
						}
					}
					if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null){
						p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§3HOTSPOT LIFE: " + ((wcs.getPatrolDiff() * 100) - wcs.getPatrolKills()));
					}
					BarAPI.setMessage(p, "§3HOTSPOT LIFE", ((float)((wcs.getPatrolDiff() * 100) - wcs.getPatrolKills())/wcs.getPatrolDiff()));
				}
			}
		}

		if (used.size() <= 0){
			for (LivingEntity le : ents){
				if (!le.isDead()){
					le.remove();
				}
			}
		} else {
			wcs.setCanPatrolAware(true);
		}
		
		pl.wcm.updateSystem("system", wcs);
		pl.api.ls.callDelay(pl, this, "stopAware");
	}
}
