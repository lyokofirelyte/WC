package com.github.lyokofirelyte.WC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Util.FireworkShenans;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCMobDrops implements Listener {

	WCMain plugin;
	WCPlayer wcp;
	
	public WCMobDrops(WCMain instance){
	   plugin = instance;
    } 
	
	List <Integer> laserFwTasks = new ArrayList<Integer>();
	
	
	 @SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	  public void onPlayerBadTouch(PlayerInteractEvent event){
		 

		 	  	if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
		 	  		cookie(event, event.getPlayer());
		 	  	} 
		 		 
	    	  	if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
	    	  		
	    	  		switch (event.getClickedBlock().getType()){
	    	  		
		    	  		default:
		    	  			
		    	  		break;
		    	  		
		    	  		case WALL_SIGN:
		    	  			
		    	  			paragonSign(event, event.getPlayer());
		    	  			
		    	  	    break;
		    	  	    
		    	  		case STONE_BUTTON:
		    	  			
		    	  			paragonCheckout(event, event.getPlayer());
		    	  			
		    	  	    break;
		    	  	    
		    	  		case GLOWSTONE:
		    	  			
		    	  			obeliskCheck(event, event.getPlayer());
		    	  			
		    	  	    break;
	    	  	    
	    	  		}
	    	  	}
   	  	
	    	  if (event.getPlayer().getInventory().getItemInHand().getTypeId() == 371) {

	 		  Player player = event.getPlayer();
	 		  
		 		  if (player.getInventory().getItemInHand().getItemMeta().hasDisplayName()){
		 		  
			          if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().contains("Shinies") && 
			        	  player.getInventory().getItemInHand().getItemMeta().hasLore()) {
			        	  
			        	  int goldInHand = player.getInventory().getItemInHand().getAmount();
			        	  if (event.getPlayer().hasPermission("wa.statesman")){
			        	  Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + player.getName() + " " + (goldInHand * 40));
			        	  } else {
			        	  Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + player.getName() + " " + (goldInHand * 20));
			        	  }
			        	  player.setItemInHand(new ItemStack(Material.AIR)); 
			        	  return;
			       
			          }
		        }
	    	}
	    }

	private void cookie(PlayerInteractEvent e, Player p) {
		
	if (e.getAction() == Action.RIGHT_CLICK_AIR){
		
		Location loc = p.getLocation();
		double y = loc.getY();
		y++;
		Location newLoc = new Location(p.getWorld(), loc.getX(),y,loc.getZ());
		
		if (p.isSneaking() &&  plugin.datacore.getBoolean("Users." + p.getName() + ".Throw")){
			
			ItemStack i = p.getItemInHand();
			if (i.hasItemMeta() || i.getType().isEdible() || i.getType().toString().equals("EGG")
				|| i.getType().toString().equals("POTION")  || i.getType().toString().equals("BOW") || i.getType().toString().contains("SWORD") || i.getType().toString().contains("HELMET") ||
				i.getType().toString().contains("CHESTPLATE") || i.getType().toString().contains("LEGGING") || i.getType().toString().contains("BOOTS")){
				
				p.sendMessage(WCMail.WC + "You can't throw that!");
				return;
			}
			ItemStack cobble = new ItemStack(i.getType(), 1);
			cobble.setDurability(i.getDurability());
			Item dropped = p.getWorld().dropItem(newLoc, cobble);
			dropped.setPickupDelay(20);
			dropped.setVelocity(p.getLocation().getDirection().add(dropped.getVelocity().setY(0.5)));
		    ItemStack old = new ItemStack(i.getType(), i.getAmount() - 1);
		    p.setItemInHand(old);
			p.getWorld().playSound(p.getLocation(), Sound.CLICK, 3.0F, 0.5F);
			return;
		}
	
		return;
	}
		double x = e.getClickedBlock().getX();
		double y = e.getClickedBlock().getY();
		double z = e.getClickedBlock().getZ();
		
		if (x == -270.0 && y == 64.0 && z == -42.0){
  	  	
			int cookies = plugin.datacore.getInt("Users." + p.getName() + ".Cookies");
			cookies++;
			plugin.datacore.set("Users." + p.getName() + ".Cookies", cookies);
			
			int goalCount = plugin.datacore.getInt("Users." + p.getName() + ".GoalCount");
			
				if (goalCount >= 100){
					p.sendMessage(WCMail.WC + "Cookie count: " + cookies);
					plugin.datacore.set("Users." + p.getName() + ".GoalCount", 0);
				}
				
			goalCount = plugin.datacore.getInt("Users." + p.getName() + ".GoalCount");
			goalCount++;
			plugin.datacore.set("Users." + p.getName() + ".GoalCount", goalCount);
			return;
			
		}
		
		if (p.getItemInHand().hasItemMeta()){
			if (p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().hasDisplayName()){
				if (p.getItemInHand().getItemMeta().getDisplayName().toString().contains("HAMDRAX")){
					String block = e.getClickedBlock().getType().toString();
					short dur = p.getItemInHand().getDurability();

					List <String> picks = plugin.config.getStringList("Hamdrax.Pick");
					List <String> shovels = plugin.config.getStringList("Hamdrax.Shovel");
					List <String> axes = plugin.config.getStringList("Hamdrax.Axe");
					List <String> shears = plugin.config.getStringList("Hamdrax.Shears");
					List <String> swords = plugin.config.getStringList("Hamdrax.Sword");
					
						if (picks.contains(block)){
							
							if (!p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
								if (p.getItemInHand().getType().toString().equals("SHEARS")){
									dur = (short) plugin.datacore.getInt("Users." + p.getName() + ".HamDur");
									swapDrax(Material.DIAMOND_PICKAXE, p, dur, "Pick");
									return;
								}
								swapDrax(Material.DIAMOND_PICKAXE, p, dur, "Pick");
							}
							
						}
						
						if (shovels.contains(block)){
							
							if (!p.getItemInHand().getType().equals(Material.DIAMOND_SPADE)){
								if (p.getItemInHand().getType().toString().equals("SHEARS")){
									dur = (short) plugin.datacore.getInt("Users." + p.getName() + ".HamDur");
									swapDrax(Material.DIAMOND_SPADE, p, dur, "Shovel");
									return;
								}
								swapDrax(Material.DIAMOND_SPADE, p, dur, "Shovel");
							}
							
						}

						if (axes.contains(block)){
							
							if (!p.getItemInHand().getType().equals(Material.DIAMOND_AXE)){
								if (p.getItemInHand().getType().toString().equals("SHEARS")){
									dur = (short) plugin.datacore.getInt("Users." + p.getName() + ".HamDur");
									swapDrax(Material.DIAMOND_AXE, p, dur, "Axe");
									return;
								}
								swapDrax(Material.DIAMOND_AXE, p, dur, "Axe");
							}
							
						}
						
						if (shears.contains(block)){
							
							if (!p.getItemInHand().getType().equals(Material.SHEARS)){
								swapDrax(Material.SHEARS, p, (short) 0, "Shears");
								plugin.datacore.set("Users." + p.getName() + ".HamDur", dur);
							}
							
						}	
						
						if (swords.contains(block)){
							
							if (!p.getItemInHand().getType().equals(Material.DIAMOND_SWORD)){
								if (p.getItemInHand().getType().toString().equals("SHEARS")){
									dur = (short) plugin.datacore.getInt("Users." + p.getName() + ".HamDur");
									swapDrax(Material.DIAMOND_SWORD, p, dur, "Sword");
									return;
								}
								swapDrax(Material.DIAMOND_SWORD, p, dur, "Sword");
							}
							
						}
				}
			}
		}
	}




	@SuppressWarnings("deprecation")
	public
	static void swapDrax(Material type, Player p, short dur, String form) {

	    ArrayList<String> lore;
	    ItemStack token = new ItemStack(type, 1);
        ItemMeta name = token.getItemMeta();
        lore = new ArrayList<String>();
        name.addEnchant(Enchantment.DIG_SPEED, 5, true);
        name.addEnchant(Enchantment.DURABILITY, 3, true);
        name.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        name.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
        name.addEnchant(Enchantment.FIRE_ASPECT, 3, true);
        name.setDisplayName("§a§o§lHAMDRAX OF " + p.getDisplayName());
        lore.add("§7§oForm: " + form);
        name.setLore(lore);
        token.setItemMeta((ItemMeta)name);
        p.getInventory().setItemInHand(token);
        p.getItemInHand().setDurability(dur);
        p.updateInventory();	
	}




	private void obeliskCheck(PlayerInteractEvent e, Player p) {
		
		double x = e.getClickedBlock().getX();
		double y = e.getClickedBlock().getY();
		double z = e.getClickedBlock().getZ();
		String xyz = x + "," + y + "," + z;
		
		Boolean clickedAlready = plugin.datacore.getBoolean("Users." + p.getName() + ".ObeliskSelection");
			if (clickedAlready){
				
				String latest = plugin.datacore.getString("Users." + p.getName() + ".ObeliskLocation");
				String latestCoords = plugin.config.getString("Obelisks.ListGrab." + latest);
				
				double xTo = plugin.config.getInt("Obelisks.Locations." + latestCoords + ".X");
				double yTo = plugin.config.getInt("Obelisks.Locations." + latestCoords + ".Y");
				double zTo = plugin.config.getInt("Obelisks.Locations." + latestCoords + ".Z");
						
				String location = plugin.datacore.getString("Users." + p.getName() + ".ObeliskLocation");
				obeliskTeleport(e, p, location, x, y, z, xTo, yTo, zTo);
				return;
			}
	
		
		String obeliskCoords = plugin.config.getString("Obelisks.Locations." + xyz + ".Name");
		
		if (obeliskCoords == null){
			return;
		} else if(p.getFoodLevel() != 20){
			p.sendMessage(WCMail.WC + "You must have full energy to use this!");
			return;
		} else {
			plugin.datacore.set("Users." + p.getName() + ".ObeliskTemp", true);
			List <String> obeliskLocations = plugin.config.getStringList("Obelisks.Names");
			p.sendMessage(WCMail.WC + "Please type the location you would like to visit or type ## to cancle!");
		    p.sendMessage(obeliskLocations.toString());
		}
	}




	private void obeliskTeleport(PlayerInteractEvent e, final Player p, String location, final double x, final double y, final double z, final double xTo, final double yTo, final double zTo) {

		plugin.datacore.set("Users." + p.getName() + ".ObeliskSelection", null);
		
		final World world = p.getWorld();
		Location loc = new Location(world, x, y, z);
		
		final Location tp = new Location(world, x, y+1, z, 0, 180);
		Location loc2 = new Location(world, x, y+1, z);
		final Location finalLoc = new Location(world, xTo, yTo+50, zTo, 0, 180);
		final Location finalLocLanding = new Location(world, xTo, yTo, zTo, 0, 180);
        p.teleport(tp);
		world.playEffect(loc, Effect.ENDER_SIGNAL, 0);
		world.playEffect(loc2, Effect.ENDER_SIGNAL, 0);
		world.playEffect(loc, Effect.BLAZE_SHOOT, 0);
		world.playEffect(loc2, Effect.MOBSPAWNER_FLAMES, 0);
		world.playEffect(loc, Effect.SMOKE, 0);
		world.playEffect(loc2, Effect.SMOKE, 0);
		p.setFoodLevel(0);
		
		fireWorkCrazyAssShit(loc, p);
		
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 999999999, 0));
		        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 60, 0));
		        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 9999999, 5));
		        plugin.datacore.set("Users." + p.getName() + ".NoDamage", true);
		      }
		    }
		    , 10L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	p.setVelocity(new Vector(0, 3, 0));
		      }
		    }
		    , 15L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	  p.setVelocity(new Vector(0, 5, 0));
		    	  world.playEffect(p.getLocation(), Effect.GHAST_SHOOT, 0);
		      }
		    }
		    , 55L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	p.teleport(finalLoc);
		    	fireWorkCrazyAssShit(finalLocLanding, p);
		      }
		    }
		    , 70L);	 
			 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	  p.setVelocity(new Vector(0, -5, 0));
		    	  world.playEffect(p.getLocation(), Effect.GHAST_SHOOT, 0);
		      }
		    }
		    , 83L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	world.playEffect(finalLocLanding, Effect.ENDER_SIGNAL, 0);
		  		world.playEffect(finalLocLanding, Effect.ENDER_SIGNAL, 0);
		  		world.playEffect(finalLocLanding, Effect.BLAZE_SHOOT, 0);
		  		world.playEffect(finalLocLanding, Effect.MOBSPAWNER_FLAMES, 0);
		  		world.playEffect(finalLocLanding, Effect.SMOKE, 0);
		  		p.removePotionEffect(PotionEffectType.CONFUSION);
		  		p.removePotionEffect(PotionEffectType.NIGHT_VISION);
		  		plugin.datacore.set("Users." + p.getName() + ".NoDamage", null);
		  		p.sendMessage(WCMail.WC + "You've arrived safely.. but you feel strangely drained of energy...");
		      }
		    }
		    , 105L);
		 
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
		    {
		      public void run()
		      {
		    	  p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		      }
		    }
		    , 175L);
	}




	public void fireWorkCrazyAssShit(Location loc, final Player p) {
		
	int w = 0;
		
	while (w <= 50){
        List<Location> circleblocks = Utils.circle(p, loc, 5, 1, true, false, w);
        long delay =  0L;
        
        	for (final Location l : circleblocks){
        		delay = delay + 3L;
        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
        	    {
        	      public void run()
        	      {
        	        	FireworkShenans fplayer = new FireworkShenans();
        	        	try {
							fplayer.playFirework(p.getWorld(), l,
							FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).build());
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}        	      }
        	    }
        	    , delay);
        	}
        	
        w++;

	}
		
	}




	private void paragonCheckout(PlayerInteractEvent e, Player p) {
		
		
		
		double x = e.getClickedBlock().getX();
		double y = e.getClickedBlock().getY();
		double z = e.getClickedBlock().getZ();
		String xyz = x + "," + y + "," + z;
		
		String rewardInfo = plugin.config.getString("Paragons.Rewards." + xyz + ".Info");
		
			if (rewardInfo == null){
				return;
				
			} else {
				
				int action = plugin.datacore.getInt("Users." + p.getName() + ".ParagonAction");
				
					switch (action){
					
					case 1:
						p.sendMessage(WCMail.WC + "That updates automatically now! Buy something else!");
						break;
					
					case 2: 
						paragonAmountCheck(15, p, action);
						break;
						
					case 3: 
						paragonAmountCheck(10, p, action);
						break;
						
					case 4: 
						paragonAmountCheck(10, p, action);
						break;
						
					case 5: 
						paragonAmountCheck(10, p, action);
						break;
						
					case 6: 
						paragonAmountCheck(5, p, action);
						break;
						
					case 7: 
						paragonAmountCheck(20, p, action);
						break;
						
					case 8: 
						paragonAmountCheck(20, p, action);
						break;
						
					case 9: 
						paragonAmountCheck(45, p, action);
						break;
						
					case 10:
						paragonAmountCheck(64, p, action);
						break;
						
					case 11:
						paragonAmountCheck(15, p, action);
						break;
						
					default:
						p.sendMessage(WCMail.WC + "You've not selected anything to purchase!");
						break;
					}
			}
		
	}


	private void paragonAmountCheck(int cost, Player p, int action) {
		
		if (p.getInventory().getItemInHand().hasItemMeta()){
			
			if (p.getInventory().getItemInHand().getItemMeta().hasDisplayName()){

				if (p.getInventory().getItemInHand().getItemMeta().getDisplayName().toString().contains("TOKEN")
					&& p.getInventory().getItemInHand().getItemMeta().hasLore()){
				
					int amount = p.getInventory().getItemInHand().getAmount();
				
						if (amount < cost){
							p.sendMessage(WCMail.WC + "You know very well you don't have enough to afford that.");
							return;
						} else {
							paragonRewardFinal(cost, p, action, amount);
							return;
						}
				
				} else {
					p.sendMessage(WCMail.WC + "Please hold your tokens in your hand while making a purchase.");
					return;
				}
			} else {
				p.sendMessage(WCMail.WC + "Please hold your tokens in your hand while making a purchase.");
				return;
			}
		} else {
			p.sendMessage(WCMail.WC + "Please hold your tokens in your hand while making a purchase.");
			return;
		}
	}


	private void paragonRewardFinal(int cost, Player p, int action, int amount) {
		
		switch (action){
		
		case 1:
			
			p.sendMessage(WCMail.WC + "That updates automatically now, buy something else!");
			break;
		
		case 2:
			
			int teleports = plugin.datacore.getInt("Users." + p.getName() + ".TPTokens");
			teleports = teleports + 3;
			plugin.datacore.set("Users." + p.getName() + ".TPTokens", teleports);
			p.sendMessage(WCMail.WC + "Added 3 TP tokens to your account! Total: " + teleports);
			p.sendMessage(WCMail.WC + "Use them with /wc tp <player>");
			p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
			List <String> purchases1 = plugin.datacore.getStringList("Users." + p.getName() + ".Purchases");
			purchases1.add("/wc tp <player> - Remaining Tokens: " + teleports);
			plugin.datacore.set("Users." + p.getName() + ".Purchases", purchases1);
			break;
			
		case 3:
			
			Boolean home = plugin.datacore.getBoolean("Users." + p.getName() + ".SpecialHome");
			
				if (home){
					p.sendMessage(WCMail.WC + "Hey, if you want to waste your tokens, Hugs could use some extra ones.");
					break;
				} else {
					plugin.datacore.set("Users." + p.getName() + ".SpecialHome", true);
					p.sendMessage(WCMail.WC + "You can now set your special home with /wc sethome. Use it with /wc home.");
					List <String> purchases2 = plugin.datacore.getStringList("Users." + p.getName() + ".Purchases");
					purchases2.add("/wc sethome, /wc home");
					plugin.datacore.set("Users." + p.getName() + ".Purchases", purchases2);
					p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
				} 
				
			break;
			
		case 4:
			
			int backs = plugin.datacore.getInt("Users." + p.getName() + ".BackTokens");
			backs = backs + 1;
			plugin.datacore.set("Users." + p.getName() + ".BackTokens", backs);
			p.sendMessage(WCMail.WC + "Added 1 back tokens to your account! Total: " + backs);
			p.sendMessage(WCMail.WC + "Use them with /wc back");
			p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
			List <String> purchases3 = plugin.datacore.getStringList("Users." + p.getName() + ".Purchases");
			purchases3.add("/wc back - Remaining Tokens: " + backs);
			plugin.datacore.set("Users." + p.getName() + ".Purchases", purchases3);
			break;
			
		case 5:
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 999999999, 0));
			p.sendMessage(WCMail.WC + "Enjoy!");
			p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
			break;
			
		case 6:
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 0));
			p.sendMessage(WCMail.WC + "Enjoy!");
			p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
			break;
			
		case 7:
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 1));
			p.sendMessage(WCMail.WC + "Enjoy!");
			p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
			break;
			
		case 8:
			
			Boolean market = plugin.datacore.getBoolean("Users." + p.getName() + ".Market");
			
			if (market){
				p.sendMessage(WCMail.WC + "Hey, if you want to waste your tokens, Hugs could use some extra ones.");
				break;
			} else {
				plugin.datacore.set("Users." + p.getName() + ".Market", true);
				p.sendMessage(WCMail.WC + "You can now warp to the market with /wc market.");
				List <String> purchases4 = plugin.datacore.getStringList("Users." + p.getName() + ".Purchases");
				purchases4.add("/wc market");
				plugin.datacore.set("Users." + p.getName() + ".Purchases", purchases4);
				p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
			} 
			
		break;
		
		case 9:
			
			Boolean money = plugin.datacore.getBoolean("Users." + p.getName() + ".MoneyUsed");
			
				if (money){
					p.sendMessage(WCMail.WC + "You've already purchased this!");
					break;
				}
				
				plugin.datacore.set("Users." + p.getName() + ".MoneyUsed", true);
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "eco give " + p.getName() + " 85000");
				p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
				break;
				
		case 10:
			
			Boolean aval = plugin.datacore.getBoolean("Users." + p.getName() + ".Hamdrax");
				if (aval == false){
					plugin.datacore.set("Users." + p.getName() + ".Hamdrax", true);
					p.sendMessage(WCMail.WC + "You need to claim this with /wc hamdrax!");
					p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
					break;
				} else {
					p.sendMessage(WCMail.WC + "You need to claim this with /wc hamdrax!");
				}
				
			break;
			
		case 11:
			
			Boolean aval2 = plugin.datacore.getBoolean("Users." + p.getName() + ".HamdraxRepair");
			if (aval2 == false){
				plugin.datacore.set("Users." + p.getName() + ".HamdraxRepair", true);
				p.sendMessage(WCMail.WC + "You need to claim this with /wc hamrepair!");
				p.getInventory().getItemInHand().setAmount((amount - (cost-1)));
				break;
			} else {
				p.sendMessage(WCMail.WC + "You need to claim this with /wc hamrepair!");
			}
			
		}
		
		
	}

	private void paragonSign(PlayerInteractEvent e, Player p) {
		
		double x = e.getClickedBlock().getX();
		double y = e.getClickedBlock().getY();
		double z = e.getClickedBlock().getZ();
		String xyz = x + "," + y + "," + z;
		
		String rewardInfo = plugin.config.getString("Paragons.Rewards." + xyz + ".Info");
		
			if (rewardInfo == null){
				return;
				
			} else {
				
				int act = plugin.config.getInt("Paragons.Rewards." + xyz + ".Action");
				p.sendMessage(Utils.AS(WCMail.WC + rewardInfo));
				p.sendMessage(WCMail.WC + "Purchase this by pressing the checkout button.");
				plugin.datacore.set("Users." + p.getName() + ".ParagonAction", act);
			}
		
	}


	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityKersplode(EntityDeathEvent event){
		
	
	if ((event.getEntity().getKiller() instanceof Player)) {
		
		    EntityType type = event.getEntityType();
		    List <String> invalidMobs = plugin.config.getStringList("Mobs.NoShinyDrops");

		    	if (invalidMobs.contains(type.toString())){
		    		return;
		    	}
		
			ArrayList<ItemStack> drops;
			ArrayList<String> lore;
			Random rand = new Random();
			Random moneyAmount = new Random();
			Random paragonRand = new Random();
			int randomMoneyAmount = moneyAmount.nextInt(140) + 1;
		    int randomNumber = rand.nextInt(4) + 1;
		    int paragon = paragonRand.nextInt(1000) + 1;
		
		
		if (randomNumber == 4) {
			
			ItemStack shard = new ItemStack(Material.GOLD_NUGGET, (randomMoneyAmount / 20));
            ItemMeta name = shard.getItemMeta();
            drops = new ArrayList<ItemStack>();
            lore = new ArrayList<String>();

            name.addEnchant(Enchantment.DURABILITY, 10, true);
            	if (event.getEntity().getKiller().hasPermission("wa.statesman")){
            		name.setDisplayName("§6§oFourty Shinies!");
            	} else {
            		name.setDisplayName("§6§oTwenty Shinies!");
            	}
            lore.add("§7§oIt's so shiny...I should right click with it in my hand.");
            name.setLore(lore);
            shard.setItemMeta((ItemMeta)name);
            drops.add(shard);
            event.getDrops().addAll(drops);
          }
		
		if (paragon == 500 && event.getEntity().getKiller().getWorld().getName().equals("world")){
			
			ItemStack paragonDrop = new ItemStack(Material.STAINED_CLAY, 1, (short) 15);
            ItemMeta paragonName = paragonDrop.getItemMeta();
            drops = new ArrayList<ItemStack>();
            lore = new ArrayList<String>();

            paragonName.addEnchant(Enchantment.DURABILITY, 10, true);
            paragonName.setDisplayName("§0§lDEATH PARAGON");
            lore.add("§7§oI should return this to the paragon station near spawn...");
            paragonName.setLore(lore);
            paragonDrop.setItemMeta((ItemMeta)paragonName);
            drops.add(paragonDrop);
            event.getDrops().addAll(drops);
            Bukkit.broadcastMessage(Utils.AS(WCMail.WC + event.getEntity().getKiller().getDisplayName() + " &dhas found a &0death &dparagon from a " + event.getEntityType().toString().toLowerCase() + "&d."));
		}
		
	}
	
	
	
	}
}
