package com.github.lyokofirelyte.WC;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class WCTelePads implements Listener {

	WCMain pl;
	public WCTelePads(WCMain instance){
	pl = instance;
	}
	
	Location current;
	Boolean found = false;
	List<Location> blackList = new ArrayList<>();
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlace(BlockPlaceEvent e){
		if (e.getItemInHand().getType().equals(Material.DAYLIGHT_DETECTOR)){
			ItemStack i = e.getItemInHand();
			if (i.hasItemMeta()){
				ItemMeta im = i.getItemMeta();
				if (im.hasDisplayName() && im.hasLore() && im.getDisplayName().contains("TELEPORT PAD")){
					WCSystem wcs = pl.wcm.getWCSystem("system");
					List<Location> l = wcs.getTeleportPads();
					l.add(e.getBlock().getLocation());
					wcs.setTeleportPads(l);
					pl.wcm.updateSystem("system", wcs);
					s(e.getPlayer(), "Added pad at &6" + e.getBlock().getLocation().getX() + " &6" + e.getBlock().getLocation().getY() + " &6" + e.getBlock().getLocation().getZ() + "&d. Connect another one with redstone!");
					s(e.getPlayer(), "&4WARNING: Connecting multiple with the same wire may cause unexpected results!");
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onBreak(BlockBreakEvent e){
		if (e.getBlock().getType().equals(Material.DAYLIGHT_DETECTOR)){
			WCSystem wcs = pl.wcm.getWCSystem("system");
			if (wcs.getTeleportPads().contains(e.getBlock().getLocation())){
				List<Location> l = wcs.getTeleportPads();
				l.remove(e.getBlock().getLocation());
				wcs.setTeleportPads(l);
				pl.wcm.updateSystem("system", wcs);
				s(e.getPlayer(), "Pad removed!");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onClick(PlayerInteractEvent e){
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType().equals(Material.DAYLIGHT_DETECTOR)){
			
			Location c = e.getClickedBlock().getLocation();
			Player p = e.getPlayer();
			WCSystem wcs = pl.wcm.getWCSystem("system");
			List<Location> locs = wcs.getTeleportPads();
			
			for (Location l : locs){
				if (l.getWorld() == p.getWorld() && l.getX() == c.getX() && l.getY() == c.getY() && l.getZ() == c.getZ()){
					if (l.getBlockX() != p.getLocation().getBlockX() || l.getBlockY() != p.getLocation().getBlockY() || l.getBlockZ() != p.getLocation().getBlockZ()){
						s(p, "You must be standing on the pad to activate it.");
						return;
					}
					search(p, c);
					return;
				}
			}
		}
	}
	
	public void search(Player p, Location l){

		current = l;
		found = false;
		blackList = new ArrayList<Location>();
		blackList.add(l);
		
		for (int x = 0; x <= 1000; x++){
			if (!found){
				getValid(p, current);
			} else {
				return;
			}
		}
		
		s(p, "No link found!");
	}
	
	public void getValid(Player p, Location l){
		
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		
		if (red(p, loc(x+1, y, z))){
			current = loc(x+1, y, z);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x-1, y, z))){
			current = loc(x-1, y, z);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x, y, z+1))){
			current = loc(x, y, z+1);
			blackList.add(current);
			return;
		}

		if (red(p, loc(x, y, z-1))){
			current = loc(x, y, z-1);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x+1, y+1, z))){
			current = loc(x+1, y+1, z);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x-1, y+1, z))){
			current = loc(x-1, y+1, z);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x+1, y-1, z))){
			current = loc(x+1, y-1, z);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x-1, y-1, z))){
			current = loc(x-1, y-1, z);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x, y+1, z+1))){
			current = loc(x, y+1, z+1);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x, y+1, z-1))){
			current = loc(x, y+1, z-1);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x, y-1, z+1))){
			current = loc(x, y-1, z+1);
			blackList.add(current);
			return;
		}
		
		if (red(p, loc(x, y-1, z-1))){
			current = loc(x, y-1, z-1);
			blackList.add(current);
			return;
		}
	}
	
	public Location loc(double x, double y, double z){
		return new Location(current.getWorld(), x, y, z);
	}
	
	public Boolean red(Player p, Location l){
		Material it = l.getBlock().getType();
		if ((it.equals(Material.REDSTONE_WIRE) || it.equals(Material.REDSTONE) || it.equals(Material.DIODE) || it.equals(Material.DIODE_BLOCK_OFF) ||  it.equals(Material.DIODE_BLOCK_ON) || it.equals(Material.REDSTONE_COMPARATOR_ON) || it.equals(Material.REDSTONE_COMPARATOR_OFF)) && l.getBlock().isBlockPowered() && !blackList.contains(l)){
			return true;
		} else if (it.equals(Material.DAYLIGHT_DETECTOR) && pl.wcm.getWCSystem("system").getTeleportPads().contains(l) && !blackList.contains(l)){
			Utils.effects(p);
			p.teleport(l);
			Utils.effects(p);
			found = true;
		}
		return false;
	}
}
