package com.github.lyokofirelyte.WC;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCUtils;

public class WCMarkkit implements Listener {

		WCMain pl;
		private HashMap<String, String> invName = new HashMap<String, String>();
		private HashMap<String, Integer> totalPrice = new HashMap<String, Integer>();
		public WCMarkkit(WCMain instance){
		pl = instance;
		}

		@EventHandler (priority = EventPriority.NORMAL)
		public void onSignCheaingeEhbvent(SignChangeEvent e) {
			
			Player p = e.getPlayer();
			
			if (p.hasPermission("wa.staff") && e.getLine(0).equalsIgnoreCase("markkit") && e.getLine(1) != null && !e.getLine(1).equals("")){
					e.setLine(0, Utils.AS("&dWC &5Markkit"));
					e.setLine(1, Utils.AS("&f" + e.getLine(1)));
			} else if (e.getLine(0).equalsIgnoreCase("markkit")){
				e.setLine(0, Utils.AS("&4INVALID!"));
				e.setLine(1, Utils.AS("&cWE DIDN'T"));
				e.setLine(2, Utils.AS("&cLISTEN! D:"));
			}
		}
		
		@EventHandler
		public void onClick(InventoryClickEvent e){
			Player p = (Player) e.getWhoClicked();
			System.out.println(e.getInventory().getName().equals("§dMarkkit"));
			System.out.println(e.getInventory().getName());
			System.out.println(e.getRawSlot());
			if(e.getInventory().getName().equals("§dMarkkit")){
				e.setCancelled(true);

				switch(e.getRawSlot()){
				
				//the buying area aka shoppig cart
				case 6: case 7: case 8: case 15: case 16: case 17: case 24: case 25: case 26: case 33: case 34: case 35: case 42: case 43: case 44:
					System.out.println(e.getInventory().getItem(e.getRawSlot()) != null);
					if(e.getInventory().getItem(e.getRawSlot()) != null){
						e.getInventory().setItem(e.getRawSlot(), new ItemStack(Material.AIR));
					}
					break;
				
				case 51:
					
					for(int i = 0; i< 60; i++){
						if(i == 6 || i == 7 || i == 8 || i == 15 || i == 16 || i == 17 || i == 24 || i == 25 || i == 26 || i == 33 || i == 34 || i == 35 || i == 42 || i == 43 || i == 44){
							e.getInventory().setItem(i, new ItemStack(Material.AIR));
						}
					}
					
					break;
					
				case 52:
					for(int i = 0; i< 60; i++){
						if(i == 6 || i == 7 || i == 8 || i == 15 || i == 16 || i == 17 || i == 24 || i == 25 || i == 26 || i == 33 || i == 34 || i == 35 || i == 42 || i == 43 || i == 44){
							String name = invName.get(e.getWhoClicked().getName());
							if(e.getInventory().getItem(i) != null){
								int price = pl.markkitYaml.getInt("Items." + name + "." + e.getInventory().getItem(i).getAmount() + ".buyprice");
								System.out.println(pl.markkitYaml.getInt("Items." + name + "." + e.getInventory().getItem(i).getAmount() + ".buyprice"));
								if(totalPrice.get(e.getWhoClicked().getName()) == null){
									totalPrice.put(e.getWhoClicked().getName(), 0);
								}
								totalPrice.put(e.getWhoClicked().getName(), totalPrice.get(e.getWhoClicked().getName()) + price);
							}
								if(i == 44){
									WCPlayer wcp = new WCPlayer(e.getWhoClicked().getName());
									
									wcp.setBalance(wcp.getBalance() - totalPrice.get(e.getWhoClicked().getName()));
									WCUtils.s((Player)e.getWhoClicked(), totalPrice.get(e.getWhoClicked().getName()) + " was taken from your account!");
									totalPrice.put(e.getWhoClicked().getName(), null);
									for(int x = 0; x< 60; x++){
										if(x == 6 || x == 7 || x == 8 || x == 15 || x == 16 || x == 17 || x == 24 || x == 25 || x == 26 || x == 33 || x == 34 || x == 35 || x == 42 || x == 43 || x == 44){
											if(e.getInventory().getItem(x) != null){
												p.getInventory().addItem(e.getInventory().getItem(x));
											}
											e.getInventory().setItem(x, new ItemStack(Material.AIR));
										}
									}
									
								}
						}
					}
					break;
					
				//the item you have to click
				case 4: case 13: case 22: case 31: case 40: case 49:
					ItemStack clicked = e.getInventory().getItem(e.getRawSlot());
					if(e.getInventory().getItem(6) == null){
						e.getInventory().setItem(6, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(7) == null){
						e.getInventory().setItem(7, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(8) == null){
						e.getInventory().setItem(8, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(15) == null){
						e.getInventory().setItem(15, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(16) == null){
						e.getInventory().setItem(16, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(17) == null){
						e.getInventory().setItem(17, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(24) == null){
						e.getInventory().setItem(24, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(25) == null){
						e.getInventory().setItem(25, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(26) == null){
						e.getInventory().setItem(26, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(33) == null){
						e.getInventory().setItem(33, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(34) == null){
						e.getInventory().setItem(34, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(35) == null){
						e.getInventory().setItem(35, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(42) == null){
						e.getInventory().setItem(42, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(43) == null){
						e.getInventory().setItem(43, clicked);
						p.updateInventory();
					}else if(e.getInventory().getItem(44) == null){
						e.getInventory().setItem(44, clicked);
						p.updateInventory();
					}
					break;
				}
			}
		}
		@EventHandler
		public void onClickyTheSign(PlayerInteractEvent e) {
			
			Inventory inv = Bukkit.createInventory(null, 54, Utils.AS("&dMarkkit"));

			if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
				
				if (e.getClickedBlock().getState() instanceof Sign){
					Sign sign = (Sign) e.getClickedBlock().getState();
					if (sign.getLine(0).equals(Utils.AS("&dWC &5Markkit"))){
						
						if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() != Material.AIR){
							WCUtils.s(e.getPlayer(), "You must use your hand to activate the sign.");
							return;
						}
						String name = sign.getLine(1).replace("§f", "");
						invName.put(e.getPlayer().getName(), name);
						Material mat = Material.getMaterial(pl.markkitYaml.getInt("Items." + name + ".ID"));
						short damage = (short) pl.markkitYaml.getInt("Items." + name + ".Damage");
						
						for(int i = 0; i<51; i++){
							if(i == 3 || i == 12 || i == 21 || i == 30 || i == 39 || i == 48 || i == 5 | i == 14 || i == 23 || i == 32 || i == 41 | i == 50){
								ItemStack divider = new ItemStack(Material.THIN_GLASS);
								ItemMeta divide = divider.getItemMeta();
								divide.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Separator");
								divider.setItemMeta(divide);
								inv.setItem(i, divider);
							}
						}
						
						ItemStack redCancel = new ItemStack(Material.WOOL, 1, (short) 14);
						ItemStack greenAccept = new ItemStack(Material.WOOL, 1, (short) 5);
						ItemMeta cancel = redCancel.getItemMeta();
						ItemMeta accept = greenAccept.getItemMeta();
						cancel.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Cancel!");
						redCancel.setItemMeta(cancel);
						accept.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Accept!");
						greenAccept.setItemMeta(accept);
						
						
						inv.setItem(47, redCancel);
						inv.setItem(51, redCancel);
						inv.setItem(46, greenAccept);
						inv.setItem(52, greenAccept);
						
						if(pl.markkitYaml.contains("Items." + name + "." + 64)){
							ItemStack item = new ItemStack(mat, 64, damage);
							ItemMeta itemMeta = item.getItemMeta();
							itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "Buy", pl.markkitYaml.getString("Items." + name + ".64.buyprice"), ChatColor.RED + "Sell", pl.markkitYaml.getString("Items." + name + ".64.sellprice")));
							item.setItemMeta(itemMeta);
							inv.setItem(4, item);
						}
						
						if(pl.markkitYaml.contains("Items." + name + "." + 32)){
							ItemStack item = new ItemStack(mat, 32, damage);
							ItemMeta itemMeta = item.getItemMeta();
							itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "Buy", pl.markkitYaml.getString("Items." + name + ".32.buyprice"), ChatColor.RED + "Sell", pl.markkitYaml.getString("Items." + name + ".32.sellprice")));
							item.setItemMeta(itemMeta);
							inv.setItem(13, item);

						}
						
						if(pl.markkitYaml.contains("Items." + name + "." + 16)){
							ItemStack item = new ItemStack(mat, 16, damage);
							ItemMeta itemMeta = item.getItemMeta();
							itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "Buy", pl.markkitYaml.getString("Items." + name + ".16.buyprice"), ChatColor.RED + "Sell", pl.markkitYaml.getString("Items." + name + ".16.sellprice")));
							item.setItemMeta(itemMeta);
							inv.setItem(22, item);

						}
						
						if(pl.markkitYaml.contains("Items." + name + "." + 8)){
							ItemStack item = new ItemStack(mat, 8, damage);
							ItemMeta itemMeta = item.getItemMeta();
							itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "Buy", pl.markkitYaml.getString("Items." + name + ".8.buyprice"), ChatColor.RED + "Sell", pl.markkitYaml.getString("Items." + name + ".8.sellprice")));
							item.setItemMeta(itemMeta);
							inv.setItem(31, item);

						}
						
						if(pl.markkitYaml.contains("Items." + name + "." + 1)){
							ItemStack item = new ItemStack(mat, 1, damage);
							ItemMeta itemMeta = item.getItemMeta();
							itemMeta.setLore(Arrays.asList(ChatColor.GREEN + "Buy", pl.markkitYaml.getString("Items." + name + ".1.buyprice"), ChatColor.RED + "Sell", pl.markkitYaml.getString("Items." + name + ".1.sellprice")));
							item.setItemMeta(itemMeta);
							inv.setItem(40, item);
						}
						
						e.getPlayer().openInventory(inv);
					}
				}
			}
		}
		
		@EventHandler
		public void onClose(InventoryCloseEvent e){
			
		}
}