package com.github.lyokofirelyte.WC.Gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.WCManager;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;

public class GuiStats extends WCGui {
	
	private WCMain main;
	private WCGui parent;
	
	public GuiStats(WCMain main, WCGui parent){
		
		super(9, "&3STATS");
		this.main = main;
		this.parent = parent;
		
	}
	
	@Override
	public void create(){
		
		this.addButton(0, InventoryManager.createItem("&aME", new String[] { "&2Personal stats" }, Material.INK_SACK, 1, 2));
		this.addButton(1, InventoryManager.createItem("&cOTHERS", new String[] { "&4Other people's stats" }, Material.INK_SACK, 1, 1));
		this.addButton(8, InventoryManager.createItem("&bWATERCLOSET CORE v5", new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(final Player p){
		
		switch (this.slot){
		
		case 0:
			
			WCManager.callChat(p, "!stats " + p.getName());
			p.closeInventory();
			break;
			
		case 1:
			
			this.main.wcm.displayGui(p, new GuiPlayerSelection(this.main, new Runnable(){
				
				public void run(){
					
					WCManager.callChat(p, "!stats " + GuiPlayerSelection.player.getName());
					
				}
				
			}, true, this));
			
			break;
			
		case 8:
			
			this.main.wcm.displayGui(p, this.parent);
			break;
			
		}
		
	}
	
}
