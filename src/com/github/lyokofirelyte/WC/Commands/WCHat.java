package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WC.WCMain.s;

public class WCHat implements CommandExecutor {

	WCMain pl;
	public WCHat(WCMain instance){
	pl = instance;
    }

	  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		  Player p = ((Player)sender);
		  
		  switch (cmd.getName().toLowerCase()){
		  
			  case "hat":
				  
				  if (p.getItemInHand() != null){
					  if (p.getEquipment().getHelmet() != null){
						  p.getInventory().addItem(p.getEquipment().getHelmet());
					  }
					  p.getEquipment().setHelmet(p.getItemInHand());
					  p.setItemInHand(new ItemStack(Material.AIR));
					  s(p, "Hat updated!");
				  }
				  
				  break;
				  
			  case "chestplate":
				  
				  if (p.getItemInHand() != null){
					  if (p.getEquipment().getChestplate() != null){
						  p.getInventory().addItem(p.getEquipment().getChestplate());
					  }
					  p.getEquipment().setChestplate(p.getItemInHand());
					  p.setItemInHand(new ItemStack(Material.AIR));
					  s(p, "Chestplate updated!");
				  }
				  
				  break;
				  
			  case "leggings":
				  
				  if (p.getItemInHand() != null){
					  if (p.getEquipment().getLeggings() != null){
						  p.getInventory().addItem(p.getEquipment().getLeggings());
					  }
					  p.getEquipment().setLeggings(p.getItemInHand());
					  p.setItemInHand(new ItemStack(Material.AIR));
					  s(p, "Leggings updated!");
				  }
				  
				  break;
				  
			  case "boots":
				  
				  if (p.getItemInHand() != null){
					  if (p.getEquipment().getBoots() != null){
						  p.getInventory().addItem(p.getEquipment().getBoots());
					  }
					  p.getEquipment().setBoots(p.getItemInHand());
					  p.setItemInHand(new ItemStack(Material.AIR));
					  s(p, "Boots updated!");
				  }
				  
				  break;
		  }

		  return true;
	  }
}
