package com.github.lyokofirelyte.WC.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerFishEvent;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.ParagonFindEvent;
import com.github.lyokofirelyte.WC.WCMain;

public class WCBlockBreak implements Listener{
	
	WCMain plugin;
	WCPlayer wcp;
	Boolean natural;
	String type;
	ArrayList<String> lore;
	List <String> blocks;
	Material mat;
	
	public WCBlockBreak(WCMain instance){
    plugin = instance;
    }
	

	@EventHandler(priority = EventPriority.NORMAL)
	public void onFurance(FurnaceExtractEvent e) {
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(200) + 1;
		
		if (randomNumber == 100){
	        dropParagon(e.getPlayer(), 1, Material.FURNACE, "§9REFINED PARAGON", "§9refined");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onFish(PlayerFishEvent e) throws IllegalArgumentException, Exception{
		
		if (e.getExpToDrop() > 0){
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(800) + 1;

			if (randomNumber == 500){
			    dropParagon(e.getPlayer(), 3, Material.STAINED_CLAY, "§aAQUATIC PARAGON", "§aaquatic");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void setFiah(BlockIgniteEvent e) {
			
		if (e.getCause().toString().equals("FLINT_AND_STEEL")){
			Random rand = new Random();
			int randomNumber = rand.nextInt(800) + 1;
				
			if (randomNumber == 500){
				dropParagon(e.getPlayer(), 1, Material.HARD_CLAY, "§4INFERNO PARAGON", "§4inferno");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) throws IllegalArgumentException, Exception {
		
		wcp = plugin.wcm.getWCPlayer(e.getPlayer().getName());
		wcp.setBlocksMined(wcp.getBlocksMined() + 1);
		
		switch (e.getBlock().getType()){
		
			case SIGN: case SIGN_POST: case WALL_SIGN:
			
			YamlConfiguration warps = new YamlConfiguration();
	        File warpSignsFile = new File(plugin.getDataFolder(), "warps.yml");
				
			if (!warpSignsFile.exists()) {
				break;
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
			String loc = x + "," + y + "," + z + "," + w;
			  
			List <String> validWarps = warps.getStringList("Warps");
			
			for (String s : validWarps){
				
				if (s.contains(loc)){
					validWarps.remove(s);
					warps.set("Warps", validWarps);
						
					 try {
						 warps.save(warpSignsFile);
						 } catch (IOException a) {
						a.printStackTrace();
					  }
						  
					break;
				}
			}
				
			break;
				
			default:
			
			break;

		}
		
	blocks = plugin.config.getStringList("Paragons.Blocks");
		
	if (blocks.contains(e.getBlock().getType().toString())){
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(1000) + 1;
		
		if (randomNumber == 500){
	
			switch (e.getBlock().getType()){
		
				case STONE: case MOSSY_COBBLESTONE: case COBBLESTONE:
					
					dropParagon(e.getPlayer(), 9, e.getBlock().getType(), "§7MINERAL PARAGON", "&7mineral");
					break;
					
				case ENDER_STONE:
					
					dropParagon(e.getPlayer(), 0, Material.ENDER_STONE, "§dDRAGON PARAGON", "&ddragon");
					break;
					
				case WOOD: case LOG:
					
					dropParagon(e.getPlayer(), 7, Material.WOOD, "§6NATURE PARAGON", "&6nature");
					break;
					
				case DIAMOND_ORE: case LAPIS_ORE: case GLOWING_REDSTONE_ORE: case REDSTONE_ORE: case EMERALD_ORE: case GOLD_ORE:
					
					dropParagon(e.getPlayer(), 11, e.getBlock().getType(), "§bCRYSTAL PARAGON", "&bcrystal");
					break;
					
				case SAND:
					
					dropParagon(e.getPlayer(), 4, Material.SAND, "§eSUN PARAGON", "&esun");
					break;
					
				case NETHERRACK: case GLOWSTONE: case SOUL_SAND:
					
					dropParagon(e.getPlayer(), 14, e.getBlock().getType(), "§cHELL PARAGON", "&chell");
					break;
					
				case GRASS: case DIRT: case GRAVEL:
					
					dropParagon(e.getPlayer(), 12, e.getBlock().getType(), "§8EARTH PARAGON", "&8earth");
					break;
					
				case COAL_ORE: case IRON_ORE:
					
					dropParagon(e.getPlayer(), 15, e.getBlock().getType(), "§1INDUSTRIAL PARAGON", "&1industrial");
					break;
					
				case WATER_LILY: case CACTUS: case YELLOW_FLOWER: case RED_ROSE: case PUMPKIN: case MELON_BLOCK: case SUGAR_CANE_BLOCK:
					
					dropParagon(e.getPlayer(), 1, e.getBlock().getType(), "§6LIFE PARAGON", "&6life");
					break;
					
				case ICE: case SNOW_BLOCK: case SNOW: case SNOW_BALL:
					
					dropParagon(e.getPlayer(), 15, e.getBlock().getType(), "§1FRO§9§lST PARAGON", "&1fro&9st");
					break;
				
				default: 
				break;
			}
		}
	}
	}
	
	public void dropParagon(Player p, int color, Material mat, String disp, String paragon){
        ParagonFindEvent pEvent = new ParagonFindEvent(p, paragon, disp.substring(0, 1), Utils.getRandomColor(), color, mat, wcp.getBlocksMined());
        plugin.getServer().getPluginManager().callEvent(pEvent);
	}
}
