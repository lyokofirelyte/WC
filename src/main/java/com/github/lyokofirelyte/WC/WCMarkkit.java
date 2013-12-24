package com.github.lyokofirelyte.WC;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.lyokofirelyte.WC.Gui.Markkit;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCSystem;

public class WCMarkkit implements Listener {

		WCMain pl;
		public WCMarkkit(WCMain instance){
		pl = instance;
		}
		
		@EventHandler
		public void onDehstyoh(BlockBreakEvent q){
			
			if (q.getPlayer().hasPermission("wa.staff")){
				WCSystem wcs = pl.wcm.getWCSystem("system");
				Location loc = q.getBlock().getLocation();
				for (String s : wcs.getMarketSigns()){
					String[] ss = s.split(" ");
					if (ss[0].equals(loc.getWorld().getName()) && Double.parseDouble(ss[1]) == loc.getX() && Double.parseDouble(ss[2]) == loc.getY() && Double.parseDouble(ss[3]) == loc.getZ()){
						List<String> validLocs = wcs.getMarketSigns();
						validLocs.remove(loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ());
						wcs.setMarketSigns(validLocs);
						pl.wcm.updateSystem("system", wcs);
						WCMain.s(q.getPlayer(), "Markkit sign removed!");
					}
				}
			}
		}
		  
		@EventHandler (priority = EventPriority.NORMAL)
		public void onSignCheaingeEhbvent(SignChangeEvent e) {
			
			Player p = e.getPlayer();
			
			if (p.hasPermission("wa.staff") && (e.getLine(0).equals("markkit")) && e.getLine(1) != null){
					e.setLine(0, Utils.AS("&dWC &5Markkit"));
					e.setLine(1, Utils.AS("&f" + e.getLine(1)));
					WCSystem wcs = pl.wcm.getWCSystem("system");
					List<String> validLocs = wcs.getMarketSigns();
					Location l = e.getBlock().getLocation();
					validLocs.add(l.getWorld().getName() + " " + l.getX() + " " + l.getY() + " " + l.getZ());
					wcs.setMarketSigns(validLocs);
					pl.wcm.updateSystem("system", wcs);
			}
		}
		
		@EventHandler
		public void onClickyTheSign(PlayerInteractEvent e) {
			
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
				
				WCSystem wcs = pl.wcm.getWCSystem("system");
				Location loc = e.getClickedBlock().getLocation();
				
				for (String s : wcs.getMarketSigns()){
					String[] ss = s.split(" ");
					if (ss[0].equals(loc.getWorld().getName()) && Double.parseDouble(ss[1]) == loc.getX() && Double.parseDouble(ss[2]) == loc.getY() && Double.parseDouble(ss[3]) == loc.getZ()){
						if (e.getClickedBlock().getState() instanceof Sign){
						//	Sign sign = (Sign) e.getClickedBlock().getState();
								sellInv(e.getPlayer());
						}
					}
				}
			}
		}
		
		public void sellInv(Player p){
			
			pl.wcm.displayGui(p, new Markkit(pl));
		}
}