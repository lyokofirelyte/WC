package com.github.lyokofirelyte.WC.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Util.FireworkShenans;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
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
	public void onFurance(FurnaceExtractEvent e) throws IllegalArgumentException, Exception{
		FEE(e.getPlayer(), e.getBlock(), e.getItemType(), e.getItemAmount(), e.getExpToDrop());
	}
	
	@SuppressWarnings("deprecation")
	public void FEE(Player p, Block block, Material itemType, int itemAmount, int exp) throws IllegalArgumentException, Exception{
		
		if (exp > 0){
			
			Random rand = new Random();
			int randomNumber = rand.nextInt(200) + 1;
			
			if (randomNumber == 100){
				
				wcp = plugin.wcm.getWCPlayer(p.getName());
		        ItemStack paragon = new ItemStack(Material.HARD_CLAY, 1);
		        ItemMeta paragonName = paragon.getItemMeta();
		        lore = new ArrayList<String>();
		        
		        lore.add("�7�oI should return this");
		        lore.add("�7�oto the shrine near spawn.");
		        paragonName.setLore(lore);
		        
		        paragonName.setDisplayName("�9�lREFINED PARAGON");
		        paragonName.addEnchant(Enchantment.DURABILITY, 10, true);
		        paragon.setItemMeta(paragonName);
		        	if (p.getInventory().firstEmpty() == -1){
		        		Location loc = p.getLocation();
		        		loc.getWorld().dropItemNaturally(loc, paragon);
		        	} else {
		        p.getInventory().addItem(paragon);
		        p.updateInventory();
		        	}
		        	
		        if (wcp.getFireworks()){ 
			        FireworkShenans fplayer = new FireworkShenans();
			        fplayer.playFirework(p.getWorld(), p.getLocation(),
		            FireworkEffect.builder().with(Type.BURST).withColor(Color.BLACK).build());
		        }
		        		
		        Bukkit.broadcastMessage(Utils.AS(WCMail.WC + p.getDisplayName() + " &dhas found a &9refined &dparagon from smelting!"));
		}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onFish(PlayerFishEvent e) throws IllegalArgumentException, Exception{
		
		if (e.getExpToDrop() > 0){
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(800) + 1;
		
		if (randomNumber == 500){

			wcp = plugin.wcm.getWCPlayer(e.getPlayer().getName());
	        ItemStack paragon = new ItemStack(Material.STAINED_CLAY, 1, (short) 3);
	        ItemMeta paragonName = paragon.getItemMeta();
	        lore = new ArrayList<String>();
	        
	        lore.add("�7�oI should return this");
	        lore.add("�7�oto the shrine near spawn.");
	        paragonName.setLore(lore);
	        
	        paragonName.setDisplayName("�a�lAQUATIC PARAGON");
	        paragonName.addEnchant(Enchantment.DURABILITY, 10, true);
	        paragon.setItemMeta(paragonName);
	        	if (e.getPlayer().getInventory().firstEmpty() == -1){
	        		Location loc = e.getPlayer().getLocation();
	        		loc.getWorld().dropItemNaturally(loc, paragon);
	        	} else {
	        e.getPlayer().getInventory().addItem(paragon);
	        e.getPlayer().updateInventory();
	        	}
	        
	        if (wcp.getFireworks()){
		        FireworkShenans fplayer = new FireworkShenans();
		        fplayer.playFirework(e.getPlayer().getWorld(), e.getPlayer().getLocation(),
	            FireworkEffect.builder().with(Type.BURST).withColor(Color.BLUE).build());
	        }	
	        Bukkit.broadcastMessage(Utils.AS(WCMail.WC + e.getPlayer().getDisplayName() + " &dhas found an �aaquatic &dparagon from fishing!"));
		}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
		public void setFiah(BlockIgniteEvent e) throws IllegalArgumentException, Exception{
			
			if (e.getCause().toString().equals("FLINT_AND_STEEL")){
				Random rand = new Random();
				int randomNumber = rand.nextInt(800) + 1;
				
				if (randomNumber == 500){
	
					wcp = plugin.wcm.getWCPlayer(e.getPlayer().getName());
			        ItemStack paragon = new ItemStack(Material.HARD_CLAY, 1);
			        ItemMeta paragonName = paragon.getItemMeta();
			        lore = new ArrayList<String>();
			        
			        lore.add("�7�oI should return this");
			        lore.add("�7�oto the shrine near spawn.");
			        paragonName.setLore(lore);
			        
			        paragonName.setDisplayName("�6�lINFERNO PARAGON");
			        paragonName.addEnchant(Enchantment.DURABILITY, 10, true);
			        paragon.setItemMeta(paragonName);
			        	if (e.getPlayer().getInventory().firstEmpty() == -1){
			        		Location loc = e.getPlayer().getLocation();
			        		loc.getWorld().dropItemNaturally(loc, paragon);
			        	} else {
			        e.getPlayer().getInventory().addItem(paragon);
			        e.getPlayer().updateInventory();
			        	}
			        
			        if (wcp.getFireworks()){
				        FireworkShenans fplayer = new FireworkShenans();
				        fplayer.playFirework(e.getPlayer().getWorld(), e.getPlayer().getLocation(),
			            FireworkEffect.builder().with(Type.BURST).withColor(Color.ORANGE).build());
			        }	
			        Bukkit.broadcastMessage(Utils.AS(WCMail.WC + e.getPlayer().getDisplayName() + " &dhas found an �4inferno &dparagon from setting fires."));
				}
			}
		}
	

	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) throws IllegalArgumentException, Exception {
		
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

		}
		
		blocks = plugin.config.getStringList("Paragons.Blocks");
		
	if (blocks.contains(e.getBlock().getType().toString())){
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(1000) + 1;
		
		if (randomNumber == 500){
	
		switch (e.getBlock().getType()){
	
		case STONE: case MOSSY_COBBLESTONE: case COBBLESTONE:
			
			dropParagon(e, e.getPlayer(), 9, e.getBlock().getType(), "�7�lMINERAL PARAGON", "&7mineral");
			break;
			
		case ENDER_STONE:
			
			dropParagon(e, e.getPlayer(), 0, Material.ENDER_STONE, "�d�lDRAGON PARAGON", "&ddragon");
			break;
			
		case WOOD: case LOG:
			
			dropParagon(e, e.getPlayer(), 7, Material.WOOD, "�6�lNATURE PARAGON", "&6nature");
			break;
			
		case DIAMOND_ORE: case LAPIS_ORE: case GLOWING_REDSTONE_ORE: case REDSTONE_ORE: case EMERALD_ORE: case GOLD_ORE:
			
			dropParagon(e, e.getPlayer(), 11, e.getBlock().getType(), "�b�lCRYSTAL PARAGON", "&bcrystal");
			break;
			
		case SAND:
			
			dropParagon(e, e.getPlayer(), 4, Material.SAND, "�e�lSUN PARAGON", "&esun");
			break;
			
		case NETHERRACK: case GLOWSTONE: case SOUL_SAND:
			
			dropParagon(e, e.getPlayer(), 14, e.getBlock().getType(), "�c�lHELL PARAGON", "&chell");
			break;
			
		case GRASS: case DIRT: case GRAVEL:
			
			dropParagon(e, e.getPlayer(), 12, e.getBlock().getType(), "�8�lEARTH PARAGON", "&8earth");
			break;
			
		case COAL_ORE: case IRON_ORE:
			
			dropParagon(e, e.getPlayer(), 15, e.getBlock().getType(), "�1�lINDUSTRIAL PARAGON", "&1industrial");
			break;
			
		case WATER_LILY: case CACTUS: case YELLOW_FLOWER: case RED_ROSE: case PUMPKIN: case MELON_BLOCK: case SUGAR_CANE_BLOCK:
			
			dropParagon(e, e.getPlayer(), 1, e.getBlock().getType(), "�6�lLIFE PARAGON", "&6life");
			break;
			
		case ICE: case SNOW_BLOCK: case SNOW: case SNOW_BALL:
			
			dropParagon(e, e.getPlayer(), 15, e.getBlock().getType(), "�1�lFRO�9�lST PARAGON", "&1fro&9st");
			break;
		
		default: 
			break;
		}
		
	}
	}
	}
	
	@SuppressWarnings("deprecation")
	public void dropParagon(final BlockBreakEvent e, Player p, int color, Material mat, String disp, String type) throws IllegalArgumentException, Exception{
		
		e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        
        wcp = plugin.wcm.getWCPlayer(p.getName());
        
        ItemStack paragon = new ItemStack(Material.STAINED_CLAY, 1, (short) color);
        ItemMeta paragonName = paragon.getItemMeta();
        lore = new ArrayList<String>();
        
        lore.add("�7�oI should return this");
        lore.add("�7�oto the shrine near spawn.");
        paragonName.setLore(lore);
        
        paragonName.setDisplayName(disp);
        paragonName.addEnchant(Enchantment.DURABILITY, 10, true);
        paragon.setItemMeta(paragonName);
        	if (e.getPlayer().getInventory().firstEmpty() == -1 && e.getPlayer().getInventory().contains(paragon) == false){
    			Location loc = e.getPlayer().getLocation();
    			loc.getWorld().dropItemNaturally(loc, paragon);
    			e.getPlayer().sendMessage(WCMail.WC + "Your inventory was full so we dropped it for you. YOU'RE WELCOME.");
        	} else {
        		e.getPlayer().getInventory().addItem(paragon);
        		e.getPlayer().updateInventory();
        	}
        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(mat, 2));
        
        if (wcp.getFireworks()){
	        List<Location> circleblocks = Utils.circle(e.getPlayer(), e.getPlayer().getLocation(), 5, 1, true, false, 1);
	        long delay =  0L;
	        	for (final Location l : circleblocks){
	        		delay = delay + 2L;
	        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
	        	    {
	        	      public void run()
	        	      {
	        	        	FireworkShenans fplayer = new FireworkShenans();
	        	        	try {
								fplayer.playFirework(e.getPlayer().getWorld(), l,
								FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).build());
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}        	      }
	        	    }
	        	    , delay);
	        	}
        }
        Bukkit.broadcastMessage(Utils.AS(WCMail.WC + e.getPlayer().getDisplayName() + " &dhas found a(n) " + type + " &dparagon from harvesting " + mat.toString().toLowerCase() + "&d."));
		
	}
}
