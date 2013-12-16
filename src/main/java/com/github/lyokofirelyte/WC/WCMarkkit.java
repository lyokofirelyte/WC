package com.github.lyokofirelyte.WC;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;

public class WCMarkkit implements Listener {

		WCMain pl;
		public WCMarkkit(WCMain instance){
		pl = instance;
		}
		  
		@EventHandler (priority = EventPriority.NORMAL)
		public void onSignCheaingeEhbvent(SignChangeEvent e) {
			
			Player p = e.getPlayer();
			
			if (p.hasPermission("wa.staff") && (e.getLine(0).equals("sell") || e.getLine(0).equals("buy"))){
				if (Utils.isInteger(e.getLine(1)) && e.getLine(3).startsWith("*")){
					e.setLine(0, Utils.AS("&3>> " + e.getLine(0) + " >>"));
					WCSystem wcs = pl.wcm.getWCSystem("system");
					List<String> validLocs = wcs.getMarketSigns();
					Location l = e.getBlock().getLocation();
					validLocs.add(l.getWorld().getName() + " " + Math.round(l.getX()) + " " + Math.round(l.getY()) + " " + Math.round(l.getZ()));
					wcs.setMarketSigns(validLocs);
					pl.wcm.updateSystem("system", wcs);
				} else {
					e.setLine(0, Utils.AS("&4invalid sign"));
				}
			}
		}
		
		@EventHandler
		public void onClickyTheSign(PlayerInteractEvent e) {
			
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
				
				WCSystem wcs = pl.wcm.getWCSystem("system");
				Location loc = e.getClickedBlock().getLocation();
				
				for (String s : wcs.getMarketSigns()){
					String[] ss = s.split(" ");
					if (ss[0] == loc.getWorld().getName() && ss[1].equals(Math.round(loc.getX())) && ss[2].equals(Math.round(loc.getY())) && ss[3].equals(Math.round(loc.getZ()))){
						System.out.println("sign good loc1");
						if (e.getClickedBlock().getState() instanceof Sign){
							Sign sign = (Sign) e.getClickedBlock().getState();
							System.out.println("sign good loc2");
							if (sign.getLine(0).contains("sell")){
								sellCheck(e.getPlayer(), Integer.parseInt(sign.getLine(1)), sign.getLine(2), sign.getLine(3));
							} else {
								buyCheck(e.getPlayer(), Integer.parseInt(sign.getLine(1)), sign.getLine(2), sign.getLine(3));
							}
						}
					}
				}
			}
		}
		
		@SuppressWarnings("deprecation")
		public void sellCheck(Player p, int amount, String item, String price){
			
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			
			if (wcp.getBalance() >= Integer.parseInt(price.replace("*", ""))){
				if (p.getInventory().firstEmpty() == -1){
					WCMain.s(p, "Your inventory is full!");
				} else {
					String[] iSplit = item.split(":");
					ItemStack i = new ItemStack(Integer.parseInt(iSplit[0]), amount, (byte)Integer.parseInt(iSplit[1]));
					p.getInventory().addItem(i);
					wcp.setBalance(wcp.getBalance() - Integer.parseInt(price));
				}
			}
		}
		
		@SuppressWarnings("deprecation")
		public void buyCheck(Player p, int amount, String item, String price){
			
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			
			String[] iSplit = item.split(":");
			ItemStack i = new ItemStack(Integer.parseInt(iSplit[0]), amount, (byte)Integer.parseInt(iSplit[1]));
			
			for (ItemStack ii : p.getInventory()){
				if (ii == i){
					ii.setType(Material.AIR);
					wcp.setBalance(wcp.getBalance() + Integer.parseInt(price.replace("*", "")));
				}
			}
		}
}