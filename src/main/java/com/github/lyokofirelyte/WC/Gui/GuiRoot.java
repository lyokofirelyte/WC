package com.github.lyokofirelyte.WC.Gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

public class GuiRoot extends WCGui {
	
	public WCMain main;
	
	private List<Integer> rootW;
	private List<Integer> rootC;

	public GuiRoot(WCMain main){
		
		super(54, "&dWATERCLOSET CORE v5");
		this.main = main;
		
		this.rootW = Arrays.asList(1, 3, 10, 11, 12, 19, 20, 21);
		this.rootC = Arrays.asList(5, 6, 7, 14, 23, 24, 25);
		
	}
	
	@Override
	public void create(){
		
		// Create the WC logo.
		
		for (int x = 0; x < 54; x++){
			if (rootW.contains(x)){
				addButton(x, createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 2));
			} else if (rootC.contains(x)){
				addButton(x, createItem("&aWCV5", new String[] { "&3WCV5" }, Material.STAINED_GLASS, 1, 10));
			}
		}
		
		// Now for the actual buttons.
		
		addButton(37, createItem("&aCHAT", new String[] { "&3Chat Options" }, Material.INK_SACK, 1, 9));
		addButton(38, createItem("&4TOGGLES", new String[] { "&cToggle Options" }, Material.INK_SACK, 1, 1));
		addButton(39, createItem("&cSTATS", new String[] { "&eStat Viewer" }, Material.INK_SACK, 1, 5));
		addButton(41, createItem("&aTHE CLOSET", new String[] { "&3General Store Trading" }, Material.INK_SACK, 1, 12));
		addButton(42, createItem("&3ALLIANCES", new String[] { "&aAlliance controls" }, Material.INK_SACK, 1, 2));
		addButton(43, createItem("&4INCINERATOR", new String[] { "&cThrow away items" }, Material.INK_SACK, 1, 10));
		addButton(46, createItem("&5STAFF SECTION", new String[] { "&dStaff only" }, Material.INK_SACK, 1, 8));
		addButton(47, createItem("&dPARAGON SHOPPE", new String[] { "&5Paragon rewards" }, Material.INK_SACK, 1, 6));
		addButton(48, createItem("&bPATROLS", new String[] { "&dPatrol Menu" }, Material.INK_SACK, 1, 11));
		addButton(50, createItem("&1QUICK COMMANDS", new String[] { "&5Command Menu" }, Material.INK_SACK, 1, 14));
		addButton(51, createItem("&2CREATIVE WORLD", new String[] { "&aWarp to creative" }, Material.INK_SACK, 1, 13));
		addButton(52, createItem("&eLOGOFF", new String[] { "&6Leave the game" }, Material.INK_SACK));
		addButton(31, createItem("&bCLOSE", new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
	
		switch (this.slot){
		
		case 31:
			
			p.closeInventory();
			break;
			
		case 37:
			
			main.wcm.displayGui(p, new GuiChat(this.main));
			break;
			
		}
		
	}
}