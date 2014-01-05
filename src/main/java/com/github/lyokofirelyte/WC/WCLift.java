package com.github.lyokofirelyte.WC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;

import static com.github.lyokofirelyte.WCAPI.WCUtils.AS;
import static com.github.lyokofirelyte.WCAPI.WCUtils.isInteger;
import static com.github.lyokofirelyte.WCAPI.WCUtils.isDouble;
import static com.github.lyokofirelyte.WCAPI.WCUtils.s;

public class WCLift implements Listener {
	
	WCMain pl;
	
	public WCLift(WCMain i){
		pl = i;
	}
	
	@EventHandler							// 0          1            2                 3
	public void onSign(SignChangeEvent e){ // WCLift - SPEED - DESTINATION FLOOR - CURRENT FLOOR
		
		Player p = e.getPlayer();
		if (e.getLine(0).contains(AS("WCLift"))){ 
			if (p.hasPermission("wa.staff") && isDouble(e.getLine(1)) && isInteger(e.getLine(2)) && isInteger(e.getLine(3))){
				e.setLine(0, AS("&d" + e.getLine(0)));
				e.setLine(1, AS("&aSpeed: " + e.getLine(1)));
				e.setLine(2, AS("&aDest: " + e.getLine(2)));
				e.setLine(3, AS("&aCurrent: " + e.getLine(3)));
			} else {
				e.setLine(0, AS("&4INVALID!"));
				e.setLine(1, "speed here");
				e.setLine(2, "destination #");
				e.setLine(3, "current #");
			}
		}
	}
	
	@EventHandler
	public void onClickSign(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		Location pLoc = p.getLocation();
		Boolean passed = false;

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType().equals(Material.WALL_SIGN) && e.getClickedBlock() != null){
			
			Sign sign = (Sign) e.getClickedBlock().getState();
			
			if (sign.getLine(0).equals(AS("&dWCLift"))){
				
				if (pl.wcm.getWCSystem("system").isElevatorActive()){
					s(p, "An elevator is active somewhere - please try again in a minute.");
					return;
				}
				
				Location sl = sign.getLocation();
				Location theFloor = new Location(sl.getWorld(), sl.getX(), sl.getY()-2, sl.getZ());
				
				if (theFloor.getBlock().getType().equals(Material.GLASS)){
					
					pl.wcm.getWCSystem("system").setElevatorActive(true);
					WCLiftFloor wclf = new WCLiftFloor(p.getName());
					Map<String, Map<Float, Float>> players = new HashMap<String, Map<Float, Float>>();
					Map<Float, Float> speeds = new HashMap<Float, Float>();
					Map<Location, Material> matsFloor = new HashMap<Location, Material>();
					Map<Location, Material> matsAll = new HashMap<Location, Material>();
					Map<Integer, Location> floorSigns = new HashMap<Integer, Location>();
					List<Location> toCheck = WCUtils.circle(theFloor, 5, 1, false, false, 0);

					for (int x = 1; x < 256; x++){
						
						Location l = new Location(sign.getWorld(), sign.getX(), x, sign.getZ());	
						
						if (l.getBlock().getType().equals(Material.WALL_SIGN)){
							
							Sign checkSign = (Sign) l.getBlock().getState();
							
							if (checkSign.getLine(0).contains(AS("&dWCLift"))){		
								wclf.addFloor();
								floorSigns.put(wclf.getFloorAmount(), l);		
							}
						}
					}
					
					for (Location checkBlock : toCheck){
						if (checkBlock.getBlock().getType().equals(Material.GLASS)){
							matsFloor.put(checkBlock, checkBlock.getBlock().getType());
							matsAll.put(checkBlock, checkBlock.getBlock().getType());
						}
					}
					
					for (Location l : matsFloor.keySet()){
						if (Math.round(l.getX()) == Math.round(pLoc.getX()) && Math.round(l.getZ()) == Math.round(pLoc.getZ())){
							passed = true;
						}
					}
					
					if (!passed){
						s(p, "You must stand on the elevator pad.");
						return;
					}
					
					for (Entity ee : p.getNearbyEntities(10D, 1D, 10D)){
						if (ee instanceof Player){
							Player eep = (Player) ee;
							Location ploc = eep.getLocation();
							for (Location l : matsFloor.keySet()){
								if (Math.round(l.getX()) == Math.round(ploc.getX()) && Math.round(l.getZ()) == Math.round(ploc.getZ())){
									speeds.put(eep.getWalkSpeed(), eep.getFlySpeed());
									players.put(eep.getName(), new HashMap<Float, Float>(speeds));
									speeds = new HashMap<Float, Float>();
								}
							}
						}
					}
					
					speeds.put(p.getWalkSpeed(), p.getFlySpeed());
					players.put(p.getName(), new HashMap<Float, Float>(speeds));
					
					for (int x = (int) floorSigns.get(1).getY(); x <= floorSigns.get(floorSigns.values().size()).getY(); x++){
						for (Location l : matsFloor.keySet()){
							Location ll = new Location(l.getWorld(), l.getX(), x, l.getZ());
							matsAll.put(ll, ll.getBlock().getType());
						}
					}
					
					wclf.setFloors(matsAll);
					wclf.setFloorSigns(floorSigns);
					wclf.setPlayers(players);
					wclf.setElevatorSpeed(Double.parseDouble(sign.getLine(1).substring(9)));
					
					if (Integer.parseInt(sign.getLine(2).substring(8)) > Integer.parseInt(sign.getLine(3).substring(11))){
						wclf.setStart("up");
					} else {
						wclf.setStart("down");
					}

					if (floorSigns.get(Integer.parseInt(sign.getLine(2).substring(8))) != null){
						wclf.setDest(Integer.parseInt(sign.getLine(2).substring(8)));
						Map<String, WCLiftFloor> map = new HashMap<String, WCLiftFloor>();
						map.put(p.getName(), wclf);
						pl.wcm.getWCSystem("system").setElevatorUser(p);
						pl.wcm.getWCSystem("system").setElevatorMap(map);
						for (Location l : matsAll.keySet()){
							if (l.getBlock().getType() != Material.WALL_SIGN){
								l.getBlock().setType(Material.AIR);
							}
						}
						for (String eep : players.keySet()){
							Player player = Bukkit.getPlayer(eep);
							player.setWalkSpeed(0);
							player.setFlySpeed(0);
							player.setAllowFlight(true);
							player.setFlying(true);
							try {
								pl.api.ls.callDelay(getClass().getMethod("ascend"), getClass(), pl);
							} catch (Exception eq){
								eq.printStackTrace();
							}
						}
					} else {
						s(p, "Your destination does NOT exist!");
						return;
					}	
				} else {
					s(p, "This sign needs glass two full blocks below it, and probably a platform so you don't glitch. So yeah...");
				}
			}
		}
	}
	
	@WCDelay (time = 1L)
	public void ascend(){
		
		WCLiftFloor wclf = pl.wcm.getWCSystem("system").getElevatorMap().get(pl.wcm.getWCSystem("system").getElevatorUser().getName());
		List<Player> endPlayers = new ArrayList<Player>();
			
		for (String s : wclf.getPlayers().keySet()){
			Player p = Bukkit.getPlayer(s);
			if (wclf.getStart().equals("up")){
				p.setVelocity(new Vector(0, (wclf.getElevatorSpeed()), 0));
				if (Math.round(wclf.getFloorSigns().get(wclf.getDest()).getY()) <= Math.round(p.getLocation().getY())){
					endPlayers.add(p);
				}
			} else {
				p.setVelocity(new Vector(0, -(wclf.getElevatorSpeed()), 0));
				if (Math.round(wclf.getFloorSigns().get(wclf.getDest()).getY()) >= Math.round(p.getLocation().getY())){
					endPlayers.add(p);
				}
			}
		}
		
		for (Player p : endPlayers){
			
			p.setVelocity(new Vector(0, 0, 0));
			
			if (!p.hasPermission("wa.mod2")){
				p.setAllowFlight(false);
			}
			
			p.setFlying(false);
			
			for (Float a : wclf.getPlayers().get(p.getName()).keySet()){
				p.setWalkSpeed(a);
			}
			
			for (Float a : wclf.getPlayers().get(p.getName()).values()){
				p.setFlySpeed(a);
			}
			
			if (pl.wcm.getWCSystem("system").getElevatorUser() != p){
				wclf.getPlayers().remove(p.getName());
			} else if (wclf.getPlayers().keySet().size() <= 1){
				wclf.getPlayers().remove(p.getName());
				pl.wcm.getWCSystem("system").setElevatorActive(false);
				
				for (Location l : wclf.getFloors().keySet()){
					if (l.getBlock().getType() != Material.WALL_SIGN){
						l.getBlock().setType(wclf.getFloors().get(l));
					}
				}
				return;
			}
		}
		
		try {
			pl.api.ls.callDelay(getClass().getMethod("ascend"), getClass(), pl);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}