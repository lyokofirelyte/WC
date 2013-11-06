package com.github.lyokofirelyte.WC.Listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.lyokofirelyte.WC.WCMain;

public class WCBlockPlace implements Listener{
	
	WCMain plugin;
	WCSystem system;
	WCPlayer wcp;
	
	ArrayList<String> lore;
	
	public WCBlockPlace(WCMain instance){
    plugin = instance;
    } 

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e){
		
		if (e.getItemInHand().hasItemMeta() && e.getItemInHand().getItemMeta().hasLore()){
			 
	         if(e.getPlayer().getItemInHand().getItemMeta().getLore().toString().toLowerCase().contains("i should")){
	        	 
	        	double x = e.getBlock().getLocation().getX();
	 			double y = e.getBlock().getLocation().getY();
	 			double z = e.getBlock().getLocation().getZ();
	 			String xyz = x + "," + y + "," + z;
	 			
	 			List <String> allowedLocations = plugin.config.getStringList("Paragons.Locations");
	        	
	 				if (allowedLocations.contains(xyz)){
	 					e.setCancelled(false);
	 					paragonPlace(e.getPlayer(), e.getPlayer().getItemInHand().getItemMeta().getDisplayName().toString());
	 					e.getPlayer().getWorld().playEffect(e.getBlock().getLocation(), Effect.ENDER_SIGNAL, 0);
	 					e.getBlock().setType(Material.AIR);
	 					return;
	 				} else {
	 					e.setCancelled(true);
	 					e.getPlayer().updateInventory();
	 					e.getPlayer().sendMessage(Utils.AS(WCMail.WC + "You should only place these at the shrine near spawn!"));
	 					return;
	 				}
	         }
				
		}
		
		if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".ParagonPlaceMode")){
			
				double x = e.getBlock().getLocation().getX();
				double y = e.getBlock().getLocation().getY();
				double z = e.getBlock().getLocation().getZ();
				String xyz = x + "," + y + "," + z;
				List <String> allowedLocations = plugin.config.getStringList("Paragons.Locations");
				allowedLocations.add(xyz);
				plugin.config.set("Paragons.Locations", allowedLocations);
				e.getPlayer().sendMessage(Utils.AS(WCMail.WC + "Location set!"));
				e.setCancelled(true);
				e.getPlayer().updateInventory();
				return;
		}
	
	
		if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".ObeliskPlaceMode")){
			
			plugin.datacore.set("Users." + e.getPlayer().getName() + ".ObeliskPlaceMode", false);
			double x = e.getBlock().getLocation().getX();
			double y = e.getBlock().getLocation().getY();
			double z = e.getBlock().getLocation().getZ();
			
			String xyz = x + "," + y + "," + z;
			String latest = plugin.datacore.getString("Obelisks.Latest");
			String type = plugin.datacore.getString("Obelisks.LatestType");
			
			plugin.config.set("Obelisks.Locations." + xyz + ".X", x);
			plugin.config.set("Obelisks.Locations." + xyz + ".Y", y);
			plugin.config.set("Obelisks.Locations." + xyz + ".Z", z);
			plugin.config.set("Obelisks.Locations." + xyz + ".Name", latest);
			plugin.config.set("Obelisks.Locations." + xyz + ".Type", type);
			plugin.config.set("Obelisks.ListGrab." + latest, xyz);
			
			e.getPlayer().sendMessage(Utils.AS(WCMail.WC + "Location set for " + latest));
			return;
		}
		
		if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".ParagonBreakMode")){
			
			double x = e.getBlock().getLocation().getX();
			double y = e.getBlock().getLocation().getY();
			double z = e.getBlock().getLocation().getZ();
			String xyz = x + "," + y + "," + z;
			List <String> allowedLocations = plugin.config.getStringList("Paragons.Locations");
			allowedLocations.remove(xyz);
			plugin.config.set("Paragons.Locations", allowedLocations);
			e.getPlayer().sendMessage(Utils.AS(WCMail.WC + "Location removed!"));
			e.setCancelled(true);
			e.getPlayer().updateInventory();
			return;
		}
	}

	private void paragonPlace(Player p, String paragonType) {
		
		switch (paragonType){
		
		case "§7MINERAL PARAGON":
			
			paragonUpdate(p, 9);
			break;
			
		case "§0DEATH PARAGON":
			
			paragonUpdate(p, 15);
			break;
			
		case "§dDRAGON PARAGON":
			
			paragonUpdate(p, 0);
			break;
			
		case "§6NATURE PARAGON":
			
			paragonUpdate(p, 7);
			break;
			
		case "§bCRYSTAL PARAGON":
			
			paragonUpdate(p, 11);
			break;
			
		case "§eSUN PARAGON":
			
			paragonUpdate(p, 4);
			break;
			
		case "§cHELL PARAGON":
			
			paragonUpdate(p, 14);
			break;
			
		case "§8EARTH PARAGON":
			
			paragonUpdate(p, 12);
			break;
			
		case "§1INDUSTRIAL PARAGON":
			
			paragonUpdate(p, 15);
			break;
			
		case "§6LIFE PARAGON":
			
			paragonUpdate(p, 1);
			break;
			
		case "§4INFERNO PARAGON":
			
			paragonUpdate(p, 2);
			break;
			
		case "§aAQUATIC PARAGON":
			
			paragonUpdate(p, 3);
			break;
			
		case "§9REFINED PARAGON":
			
			paragonUpdate(p, 14);
			break;
			
		case "§1FRO§9§lST PARAGON":
			
			paragonUpdate(p, 15);
			break;
			
		default: break;
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private void paragonUpdate(Player p, int clay){
		
		system = plugin.wcm.getWCSystem("system");
		wcp = plugin.wcm.getWCPlayer(p.getName());
		
		int selfTotal = wcp.getParagonCount();
		int total = system.getParagonTotal();
		int size = system.getParagonNewListSize();
		int tempTotal = wcp.getParagonTempTotal();
		
		if (tempTotal < 0){
			tempTotal = 0;
		}

		total++;
		selfTotal++;
		tempTotal++;
		
		wcp.setParagonCount(selfTotal);
		wcp.setParagonTempTotal(tempTotal);
		system.setParagonTotal(total);

		p.sendMessage(Utils.AS(WCMail.WC + "Your donation of x1 paragon was added to THE WALL."));
		callToken(p, wcp);
		
		if (size == 1){
			
			int tier = system.getParagonTier();
			tier++;
			system.setParagonTier(tier);
			reset(1, p);
		    system.setParagonNewListSize(131);
		    plugin.wcm.updateSystem("system", system);
		    Bukkit.broadcastMessage(WCMail.WC + "THE WALL at the Paragon Collectorium has been filled and reset!");
		    Bukkit.broadcastMessage(Utils.AS(WCMail.WC + "&b&oThe Paragon Collectorium is now tier " + tier + "&b!"));
		    
		} else {
			
			double spotX = plugin.config.getInt("Paragons.Spots." + size + ".X");
			double spotY = plugin.config.getInt("Paragons.Spots." + size + ".Y");
			double spotZ = plugin.config.getInt("Paragons.Spots." + size + ".Z");
			Location loc = new Location(p.getWorld(), spotX, spotY, spotZ);
			loc.getBlock().setType(Material.STAINED_CLAY);
			loc.getBlock().setData((byte)clay);
			size--;
			system.setParagonNewListSize(size);
			plugin.wcm.updateSystem("system", system);
			
		}
	}

	@SuppressWarnings("deprecation")
	private void callToken(Player p, WCPlayer wcp) {

		ItemStack token = plugin.invManager.makeItem("§e§o§lPARAGON TOKEN", "§7§oIt's currency!", true, Enchantment.DURABILITY, 11, Material.INK_SACK, 1);
        
        if (p.getInventory().firstEmpty() == -1){
        	p.getWorld().dropItemNaturally(p.getLocation(), token);
        	updateCheck(p, wcp);
        } else {
        	p.getInventory().addItem(token);
        	p.updateInventory();
        }
    			
    	updateCheck(p, wcp);
	}

	private void updateCheck(Player p, WCPlayer wcp) {
		
		int tempTotal = wcp.getParagonTempTotal();
		int reqLevel = wcp.getParagonReqLevel();
		
			if (tempTotal >= reqLevel){
				reqLevel++;
				wcp.setParagonReqLevel(reqLevel);
				wcp.setParagonTempTotal(0);
				update(p, wcp);
			} else {
				plugin.wcm.updatePlayerMap(p.getName(), wcp);
			}
	}

	private void update(Player p, WCPlayer wcp) {
		
        int level = wcp.getParagonLevel();
        level++;
        wcp.setParagonLevel(level);
        plugin.wcm.updatePlayerMap(p.getName(), wcp);
        Bukkit.broadcastMessage(Utils.AS(WCMail.WC + p.getDisplayName() + " has ascended to Paragon Level " + level));
		ScoreboardUpdateEvent scoreboardEvent = new ScoreboardUpdateEvent(p);
		plugin.getServer().getPluginManager().callEvent(scoreboardEvent);
	}

	private void reset(int size2, Player p) {
		
		while (size2 <= 131){
			double spotX = plugin.config.getInt("Paragons.Spots." + size2 + ".X");
			double spotY = plugin.config.getInt("Paragons.Spots." + size2 + ".Y");
			double spotZ = plugin.config.getInt("Paragons.Spots." + size2 + ".Z");
			Location loc = new Location(p.getWorld(), spotX, spotY, spotZ);
			loc.getBlock().setType(Material.AIR);
			size2++;
		}
	}
}

