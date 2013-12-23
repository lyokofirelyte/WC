package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.WCMain;

 public class WCInvSee implements CommandExecutor, Listener {

	WCMain plugin;
	public WCInvSee(WCMain instance){
	plugin = instance;
	}
	
	public List<String> invUsers = new ArrayList<String>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equals("invsee")){
			Player p = ((Player)sender);
			
			if (args.length == 0){
				WCMain.s(p, "Try /invsee <player>");
				return true;
			}
			
			if (Bukkit.getPlayer(args[0]) == null){
				WCMain.s(p, "That player is not online!");
				return true;
			}
			
			final Inventory inv = Bukkit.getPlayer(args[0]).getInventory();
			final Inventory cinv = Bukkit.createInventory(null, 36, "§b(" + Bukkit.getPlayer(args[0]).getDisplayName() + "§b)");
			int x = 0;
			
			for (ItemStack i : inv.getContents()){
				cinv.setItem(x, i);
				x++;
			}
			
			if (args.length == 2){
				p.openInventory(cinv);
			} else {
				p.openInventory(inv);
			}
			
			invUsers.add(p.getName());
			WCMain.s(p, "Viewing the inventory of " + Bukkit.getPlayer(args[0]).getDisplayName());
		}
		
		return true;
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onClick(InventoryClickEvent e){
		if (e.getWhoClicked() instanceof Player){
			Player p = ((Player)e.getWhoClicked());
			if(invUsers.contains(p.getName())){
				if (!p.hasPermission("wa.mod2")){
					WCMain.s((Player)e.getWhoClicked(), "You don't have permission to edit!");
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
