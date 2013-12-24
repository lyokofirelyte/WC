package com.github.lyokofirelyte.WC.Gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;
import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

public class GuiToggles extends WCGui {
	
	private WCMain main;
	private WCGui parent;
	
	public GuiToggles(WCMain main, WCGui parent){
		
		super(18, "&4TOGGLES");
		this.main = main;
		this.parent = parent;
		
	}
	
	@Override
	public void create(){
		
		this.addButton(0, createItem("&aHOME SOUNDS", new String[] { "&2/home sounds" }, Material.WORKBENCH));
		this.addButton(1, createItem("&eSIDEBOARD COORDS", new String[] { "&9Toggle name / coords", "&9on sideboard" }, Material.REDSTONE));
		this.addButton(2, createItem("&eSIDEBOARD", new String[] { "&6The scoreboard" }, Material.GLOWSTONE));
		this.addButton(3, createItem("&bPOKES", new String[] { "&9Toggle pokes" }, Material.STICK));
		this.addButton(4, createItem("&4PVP", new String[] { "&cToggle PVP Mode" }, Material.DIAMOND_SWORD));
		this.addButton(5, createItem("&dFIREWORKS", new String[] { "&8Toggle paragon fireworks" }, Material.FIREWORK));
		this.addButton(6, createItem("&3EMOTES", new String[] { "&aToggle auto-emotes", "on chat" }, Material.CAKE));
		this.addButton(7, createItem("&2ROOT SHORTCUT", new String[] { "&3Shift + left click", "&3for root menu" }, Material.ANVIL));
		this.addButton(8, createItem("&cNAME PLATE", new String[] { "&bToggle alliance nameplate" }, Material.NAME_TAG));
		this.addButton(13, createItem("&bWATERCLOSET CORE v5", new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (this.slot){
		
		case 0:
			
			p.performCommand("wc homesounds");
			break;
			
		case 1:
			
			p.performCommand("wc sideboardcoords");
			break;
			
		case 2:
			
			p.performCommand("wc sidebar");
			break;
			
		case 3:
			
			p.performCommand("wc poke");
			break;
			
		case 4:
			
			p.performCommand("wc pvp");
			break;
			
		case 5:
			
			p.performCommand("wc fwtoggle");
			break;
			
		case 6:
			
			p.performCommand("wc emotes");
			break;
			
		case 7:
			
			p.performCommand("wc rootshortcut");
			break;
			
		case 8:
			
			p.performCommand("wc nameplate");
			break;
			
		case 13:
			
			this.main.wcm.displayGui(p, this.parent);
			break;
			
		}
		
	}
	
}
