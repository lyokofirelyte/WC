package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

import static com.github.lyokofirelyte.WC.WCMain.s;

public class WCHat{

	WCMain pl;
	public WCHat(WCMain instance){
	pl = instance;
    }

	  @WCCommand(aliases = {"hat"}, desc = "Put on a hat", help = "/hat", max = 0, perm = "wa.districtman")
	  public void onHat(Player sender, String[] args){
		  
		  Player p = ((Player)sender);

				  if (p.getItemInHand() != null){
					  if (p.getEquipment().getHelmet() != null){
						  p.getInventory().addItem(p.getEquipment().getHelmet());
					  }
					  p.getEquipment().setHelmet(p.getItemInHand());
					  p.setItemInHand(new ItemStack(Material.AIR));
					  s(p, "Hat updated!");
				  }
				  
	  }
				 
	  @WCCommand(aliases = {"chestplate"}, desc = "Put on a chestplate", help = "/chestplate", max = 0, perm = "wa.districtman")
	  public void onChestplate(Player p, String[] args){
				  
				  if (p.getItemInHand() != null){
					  if (p.getEquipment().getChestplate() != null){
						  p.getInventory().addItem(p.getEquipment().getChestplate());
					  }
					  p.getEquipment().setChestplate(p.getItemInHand());
					  p.setItemInHand(new ItemStack(Material.AIR));
					  s(p, "Chestplate updated!");
				  }
				  
	  }
	  
	  @WCCommand(aliases = {"leggings"}, desc = "Put on leggings", help = "/leggings", max = 0, perm = "wa.districtman")
	  public void onLeggings(Player p, String[] args){
				  
				  if (p.getItemInHand() != null){
					  if (p.getEquipment().getLeggings() != null){
						  p.getInventory().addItem(p.getEquipment().getLeggings());
					  }
					  p.getEquipment().setLeggings(p.getItemInHand());
					  p.setItemInHand(new ItemStack(Material.AIR));
					  s(p, "Leggings updated!");
				  }
				  
	  }
	  
	  @WCCommand(aliases = {"boots"}, desc = "Put on boots", help = "/boots", max = 0, perm = "wa.districtman")
	  public void onBoots(Player p, String[] args){
 
				  if (p.getItemInHand() != null){
					  if (p.getEquipment().getBoots() != null){
						  p.getInventory().addItem(p.getEquipment().getBoots());
					  }
					  p.getEquipment().setBoots(p.getItemInHand());
					  p.setItemInHand(new ItemStack(Material.AIR));
					  s(p, "Boots updated!");
				  }
				  
		  }

	  }
