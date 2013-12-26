package com.github.lyokofirelyte.WC;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.github.lyokofirelyte.WC.Gui.GuiMarkkit;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;

public class WCMarkkit implements Listener {

		WCMain pl;
		public WCMarkkit(WCMain instance){
		pl = instance;
		}
		
		public void removeQue(WCSystem wcs, String s, List<String> signs){		
			signs.remove(s);
			wcs.setMarketSigns(signs);
			pl.wcm.updateSystem("system", wcs);
		}
		
		@EventHandler
		public void onDehstyoh(BlockBreakEvent q){
			
			if (q.getPlayer().hasPermission("wa.staff")){
				WCSystem wcs = pl.wcm.getWCSystem("system");
				Location loc = q.getBlock().getLocation();
				for (String s : wcs.getMarketSigns()){
					String[] ss = s.split(" ");
					if (ss[0].equals(loc.getWorld().getName()) && Double.parseDouble(ss[1]) == loc.getX() && Double.parseDouble(ss[2]) == loc.getY() && Double.parseDouble(ss[3]) == loc.getZ()){
						removeQue(wcs, s, wcs.getMarketSigns());
						WCMain.s(q.getPlayer(), "Removed!");
						return;
					}
				}
			}
		}
		  
		@EventHandler (priority = EventPriority.NORMAL)
		public void onSignCheaingeEhbvent(SignChangeEvent e) {
			
			Player p = e.getPlayer();
			
			if (p.hasPermission("wa.staff") && e.getLine(0).equalsIgnoreCase("markkit") && e.getLine(1) != null && !e.getLine(1).equals("")){
					e.setLine(0, Utils.AS("&dWC &5Markkit"));
					e.setLine(1, Utils.AS("&f" + e.getLine(1)));
					WCSystem wcs = pl.wcm.getWCSystem("system");
					List<String> validLocs = wcs.getMarketSigns();
					Location l = e.getBlock().getLocation();
					validLocs.add(l.getWorld().getName() + " " + l.getX() + " " + l.getY() + " " + l.getZ());
					wcs.setMarketSigns(validLocs);
					pl.wcm.updateSystem("system", wcs);
			} else if (e.getLine(0).equalsIgnoreCase("markkit")){
				e.setLine(0, Utils.AS("&4INVALID"));
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
							Sign sign = (Sign) e.getClickedBlock().getState();
							WCGui gui = new GuiMarkkit(pl);
							if (pl.markkitInvs.containsKey(sign.getLine(1).substring(2))){
								if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() != Material.AIR){
									WCMain.s(e.getPlayer(), "You must use your hand to activate the sign.");
									return;
								}
								Inventory inv = Bukkit.createInventory(null, 54, Utils.AS("&dMarkkit"));
								inv.setContents(pl.markkitInvs.get(sign.getLine(1).substring(2)).getContents());
								gui.setInv(inv);
								pl.wcm.displayGui(e.getPlayer(), gui);
							} else {
								pl.wcm.displayGui(e.getPlayer(), new GuiMarkkit(pl));
							}
							pl.wcm.getWCPlayer(e.getPlayer().getName()).setCurrentMarkkitEdit(sign.getLine(1).substring(2));
							return;
						}
					}
				}
			}
		}
		
		@EventHandler
		public void onClose(InventoryCloseEvent e){
			
			if (pl.wcm.currentGui.containsKey(e.getPlayer().getName())){
				WCPlayer wcp = pl.wcm.getWCPlayer(e.getPlayer().getName());
				if (wcp.getCurrentMarkkitEdit() != "none" && wcp.getMarkkitEditMode()){
					pl.markkitInvs.put(wcp.getCurrentMarkkitEdit(), e.getInventory());
				}
				WCGui gui = pl.wcm.currentGui.get(e.getPlayer().getName());
				if (e.getInventory().getTitle().equals(Utils.AS("&dMarkkit"))){
					List<Integer> sellSlots = Arrays.asList(0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45);
					for (int i = 0; i < e.getInventory().getSize(); i++){
						if (sellSlots.contains(i)){
							if (e.getInventory().getItem(i) != null && e.getInventory().getItem(i).getType() != Material.AIR){
								if (e.getPlayer().getInventory().firstEmpty() != -1){
									e.getPlayer().getInventory().addItem(gui.inv.getItem(i));
								} else {
									e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), e.getInventory().getItem(i));
								}
							}
						}
					}
				}	
			}
		}
}