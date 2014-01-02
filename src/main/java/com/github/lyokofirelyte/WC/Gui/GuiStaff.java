package com.github.lyokofirelyte.WC.Gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

public class GuiStaff extends WCGui {
	
	private WCMain main;
	private WCGui parent;
	
	private Player p;
	
	public GuiStaff(WCMain main, Player p, WCGui parent){
		
		super(9, p.getDisplayName());
		this.main = main;
		this.parent = parent;
		
		this.p = p;
		
	}
	
	@Override
	public void create(){
		
		this.addButton(0, createItem("&4BANNING", new String[] { "&3View banning options" }, Material.STAINED_CLAY, 1, 6));
		this.addButton(1, createItem("&eWARNINGS", new String[] { "&3View warnings" }, Material.STAINED_CLAY));
		this.addButton(2, createItem("&eTELEPORT", new String[] { "&3Teleport to player" }, Material.STAINED_CLAY));
		this.addButton(3, createItem("&eSPECTATE", new String[] { "&3Spectate player" }, Material.STAINED_CLAY));
		this.addButton(4, createItem("&3INVENTORY", new String[] { "&3View inventory" }, Material.CHEST));
		this.addButton(5, createItem("&3FROZEN INVENTORY", new String[] { "&3View frozen inventory" }, Material.REDSTONE));
		this.addButton(8, createItem("&bWATERCLOSET CORE v5", new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (this.slot){
		
		case 8:
			
			this.main.wcm.displayGui(p, this.parent);
			break;
			
		}
		
	}
	
}
