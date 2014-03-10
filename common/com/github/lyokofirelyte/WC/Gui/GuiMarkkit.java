package com.github.lyokofirelyte.WC.Gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCGui;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCUtils;

import static com.github.lyokofirelyte.WCAPI.Manager.InventoryManager.createItem;

public class GuiMarkkit extends WCGui {
	
	public WCMain main;

	public GuiMarkkit(WCMain main){
		super(54, "&dMarkkit");
		this.main = main;	
	}
	
	List<Integer> rootW = Arrays.asList(3, 12, 21, 30, 39, 48);
	List<Integer> rootA = Arrays.asList(31, 32, 33, 34, 35);
	List<Integer> sellSlots = Arrays.asList(0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 45);
	List<Integer> buySlots = Arrays.asList(4, 5, 6, 7, 8, 13, 14, 15, 16, 17, 22, 23, 24, 25, 26);
	List<Integer> buyCart = Arrays.asList(51, 50, 49, 44, 43, 42, 41, 40);
	
	@Override
	public void create(){
		
		for (int x = 0; x < 54; x++){
			if (rootW.contains(x)){
				addButton(x, createItem("&2<-- sell", new String[] { "&3--> buy" }, Material.STAINED_GLASS_PANE, 1, 0));
			}
			if (rootA.contains(x)){
				addButton(x, createItem("&2^ buy ^", new String[] { "&3v checkout cart v" }, Material.STAINED_GLASS_PANE, 1, 0));
			}
		}  

		addButton(53, createItem("&cCANCEL", new String[] { "&4Clear cart" }, Material.WOOL, 1, 14));
		addButton(52, createItem("&aCONFIRM", new String[] { "&2Accept purchase" }, Material.WOOL, 1, 13));
		addButton(47, createItem("&cCANCEL", new String[] { "&4Cancel sale" }, Material.WOOL, 1, 14));
		addButton(46, createItem("&aCONFIRM", new String[] { "&2Accept sale" }, Material.WOOL, 1, 13));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(Player p){
		
		int z = 0; //
		int a = 0; // used to stop loops without return;
		int b = 0; //
		int sweep = 0;
		int sellPrice = 0;
		int totalPrice = 0;
		
		if (main.wcm.getWCPlayer(p.getName()).getMarkkitEditMode()){
			if (current.equals("click")){
				click.setCancelled(false);
			} else if (current.equals("drag")){
				drag.setCancelled(false);
			} else if (current.equals("move")){
				move.setCancelled(false);
			} else if (current.equals("interact")){
				interact.setCancelled(false);
			}
			return;
		}
		
		if (sellSlots.contains(slot)){
			if (item != null && item.getType() != Material.AIR){
				for (int i : sellSlots){
					if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR && inv.getItem(i).getTypeId() == item.getTypeId() && inv.getItem(i).getAmount() == item.getAmount()){
						inv.setItem(i, new ItemStack(Material.AIR));
						p.getInventory().addItem(item);
						return;
					}
				}
			}
		}
		
		if (buySlots.contains(slot) && item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasLore()){
			for (int i : buyCart){
				if (z == 0 && (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR))){
					inv.setItem(i, item);
					z++;
				}
			}
		}
		
		for (int i : buyCart){
			if (inv.getItem(i) != null && !inv.getItem(i).getType().equals(Material.AIR)){
				int price = Integer.parseInt(inv.getItem(i).getItemMeta().getLore().get(1));
				totalPrice = totalPrice + price;
			}
			ItemMeta im = inv.getItem(52).getItemMeta();
			im.setLore(new ArrayList<String>(Arrays.asList(Utils.AS("&4" + totalPrice))));
			inv.getItem(52).setItemMeta(im);
		}
		
		for (int ii : sellSlots){
			if (inv.getItem(ii) != null && inv.getItem(ii).getType() != Material.AIR){
				for (int i : buySlots){
					if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR){
						if (inv.getItem(i).getTypeId() == inv.getItem(ii).getTypeId() && inv.getItem(i).getAmount() == inv.getItem(ii).getAmount()){
							int price = Integer.parseInt(inv.getItem(i).getItemMeta().getLore().get(3));
							sellPrice = sellPrice + price;
							ItemMeta im = inv.getItem(46).getItemMeta();
							im.setLore(new ArrayList<String>(Arrays.asList(Utils.AS("&2" + sellPrice))));
							inv.getItem(46).setItemMeta(im);
						}
					}
				}
			}
		}
		
		if (item != null && !item.hasItemMeta() && a == 0){
			for (int i : buySlots){
				if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR){
					if (inv.getItem(i).getTypeId() == item.getTypeId() && inv.getItem(i).getAmount() == item.getAmount()){
						for (int ii : sellSlots){
							if ((inv.getItem(ii) == null || inv.getItem(ii).getType() == Material.AIR) && a == 0){
								inv.setItem(ii, item);
								for (ItemStack iii : p.getInventory().getContents()){
									if (iii != null && iii.getType() != Material.AIR && iii.getTypeId() == item.getTypeId() && iii.getAmount() == item.getAmount() && b == 0){
										p.getInventory().setItem(sweep, new ItemStack(Material.AIR));
										b++;
									}
									sweep++;
								}
								int price = Integer.parseInt(inv.getItem(i).getItemMeta().getLore().get(3));
								sellPrice = sellPrice + price;
								ItemMeta im = inv.getItem(46).getItemMeta();
								im.setLore(new ArrayList<String>(Arrays.asList(Utils.AS("&2" + sellPrice))));
								inv.getItem(46).setItemMeta(im);
								a++;
							}
						}
					}
				}
			}
		}
		
		switch (slot){
		
			case 46:
			
				for (int i : sellSlots){
					inv.setItem(i, new ItemStack(Material.AIR));
				}
				ItemMeta im = inv.getItem(46).getItemMeta();
				im.setLore(new ArrayList<String>(Arrays.asList(Utils.AS("&2Accept sale"))));
				inv.getItem(46).setItemMeta(im);
				WCPlayer wcp = main.wcm.getWCPlayer(p.getName());
				wcp.setBalance(wcp.getBalance() + sellPrice);
				main.wcm.updatePlayerMap(p.getName(), wcp);
				WCUtils.s(p, "Sold for " + sellPrice);
				
				break;
				
			case 47:
				
				for (int i : sellSlots){
					if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR){
						p.getInventory().addItem(inv.getItem(i));
						inv.setItem(i, new ItemStack(Material.AIR));
					}
				}
				im = inv.getItem(46).getItemMeta();
				im.setLore(new ArrayList<String>(Arrays.asList(Utils.AS("&2Accept sale"))));
				inv.getItem(46).setItemMeta(im);
				sellPrice = 0;
				
				break;
			
			case 53: 

				for (int i : buyCart){
					inv.setItem(i, new ItemStack(Material.AIR));
				}
				
				break;
			
			case 52: 

				int x = 0;
				int y = 0;
				
				for (ItemStack i : p.getInventory().getContents()){
					if (i == null || i.getType() == Material.AIR){
						y++;
					}
				}
				
				if (y < x){
					WCUtils.s(p, "You don't have enough room!");
					return;
				}
				
				if (totalPrice > 0){
					wcp = main.wcm.getWCPlayer(p.getName());
					if (wcp.getBalance() >= totalPrice){
						for (int i : buyCart){
							if (inv.getItem(i) != null && !inv.getItem(i).getType().equals(Material.AIR)){
								im = inv.getItem(i).getItemMeta();
								im.setLore(new ArrayList<String>());
								im.setDisplayName(null);
								inv.getItem(i).setItemMeta(im);
								p.getInventory().addItem(inv.getItem(i));
							}
						}
						for (int i : buyCart){
							inv.setItem(i, new ItemStack(Material.AIR));
						}
						wcp.setBalance(wcp.getBalance() - totalPrice);
						main.wcm.updatePlayerMap(p.getName(), wcp);
					} else {
						WCUtils.s(p, "You lack the funds!");
						return;
					}
				}
		}	
		
		p.updateInventory();
	}
}