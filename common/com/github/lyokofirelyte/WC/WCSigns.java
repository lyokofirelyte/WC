package com.github.lyokofirelyte.WC;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCUtils;

public class WCSigns implements Listener {

	WCMain plugin;
	
	public WCSigns(WCMain instance){
		plugin = instance;
	}
	  
	@EventHandler (priority = EventPriority.NORMAL)
	public void onSignCheaingeEhbvent(SignChangeEvent e) throws IOException{
		
		String signLine1 = e.getLine(0);
		String signLine2 = e.getLine(1);
		
		if (signLine1.equalsIgnoreCase("warp") && e.getPlayer().hasPermission("wa.mod")){
			
			File warpFile = new File(plugin.getDataFolder() + File.separator + "Warps", signLine2.toLowerCase() + ".yml");
			
			if (!warpFile.exists()) {
				e.setLine(0, ("§f//§4Warp§f//"));
				e.setLine(1, ("§cINVALID"));
				e.setLine(2, ("§cWARP!"));
				e.setLine(3, ("§7§oSee /warp"));
				return;
			}
			
			YamlConfiguration warps = new YamlConfiguration();
			File warpSignsFile = new File(plugin.getDataFolder(), "warps.yml");
			
			if (!warpSignsFile.exists()) {
				warpSignsFile.createNewFile();
			}
			
			try {
				warps.load(warpSignsFile);
			} catch (Exception i) {
				i.printStackTrace();
			}
			
			double x = e.getBlock().getLocation().getX();
			double y = e.getBlock().getLocation().getY();
			double z = e.getBlock().getLocation().getZ();
			String w = e.getBlock().getLocation().getWorld().getName();
			
			List <String> validWarps = warps.getStringList("Warps");
			
			if (e.getLine(2) != null && e.getLine(2).contains(".")){
				String loc = x + "," + y + "," + z + "," + w + "," + e.getLine(2);
				validWarps.add(loc);
			} else {
				String loc = x + "," + y + "," + z + "," + w;
				validWarps.add(loc);
			}
			
			warps.set("Warps", validWarps);  
			
			try {
				warps.save(warpSignsFile);
			} catch (IOException a) {
				a.printStackTrace();
			}
			
			e.setLine(0, ("§f//§1Warp§f//"));
			if (e.getLine(2) != null && e.getLine(2).contains(".")){
				e.setLine(1, "§e" + e.getLine(1));
			} else {
				e.setLine(1, "§b" + e.getLine(1));
			}
			e.setLine(2, "");
			e.setLine(3, "");
		}
		
		if (signLine1.equalsIgnoreCase("enderdragon")){
			
			e.setLine(0, ("§5// WC //"));
			e.setLine(1, ("§aSPAWN DRAGON"));
			e.setLine(2, ("§5[ press ]"));
			
			String world = e.getBlock().getLocation().getWorld().getName();
			double x = e.getBlock().getLocation().getX();
			double y = e.getBlock().getLocation().getY();
			double z = e.getBlock().getLocation().getZ();
			
			List<String> list = plugin.datacore.getStringList("EnderDragonSigns." + world);
			list.add(x + ", " + y + ", " + z);
			
			plugin.datacore.set("EnderDragonSigns." + world, list);
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onSign(PlayerInteractEvent e) throws IndexOutOfBoundsException, IOException{
		
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST)){
			
			Player p = e.getPlayer();
			
			if (validateSign(e, p)){
				
				Block block = e.getClickedBlock();
				BlockState state = block.getState();
				Sign sign = (Sign)state;
				String signLine1 = sign.getLine(0);
				String signLine2 = sign.getLine(1);
				
				if (signLine1.equals("§f//§1Warp§f//")){
					
					YamlConfiguration warpLoad = new YamlConfiguration();
					File warpFile = new File(plugin.getDataFolder() + File.separator + "Warps", signLine2.replace("§b", "").replace("§e", "").toLowerCase() + ".yml");
					if (!warpFile.exists()) {
						sign.setLine(0, ("§f//§4Warp§f//"));
						sign.setLine(1, ("§cINVALID"));
						sign.setLine(2, ("§cWARP!"));
						sign.setLine(3, ("§7§oSee /warp"));
						sign.update(true);
						return;
					}
					
					try {
						warpLoad.load(warpFile);
					} catch (Exception i) {
						i.printStackTrace();
					}
					
					World w = Bukkit.getWorld(warpLoad.getString("world"));
					double x = warpLoad.getInt("x");
					double y = warpLoad.getInt("y");
					double z = warpLoad.getInt("z");
					float yaw = warpLoad.getInt("yaw");
					float pitch = warpLoad.getInt("pitch");
					Location warpTo = new Location(w, x, y+1, z, yaw, pitch);
					p.teleport(warpTo);
					Utils.effects(p);
				}
				
			} else {
				
				double x = e.getClickedBlock().getLocation().getX();
				double y = e.getClickedBlock().getLocation().getY();
				double z = e.getClickedBlock().getLocation().getZ();
				String w = e.getClickedBlock().getLocation().getWorld().getName();
				String loc = x + ", " + y + ", " + z;
				
				List<String> list = plugin.datacore.getStringList("EnderDragonSigns." + w);
				
				if (list.contains(loc)){
					p.performCommand("wc dragonspawn");
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerTHUNDERSMASHSign(BlockBreakEvent e){
		
		if (e.getBlock().getType() == Material.WALL_SIGN){
			
			Location l = e.getBlock().getLocation();
			double x = l.getX();
			double y = l.getY();
			double z = l.getZ();
			String w = l.getWorld().getName();
			String loc = x + ", " + y + ", " + z + ", ";
			
			List<String> list = plugin.datacore.getStringList("EnderDragonSigns." + w);
			
			while (true){
				
				if (list.contains(loc)){
					
					list.remove(loc);
					
				} else {
					
					plugin.datacore.set("EnderDragonSigns." + w, list);
					
					break;
					
				}
				
			}
			
		}
		
	}
	
	private boolean validateSign(PlayerInteractEvent e, Player p) throws IOException {
		
		double x = e.getClickedBlock().getLocation().getX();
		double y = e.getClickedBlock().getLocation().getY();
		double z = e.getClickedBlock().getLocation().getZ();
		String w = e.getClickedBlock().getLocation().getWorld().getName();
		String loc = x + "," + y + "," + z + "," + w;
		
		YamlConfiguration warps = new YamlConfiguration();
		File warpSignsFile = new File(plugin.getDataFolder(), "warps.yml");
		
		if (!warpSignsFile.exists()) {
			warpSignsFile.getParentFile().mkdirs();
			warpSignsFile.createNewFile();
		}
		
		try {
			warps.load(warpSignsFile);
		} catch (Exception i) {
			i.printStackTrace();
		}
		
		List <String> validWarps = warps.getStringList("Warps");
		
		for (String s : validWarps){
			if (s.contains(loc)){
				String[] permSplit = s.split(",");
				if (permSplit.length == 5){
					if (!p.hasPermission(permSplit[4])){
						WCUtils.s(p, "You don't have permission to use this warp!");
						return false;
					}
				}
				return true;
			}
		}
		
		return false;
		
	}
	
}
