package com.github.lyokofirelyte.WC;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Events.ParagonFindEvent;
import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;
import com.github.lyokofirelyte.WCAPI.Loops.WCLoop;

import static com.github.lyokofirelyte.WCAPI.WCUtils.AS;
import static com.github.lyokofirelyte.WCAPI.WCUtils.s;

public class WCMineNDash implements Listener {
	
	WCMain pl;
	
	public WCMineNDash(WCMain i){
		pl = i;
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onParagon(ParagonFindEvent e){
		
		if (pl.wcm.getWCSystem("system").getElevatorUser().equals(e.getPlayer())){
			ItemStack token = pl.invManager.makeItem("§e§o§lPARAGON TOKEN", "§7§oIt's currency!", true, Enchantment.DURABILITY, 10, 11, Material.INK_SACK, 1);
			if (e.getPlayer().getInventory().firstEmpty() == -1){
				e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), token);
			} else {
				e.getPlayer().getInventory().addItem(token);
			}
			Bukkit.broadcastMessage(AS("&d>> &6An extra token was awarded for finding it during MineNDash! &d<<"));
		}
	}

	@EventHandler
	public void onBuild(BlockBreakEvent e){
		
		if (pl.wcm.getWCSystem("system").getMinendashLocs().contains(e.getBlock().getLocation())){
			if (pl.wcm.getWCSystem("system").getMinendashUser() != e.getPlayer()){
				s(e.getPlayer(), "You aren't in MineNDash.");
				e.setCancelled(true);
			}
		}
	}
	
    @EventHandler
    public void onSign(SignChangeEvent e){

    	if (e.getLine(0).contains("MineNDash")){
    		if (!e.getPlayer().hasPermission("wa.staff")){
    			e.setLine(0, AS("&4INVALID"));
    		} else {
    			e.setLine(0, AS("&dMineNDash"));
    			e.setLine(2, AS("&fTo fill"));
    			e.setLine(3, AS("&fClick Here"));
    		}
    	}
    }
    
    @EventHandler
    public void onSignClick(PlayerInteractEvent e){
    
	    if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
	    	if(e.getClickedBlock().getState() instanceof Sign){
	    		Sign s = (Sign) e.getClickedBlock().getState();
	    		if (s.getLine(0).equals(AS("&dMineNDash"))){
	    			Player p = e.getPlayer();
	    			if (p.getName() != "Hugh_Jasses" && (!p.hasPermission("wa.districtman") || pl.wcm.getWCSystem("system").getMinendashActive())){
	    				s(p, "You must be disctrictman+ for this or it's already in use.");
	    			} else if (pl.wcm.getWCPlayer(p.getName()).getMineTimer() < System.currentTimeMillis() && pl.wcm.getWCPlayer(p.getName()).getMineTimer() != 0){
	    				s(p, "You still have &6" + ((pl.wcm.getWCPlayer(p.getName()).getMineTimer()/1000L) - (System.currentTimeMillis()/1000L)) + " &dseconds left.");
	    			} else {
	    				pl.wcm.getWCPlayer(p.getName()).setMineTimer(System.currentTimeMillis() + 604800000L);
	    				pl.wcm.getWCSystem("system").setMinendashActive(true);
	    				pl.wcm.getWCSystem("system").setMinendashUser(e.getPlayer());
	    				
	    				try {
							pl.api.ls.callLoop(getClass().getMethod("scheduleCountdown"), getClass(), pl);
							pl.api.ls.callDelay(getClass().getMethod("endCount"), getClass(), pl);
							pl.api.ls.callDelay(getClass().getMethod("endRound"), getClass(), pl);
						} catch (NoSuchMethodException | SecurityException e1) {
							e1.printStackTrace();
						}
	    				
	    				setArena(e.getClickedBlock().getLocation());
	    				s(p, "You have 5 minutes to mine!");
	    			}
	    		}
	    	}
	    }
    }
    
    @WCDelay(time = 5900L)
    public void endCount(){
    	
		try {
			pl.api.ls.callLoop(getClass().getMethod("scheduleCountdown"), getClass(), pl);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
    }
    
    @WCDelay(time = 6000L)
    public void endRound(){
    	
    	WCSystem wcs = pl.wcm.getWCSystem("system");
    	
    	if (wcs.getMinendashUser().isOnline()){
    		wcs.getMinendashUser().performCommand("spawn");
    	}
    	
    	for (Location l : wcs.getMinendashLocs()){
    		l.getBlock().setType(Material.BEDROCK);
    	}
    	
    	wcs.setMinendashUser(null);
    	wcs.setMinendashActive(false);
    	pl.wcm.updateSystem("system", wcs);
    }
    
    @WCLoop(time = 20L, delay = 0L, repeats = 4)
    public void scheduleCountdown(){
    	
    	WCSystem wcs = pl.wcm.getWCSystem("system");
    	
    	if (wcs.getMinendashUser().isOnline()){
    		wcs.getMinendashUser().sendMessage(AS("&6" + wcs.getMinendashCountDown()));
    	}
    	
    	if (wcs.getMinendashCountDown() <= 0){
    		wcs.setMinendashCountDown(3);
    	} else {
        	wcs.setMinendashCountDown(wcs.getMinendashCountDown()-1);
    	}
    	
    	pl.wcm.updateSystem("system", wcs);
    }

    public void setArena(Location l){
    	
    	Random rand = new Random();
    	Location start = new Location(l.getWorld(), l.getX()+20, l.getY()-20, l.getZ());
    	List<Location> locs = WCUtils.circle(start, 10, 20, false, false, 0);
    	List<Location> locWalls = WCUtils.circle(start, 11, 10, true, false, 20);
    	pl.wcm.getWCSystem("system").setMinendashLocs(locs);
    	
    	for (int x = 1; x <= 10; x++){
    		Location path = new Location(l.getWorld(), l.getX()+x, l.getY()-1, l.getZ());
    		path.getBlock().setType(Material.GLOWSTONE);
    	}
    	
    	for (Location lc : locWalls){
    		
    		if (new Location(lc.getWorld(), lc.getX(), lc.getY()-1, lc.getZ()).getBlock().getType().equals(Material.GLOWSTONE)){
        		lc.getBlock().setType(Material.GLASS);
    		} else {
    			lc.getBlock().setType(Material.GLOWSTONE);
    		}

    	}
    	
    	for (Location lc : locs){
    		
    		Material mat = null;
    		int block = rand.nextInt(100);
    		
    		switch(block){
    		
    			case 1: case 2:
    				mat = Material.DIAMOND_ORE;
    				break;
    			
    			case 3: case 4: 
    				mat = Material.EMERALD_ORE;
    				break;
    				
    			case 5: case 6: case 7:
    				mat = Material.REDSTONE_ORE;
    				break;
    			
    			case 8: case 9:
    				mat = Material.GOLD_ORE;
    				break;
    				
    			case 10: case 11: case 12:
    				mat = Material.IRON_ORE;
    				break;
    				
    			case 13: case 14: case 15:
    				mat = Material.COAL_ORE;
    				break;
    				
    			case 16: case 17:
    				mat = Material.LAPIS_ORE;
    				break;
    				
    			case 18: case 19:
    				mat = Material.QUARTZ_ORE;
    				break;
    				
    			default:
    				mat = Material.STONE;
    				break;
    		}
    		
    		lc.getBlock().setType(mat);
    	}
    	
    	pl.wcm.getWCSystem("system").getMinendashUser().teleport(new Location(l.getWorld(), l.getX()+20, l.getY()+1, l.getZ()));
    	WCUtils.effects(l);
    }
}