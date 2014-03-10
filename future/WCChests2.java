package com.github.lyokofirelyte.WC;

import static com.github.lyokofirelyte.WC.WCMain.s;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.block.Sign;

import com.github.lyokofirelyte.WCAPI.WCSystem;

public class WCChests2 implements Listener {
	
	WCMain pl;
	public WCChests2(WCMain instance){
	pl = instance;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onBreak(BlockBreakEvent e){
		
		if (e.getBlock().getState() instanceof Chest || e.getBlock().getState() instanceof DoubleChest){
			
			WCSystem wcs = pl.wcm.getWCSystem("system");
			
			Location loc1 = e.getBlock().getLocation();
			Location loc2 = e.getBlock().getLocation();
			Location loc3 = e.getBlock().getLocation();
	            
	        if (e.getBlock().getState() instanceof DoubleChest){
	            
	        	DoubleChest dc = (DoubleChest) e.getBlock().getState();
	        	InventoryHolder ih = dc.getLeftSide().getInventory().getHolder();
	            loc2 = ((DoubleChest) ih).getLocation();
	                
	            ih = dc.getRightSide().getInventory().getHolder();
	            loc3 = ((DoubleChest) ih).getLocation();
	        }
	        
			for (String s : wcs.getChests()){
				
				String[] cSplit = s.split(" ");

				if (locChec(cSplit, loc1, loc2, loc3)){
					
					s(e.getPlayer(), "This chest is protected by WC. See /protect for removal instructions.");
					loc1.getWorld().playEffect(loc1, Effect.MOBSPAWNER_FLAMES, 2);
					e.setCancelled(true);
					return;
				}
			}
		}
		
		if (e.getBlock().getState() instanceof Sign){
			
			Sign sign = (Sign) e.getBlock().getState();
			
			if (sign.getLine(0).equals("§f// §5WC §f//")){
				s(e.getPlayer(), "This sign is protected by WC. See /protect for removal instructions.");
				e.setCancelled(true);
				return;
			}
		}
	}
	
	public static void updateSign(Location l, String msg){
		
		Sign sign = (Sign) l.getBlock().getState();
		sign.setLine(0, msg.substring(0, 14));
		sign.setLine(1, msg.substring(14, msg.length()));
		sign.update();
	}
	
	
	public Boolean locChec(String[] cSplit, Location loc1, Location loc2, Location loc3){
		
		if ((cSplit[1].equals(loc1.getWorld().getName()) && cSplit[2].equals(loc1.getX() + "") && cSplit[3].equals(loc1.getY() + "") && cSplit[4].equals(loc1.getZ() + "")) 
		|| (cSplit[1].equals(loc2.getWorld().getName()) && cSplit[2].equals(loc2.getX() + "") && cSplit[3].equals(loc2.getY() + "") && cSplit[4].equals(loc2.getZ() + ""))
		|| (cSplit[1].equals(loc3.getWorld().getName()) && cSplit[2].equals(loc3.getX() + "") && cSplit[3].equals(loc3.getY() + "") && cSplit[4].equals(loc3.getZ() + ""))){
			return true;
		} else {
			return false;
		}
	}

}
