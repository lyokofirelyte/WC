package com.github.lyokofirelyte.WC.Listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.WCMain;

public class WCBlockPlace implements Listener{
	
	WCMain plugin;
	
	public WCBlockPlace(WCMain instance){
    plugin = instance;
    } 

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e){
		
	if (e.getItemInHand().hasItemMeta()){	
		 
         if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().toString().toLowerCase().contains("paragon")){
        	 
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
		
		case "§7§lMINERAL PARAGON":
			
			paragonUpdate("mineral", p, 9);
			break;
			
		case "§0§lDEATH PARAGON":
			
			paragonUpdate("death", p, 15);
			break;
			
		case "§d§lDRAGON PARAGON":
			
			paragonUpdate("dragon", p, 0);
			break;
			
		case "§6§lNATURE PARAGON":
			
			paragonUpdate("nature", p, 7);
			break;
			
		case "§b§lCRYSTAL PARAGON":
			
			paragonUpdate("crystal", p, 11);
			break;
			
		case "§e§lSUN PARAGON":
			
			paragonUpdate("sun", p, 4);
			break;
			
		case "§c§lHELL PARAGON":
			
			paragonUpdate("hell", p, 14);
			break;
			
		case "§8§lEARTH PARAGON":
			
			paragonUpdate("earth", p, 12);
			break;
			
		case "§1§lINDUSTRIAL PARAGON":
			
			paragonUpdate("industrial", p, 15);
			break;
			
		case "§6§lLIFE PARAGON":
			
			paragonUpdate("life", p, 1);
			break;
			
		case "§4§lINFERNO PARAGON":
			
			paragonUpdate("inferno", p, 2);
			break;
			
		case "§a§lAQUATIC PARAGON":
			
			paragonUpdate("aquatic", p, 3);
			break;
			
		case "§9§lREFINED PARAGON":
			
			paragonUpdate("refined", p, 14);
			break;
			
		case "§1§lFRO§9§lST PARAGON":
			
			paragonUpdate("frost", p, 15);
			break;
			
		default: break;
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private void paragonUpdate(String type, Player p, int clay){
		
		int global = plugin.datacore.getInt("Paragons." + type);
		int self = plugin.datacore.getInt("Users." + p.getName() + ".Paragons." + type);
		int selfTotal = plugin.datacore.getInt("Users." + p.getName() + ".Paragons.Total");
		int total = plugin.datacore.getInt("Paragons.Total");
		int size = plugin.config.getInt("Paragons.NewList.Size");
		int tempTotal = plugin.datacore.getInt("Users." + p.getName() + ".Paragons.TempTotal");
		
			if (tempTotal < 0){
				tempTotal = 0;
			}

		global++;
		self++;
		total++;
		selfTotal++;
		tempTotal++;
		
		plugin.datacore.set("Paragons." + type, global);
		plugin.datacore.set("Users." + p.getName() + ".Paragons." + type, self);
		plugin.datacore.set("Users." + p.getName() + ".Paragons.Total", selfTotal);
		plugin.datacore.set("Users." + p.getName() + ".Paragons.TempTotal", tempTotal);
		plugin.datacore.set("Paragon.Total", total);

		p.sendMessage(Utils.AS(WCMail.WC + "Your donation of x1 &6" + type + " &dparagon was added to THE WALL."));
		callToken(p, type);
		
		if (size == 1){
			int size2 = 1;
			int tier = plugin.config.getInt("Paragons.Tier");
			tier++;
			plugin.config.set("Paragons.Tier", tier);
			reset(size2, p);
		    plugin.config.set("Paragons.NewList.Size", 131);
		    Bukkit.broadcastMessage(WCMail.WC + "THE WALL at the Paragon Collectorium has been filled and reset!");
		    Bukkit.broadcastMessage(Utils.AS(WCMail.WC + "&b&oThe Paragon Collectorium is now tier " + plugin.config.getInt("Paragons.Tier") + "&b!"));
			return;
		} else {
			double spotX = plugin.config.getInt("Paragons.Spots." + size + ".X");
			double spotY = plugin.config.getInt("Paragons.Spots." + size + ".Y");
			double spotZ = plugin.config.getInt("Paragons.Spots." + size + ".Z");
			World world = p.getWorld();
			Location loc = new Location(world, spotX, spotY, spotZ);
			loc.getBlock().setType(Material.STAINED_CLAY);
			loc.getBlock().setData((byte)clay);
			size--;
			plugin.config.set("Paragons.NewList.Size", size);
			return;
		}
	}

	@SuppressWarnings("deprecation")
	private void callToken(Player p, String type) {
		
		ArrayList<String> lore;
		
			if (type.equals("crystal")){
				
				ItemStack token = new ItemStack(Material.INK_SACK, 5, (short) 11);
		        ItemMeta name = token.getItemMeta();
		        lore = new ArrayList<String>();
		        name.addEnchant(Enchantment.DURABILITY, 10, true);
		        name.setDisplayName("§e§o§lPARAGON TOKEN");
		        lore.add("§7§oIt's currency!");
		        name.setLore(lore);
		        token.setItemMeta((ItemMeta)name);
		        	if (p.getInventory().firstEmpty() == -1){
		        		p.getWorld().dropItemNaturally(p.getLocation(), token);
		        	} else {
		        		p.getInventory().addItem(token);
		        		p.updateInventory();
		        	}
		        updateCheck(p);
		        
			} else {
				
		ItemStack token = new ItemStack(Material.INK_SACK, 1, (short) 11);
        ItemMeta name = token.getItemMeta();
        lore = new ArrayList<String>();
        name.addEnchant(Enchantment.DURABILITY, 10, true);
        name.setDisplayName("§e§o§lPARAGON TOKEN");
        lore.add("§7§oIt's currency!");
        name.setLore(lore);
        token.setItemMeta((ItemMeta)name);
        	if (p.getInventory().firstEmpty() == -1){
        		p.getWorld().dropItemNaturally(p.getLocation(), token);
        		updateCheck(p);
        	} else {
        		p.getInventory().addItem(token);
        		p.updateInventory();
        		updateCheck(p);
        	}
    			
        
			}
			
	}

	private void updateCheck(Player p) {
		
		int total = plugin.datacore.getInt("Users." + p.getName() + ".Paragons.TempTotal");
		int reqLevel = plugin.datacore.getInt("Users." + p.getName() + ".reqLevel");
			if (total >= reqLevel){
				reqLevel++;
				plugin.datacore.set("Users." + p.getName() + ".reqLevel", reqLevel);
				plugin.datacore.set("Users." + p.getName() + ".Paragons.TempTotal", 0);
				update(p);
			}
	}

	private void update(Player p) {
		
        int level = plugin.datacore.getInt("Users." + p.getName() + ".ParagonLevel");
        level++;
        plugin.datacore.set("Users." + p.getName() + ".ParagonLevel", level);
        Bukkit.broadcastMessage(Utils.AS(WCMail.WC + p.getDisplayName() + " has ascended to Paragon Level " + level));
		plugin.config.set("Users." + p.getName() + ".ParagonLevelDisplay", level);
		p.sendMessage(WCMail.WC + "Title updated! :)");
	}

	private void reset(int size2, Player p) {
		
	while (size2 <= 131){
		double spotX = plugin.config.getInt("Paragons.Spots." + size2 + ".X");
		double spotY = plugin.config.getInt("Paragons.Spots." + size2 + ".Y");
		double spotZ = plugin.config.getInt("Paragons.Spots." + size2 + ".Z");
		World world = p.getWorld();
		Location loc = new Location(world, spotX, spotY, spotZ);
		loc.getBlock().setType(Material.AIR);
		size2++;
	}
	}
}

