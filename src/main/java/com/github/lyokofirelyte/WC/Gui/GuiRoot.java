package com.github.lyokofirelyte.WC.Gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;

public class GuiRoot extends WCGui {
	
	public WCMain main;

	public GuiRoot(WCMain main){
		
		super(54, "&dWATERCLOSET CORE v5");
		this.main = main;
		
	}
	
	@Override
	public void create(){
		
		// Create the WC logo.
		
		this.addButton(1, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 2));
		this.addButton(3, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 2));
		this.addButton(10, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 2));
		this.addButton(11, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 2));
		this.addButton(12, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 2));
		this.addButton(19, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 2));
		this.addButton(20, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 2));
		this.addButton(21, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 2));
		this.addButton(5, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 10));
		this.addButton(6, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 10));
		this.addButton(7, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 10));
		this.addButton(14, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 10));
		this.addButton(23, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 10));
		this.addButton(24, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 10));
		this.addButton(25, InventoryManager.createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 10));
		
		// Now for the actual buttons.
		
		this.addButton(37, InventoryManager.createItem("&aCHAT", new String[] { "&3Chat Options" }, Material.INK_SACK, 1, 9));
		this.addButton(38, InventoryManager.createItem("&4TOGGLES", new String[] { "&cToggle Options" }, Material.INK_SACK, 1, 1));
		this.addButton(39, InventoryManager.createItem("&cSTATS", new String[] { "&eStat Viewer" }, Material.INK_SACK, 1, 5));
		this.addButton(41, InventoryManager.createItem("&aTHE CLOSET", new String[] { "&3General Store Trading" }, Material.INK_SACK, 1, 12));
		this.addButton(42, InventoryManager.createItem("&3ALLIANCES", new String[] { "&aAlliance controls" }, Material.INK_SACK, 1, 2));
		this.addButton(43, InventoryManager.createItem("&4INCINERATOR", new String[] { "&cThrow away items" }, Material.INK_SACK, 1, 10));
		this.addButton(46, InventoryManager.createItem("&5STAFF SECTION", new String[] { "&dStaff only" }, Material.INK_SACK, 1, 8));
		this.addButton(47, InventoryManager.createItem("&dPARAGON SHOPPE", new String[] { "&5Paragon rewards" }, Material.INK_SACK, 1, 6));
		this.addButton(48, InventoryManager.createItem("&bPATROLS", new String[] { "&dPatrol Menu" }, Material.INK_SACK, 1, 11));
		this.addButton(50, InventoryManager.createItem("&1QUICK COMMANDS", new String[] { "&5Command Menu" }, Material.INK_SACK, 1, 14));
		this.addButton(51, InventoryManager.createItem("&2CREATIVE WORLD", new String[] { "&aWarp to creative" }, Material.INK_SACK, 1, 13));
		this.addButton(52, InventoryManager.createItem("&eLOGOFF", new String[] { "&6Leave the game" }, Material.INK_SACK));
		this.addButton(31, InventoryManager.createItem("&bCLOSE", new String[] { "&b< < <" }, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (this.slot){
		
		
			
		}
		
	}

}
