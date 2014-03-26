package com.github.lyokofirelyte.WC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Sign;

import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;

import static com.github.lyokofirelyte.WC.WCMain.*;

public class WCChests implements Listener {
	
	WCMain pl;
	
	public WCChests(WCMain instance){
	pl = instance;
	}  
	
   	public BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    public BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };


	@EventHandler(priority = EventPriority.NORMAL)
	public void onClick(PlayerInteractEvent e){
		
		if (e.getAction() == Action.LEFT_CLICK_BLOCK && e.getPlayer().isSneaking() && (e.getClickedBlock().getState() instanceof Chest || e.getClickedBlock().getState() instanceof DoubleChest)){
			
			WCSystem wcs = pl.wcm.getWCSystem("system");
			WCPlayer wcp = pl.wcm.getWCPlayer(e.getPlayer().getName());
			
			Location loc1 = e.getClickedBlock().getLocation();
			Location loc2 = e.getClickedBlock().getLocation();
			Location loc3 = e.getClickedBlock().getLocation();
	            
	        if (e.getClickedBlock().getState() instanceof DoubleChest){
	            
	        	DoubleChest dc = (DoubleChest) e.getClickedBlock().getState();
	        	InventoryHolder ih = dc.getLeftSide().getInventory().getHolder();
	            loc2 = ((DoubleChest) ih).getLocation();
	                
	            ih = dc.getRightSide().getInventory().getHolder();
	            loc3 = ((DoubleChest) ih).getLocation();
	        }
	        
	        int x = 0;
	        int y = 0;
	        
			for (String s : wcs.getChests()){
				
				String[] cSplit = s.split(" ");
				x++;
				
				if (locChec(cSplit, loc1, loc2, loc3)){

					if (cSplit[0].equals(e.getPlayer().getName())){
						
						s(e.getPlayer(), "Chest selected! Type /protect for options! :D");
						wcp.setSelChest(loc1);
						pl.wcm.updatePlayerMap(e.getPlayer().getName(), wcp);
						return;
					}
					
					y++;
				}
				
				if (x >= wcs.getChests().size() && y >= 1){
					return;
				}
			}
			
			wcs.getChests().add(e.getPlayer().getName() + " " + loc1.getWorld().getName() + " " + loc1.getX() + " " + loc1.getY() + " " + loc1.getZ());
			wcs.getChests().add(e.getPlayer().getName() + " " + loc2.getWorld().getName() + " " + loc2.getX() + " " + loc2.getY() + " " + loc2.getZ());
			wcs.getChests().add(e.getPlayer().getName() + " " + loc3.getWorld().getName() + " " + loc3.getX() + " " + loc3.getY() + " " + loc3.getZ());
			
			pl.wcm.updateSystem("system", wcs);
			
			BlockFace b = yawToFace(e.getPlayer().getEyeLocation().getYaw(), false);
			e.getClickedBlock().getRelative(b).setType(Material.WALL_SIGN);
			
			BlockState bs = e.getClickedBlock().getRelative(b).getState();
			
			((Sign) bs.getData()).setFacingDirection(b);
			bs.update();
			s(e.getPlayer(), "Chest protected. Type /protect for more options!");
			WCChests2.updateSign(e.getClickedBlock().getRelative(b).getLocation(), "§f// §5WC §f//" + e.getPlayer().getDisplayName());
		}
	}
	
	public BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
		
		if (useSubCardinalDirections) {
	         return radial[Math.round(yaw / 45f) & 0x7];
		} else {
	         return axis[Math.round(yaw / 90f) & 0x3];
	    }
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onOpen(InventoryOpenEvent e){

		if (e.getPlayer() instanceof Player && (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest)){
			
			Location loc1;
			Location loc2;

			if (e.getInventory().getHolder() instanceof Chest){
				loc1 = ((Chest) e.getInventory().getHolder()).getLocation();
			}	
			
			if (e.getInventory().getHolder() instanceof DoubleChest){
				loc1 = ((DoubleChest) e.getInventory().getHolder()).getLocation();
			}	
			
			loc2 = checkDouble(loc1);
		
			WCSystem wcs = pl.wcm.getWCSystem("system");
			int x = 0;
	
			for (String s : wcs.getChests()){
				
				String[] cSplit = s.split(" ");
				
				if (!locChec(cSplit, loc1, loc2, loc3)){
					x++;
				}
				
				if (!locChec(cSplit, loc1, loc2, loc3) && x >= wcs.getChests().size()){
					return;
				}
			}
			
			String owners = "";
			
			for (String s : wcs.getChests()){
				
				String[] cSplit = s.split(" ");
				
				if (locChec(cSplit, loc1, loc2, loc3) && !owners.contains(cSplit[0])){
					
					if (owners.equals("")){
						owners = "&6" + cSplit[0];
					} else {
						owners = owners + "&f, &6" + cSplit[0];
					}
				}
			}
			
			Player player = ((Player)e.getPlayer());
        
						
			if (e.getPlayer().hasPermission("wa.mod2") && !owners.contains(player.getName())){		
				for (Player p : Bukkit.getOnlinePlayers()){
					if (p.hasPermission("wa.staff")){
						s(p, player.getDisplayName() + " &6is snooping around in a chest owned by " + owners + "&6!");
					}
				}
					return;
			}

			e.setCancelled(true);
			s(player, "This chest is protected by " + owners + "&d.");
		}
	}
	
	private Location checkDouble(Location loc1) {
		
		
		return null;
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
