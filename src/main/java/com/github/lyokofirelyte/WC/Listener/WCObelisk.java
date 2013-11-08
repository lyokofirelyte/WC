package com.github.lyokofirelyte.WC.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCSystem;

public class WCObelisk implements Listener {
	
	WCMain plugin;
	public WCObelisk(WCMain instance){
	plugin = instance;
	}
	
	WCSystem system;
	List<String> obelisks;
	Inventory inv;
	public Map <String, Integer> coolDown = new HashMap<>();
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onInteract(PlayerInteractEvent e){
		
		system = plugin.wcm.getWCSystem("system");
		obelisks = system.getObelisks();
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
		
			for (String s : obelisks){
				String[] oblSplit = s.split(" ");
				double xx = Double.parseDouble(oblSplit[1]);
				double yy = Double.parseDouble(oblSplit[2]);
				double zz = Double.parseDouble(oblSplit[3]);
				
				if (xx == e.getClickedBlock().getLocation().getX() && yy == e.getClickedBlock().getLocation().getY() && zz == e.getClickedBlock().getLocation().getZ()){
					
					inv = Bukkit.createInventory(null, 18, "§bOb§cel§3is§4k §6Sy§1st§aem");
					
					for (String s2 : obelisks){
						oblSplit = s2.split(" ");
						inv.addItem(plugin.invManager.makeItem(oblSplit[0], "Click to fly...", false, Enchantment.DURABILITY, 1, Material.DIAMOND_BARDING, 1));
					}
					
					e.getPlayer().openInventory(inv);
					return;
				}
			}
			
		}

	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onClick(final InventoryClickEvent e){
		
		if (e.getInventory().getName().equals("§bOb§cel§3is§4k §6Sy§1st§aem") && e.getCurrentItem() != null && e.getWhoClicked() instanceof Player){
			e.setCancelled(true);
			final Player p = ((Player)e.getWhoClicked());
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.CLICK, 3F, 0.5F);
			
			for (final String s : obelisks){
				
				if (s.startsWith(e.getCurrentItem().getItemMeta().getDisplayName())){
					
					String[] oblSplit = s.split(" ");
					
					final double xTarget = Double.parseDouble(oblSplit[1]);
					final double zTarget = Double.parseDouble(oblSplit[3]);
					
					final double xStart = p.getLocation().getX();
					final double zStart = p.getLocation().getZ();

					final double xCurrent = p.getLocation().getX();
					final double zCurrent = p.getLocation().getZ();
					
					p.setAllowFlight(true);
					p.setFlying(true);
					coolDown.put(p.getName(), 0);
				
					WCMain.s(p, "We're off! Let's hope this damn thing can figure out where ever the hell " + e.getCurrentItem().getItemMeta().getDisplayName() + " &dis...");
					
				final int groove = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
				public void run(){
					
					while (p.getLocation().getY() < 200 && coolDown.get(p.getName()) <= 0){
						p.setVelocity(new Vector(0, 1, 0));
						cool(p.getName());
					}
					
					if (xTarget > xStart){ // Target is bigger, so we need to add velocity to the X direction
						
						
						if (zTarget > zStart){ // Target is bigger, so we need to add velocity to the Z direction as well!
							
							while (xTarget > xCurrent && zTarget > zCurrent && coolDown.get(p.getName()) <= 0){
								
								double xCurrent = p.getLocation().getX();
								double zCurrent = p.getLocation().getZ();
								p.setVelocity(new Vector(3, 0, 3));
								altCheck(p);
										
								if (xTarget <= xCurrent && zTarget <= zCurrent){ // We're there! (RESULTS MAY VARY)
									end(p);
									return;
								}
								
								cool(p.getName());
							}
							
							return;
						}
						
						
						if (zTarget < zStart){ // Target is smaller, so we need to subtract velocity to the Z direction as well!

							while (xTarget > xCurrent && zTarget < zCurrent && coolDown.get(p.getName()) <= 0){
								
								double xCurrent = p.getLocation().getX();
								double zCurrent = p.getLocation().getZ();
								p.setVelocity(new Vector(3, 0, -3));
								altCheck(p);
		
								if (xTarget >= xCurrent && zTarget >= zCurrent){ // We're there! (RESULTS MAY VARY)
									end(p);
									return;
								}
								
								cool(p.getName());

							}
							
							return;
						}
						
						
					}
					
					
					if (xTarget < xStart){ // target is smaller, rem vel 
						
						if (zTarget > zStart){ // Target is bigger, so we need to add velocity to the Z direction as well!
							
							while (xTarget < xCurrent && zTarget > zCurrent && coolDown.get(p.getName()) <= 0){
								
								double xCurrent = p.getLocation().getX();
								double zCurrent = p.getLocation().getZ();
								p.setVelocity(new Vector(-3, 0, 3));
								altCheck(p);
								
								if (xTarget >= xCurrent && zTarget <= zCurrent){ // We're there! (RESULTS MAY VARY)
									end(p);
									return;
								}
								
								cool(p.getName());
								
							}
						}
						
						
						if (zTarget < zStart){ // Target is smaller, so we need to subtract velocity to the Z direction as well!
							
							while (xTarget < xCurrent && zTarget < zCurrent && coolDown.get(p.getName()) <= 0){
								
								double xCurrent = p.getLocation().getX();
								double zCurrent = p.getLocation().getZ();
								p.setVelocity(new Vector(-3, 0, -3));
								altCheck(p);
								
								if (xTarget >= xCurrent && zTarget >= zCurrent){ // We're there! (RESULTS MAY VARY)
									end(p);
									return;
								}
								
								cool(p.getName());

							}
						}
					}
				}}, 0L, 5L);	
					
				plugin.datacore.set("Users." + p.getName() + ".TAZK", groove);
				return;
				}
			}
		}
	}
	
	private void cool(final String name) {
		coolDown.put(name, 5);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
		public void run(){ coolDown.put(name, 0); }}, 10L);
		
	}

	public void end(Player p){
		p.setAllowFlight(false);
		p.setFlying(false);
		p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 10, 3)); // Ehh, this should absorb the fall.. right?
		p.setFoodLevel(0);
		Bukkit.getScheduler().cancelTask(plugin.datacore.getInt("Users." + p.getName() + ".TAZK"));
		WCMain.s(p, "Reached destination! (Or close to... :3)");
	}
	
	public void altCheck(Player p){
		
		if (p.getLocation().getY() < 100){
			p.setVelocity(new Vector(0, 2, 0));
		}
	}

}
