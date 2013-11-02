package com.github.lyokofirelyte.WC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WCMenus implements Listener, CommandExecutor {
	
	WCMain plugin;
	public WCMenus(WCMain instance){
	this.plugin = instance;
	}
	
	public List <Material> mats = new ArrayList<Material>();
	public static List <String> menu = new ArrayList<String>();
	public static Map <String, String> staffTools = new HashMap<>();
	public static List <String> lores = new ArrayList<String>();
	public static Map <String, Inventory> invs = new HashMap<String, Inventory>();
	public static Map <String, String> playerSelection = new HashMap<>();
	public static Map <String, String> colorSelection = new HashMap<>();
	public static Map <String, String> openInvs = new HashMap<>();
	public static Map <String, String> actions = new HashMap<>();
	public static Map <String, String> banActions = new HashMap<>();
	
	public Inventory inv;
	Boolean poke = true;
	Boolean setup = false;
	String sel;
	public String colorType;
	Player p;
	
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("root")){
			
			colorSelection.put(((Player)sender).getName(), "red");
			
			if (setup == false){
				setup = true;
				setUp();
			
			}
			
			((Player)sender).openInventory(invs.get("mainMenu"));		
		}
		
		return true;
	}
	
	public void setBanOption(String name, String option){
		playerSelection.put(p.getName(), playerSelection.get(p.getName()) + " " + option);
	}
	
	public void setUp(){
		
		// Menus that accept input
		menu.add("WATERCLOSET CORE v4");
		menu.add("MEMBERS OPTIONS");
		menu.add("CHAT");
		menu.add("COLOR SELECTION");
		menu.add("TIME CODES");
		menu.add("STAFF OPTIONS");
		menu.add("CHAT TIMESTAMPS");
		menu.add("STATS");
		menu.add("STAT VIEWER");
		menu.add("TOGGLES");
		menu.add("*CHOOSE BAN TYPE");
		menu.add("*CHOOSE REASON");
		menu.add("*SELECT PROOF");
		menu.add("*CONFIRM THIS BAN?");

		// Inventory references, on hover name -> inv
		openInvs.put("STAFF SECTION", "staffMenu");
		openInvs.put("CHAT", "chatMenu");
		openInvs.put("STATS", "statsMenu");
		openInvs.put("TIME CODES", "timeCodeMenu");
		openInvs.put("TOGGLES", "toggleMenu");
		openInvs.put("ALLIANCE COLOR", "colorMenu");
		openInvs.put("GLOBAL COLOR", "colorMenu");
		openInvs.put("PM COLOR", "colorMenu");
		openInvs.put("WATERCLOSET CORE v4", "mainMenu");
		openInvs.put("OTHERS", "statViewMenu");
		openInvs.put("NO!", "mainMenu");
		
		// on hover ban -> inv open
		openInvs.put("BANNING", "banTypeMenu");
		openInvs.put("LOCAL", "mainMenu");
		openInvs.put("GLOBAL", "banReasonMenu");
		
		openInvs.put("MOD CLIENT (XRAY)", "banProofMenu");
		openInvs.put("MOD CLIENT (JUMP)", "banProofMenu");
		openInvs.put("MOD CLIENT (FLY)", "banProofMenu");
		openInvs.put("MOD CLIENT (SPEED)", "banProofMenu");
		openInvs.put("RACISM", "banProofMenu");
		openInvs.put("GRIEFING", "banProofMenu");
		openInvs.put("HOMOPHOBIA", "banProofMenu");
		openInvs.put("SHOCK SITE", "banProofMenu");
		openInvs.put("NAZI", "banProofMenu");
		openInvs.put("PORN LINKS", "banProofMenu");
		
		openInvs.put("PRISM / SCREENSHOT", "banConfirmMenu");
		openInvs.put("PRISM / VIDEO", "banConfirmMenu");
		openInvs.put("VIDEO", "banConfirmMenu");
		openInvs.put("SCREENSHOT", "banConfirmMenu");
		
		// on hover -> action
		actions.put("TOGGLE", "wc timecode");
		actions.put("HOME SOUNDS", "wc homesounds");
		actions.put("THROW ITEMS", "wc throw");
		actions.put("SIDEBOARD", "wc sidebar");
		actions.put("PVP", "wc pvp");
		actions.put("POKES", "wc poke");
		actions.put("FIREWORKS", "wc fwtoggle");
		actions.put("ME", "wcstats");
		
		// store invs in hashmap
		inv = Bukkit.createInventory(null, 9, "§c*CONFIRM THIS BAN?");
		inv = addToInv(Material.INK_SACK, "§aYES!", 3, "§2Fire teh lazorz!", 2, inv);
		inv = addToInv(Material.INK_SACK, "§aNO!", 5, "§2Meh.", 3, inv);
		invs.put("banConfirmMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§c*CHOOSE BAN TYPE");
		inv = addToInv(Material.INK_SACK, "§aLOCAL", 3, "§2Local-type ban", 2, inv);
		inv = addToInv(Material.INK_SACK, "§aGLOBAL", 5, "§2Global-type ban", 5, inv);
		invs.put("banTypeMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§c*SELECT PROOF");
		inv = addToInv(Material.INK_SACK, "§aPRISM / SCREENSHOT", 0, "§2Prism logs and pictures", 2, inv);
		inv = addToInv(Material.INK_SACK, "§aPRISM / VIDEO", 3, "§2Prism logs and video", 5, inv);
		inv = addToInv(Material.INK_SACK, "§aVIDEO", 5, "§2Only video", 8, inv);
		inv = addToInv(Material.INK_SACK, "§aSCREENSHOT", 8, "§2Only screenshots", 12, inv);
		invs.put("banProofMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§c*CHOOSE REASON");
		inv = addToInv(Material.STAINED_CLAY, "§aMOD CLIENT (XRAY)", 0, "§2X-ray hax", 0, inv);
		inv = addToInv(Material.STAINED_CLAY, "§aMOD CLIENT (JUMP)", 1, "§2Jump hax", 1, inv);
		inv = addToInv(Material.STAINED_CLAY, "§aMOD CLIENT (FLY)", 2, "§2Fly hax", 2, inv);
		inv = addToInv(Material.STAINED_CLAY, "§aMOD CLIENT (SPEED)", 3, "§2Speed hax", 3, inv);
		inv = addToInv(Material.STAINED_CLAY, "§aRACISM", 4, "§2Fried chicken", 4, inv);
		inv = addToInv(Material.STAINED_CLAY, "§aGRIEFING", 5, "§2Theft / wreckage", 5, inv);
		inv = addToInv(Material.STAINED_CLAY, "§aHOMOPHOBIA", 6, "§2Unacceptable", 6, inv);
		inv = addToInv(Material.STAINED_CLAY, "§aSHOCK / PORN SITE", 7, "§2Even if it's tasteful", 7, inv);
		inv = addToInv(Material.STAINED_CLAY, "§aNAZI", 8, "§2MienKhampf", 8, inv);
		invs.put("banReasonMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§eCHAT TIMESTAMPS");
		inv = addToInv(Material.INK_SACK, "§aTOGGLE", 0, "§2Toggle timestamps", 2, inv);
		inv = addToInv(Material.FLINT, "§bCHAT", 8, "§b<<---<", 1, inv);
		invs.put("timeCodeMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§eSTAT VIEWER");
		inv = addToInv(Material.FLINT, "§bSTATS", 53, "§b<<---<", 1, inv);
		invs.put("statViewMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§3STATS");
		inv = addToInv(Material.INK_SACK, "§aME", 0, "§2Personal Stats", 2, inv);
		inv = addToInv(Material.INK_SACK, "§cOTHERS", 1, "§4Other people's stats", 1, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v4", 8, "§b<<---<", 1, inv);
		invs.put("statsMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§4TOGGLES");
		inv = addToInv(Material.WORKBENCH, "§aHOME SOUNDS", 0, "§2/home sounds", 2, inv);
		inv = addToInv(Material.SNOW_BALL, "§5THROW ITEMS", 1, "§dShift-right click throwing", 5, inv);
		inv = addToInv(Material.GLASS, "§eSIDEBOARD", 2, "§6The scoreboard", 8, inv);
		inv = addToInv(Material.STICK, "§bPOKES", 3, "§9Allowing pokes", 12, inv);
		inv = addToInv(Material.DIAMOND_SWORD, "§4PVP", 4, "§9Toggle PVP Mode", 9, inv);
		inv = addToInv(Material.FIREWORK, "§dFIREWORKS", 5, "§9Toggle paragon fireworks", 11, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v4", 8, "§b<<---<", 1, inv);
		invs.put("toggleMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§dWATERCLOSET CORE v4");
		inv = addToInv(Material.INK_SACK, "§aCHAT", 0, "§3Chat Options", 9, inv);
		inv = addToInv(Material.INK_SACK, "§4TOGGLES", 1, "§cToggle Options", 1, inv);
		inv = addToInv(Material.INK_SACK, "§6STATS", 2, "§eStat Viewer", 5, inv);
		inv = addToInv(Material.INK_SACK, "§5STAFF SECTION", 3, "§dStaff only", 8, inv);
		inv = addToInv(Material.FLINT, "§bCLOSE", 8, "§b<<---<", 1, inv);
		invs.put("mainMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§dSTAFF OPTIONS");
		inv = addToInv(Material.STAINED_CLAY, "§4BANNING", 45, "§3View banning options", 6, inv);
		inv = addToInv(Material.GLASS, "§eWARNINGS", 46, "§3View warnings", 6, inv);
		inv = addToInv(Material.COMPASS, "§eTELEPORT", 47, "§3Teleport to player", 1, inv);
		inv = addToInv(Material.NETHER_STAR, "§eSPECTATE", 48, "§3Spectate player", 1, inv);
		inv = addToInv(Material.CHEST, "§eINVENTORY", 49, "§3View Inventory", 1, inv);
		inv = addToInv(Material.SULPHUR, "§a§oREFRESH", 52, "§3Refresh player list", 14, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v4", 53, "§b<<---<", 1, inv);
		invs.put("staffMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§4CHAT");
		inv = addToInv(Material.INK_SACK, "§bGLOBAL COLOR", 0, "§9Change global color", 14, inv);
		inv = addToInv(Material.INK_SACK, "§bPM COLOR", 1, "§9Change pm color", 10, inv);
		inv = addToInv(Material.INK_SACK, "§bALLIANCE COLOR", 2, "§9Change alliance color", 11, inv);
		inv = addToInv(Material.INK_SACK, "§bTIME CODES", 3, "§9Toggle chat timecodes", 12, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v4", 8, "§b<<---<", 1, inv);
		invs.put("chatMenu", inv);
		
		inv = Bukkit.createInventory(null, 18, "§3COLOR SELECTION");
		inv = addToInv(Material.STAINED_CLAY, "§fWHITE", 0, "§fIt's a color.", 0, inv);
		inv = addToInv(Material.STAINED_CLAY, "§6ORANGE-ISH", 1, "§fIt's a color.", 1, inv);
		inv = addToInv(Material.STAINED_CLAY, "§dPINK", 2, "§fIt's a color.", 2, inv);
		inv = addToInv(Material.STAINED_CLAY, "§9BLUE-ISH", 3, "§fIt's a color.", 3, inv);
		inv = addToInv(Material.STAINED_CLAY, "§eYELLOW", 4, "§fIt's a color.", 4, inv);
		inv = addToInv(Material.STAINED_CLAY, "§aLIGHT-GREEN", 5, "§fIt's a color.", 5, inv);
		inv = addToInv(Material.STAINED_CLAY, "§cLIGHT-RED", 6, "§fIt's a color.", 6, inv);
		inv = addToInv(Material.STAINED_CLAY, "§1DARK-BLUE", 7, "§fDon't ask.", 7, inv);
		inv = addToInv(Material.STAINED_CLAY, "§7LIGHT-GRAY", 16, "§fIt's a color.", 8, inv);
		inv = addToInv(Material.STAINED_CLAY, "§8GRAY", 9, "§fIt's a color.", 9, inv);
		inv = addToInv(Material.STAINED_CLAY, "§5PURPLE", 10, "§fIt's a color.", 10, inv);
		inv = addToInv(Material.STAINED_CLAY, "§3TEAL-BLUE", 11, "§fIt's a color.", 11, inv);
		inv = addToInv(Material.STAINED_CLAY, "§2DARK-GREEN", 12, "§fIt's a color.", 13, inv);
		inv = addToInv(Material.STAINED_CLAY, "§4DARK-RED", 13, "§fIt's a color.", 14, inv);
		inv = addToInv(Material.STAINED_CLAY, "§0BLACK", 14, "§fIt's a color.", 15, inv);
		inv = addToInv(Material.CLAY, "§bLIGHT-BLUE", 15, "§fIt's a color.", 1, inv);
		inv = addToInv(Material.FLINT, "§bCHAT", 17, "§b<<---<", 1, inv);
		invs.put("colorMenu", inv);
		
		plugin.getLogger().log(Level.INFO, "/options menu has been set up.");
	}
	
	public void updateMonitorInventory(String menu){
		int x = 0;
		inv = invs.get(menu);
		for (Player p2 : Bukkit.getOnlinePlayers()){
			inv = addToInv(Material.SKULL_ITEM, "§a" + p2.getName(), x, p2.getDisplayName(), 3, inv);
			x++;
		}
		invs.put(menu, inv);
	}
	
	public void updateTools(Player p){
		staffTools.put("COMPASS", "wc stafftp " + playerSelection.get(p.getName()));
		staffTools.put("CHEST", "invsee " + playerSelection.get(p.getName()));
		staffTools.put("NETHER_STAR", "spec " + playerSelection.get(p.getName()));
		staffTools.put("STAINED_CLAY", "wc stafftp " + playerSelection.get(p.getName()));
		staffTools.put("GLASS", "a sel " + playerSelection.get(p.getName()));
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onClick(InventoryClickEvent e){
		
	    String n = e.getInventory().getTitle().substring(2);
	    
			if (menu.contains(n) && e.getWhoClicked() instanceof Player && e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()){
				
				p = ((Player)e.getWhoClicked());		
				String d = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
				
					if (d.equals("CLOSE")){
						p.closeInventory();
						return;
					}
					
					if (d.contains("COLOR")){
						colorSelection.put(p.getName(), d);
					}
					
					if (d.contains("STAFF") || d.contains("OTHER")){
						updateTools(p);
						playerSelection.put(p.getName(), d);
						updateMonitorInventory("staffMenu");
						updateMonitorInventory("statViewMenu");
					}
					
					if (openInvs.containsKey(d)) {
						
						if (n.contains("*")){
							setBanOption(p.getName(), d);
						}

						open(p, openInvs.get(d));
						
					} else if (actions.containsKey(d)){
						
						p.performCommand(actions.get(d));
						
					} else if (d.equals("YES!")){
						
						p.closeInventory();
						
						final String[] a = playerSelection.get(p.getName()).split(" ");
						
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "plm reload mcbans");
						
					    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
					    { public void run(){
					        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gban " + a[0] + " " + a[2].toLowerCase() + " / " + a[3]);
					      }
					    }
					    , 40L);
						
					} else if (e.getCurrentItem().getType() == Material.STAINED_CLAY || e.getCurrentItem().getType() == Material.CLAY){
							
							if (colorSelection.get(p.getName()).equals("GLOBAL COLOR")){
								
								colorType = "globalcolor";
								normalCommand(p, d, colorType);
								
							} else if  (colorSelection.get(p.getName()).equals("PM COLOR")) {
								
								colorType = "pmcolor";
								normalCommand(p, d, colorType);
								
							} else {
								allianceCommand(p, d);
							}		
							
					} else if (e.getCurrentItem().getType() == Material.SKULL_ITEM){
							
							if (n.equals("STAT VIEWER")){
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "sudo " + p.getName() + " c:!stats " + d);
							}
							
							if (n.equals("STAFF OPTIONS")){
								playerSelection.put(p.getName(), d);
								updateTools(p);
								WCMain.s(p, "Selected " + Bukkit.getPlayer(d).getDisplayName() + "&d. Choose an action from the utility bar!");
							}
							
					} else if (staffTools.containsKey(e.getCurrentItem().getType().toString()) && n.equals("STAFF OPTIONS")){
							
							if (playerSelection.get(p.getName()) == null){
								WCMain.s(p, "Make a player selection first!");
								return;
							}
			
							p.performCommand(staffTools.get(e.getCurrentItem().getType().toString()));
					}
					
				e.setCancelled(true);
			}	
	}
	

	public void normalCommand(Player p, String d, String colorType){
		switch (d){
			case "WHITE":
				p.performCommand("wc " + colorType + " f");
				break;
			case "ORANGE-ISH":
				p.performCommand("wc " + colorType + " 6");
				break;
			case "PINK":
				p.performCommand("wc " + colorType + " d");
				break;
			case "BLUE-ISH":
				p.performCommand("wc " + colorType + " 9");
				break;
			case "YELLOW":
				p.performCommand("wc " + colorType + " e");
				break;
			case "LIGHT-GREEN":
				p.performCommand("wc " + colorType + " a");
				break;
			case "LIGHT-RED":
				p.performCommand("wc " + colorType + " c");
				break;
			case "DARK-BLUE":
				p.performCommand("wc " + colorType + " 1");
				break;
			case "LIGHT-GRAY":
				p.performCommand("wc " + colorType + " 7");
				break;
			case "GRAY":
				p.performCommand("wc " + colorType + " 8");
				break;
			case "BLACK":
				p.performCommand("wc " + colorType + " 0");
				break;
			case "DARK-RED":
				p.performCommand("wc " + colorType + " 4");
				break;
			case "DARK-GREEN":
				p.performCommand("wc " + colorType + " 2");
				break;
			case "PURPLE":
				p.performCommand("wc " + colorType + " 5");
				break;
			case "LIGHT-BLUE":
				p.performCommand("wc " + colorType + " b");
				break;
			case "TEAL-BLUE":
				p.performCommand("wc " + colorType + " 3");
				break;
		}
	}
	
	private void allianceCommand(Player p, String d) {
		switch (d){
			case "WHITE":
				p.performCommand("waa chat color" + " f");
				break;
			case "ORANGE-ISH":
				p.performCommand("waa chat color" + " 6");
				break;
			case "PINK":
				p.performCommand("waa chat color" + " d");
				break;
			case "BLUE-ISH":
				p.performCommand("waa chat color" + " 9");
				break;
			case "YELLOW":
				p.performCommand("waa chat color" + " e");
				break;
			case "LIGHT-GREEN":
				p.performCommand("waa chat color" + " a");
				break;
			case "LIGHT-RED":
				p.performCommand("waa chat color" + " c");
				break;
			case "DARK-BLUE":
				p.performCommand("waa chat color" + " 1");
				break;
			case "LIGHT-GRAY":
				p.performCommand("waa chat color" + " 7");
				break;
			case "GRAY":
				p.performCommand("waa chat color" + " 8");
				break;
			case "BLACK":
				p.performCommand("waa chat color" + " 0");
				break;
			case "DARK-RED":
				p.performCommand("waa chat color" + " 4");
				break;
			case "DARK-GREEN":
				p.performCommand("waa chat color" + " 2");
				break;
			case "PURPLE":
				p.performCommand("waa chat color" + " 5");
				break;
			case "LIGHT-BLUE":
				p.performCommand("waa chat color" + " b");
				break;
			case "TEAL-BLUE":
				p.performCommand("waa chat color" + " 3");
				break;
	}
		
	}
	
	public static Inventory addToInv(Material mat, String displayName, int slot, String lore, int id, Inventory inv) {
				List<String> newLore = new ArrayList<String>();
				newLore.add(lore);
				ItemStack is = new ItemStack(mat, 1, (byte) id);
				ItemMeta im = is.getItemMeta();
				im.setLore(newLore);
				im.setDisplayName(displayName);
					if (mat == Material.FLINT || mat == Material.SULPHUR){
						im.addEnchant(Enchantment.DURABILITY, 10, true);
					}
				is.setItemMeta(im);
				inv.setItem(slot, is);
		return inv;
	}
	
	public void open(Player p, String inv){
		p.openInventory(invs.get(inv));
	}
}
