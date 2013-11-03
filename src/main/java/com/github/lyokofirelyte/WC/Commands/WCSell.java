package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.WCMain;

import static com.github.lyokofirelyte.WC.WCMain.s;
import static com.github.lyokofirelyte.WC.WCMain.s2;
import static com.github.lyokofirelyte.WC.Util.Utils.bc;

public class WCSell implements CommandExecutor {

	 WCMain plugin;
	 public WCSell(WCMain instance){
	 this.plugin = instance;
	 }
	 
	 Chest chest;
	 Inventory inv;
	 
	 public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
			  
		if (cmd.getName().equalsIgnoreCase("sell")){
			
			Player p = ((Player)sender);
			
			if (p.getWorld().getName().equals("creative") || p.getWorld().getName().equals("world2")){
				WCMain.s(p, "You can't sell items from this world.");
				return true;
			}
			
			if (p.getItemInHand().getItemMeta().hasLore()){
				WCMain.s(p, "You can't sell that.");
				return true;
			}
			
			if (p.getItemInHand().getType().equals(Material.AIR) || p.getItemInHand() == null){
				s(p, "You can't sell your hand for money! That's... nevermind, just choose something else.");
				return true;
			}
			
			if (args.length != 1 || Integer.parseInt(args[0]) < 100){
				s(p, "/sell <price> (Price >= 100)");
				return true;
			}

			Location chestLoc = new Location(Bukkit.getWorld("hbd"), 41.0, 75.0, -520.0);
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
				  return true;
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
			}
			
		}
		
		return true;
	 }
}
