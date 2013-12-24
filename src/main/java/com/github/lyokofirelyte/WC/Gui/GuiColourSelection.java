package com.github.lyokofirelyte.WC.Gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;
import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

public class GuiColourSelection extends WCGui {
	
	private WCMain main;
	
	private WCGui parent;
	private String command;
	
	public GuiColourSelection(WCMain main, String command, WCGui parent){
		
		super(18, "&3COLOUR SELECTION");
		this.main = main;
		this.parent = parent;
		this.command = command;
		
	}
	
	@Override
	public void create(){
		
		addButton(0, createItem("&fWHITE", new String[] { "&ff" }, Material.STAINED_CLAY));
		addButton(1, createItem("&6ORANGE", new String[] { "&66" }, Material.STAINED_CLAY, 1, 1));
		addButton(2, createItem("&dPINK", new String[] { "&dd" }, Material.STAINED_CLAY, 1, 2));
		addButton(3, createItem("&9LIGHT BLUE", new String[] { "&99" }, Material.STAINED_CLAY, 1, 3));
		addButton(4, createItem("&eYELLOW", new String[] { "&ee" }, Material.STAINED_CLAY, 1, 4));
		addButton(5, createItem("&aLIGHT GREEN", new String[] { "&aa" }, Material.STAINED_CLAY, 1, 5));
		addButton(6, createItem("&cLIGHT RED", new String[] { "&cc" }, Material.STAINED_CLAY, 1, 6));
		addButton(7, createItem("&1DARK BLUE", new String[] { "&11" }, Material.STAINED_CLAY, 1, 7));
		addButton(16, createItem("&7LIGHT GRAY", new String[] { "&77" }, Material.STAINED_CLAY, 1, 8));
		addButton(9, createItem("&8GRAY", new String[] { "&88" }, Material.STAINED_CLAY, 1, 9));
		addButton(10, createItem("&5PURPLE", new String[] { "&55" }, Material.STAINED_CLAY, 1, 10));
		addButton(11, createItem("&3TEAL BLUE", new String[] { "&33" }, Material.STAINED_CLAY, 1, 11));
		addButton(12, createItem("&2DARK GREEN", new String[] { "&22" }, Material.STAINED_CLAY, 1, 13));
		addButton(13, createItem("&4DARK RED", new String[] { "&44" }, Material.STAINED_CLAY, 1, 14));
		addButton(14, createItem("&0BLACK", new String[] { "&00" }, Material.STAINED_CLAY, 1, 15));
		addButton(15, createItem("&bLIGHT BLUE", new String[] { "&bb" }, Material.CLAY));
		addButton(17, createItem("&bRETURN", new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (slot){
		
		default:
			
			p.performCommand(command + " " + item.getItemMeta().getLore().get(0).charAt(1));
			main.wcm.displayGui(p, parent);
			break;
		
		case 8:
			
			// Nothing.
			break;
		
		case 17:
			
			main.wcm.displayGui(p, parent);
			break;
		}
	}
}