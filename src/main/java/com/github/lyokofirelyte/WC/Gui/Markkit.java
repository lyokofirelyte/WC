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
		super(54, "&dMarkkit");
		this.main = main;	
	}
	
	List<Integer> rootW = Arrays.asList(3, 12, 21, 30, 31, 32, 33, 34, 35, 39, 48);
	
	@Override
	public void create(){
		
		for (int x = 0; x < 54; x++){
			if (rootW.contains(x)){
				addButton(x, createItem("", new String[] { "" }, Material.STAINED_GLASS_PANE, 1, 0));
			}
		}  

		addButton(53, createItem("&cCANCEL", new String[] { "&4Clear cart" }, Material.WOOL, 1, 14));
		addButton(52, createItem("&aCONFIRM", new String[] { "&2Accept purchase" }, Material.WOOL, 1, 13));
		addButton(47, createItem("&cCANCEL", new String[] { "&4Cancel sale" }, Material.WOOL, 1, 14));
		addButton(46, createItem("&aCONFIRM", new String[] { "&2Accept sale" }, Material.WOOL, 1, 13));
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (slot){
			
		}	
	}
}