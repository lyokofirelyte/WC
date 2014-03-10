package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

 public class WCInvSee implements Listener {

	WCMain pl;
	
	public WCInvSee(WCMain i){
		pl = i;
	}
	
	public List<String> invUsers = new ArrayList<String>();
	
	@WCCommand(aliases = {"invsee"}, help = "/invsee <player>", min = 1, max = 2, perm = "wa.mod", player = true)
	public void onInvSee(Player sender, String[] args){

			Player p = ((Player)sender);
			
			if (args.length == 0){
				WCUtils.s(p, "Try /invsee <player>. You can add -f for frozen, or -o for offline inventory.");
				return;
			}
			
			if (args.length == 2 && args[1].equals("-o")){
				if (pl.wcm.getWCPlayer(args[0]) != null){
					p.openInventory(pl.wcm.getWCPlayer(args[0]).getOfflineInventory());
					WCUtils.s(p, "Viewing the offline inventory of " + pl.wcm.getFullNick(args[0]) + ". &c&oDo not edit this!");
					invUsers.add(p.getName());
				} else {
					WCUtils.s(p, "That player can't be located in the API!");
				}
				return;
			}
			
			if (Bukkit.getPlayer(args[0]) == null){
				WCUtils.s(p, "That player is not online!");
				return;
			}

			final Inventory inv = Bukkit.getPlayer(args[0]).getInventory();
			final Inventory cinv = Bukkit.createInventory(null, 36, "§b(" + Bukkit.getPlayer(args[0]).getDisplayName() + "§b)");
			int x = 0;
			
			for (ItemStack i : inv.getContents()){
				cinv.setItem(x, i);
				x++;
			}
			
			if (args.length == 2 && args[1].equals("-f")){
				p.openInventory(cinv);
			} else {
				p.openInventory(inv);
			}
			
			invUsers.add(p.getName());
			WCUtils.s(p, "Viewing the inventory of " + Bukkit.getPlayer(args[0]).getDisplayName());
		}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onClick(InventoryClickEvent e){
		if (e.getWhoClicked() instanceof Player){
			Player p = ((Player)e.getWhoClicked());
			if(invUsers.contains(p.getName())){
				if (!p.hasPermission("wa.mod2")){
					WCUtils.s((Player)e.getWhoClicked(), "You don't have permission to edit!");
					e.setCancelled(true);
				}
		    }
		}
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onClose(InventoryCloseEvent e){
		if (invUsers.contains(e.getPlayer().getName())){
			invUsers.remove(e.getPlayer().getName());
		}
	}
 }