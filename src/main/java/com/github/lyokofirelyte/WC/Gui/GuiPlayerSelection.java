package com.github.lyokofirelyte.WC.Gui;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;

public class GuiPlayerSelection extends WCGui {
	
	public static Player player;
	
	private WCMain main;
	private WCGui parent;
	
	private Player[] players;
	private boolean close;
	private Runnable run;
	private int total;
	
	public GuiPlayerSelection(WCMain main, Runnable run, boolean close, WCGui parent){
		
		super(54, "&4PLAYER SELECTION");
		this.main = main;
		
		this.run = run;
		this.close = close;
		this.parent = parent;
		
	}
	
	@Override
	public void create(){
		
		players = Bukkit.getOnlinePlayers();
		total = this.players.length;
		
		for (int i = 0; i < this.total; i++){
			
			Player p = this.players[i];
			addButton(i, InventoryManager.createItem(p.getDisplayName(), new String[] { "&f" + p.getName() }, Material.SKULL_ITEM, 1, 3));
			
		}
		
		addButton(53, createItem("&bRETURN", new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		System.out.println(slot);
		System.out.println(total);
		
		if (current.equals("click")){
			
			if (this.slot == 53){
				
				this.main.wcm.displayGui(p, this.parent);
				
			} else if (this.slot < this.total){
				
				System.out.println(this.slot);
				
				player = players[slot];
				run.run();
				
				if (close){		
					p.closeInventory();		
				}
			}
		}
	}
}