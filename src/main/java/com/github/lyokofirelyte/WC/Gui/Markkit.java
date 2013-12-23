package com.github.lyokofirelyte.WC.Gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCGui;
import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

public class Markkit extends WCGui {
	
	public WCMain main;

	public Markkit(WCMain main){
		super(63, "&dMarkkit");
		this.main = main;	
	}
	
	List<Integer> rootW = Arrays.asList(3, 12, 21, 30, 39, 40, 41, 42, 43, 44, 45, 49, 58);
	
	@Override
	public void create(){
		
		for (int x = 0; x < 63; x++){
			if (rootW.contains(x)){
				addButton(x, createItem("", new String[] { "" }, Material.STAINED_GLASS_PANE, 1, 0));
			}
		}

		addButton(62, createItem("&cCANCEL", new String[] { "&4Clear cart" }, Material.INK_SACK, 1, 9));
		addButton(61, createItem("&aCONFIRM", new String[] { "&2Accept purchase" }, Material.INK_SACK, 1, 1));
		addButton(56, createItem("&cCANCEL", new String[] { "&4Cancel sale" }, Material.INK_SACK, 1, 5));
		addButton(55, createItem("&aCONFIRM", new String[] { "&2Accept sale" }, Material.INK_SACK, 1, 12));
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (slot){
			
		}	
	}
}