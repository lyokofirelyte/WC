package com.github.lyokofirelyte.WC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Util.FireworkShenans;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.ParagonFindEvent;

public class WCMobDrops implements Listener {

	WCMain pl;
	
	public WCMobDrops(WCMain instance){
	   pl = instance;
    } 
	
	List <Integer> laserFwTasks = new ArrayList<Integer>();
	
	 
	  @SuppressWarnings("deprecation")
	  @EventHandler(priority = EventPriority.NORMAL)
	  public void onPlayerBadTouch(PlayerInteractEvent e){

		 Player p = e.getPlayer();
		 
		 if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
			 if (p.getItemInHand().getType().equals(Material.GOLDEN_CARROT) && p.hasPermission("wa.staff")){
				 staffTool(p);
		 	 } else {
		 	  	 cookie(e, p);
		 	 }
		 }

	     if (p.getInventory().getItemInHand().getTypeId() == 371) {

	 		 Player player = p;
	 		  
	 		 if (player.getInventory().getItemInHand().getItemMeta().hasDisplayName()){
		 		  
	 			 if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().contains("Shinies") && player.getInventory().getItemInHand().getItemMeta().hasLore()) {
			        	  
	 				WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			        int goldInHand = player.getInventory().getItemInHand().getAmount();
			        
			        if (p.hasPermission("wa.celestial")){
			        	wcp.setBalance(wcp.getBalance() + (goldInHand * 70));
			        } else if (p.hasPermission("wa.statesman")){
			        	wcp.setBalance(wcp.getBalance() + (goldInHand * 50));
			        } else {
			        	wcp.setBalance(wcp.getBalance() + (goldInHand * 30));
			        }
			        
			        pl.wcm.updatePlayerMap(p.getName(), wcp);
			        player.setItemInHand(new ItemStack(Material.AIR)); 
			       	return;
	 			 }
	 		 }
	     }
	}

	private void staffTool(Player p) {

  	  final Entity proj1 = (Snowball) p.launchProjectile(Snowball.class);
	  Vector vec = p.getEyeLocation().getDirection();
	  proj1.setVelocity(vec.multiply(2));
	  p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 3.0F, 0.5F);
	      
			final List<Color> colors = new ArrayList<Color>();
			colors.add(Color.RED);
			colors.add(Color.WHITE);
			colors.add(Color.BLUE);
			colors.add(Color.ORANGE);
			colors.add(Color.FUCHSIA);
			colors.add(Color.AQUA);
			colors.add(Color.PURPLE);
			colors.add(Color.GREEN);
			colors.add(Color.TEAL);
			colors.add(Color.YELLOW);

	      
	      int laserTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
				
				public void run(){
					
					Random rand = new Random();
					int nextInt = rand.nextInt(10);
				
					
					FireworkShenans fplayer = new FireworkShenans();
					try {
						   fplayer.playFirework(proj1.getWorld(), proj1.getLocation(),
								   FireworkEffect.builder().with(Type.BURST).withColor(colors.get(nextInt)).build());
					   } catch (IllegalArgumentException e2) {
					   } catch (Exception e2) {
			    	 } 
				}
			}, 2L, 0L);
 
	      laserFwTasks.add(laserTask);
	      
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				 
		  		   public void run() 
		  		   {
		  			Bukkit.getServer().getScheduler().cancelTask(laserFwTasks.get(laserFwTasks.size()-1));
		  			laserFwTasks.remove(laserFwTasks.get(laserFwTasks.size()-1));
		  		   }
			 }
		     , 20L);
		
	}

	private void cookie(PlayerInteractEvent e, Player p) {
		
		String st = "SHEARS";
		
		if (p.getItemInHand().hasItemMeta() && e.getClickedBlock() != null && e.getAction() == Action.LEFT_CLICK_BLOCK){
			if (p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().hasDisplayName()){
				if (p.getItemInHand().getItemMeta().getDisplayName().toString().contains("HAMDRAX")){
					String block = e.getClickedBlock().getType().toString();
					short dur = p.getItemInHand().getDurability();

					List <String> picks = pl.config.getStringList("Hamdrax.Pick");
					List <String> shovels = pl.config.getStringList("Hamdrax.Shovel");
					List <String> axes = pl.config.getStringList("Hamdrax.Axe");
					List <String> shears = pl.config.getStringList("Hamdrax.Shears");
					List <String> swords = pl.config.getStringList("Hamdrax.Sword");
					
					if (picks.contains(block)){
							
						if (!p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
							if (st.contains(p.getItemInHand().getType().toString())){
								dur = (short) pl.datacore.getInt("Users." + p.getName() + ".HamDur");
								swapDrax(Material.DIAMOND_PICKAXE, p, dur, "Pick");
								return;
							}
							swapDrax(Material.DIAMOND_PICKAXE, p, dur, "Pick");
							return;
						}						
					}
						
					if (shovels.contains(block)){
							
						if (!p.getItemInHand().getType().equals(Material.DIAMOND_SPADE)){
							if (st.contains(p.getItemInHand().getType().toString())){
								dur = (short) pl.datacore.getInt("Users." + p.getName() + ".HamDur");
								swapDrax(Material.DIAMOND_SPADE, p, dur, "Shovel");
								return;
							}
							swapDrax(Material.DIAMOND_SPADE, p, dur, "Shovel");
							return;
						}					
					}

					if (axes.contains(block)){
							
						if (!p.getItemInHand().getType().equals(Material.DIAMOND_AXE)){
							if (st.contains(p.getItemInHand().getType().toString())){
								dur = (short) pl.datacore.getInt("Users." + p.getName() + ".HamDur");
								swapDrax(Material.DIAMOND_AXE, p, dur, "Axe");
								return;
							}
							swapDrax(Material.DIAMOND_AXE, p, dur, "Axe");
							return;
						}						
					}
						
					if (shears.contains(block)){
							
						if (!p.getItemInHand().getType().equals(Material.SHEARS)){
							swapDrax(Material.SHEARS, p, (short) 0, "Shears");
							pl.datacore.set("Users." + p.getName() + ".HamDur", dur);
							return;
						}
							
					}	
						
					if (swords.contains(block)){
						
						if (!p.getItemInHand().getType().equals(Material.DIAMOND_SWORD)){
							if (st.contains(p.getItemInHand().getType().toString())){
								dur = (short) pl.datacore.getInt("Users." + p.getName() + ".HamDur");
								swapDrax(Material.DIAMOND_SWORD, p, dur, "Sword");
								return;
							}
							swapDrax(Material.DIAMOND_SWORD, p, dur, "Sword");
							return;
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void swapDrax(Material type, Player p, short dur, String form) {

	    ArrayList<String> lore;
	    ItemStack token = new ItemStack(type, 1);
        ItemMeta name = token.getItemMeta();
        lore = new ArrayList<String>();
        name.addEnchant(Enchantment.DIG_SPEED, 5, true);
        name.addEnchant(Enchantment.DURABILITY, 3, true);
        name.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        name.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
        name.addEnchant(Enchantment.FIRE_ASPECT, 3, true);
        name.setDisplayName("§a§o§lHAMDRAX OF " + Utils.AS(p.getDisplayName()));
        lore.add("§7§oForm: " + form);
        name.setLore(lore);
        token.setItemMeta((ItemMeta)name);
        p.getInventory().setItemInHand(token);
        p.getItemInHand().setDurability(dur);
        p.updateInventory();	
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityKersplode(EntityDeathEvent event){
		
	
		if ((event.getEntity().getKiller() instanceof Player)) {
			
			EntityType type = event.getEntityType();
	
			if (pl.config.getStringList("Mobs.NoShinyDrops").contains(type.toString())){
			    return;
			}
			
			ArrayList<ItemStack> drops;
			ArrayList<String> lore;
			Random rand = new Random();
			int randomMoneyAmount = rand.nextInt(140) + 1;
			int randomNumber = rand.nextInt(4) + 1;
			int paragon = rand.nextInt(1000) + 1;
		
			if (randomNumber == 4) {
				
				ItemStack shard = new ItemStack(Material.GOLD_NUGGET, (Math.round(randomMoneyAmount) / 20));
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
	
	            Bukkit.broadcastMessage(Utils.AS(WCMail.WC + event.getEntity().getKiller().getDisplayName() + " &dhas found a &0death &dparagon from a " + event.getEntityType().toString().toLowerCase() + "&d."));
	            ParagonFindEvent pEvent = new ParagonFindEvent(event.getEntity().getKiller(), "&0death", "DEATH PARAGON", Utils.getRandomColor(), 15, Material.STAINED_CLAY, pl.wcm.getWCPlayer(event.getEntity().getKiller().getName()).getBlocksMined());
	            pl.getServer().getPluginManager().callEvent(pEvent);
			}
		}
	}
}