package com.github.lyokofirelyte.WC.Gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;

public class GuiChat extends WCGui {
	
	private WCMain main;
	private WCGui parent;
	
	public GuiChat(WCMain main, WCGui parent){
		
		super(9, "&4CHAT");
		this.main = main;
		this.parent = parent;
		
	}
	
	@Override
	public void create(){
		
		this.addButton(0, InventoryManager.createItem("&bGLOBAL COLOUR", new String[] { "&9Change global colour" }, Material.INK_SACK, 1, 14));
		this.addButton(1, InventoryManager.createItem("&bPM COLOUR", new String[] { "&9Change pm colour" }, Material.INK_SACK, 1, 10));
		this.addButton(2, InventoryManager.createItem("&bALLIANCE COLOUR", new String[] { "&9Change alliance colour" }, Material.INK_SACK, 1, 11));
		this.addButton(3, InventoryManager.createItem("&bTIME CODES", new String[] { "&9Toggle chat timecodes" }, Material.INK_SACK, 1, 12));
		this.addButton(8, InventoryManager.createItem("&bWATERCLOSET CORE v5", new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (this.slot){
		
		case 0:
			
			this.main.wcm.displayGui(p, new GuiColourSelection(this.main, "wc globalcolor", this));
			break;
			
		case 1:
			
			this.main.wcm.displayGui(p, new GuiColourSelection(this.main, "wc pmcolor", this));
			break;
			
		case 2:
			
			this.main.wcm.displayGui(p, new GuiColourSelection(this.main, "waa chat color", this));
			break;
			
		case 3:
			
			p.performCommand("wc timecode");
			break;
			
		case 8:
			
			this.main.wcm.displayGui(p, this.parent);
			break;
			
		}
		
	}
	
}