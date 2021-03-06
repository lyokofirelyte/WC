package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.WCMenus;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class WCSell{

	 WCMain plugin;
	 public WCSell(WCMain instance){
	 this.plugin = instance;
	 }
	 
	 Chest chest;
	 Inventory inv;
	 
	 @WCCommand(aliases = {"sell"}, help = "Sell your stuff", max = 1, player = true)
	 public void onSell(Player sender, String[] args){
			
			Player p = ((Player)sender);
			
			if (p.getWorld().getName().equals("creative") || p.getWorld().getName().equals("world2")){
				s(p, "You can't sell items from this world.");
				return;
			}
			
			if (p.getItemInHand().getItemMeta().hasLore()){
				s(p, "You can't sell that.");
				return;
			}
			
			if (p.getItemInHand().getType().equals(Material.AIR) || p.getItemInHand() == null){
				s(p, "You can't sell your hand for money! That's... nevermind, just choose something else.");
				return;
			}
			
			if (args.length != 1 || Integer.parseInt(args[0]) < 100){
				s(p, "/sell <price> (Price >= 100)");
				return;
			}

			Location chestLoc = new Location(Bukkit.getWorld("world"), -272.0, 61.0, -134.0);
			Block block = chestLoc.getBlock();
			BlockState bs = block.getState();
			
			if(bs instanceof Chest) {
				
			  chest = (Chest)bs;
			  int x = 0;
			  
			  for (ItemStack item : chest.getInventory().getContents()) {
				    if (item != null) {
						x++;
				    }
			  }
			  
			  if (x >= 27){
				  s(p, "The Closet is full! Ask staff to pull some old items down.");
				  return;
			  }
			 
			  ItemStack forSale = p.getItemInHand();
			  ItemMeta forSaleMeta = forSale.getItemMeta();
			  List<String> lore = new ArrayList<>();
			  
			  lore.add(args[0]);
			  lore.add(p.getName());
			  forSaleMeta.setLore(lore);
			  
			  forSale.setItemMeta(forSaleMeta);
			  
			  chest.getInventory().addItem(forSale);
			  p.setItemInHand(new ItemStack(Material.AIR, 1));
			  s(p, "&7&oYou've put up an item for sale! (See the shop via /root -> closet");
			  s2(p, "&7&oYou can cancel this offer by just buying it back.");
			  bc(p.getDisplayName() + " &dhas put an item up for sale in The Closet!");
			  
				for (HumanEntity a : WCMenus.invs.get("closetStore").getViewers()){
					WCMenus.openCloset((Player)a);
					s((Player)a, "Store refreshed because of sell addition.");
				}
			}
		
		return;
	 }
}
