package com.github.lyokofirelyte.WC.Staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;

import static com.github.lyokofirelyte.WC.WCMain.s;
import static com.github.lyokofirelyte.WC.WCMain.s2;

public class WCCheats implements CommandExecutor {

	WCMain pl;
	public WCCheats(WCMain instance){
    this.pl = instance;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = ((Player)sender);
		
		switch (cmd.getName().toLowerCase()){
		
			case "sm":
				
				String armors = "diamond iron chain gold leather";
				int x = 0;
				
				if (args.length < 9 && args.length != 2){
					s(p, "/sm <type> <health> <nameTag> <armorType> <weapon> <potionEffect> <location> <passenger(s)> <amount>");
					s2(p, "&6Example: &7/sm zombie 20 Grumpy_Guy diamond diamond_sword damage Hugh_Jasses skeleton,zombie 1");
					s2(p, "&dUse a '_' for spaces. Location can be 'aim' or a player.");
					s2(p, "&dFor more passengers, seperate different mobs with a ','. Use # if you don't want a passenger.");
					s2(p, "&dYou can use /sm <type> ## to indicate no extra features. Example: /sm zombie ##");
					break;
				}
			
				for (EntityType e : EntityType.values()){
					if (e.toString().toLowerCase().equals(args[0].toLowerCase())){
						if (Utils.isInteger(args[1])){
							if (armors.contains(args[3].toLowerCase())){
								int y = 0;
								for (Material m : Material.values()){
									if (m.toString().toLowerCase().equals(args[4].toLowerCase())){
										int z = 0;
										for (PotionEffectType pe : PotionEffectType.values()){
											if (String.valueOf(pe).toString().toLowerCase().contains(args[5].toLowerCase())){
												if (Bukkit.getPlayer(args[6]) != null || args[6].equals("aim")){
													if (Utils.isInteger(args[8])){
														if (args[7].contains(",") || args[7].equals("#")){
															List<String> passengers = new ArrayList<String>();
															if (args[7].contains(",")){
																passengers = Arrays.asList(args[7].split(","));
															}
															List<EntityType> goodPassengers = new ArrayList<EntityType>();
															for (String passenger : passengers){
																int a = 0;
																for (EntityType ee : EntityType.values()){
																	if (passenger.toLowerCase().equals(ee.toString().toLowerCase())){
																		goodPassengers.add(ee);
																	} else {
																		a++;
																		if (a >= EntityType.values().length){
																			s(p, "The passenger " + passenger + " is not a valid entity.");
																			break;
																		}
																	}
																}
															}
															formMob(p, e, Integer.parseInt(args[1]), args[2], args[3], m, pe, args[6], goodPassengers, Integer.parseInt(args[8]));
															break;
														} else {
															int b = 0;
															for (EntityType ee : EntityType.values()){
																if (args[7].toLowerCase().equals(ee.toString().toLowerCase())){
																	List<EntityType> gp = Arrays.asList(ee);
																	formMob(p, e, Integer.parseInt(args[1]), args[2], args[3], m, pe, args[6], gp, Integer.parseInt(args[8]));
																	break;
																} else {
																	b++;
																	if (b >= EntityType.values().length){
																		s(p, "The passenger " + args[7] + " is not a valid entity.");
																		break;
																	}
																}
															}
														}
													} else {
														s(p, "You must use a number for the amount!");
														break;
													}
												} else {
													s(p, "You've entered an invalid player since and you didn't say 'aim'.");
													break;
												}
											} else {
												z++;
												if (z >= PotionEffectType.values().length){
													s(p, "The potion effect " + args[5] + " was not found.");
													break;
												}
											}
										}
									} else {
										y++;
										if (y >= Material.values().length){
											s(p, "The material " + args[4] + " was not found.");
											break;
										}
									}
								}
							} else {
								s(p, "Choose from " + armors.replace(" ", ", "));
								break;
							}
						} else if (args[1].equals("##")){
							p.getWorld().spawnEntity(p.getTargetBlock(null, 20).getLocation(), e);
							break;
						} else {
							s(p, "Health must be a number!");
							break;
						}
					} else if (args[0].equals("#")){
						s(p, "Why the hell did you use this command then? GAH");
					} else {
						x++;
						if (x >= EntityType.values().length){
							s(p, "The entity " + args[0] + " was not found.");
							break;
						}
					}
				}
				
			break;
		
			case "sit": // Yeah, this is pretty ghetto.
				
				if (p.isInsideVehicle()){
					p.eject();
				} else {
					LivingEntity e = (LivingEntity) p.getWorld().spawnEntity(p.getLocation(), EntityType.SQUID);
					e.setMaxHealth(9999999);
					e.setHealth(9999999);
					e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1));
					e.setPassenger(p);	
				}
				
			break;
		
			case "dis":
				
				if (args.length == 0){
					p.setCustomName(p.getName());
					s(p, "Disguised as yourself! Wait a minute...");
				} else {
					p.setCustomName(args[0]);
					s(p, "Disguised as " + args[0] + "!");
				}
				
			break;
		
			case "wlist":
				
				for (World w : Bukkit.getWorlds()){
					StringBuilder sb = new StringBuilder();
					sb.append("&6" + w.getName() + " ");
					for (Player current : Bukkit.getOnlinePlayers()){
						if (current.getWorld() == w){
							sb.append(current.getDisplayName() + " ");
						}
					}
					String sb2 = sb.toString().trim();
					s2(p, sb2.replaceAll(" ", "&8, "));
				}
				
			break;
			
			case "vanish": case "v":
				
				WCSystem wcs = pl.wcm.getWCSystem("system");
				List<String> vanished = wcs.getVanishedPlayers();
				
				if (vanished.contains(p.getName())){
					vanished.remove(p.getName());
					s(p, "ELABORATE UNVANISH HOAX!");
					p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 2);
					p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 2);
					for (Player player : Bukkit.getOnlinePlayers()){
						player.showPlayer(p);
					}
				} else {	
					vanished.add(p.getName());
					s(p, "ELABORATE VANISH HOAX!");
					p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 2);
					p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 2);
					for (Player player : Bukkit.getOnlinePlayers()){
						player.hidePlayer(p);
					}
				}
				
				wcs.setVanishedPlayers(vanished);
				pl.wcm.updateSystem("system", wcs);
	
			break;
			
			case "world":
			
				if (args.length != 1){
					s(p, "/world <world>");
				} else if (Bukkit.getWorld(args[0]) == null){
					s(p, "That world does not exist. See /wlist for all of them.");
				} else {
					Location l = p.getLocation();
					p.teleport(new Location(Bukkit.getWorld(args[0]), l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw()));
					Utils.effects(p);
					s(p, "Inter-dimensional temporal shift completed.");
				}
				
			break;
		
			case "feed":
				
				if (args.length == 0){
					p.setFoodLevel(20);
					p.setSaturation(3);
				} else if (Bukkit.getPlayer(args[0]) != null){
					Bukkit.getPlayer(args[0]).setFoodLevel(20);
				} else {
					s(p, "Player not found!");
				}
			
			break;
		
			case "gm":
	
				if (args.length == 0){
					s(p, "/gm s || c || a");
					break;
				}
				
				GameMode gm;
				Boolean fly;
				
				switch (args[0].toLowerCase()){
				
					case "s": gm = GameMode.SURVIVAL; fly = false; break;
					case "c": gm = GameMode.CREATIVE; fly = true; break;
					case "a": gm = GameMode.ADVENTURE; fly = false; break;
					default: s(p, "/gm s || c || a"); return true;
				}
				
				if (args.length == 2){
					
					if (Bukkit.getPlayer(args[1]) == null){
						s(p, "That player is not online. /gm <s || c || a> <player>");
						break;
					}
					
					Player q = Bukkit.getPlayer(args[1]);
					q.setGameMode(gm);	
					q.setAllowFlight(fly);
					q.setFlying(fly);
					s(q, "GM updated.");
					s(p, "GM updated for " + q.getDisplayName());
					return true;
				}
				
				p.setGameMode(gm);
				p.setAllowFlight(fly);
				p.setFlying(fly);
				s(p, "GM updated.");
				
			break;
			
			case "fly":
				
				Player q;
				
				if (args.length == 1){
					
					if (Bukkit.getPlayer(args[0]) == null){
						s(p, "That player is not online.");
						break;
					}
					
					q = Bukkit.getPlayer(args[0]);
					
				} else {
					q = p;
				}
				
				if (q.isFlying() || q.getAllowFlight()){
					q.setAllowFlight(false);
					q.setFlying(false);
					s(q, "Fly mode &4disabled&d.");
				} else {
					q.setAllowFlight(true);
					q.setFlying(true);
					s(q, "Fly mode &aenabled&d.");
				}
				
			break;
			
			case "i":
				
				if (args.length == 0 || p.getInventory().firstEmpty() == -1){
					s(p, "/i <item> (must have room!)");
					break;
				}

				for (Material m : Material.values()){
					if (m.name().toString().toLowerCase().contains(args[0])){
						p.getInventory().addItem(new ItemStack(m, 64));
						break;
					} else if (Utils.isInteger(args[0]) && m.getId() == Integer.parseInt(args[0])){
						p.getInventory().addItem(new ItemStack(m, 64));
						break;
					}
				}
				
			break;
			
			case "ci":
				
				if (args.length == 0){
					pl.backupInvs.put(p.getName(), p.getInventory().getContents());
					p.getInventory().clear();
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_BREAK, 3F, 0.5F);
					s(p, "Cleared! Type /ci u to restore your inventory.");
				} else if (pl.backupInvs.containsKey(p.getName())){
					p.getInventory().setContents(pl.backupInvs.get(p.getName()));
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 3F, 0.5F);
					s(p, "Inventory restored!");
				} else {
					s(p, "No backup inventory found.");
				}
				
			break;	
			
			case "more":
				
				if (p.getInventory().getItemInHand() != null){
					p.getInventory().getItemInHand().setAmount(64);
				}
				
			break;
			
			case "back":
				
				WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
				if (wcp.getLastLocation() != null){
					Location l = p.getLocation();
					p.teleport(wcp.getLastLocation());
					wcp.setLastLocation(l);
					pl.wcm.updatePlayerMap(p.getName(), wcp);
					s(p, "Teleporting!");
				} else {
					s(p, "No previous location found!");
				}
				
			break;
			
			case "tppos":
				
				if (args.length != 3){
					s(p, "/tppos x y z");
				} else if (Utils.isInteger(args[0]) && Utils.isInteger(args[1]) && Utils.isInteger(args[2])){
					p.teleport(new Location(p.getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
				} else {
					s(p, "/tppos x y z");
				}
			
			break;
			
			case "speed":
				
				if (args.length != 1 || !Utils.isInteger(args[0]) || Integer.parseInt(args[0]) > 10 || Integer.parseInt(args[0]) < 0){
					s(p, "/speed <#>");
				} else {
					if (p.isFlying()){
						p.setFlySpeed(Float.parseFloat(args[0])/10);
					} else {
						p.setWalkSpeed(Float.parseFloat(args[0])/10);
					}
					s(p, "Speed updated.");
				}
				
			break;
			
			case "killall":
				
				int radius = 1000;
				int killed = 0;
				
				if (args.length > 0 && Utils.isInteger(args[0])){
					radius = Integer.parseInt(args[0]);
				}
				
				for (Entity e : p.getNearbyEntities(radius, radius, radius)){
					if (e instanceof Player == false){
						e.remove();
						killed++;
					}
				}
				
				s(p, "Killed &6" + killed + " &dmobs!");
			
			break;
		}
		
		return true;
	}

	@SuppressWarnings("deprecation")
	private void formMob(Player p, EntityType e, int health, String nameTag, String armorType, Material m, PotionEffectType pe, String location, List<EntityType> goodPassengers, int amount) {
		
		List<LivingEntity> les = new ArrayList<LivingEntity>();
		List<LivingEntity> passengers = new ArrayList<LivingEntity>();
		int z = 1;
		
		if (location.equals("aim")){
			for (int x = 0; x < amount; x++){
				LivingEntity le = (LivingEntity) p.getWorld().spawnEntity(p.getTargetBlock(null, 20).getLocation(), e);
				les.add(le);
			}
		} else {
			for (int x = 0; x < amount; x++){
				LivingEntity le = (LivingEntity) p.getWorld().spawnEntity(Bukkit.getPlayer(location).getLocation(), e);
				les.add(le);
			}
		}

		for (LivingEntity le : les){
			
			for (EntityType et : goodPassengers){
				LivingEntity la = (LivingEntity) p.getWorld().spawnEntity(p.getTargetBlock(null, 20).getLocation(), et);
				passengers.add(la);
			}

			for (LivingEntity la : passengers){
				if (z < passengers.size()){
					la.setPassenger(passengers.get(z));
				}
				z++;
			}		
			
			if (passengers.size() > 0){
				le.setPassenger(passengers.get(0));
			}
			
			le.addPotionEffect(new PotionEffect(pe, 99999, 1));
			le.setCustomName(Utils.AS(nameTag.replaceAll("_", " ")));
			le.setCustomNameVisible(true);
			le.getEquipment().setItemInHand(new ItemStack(m, 1));
			le.setMaxHealth(health);
			le.setHealth(health);
			passengers = new ArrayList<LivingEntity>();
			
			switch(armorType){
				case "diamond":
					le.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
					le.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
					le.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
					le.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				break;
				case "iron":
					le.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
					le.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
					le.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					le.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
				break;
				case "leather":
					le.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
					le.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
					le.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
					le.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
				break;
				case "chain":
					le.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
					le.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
					le.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
					le.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
				break;
				case "gold":
					le.getEquipment().setBoots(new ItemStack(Material.GOLD_BOOTS));
					le.getEquipment().setHelmet(new ItemStack(Material.GOLD_HELMET));
					le.getEquipment().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
					le.getEquipment().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
				break;		
			}
		}	
	}
}
