package com.github.lyokofirelyte.WC.Gui;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;

public class GuiSpleef extends WCGui {

	@SuppressWarnings("unused")
	private WCMain main;

	public GuiSpleef(WCMain main){
		
		super(9, "&dSpleef");
		this.main = main;
	}
	
	@Override
	public void create(){

		addButton(0, createItem("&aCHAT", new String[] { "&3Chat Options" }, Material.INK_SACK, 1, 9));

	}
	
	@Override
	public void actionPerformed(Player p){
	
		switch (slot){
			
		}
	}
}
