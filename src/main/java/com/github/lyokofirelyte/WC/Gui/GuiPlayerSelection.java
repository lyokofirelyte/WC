package com.github.lyokofirelyte.WC.Gui;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;

public class GuiPlayerSelection extends WCGui {
	
	public static Player player;
	
	private WCMain main;
	private WCGui parent;
	
	private Player[] players;
	private boolean close;
	private Runnable run;
	private int total;
	
	private String group;
	
	public GuiPlayerSelection(WCMain main, Runnable run, boolean close){
		
		super(54, "&4PLAYER SELECTION");
		this.main = main;
		
		this.run = run;
		this.close = close;
		this.parent = null;
		
		this.group = null;
		
	}
	
	public GuiPlayerSelection(WCMain main, Runnable run, boolean close, WCGui parent){
		
		super(54, "&4PLAYER SELECTION");
		this.main = main;
		
		this.run = run;
		this.close = close;
		this.parent = parent;
		
		this.group = null;
		
	}
	
	public GuiPlayerSelection(WCMain main, Runnable run, boolean close, String group){
		
		super(54, "&4PLAYER SELECTION");
		this.main = main;
		
		this.run = run;
		this.close = close;
		this.parent = null;
		
		this.group = group;
		
	}
	
	public GuiPlayerSelection(WCMain main, Runnable run, boolean close, WCGui parent, String group){
		
		super(54, "&4PLAYER SELECTION");
		this.main = main;
		
		this.run = run;
		this.close = close;
		this.parent = parent;
		
		this.group = group;
		
	}
	
	@Override
	public void create(){
		
		this.players = Bukkit.getOnlinePlayers();
		this.total = this.players.length;
		
		int a = 0;
		List<Player> playersL = new ArrayList<Player>();
		
		for (int i = 0; i < this.total; i++){
			
			Player p = this.players[i];
			
			if (!(this.group == null)){
				
				List<String> groups = Arrays.asList(WCVault.perms.getPlayerGroups(p));
				
				if (groups.contains(this.group)){
					
					addButton(i, InventoryManager.createItem(p.getDisplayName(), new String[] { "&f" + p.getName() }, Material.SKULL_ITEM, 1, 3));
					playersL.add(p);
					a++;
					
				}
				
			} else {
				
				addButton(i, InventoryManager.createItem(p.getDisplayName(), new String[] { "&f" + p.getName() }, Material.SKULL_ITEM, 1, 3));
				
			}
			
		}
		
		String close = "&bRETURN";
		
		if (this.parent == null){
			
			close = "&bCLOSE";
			
		}
		
		addButton(53, createItem(close, new String[] { "&b< < <" }, Enchantment.DURABILITY, 10, Material.FLINT));
		
		if (!(this.group == null)){
			
			this.players = playersL.toArray(this.players);
			this.total = a;
			
		}
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		System.out.println(slot);
		System.out.println(total);
		
		if (current.equals("click")){
			
			if (this.slot == 53){
				
				if (this.parent == null){
					
					p.closeInventory();
					
				} else {
					
					this.main.wcm.displayGui(p, this.parent);
					
				}
				
			} else if (this.slot < this.total){
				
				System.out.println(this.slot);
				
				player = players[slot];
				run.run();
				
				if (close){		
					p.closeInventory();		
				}
			}
		}
	}
}