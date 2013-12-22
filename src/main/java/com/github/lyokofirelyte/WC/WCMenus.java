 package com.github.lyokofirelyte.WC;

import static com.github.lyokofirelyte.WC.WCMain.s;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.Gui.GuiRoot;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPatrol;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCMenus implements Listener, CommandExecutor {
	
	WCMain pl;
	public WCMenus(WCMain instance){
	this.pl = instance;
	}
	
	public List <Material> mats = new ArrayList<Material>();
	public static List <String> allianceMenu = new ArrayList<String>();
	public static List <String> menu = new ArrayList<String>();
	public static Map <String, String> staffTools = new HashMap<>();
	public static List <String> lores = new ArrayList<String>();
	public static Map <String, Inventory> invs = new HashMap<String, Inventory>();
	public static Map <String, String> playerSelection = new HashMap<>();
	public static Map <String, String> playerTemp = new HashMap<>();
	public static Map <String, String> colorSelection = new HashMap<>();
	public static Map <String, String> openInvs = new HashMap<>();
	public static Map <String, String> actions = new HashMap<>();
	public static Map <String, String> banActions = new HashMap<>();
	
	public static Inventory inv;
	Boolean poke = true;
	Boolean setup = false;
	String sel;
	static Chest chest;
	public String colorType;
	Player p;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		if (menu.contains(e.getInventory().getTitle().substring(0,2)) && !e.getInventory().getTitle().substring(0,2).contains("INCIN")){
			e.setCancelled(true);
			((Player)e.getWhoClicked()).updateInventory();
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryMove(InventoryMoveItemEvent e) {
		if (menu.contains(e.getDestination().getTitle().substring(0,2)) && !e.getDestination().getTitle().substring(0,2).contains("INCIN")){
			e.setCancelled(true);
			((Player)e.getSource().getHolder()).updateInventory();
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryInteract(InventoryInteractEvent e) {
		if (menu.contains(e.getInventory().getTitle().substring(0,2)) && !e.getInventory().getTitle().substring(0,2).contains("INCIN")){
			e.setCancelled(true);
			((Player)e.getWhoClicked()).updateInventory();
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
			((Player)e.getPlayer()).updateInventory();
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent e){

		if (e.getAction() == Action.LEFT_CLICK_AIR && e.getPlayer().isSneaking() && pl.wcm.getWCPlayer(e.getPlayer().getName()).getRootShortCut()){
			
			colorSelection.put(e.getPlayer().getName(), "red");
			
			if (setup == false){
				p = e.getPlayer();
				playerSelection.put(e.getPlayer().getName(), "NO"); 
				playerTemp.put(e.getPlayer().getName(), "NO");
				setup = true;
				setUp();
			}
			
			e.getPlayer().openInventory(invs.get("mainMenu"));		
		}
	}
	
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("root")){
			
			colorSelection.put(((Player)sender).getName(), "red");
			
			if (setup == false){
				p = ((Player)sender);
				playerSelection.put(p.getName(), "NO"); // We don't want a null pointer here. :D
				playerTemp.put(p.getName(), "NO");
				setup = true;
				setUp();
			}
			
			//((Player)sender).openInventory(invs.get("mainMenu"));	
			
			this.pl.wcm.displayGui((Player) sender, new GuiRoot(this.pl));
			
		}
		
		if (cmd.getName().equalsIgnoreCase("qc")){
			
			WCPlayer wcp = pl.wcm.getWCPlayer(((Player)sender).getName());
			Player p = ((Player)sender);
			
			if (args.length == 0){
				if (wcp.getQuickCommands().size() <= 0){
					if (setup == false){
						playerSelection.put(p.getName(), "NO"); // We don't want a null pointer here. :D
						playerTemp.put(p.getName(), "NO");
						setup = true;
						setUp();
					}
					p.openInventory(invs.get("quickCommandsMenu"));	
				} else {
					Inventory inv = Bukkit.createInventory(null, 9, "§3ACCESS THIS WITH /QC");
					ItemStack flint = pl.invManager.makeItem("§aWATERCLOSET CORE v5", "< < <", true, Enchantment.DURABILITY, 10, 1, Material.FLINT, 1);
					inv.setItem(8, flint);
					int y = 14;
					
					for (int x = 0; x < 8; x++){
						if (wcp.getQuickCommands().size() > x){
							ItemStack ii = pl.invManager.makeItem("§a" + wcp.getQuickCommands().get(x), "§2" + x+1, true, Enchantment.DURABILITY, 10, y, Material.INK_SACK, 1);
							inv.setItem(x, ii);
						} else {
							ItemStack ii = pl.invManager.makeItem("§3Add a command...", "§b/qc add <command>", false, Enchantment.DURABILITY, 10, y, Material.INK_SACK, 1);
							inv.setItem(x, ii);
						}
						y--;
					}
					p.openInventory(inv);
				}
				return true;
			}
			
			switch(args[0]){
			
				case "add":
					
					if (args.length < 2){
						s(p, "/qc add <command>");
						break;
					}
					
					if (wcp.getQuickCommands().size() >= 8){
						s(p, "QC limit reached! Used /wc rem <command>");
						break;
					}
					
					List<String> qc = wcp.getQuickCommands();
					qc.add(Utils.createString(args, 1).replaceAll("/", ""));
					wcp.setQuickCommands(qc);
					pl.wcm.updatePlayerMap(p.getName(), wcp);
					s(p, "Added!");
					
				break;
				
				case "rem":
					
					if (args.length < 2){
						s(p, "/qc rem <command>");
						break;
					}
					
					qc = wcp.getQuickCommands();
	
					if (!wcp.getQuickCommands().contains(Utils.createString(args, 1).replaceAll("/", ""))){
						s(p, "You do not have that command set.");
						break;
					}
					
					qc.remove(Utils.createString(args, 1).replaceAll("/", ""));
					wcp.setQuickCommands(qc);
					pl.wcm.updatePlayerMap(p.getName(), wcp);
					s(p, "Removed!");
					
				break;	
			}
		}
		
		return true;
	}
	
	
	public void setBanOption(String name, String option){
		playerSelection.put(p.getName(), playerSelection.get(p.getName()) + " " + option);
	}
	
	public void setUp(){
		
		// Menus that accept input
		menu.add("WATERCLOSET CORE v5");
		menu.add("SPAWN BUILD");
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
		menu.add("THE CLOSET - TRADE CENTRE");
		menu.add("ALLIANCE FRONT DESK");
		menu.add("INVITE TO ALLIANCE");
		menu.add("KICK FROM CHAT");
		menu.add("BAN FROM CHAT");
		menu.add("UNBAN FROM CHAT");
		menu.add("ADD ADMIN");
		menu.add("REMOVE ADMIN");
		menu.add("VISIT ALLIANCE");
		menu.add("SELECT LEADER");
		menu.add("REQUEST TO JOIN AN ALLIANCE");
		menu.add("INCINERATORIUM");
		menu.add("PARAGON SHOPPE");
		menu.add("WC RANKS");
		menu.add("QUICK COMMANDS");
		menu.add("PATROLS");
		menu.add("LOCATIONS");
		menu.add("REWARDS");
		menu.add("JOIN PATROL");
		menu.add("ACTIVE GEAR");
		menu.add("ACHIEVEMENTS");
		menu.add("ACCESS THIS WITH /QC");
		
		allianceMenu.add("ALLIANCES");
		allianceMenu.add("INVITE");
		allianceMenu.add("LEADER");
		allianceMenu.add("VISIT");
		allianceMenu.add("CHAT -> BAN");
		allianceMenu.add("CHAT -> UNBAN");
		allianceMenu.add("CHAT -> ADMIN+");
		allianceMenu.add("CHAT -> ADMIN-");
		allianceMenu.add("CHAT -> KICK");
		allianceMenu.add("CHAT -> VISIT");
		
		// Inventory references, on hover name -> inv
		openInvs.put("SPAWN BUILD", "spawnBuildMenu");
		openInvs.put("PARAGON SHOPPE", "paragonShopMenu");
		openInvs.put("WC RANK SYSTEM", "rankMenu");
		openInvs.put("STAFF SECTION", "staffMenu");
		openInvs.put("CHAT", "chatMenu");
		openInvs.put("STATS", "statsMenu");
		openInvs.put("TIME CODES", "timeCodeMenu");
		openInvs.put("TOGGLES", "toggleMenu");
		openInvs.put("ALLIANCE COLOR", "colorMenu");
		openInvs.put("GLOBAL COLOR", "colorMenu");
		openInvs.put("PM COLOR", "colorMenu");
		openInvs.put("WATERCLOSET CORE v5", "mainMenu");
		openInvs.put("OTHERS", "statViewMenu");
		openInvs.put("NO!", "mainMenu");
		openInvs.put("THE CLOSET", "closet");
		openInvs.put("ALLIANCES", "allianceHomeMenu");
		openInvs.put("INVITE", "allianceInviteMenu");
		openInvs.put("LEADER", "allianceLeaderMenu");
		openInvs.put("CHAT -> VISIT", "allianceVisitMenu");
		openInvs.put("CHAT -> BAN", "allianceBanMenu");
		openInvs.put("CHAT -> UNBAN", "allianceUnbanMenu");
		openInvs.put("CHAT -> ADMIN+", "allianceAdminMenu");
		openInvs.put("CHAT -> ADMIN-", "allianceAdmin-Menu");
		openInvs.put("CHAT -> KICK", "allianceKickMenu");
		openInvs.put("ALLIANCE FRONT DESK", "allianceHomeMenu");
		openInvs.put("INCINERATOR", "incinerator");
		
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
		openInvs.put("PATROLS", "patrolMenu");
		openInvs.put("LOCATIONS", "patrolLocationMenu");
		openInvs.put("ACHIEVEMENTS", "patrolAchievementMenu");
		openInvs.put("JOIN PATROL", "patrolJoinMenu");
		
		// on hover -> action
		actions.put("TOGGLE", "wc timecode");
		actions.put("HOME SOUNDS", "wc homesounds");
		actions.put("SIDEBOARD", "wc sidebar");
		actions.put("PVP", "wc pvp");
		actions.put("POKES", "wc poke");
		actions.put("FIREWORKS", "wc fwtoggle");
		actions.put("ME", "wcstats");
		actions.put("EMOTES", "wc emotes");
		actions.put("ROOT SHORTCUT", "wc rootshortcut");
		actions.put("NAME PLATE", "wc nameplate");
		actions.put("SIDEBOARD COORDS", "wc sideboardcoords");
		actions.put("CREATIVE WORLD", "wc creative");
		
		actions.put("WC Teleport Pad", "wc paragonTeleportPad");
		actions.put("/wc back (1)", "wc paragonBack");
		actions.put("+2 hearts (no regen)", "wc paragonHearts");
		actions.put("Armour Buff (until death)", "wc paragonArmor");
		actions.put("Armour Buff+ (until death)", "wc paragonArmor2");
		actions.put("/wc home (perm)", "wc paragonHome");
		actions.put("/wc tp <player> (3)", "wc paragonTP");
		actions.put("/wc market (perm)", "wc paragonMarket");
		actions.put("$85,000 (once)", "wc paragonMoney");
		actions.put("Town Obelisk (perm)", "wc paragonObelisk");
		actions.put("New Hamdrax (half dur)", "wc paragonHamdrax");
		actions.put("Repair Hamdrax (full)", "wc paragonHamdraxRepair");
		actions.put("Spawn Egg Pack (animals)", "wc paragonSpawnEgg");
		actions.put("Custom Join/Quit Message", "wc paragonMessage");
		actions.put("WC Teleport Pad", "wc paragonTeleport");
		actions.put("LOGOFF", "logoff");
		actions.put("Reset Special Home", "wc paragonResetHome");
		
		actions.put("PATROL INFO", "wc patrolInfo");
		
		// on hover -> alliance action
		actions.put("UPGRADE", "waa upgrade");
		actions.put("INFO", "waa info");
		actions.put("DISBAND", "waa disband");
		actions.put("CHAT -> LEAVE", "waa chat leave");
		actions.put("CHAT -> JOIN", "waa chat join");
		actions.put("CHAT -> LIST", "waa chat list");
		actions.put("COLORS", "waa colors");
		actions.put("DOORS", "waa doors");
		actions.put("MOBS", "waa mobs");
		actions.put("DONATE", "waa pay");
		actions.put("QUICK COMMANDS", "qc");
		
		inv = Bukkit.createInventory(null, 9, "§1REWARDS");
		ItemStack i = pl.invManager.makeItem("§3Soul Split", "", true, Enchantment.DURABILITY, 10, 0, Material.ARROW, 1);
		ItemMeta im = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("§b§lPATROL ITEM");
		lore.add("§3Steals life from monsters");
		lore.add("§3and adds to patrol members");
		lore.add("§4DROPPED FROM HOTSPOT BOSSES");
		im.setLore(lore);
		i.setItemMeta(im);
		inv.addItem(i);
		i = pl.invManager.makeItem("§3Defender Shield", "", true, Enchantment.DURABILITY, 10, 0, Material.BOWL, 1);
		im = i.getItemMeta();
		lore = new ArrayList<String>();
		lore.add("§b§lPATROL ITEM");
		lore.add("§3The wearer will randomly");
		lore.add("§3knock back nearby monsters");
		lore.add("§3during combat");
		lore.add("§4DROPPED FROM HOTSPOT BOSSES");
		im.setLore(lore);
		i.setItemMeta(im);
		inv.addItem(i);
		i = pl.invManager.makeItem("§3Teleport Cube", "", true, Enchantment.DURABILITY, 10, 0, Material.GLASS, 1);
		im = i.getItemMeta();
		lore = new ArrayList<String>();
		lore.add("§b§lPATROL ITEM");
		lore.add("§3Allows teleportation to");
		lore.add("§3other patrol members while");
		lore.add("§3worn. /ptp <member>");
		lore.add("§4DROPPED FROM HOTSPOT BOSSES");
		im.setLore(lore);
		i.setItemMeta(im);
		inv.addItem(i);
		i = pl.invManager.makeItem("§3Build Charm", "", true, Enchantment.DURABILITY, 10, 0, Material.APPLE, 1);
		im = i.getItemMeta();
		lore = new ArrayList<String>();
		lore.add("§b§lPATROL ITEM");
		lore.add("§3Allows building in");
		lore.add("§3hotspot zones while worn");
		lore.add("§4DROPPED FROM HOTSPOT BOSSES");
		im.setLore(lore);
		i.setItemMeta(im);
		inv.addItem(i);
		i = pl.invManager.makeItem("§3Quick Bone", "", true, Enchantment.DURABILITY, 10, 0, Material.BONE, 1);
		im = i.getItemMeta();
		lore = new ArrayList<String>();
		lore.add("§b§lPATROL ITEM");
		lore.add("§3Allows faster walk speed");
		lore.add("§3while worn");
		lore.add("§4UNLOCK AT LEVEL 10");
		im.setLore(lore);
		i.setItemMeta(im);
		inv.addItem(i);
		i = pl.invManager.makeItem("§3Majjykk Stick", "", true, Enchantment.DURABILITY, 10, 0, Material.STICK, 1);
		im = i.getItemMeta();
		lore = new ArrayList<String>();
		lore.add("§b§lPATROL ITEM");
		lore.add("§3Randomly blocks attacks");
		lore.add("§3from incoming monsters");
		lore.add("§4UNLOCK AT LEVEL 20");
		im.setLore(lore);
		i.setItemMeta(im);
		inv.addItem(i);
		i = pl.invManager.makeItem("§3Destruction Ward", "", true, Enchantment.DURABILITY, 10, 0, Material.PORTAL, 1);
		im = i.getItemMeta();
		lore = new ArrayList<String>();
		lore.add("§b§lPATROL ITEM");
		lore.add("§3Monsters will randomly");
		lore.add("§3explode during combat");
		lore.add("§4UNLOCK AT LEVEL 25");
		im.setLore(lore);
		i.setItemMeta(im);
		inv.addItem(i);
		invs.put("patrolRewardMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§3ACHIEVEMENTS");
		inv = addToInv(Material.FLINT, "§3PATROLS", 8, "§b< < <", 1, inv);
		invs.put("patrolAchievementMenu", inv);
		
		inv = Bukkit.createInventory(null, 18, "§2JOIN PATROL");
		inv = addToInv(Material.FLINT, "§3PATROLS", 17, "§b< < <", 1, inv);
		invs.put("patrolJoinMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§aACTIVE GEAR");
		inv = addToInv(Material.FLINT, "§3PATROLS", 8, "§b< < <", 1, inv);
		invs.put("patrolGearMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§3PATROLS");
		inv = addToInv(Material.INK_SACK, "§3ACTIVE GEAR", 0, "§bChange your gear!", 0, inv);
		inv = addToInv(Material.INK_SACK, "§3FORM PATROL", 1, "§bMake your own patrol!", 1, inv);
		inv = addToInv(Material.INK_SACK, "§3JOIN PATROL", 2, "§bLook for active patrols!", 14, inv);
		inv = addToInv(Material.INK_SACK, "§3LEAVE PATROL", 3, "§bLeave your current patrol!", 2, inv);
		inv = addToInv(Material.INK_SACK, "§3ACHIEVEMENTS", 4, "§bView unlocked achievements!", 5, inv);
		inv = addToInv(Material.INK_SACK, "§3REWARDS", 5, "§bView avaliable rewards!", 6, inv);
		inv = addToInv(Material.INK_SACK, "§3LOCATIONS", 6, "§bView active hotspots!", 7, inv);
		inv = addToInv(Material.INK_SACK, "§3PATROL INFO", 7, "§bWhat is a patrol?", 8, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 8, "§b< < <", 1, inv);
		invs.put("patrolMenu", inv);
		
		inv = Bukkit.createInventory(null, 18, "§aPARAGON SHOPPE");
		inv = addToInv(Material.INK_SACK, "§b/wc back (1)", 0, "§e10 TOKENS", 1, inv);
		inv = addToInv(Material.INK_SACK, "§b+2 hearts (no regen)", 1, "§e10 TOKENS", 2, inv);
		inv = addToInv(Material.INK_SACK, "§bArmour Buff (until death)", 2, "§e5 TOKENS", 0, inv);
		inv = addToInv(Material.INK_SACK, "§bArmour Buff+ (until death)", 3, "§e20 TOKENS", 14, inv);
		inv = addToInv(Material.INK_SACK, "§b/wc home (perm)", 4, "§e10 TOKENS", 5, inv);
		inv = addToInv(Material.INK_SACK, "§b/wc tp <player> (3)", 5, "§e15 TOKENS", 6, inv);
		inv = addToInv(Material.INK_SACK, "§b/wc market (perm)", 6, "§e20 TOKENS", 7, inv);
		inv = addToInv(Material.INK_SACK, "§b$85,000 (once)", 7, "§e45 TOKENS", 8, inv);
		inv = addToInv(Material.INK_SACK, "§bTown Obelisk (perm)", 8, "§e80 TOKENS", 9, inv);
		inv = addToInv(Material.INK_SACK, "§bNew Hamdrax (half dur)", 9, "§e64 TOKENS", 10, inv);
		inv = addToInv(Material.INK_SACK, "§bRepair Hamdrax (full)", 10, "§e15 TOKENS", 11, inv);
		inv = addToInv(Material.INK_SACK, "§bSpawn Egg Pack (animals)", 11, "§e25 TOKENS", 12, inv);
		inv = addToInv(Material.INK_SACK, "§bCustom Join/Quit Message", 12, "§e20 TOKENS", 13, inv);
		inv = addToInv(Material.INK_SACK, "§bWC Teleport Pad", 13, "§e30 TOKENS", 0, inv);
		inv = addToInv(Material.INK_SACK, "§bReset Special Home", 14, "§e20 TOKENS", 14, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 17, "§b< < <", 1, inv);
		invs.put("paragonShopMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§4INCINERATORIUM");
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 53, "§b< < <", 1, inv);
		invs.put("incinerator", inv);
		
		inv = Bukkit.createInventory(null, 27, "§3THE CLOSET - TRADE CENTRE");
		invs.put("closetStore", inv);
		
		inv = Bukkit.createInventory(null, 54, "§aREQUEST TO JOIN AN ALLIANCE");
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 53, "§b< < <", 1, inv);
		invs.put("allianceRequestMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§3SELECT LEADER");
		inv = addToInv(Material.FLINT, "§bALLIANCE FRONT DESK", 53, "§b< < <", 1, inv);
		invs.put("allianceLeaderMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§3KICK FROM CHAT");
		inv = addToInv(Material.FLINT, "§bALLIANCE FRONT DESK", 53, "§b< < <", 1, inv);
		invs.put("allianceKickMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§3BAN FROM CHAT");
		inv = addToInv(Material.FLINT, "§bALLIANCE FRONT DESK", 53, "§b< < <", 1, inv);
		
		invs.put("allianceBanMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§3UNBAN FROM CHAT");
		inv = addToInv(Material.FLINT, "§bALLIANCE FRONT DESK", 53, "§b< < <", 1, inv);
		invs.put("allianceUnbanMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§3INVITE TO ALLIANCE");
		inv = addToInv(Material.FLINT, "§bALLIANCE FRONT DESK", 53, "§b< < <", 1, inv);
		invs.put("allianceInviteMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§3VISIT ALLIANCE");
		inv = addToInv(Material.FLINT, "§bALLIANCE FRONT DESK", 53, "§b< < <", 1, inv);
		invs.put("allianceVisitMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§3REMOVE ADMIN");
		inv = addToInv(Material.FLINT, "§bALLIANCE FRONT DESK", 53, "§b< < <", 1, inv);
		invs.put("allianceAdmin-Menu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§3ADD ADMIN");
		inv = addToInv(Material.FLINT, "§bALLIANCE FRONT DESK", 53, "§b< < <", 1, inv);
		invs.put("allianceAdminMenu", inv);
		
		inv = Bukkit.createInventory(null, 27, "§3ALLIANCE FRONT DESK");
		inv = addToInv(Material.GLOWSTONE_DUST, "§bLEADER", 0, "§9Change leaders", 1, inv);
		inv = addToInv(Material.GLOWSTONE_DUST, "§bCOLORS", 1, "§9/waa colors", 1, inv);
		inv = addToInv(Material.GLOWSTONE_DUST, "§bINVITE", 2, "§9Invite someone!", 1, inv);
		inv = addToInv(Material.GLOWSTONE_DUST, "§bDOORS", 3, "§9Toggle Doors", 1, inv);
		inv = addToInv(Material.TNT, "§cDISBAND", 4, "§cDISBAND", 1, inv);
		inv = addToInv(Material.GLOWSTONE_DUST, "§bMOBS", 5, "§9Toggle Mobs", 1, inv);
		inv = addToInv(Material.GLOWSTONE_DUST, "§bUPGRADE", 6, "§9Increase your tier", 1, inv);
		inv = addToInv(Material.GLOWSTONE_DUST, "§bDONATE", 7, "§9/waa pay <alliance> <amount>", 1, inv);
		inv = addToInv(Material.GLOWSTONE_DUST, "§bINFO", 8, "§9General alliance info", 1, inv);
		inv = addToInv(Material.REDSTONE, "§aCHAT -> JOIN", 9, "§2Join your alliance chat", 1, inv);
		inv = addToInv(Material.REDSTONE, "§aCHAT -> VISIT", 10, "§2Visit a different alliance chat", 1, inv);
		inv = addToInv(Material.REDSTONE, "§aCHAT -> LEAVE", 11, "§2Leave your alliance chat", 1, inv);
		inv = addToInv(Material.REDSTONE, "§aCHAT -> KICK", 12, "§2Remove someome from your chat", 1, inv);
		inv = addToInv(Material.REDSTONE, "§aCHAT -> BAN", 13, "§2Ban someone from your chat", 1, inv);
		inv = addToInv(Material.REDSTONE, "§aCHAT -> UNBAN", 14, "§2Unban someone from your chat", 1, inv);
		inv = addToInv(Material.REDSTONE, "§aCHAT -> LIST", 15, "§2Who is in your chat?", 1, inv);
		inv = addToInv(Material.REDSTONE, "§aCHAT -> ADMIN+", 16, "§2Add a chat admin", 1, inv);
		inv = addToInv(Material.REDSTONE, "§aCHAT -> ADMIN-", 17, "§2Remove a chat admin", 1, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 22, "§b< < <", 1, inv);
		invs.put("allianceHomeMenu", inv);
		
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
		inv = addToInv(Material.FLINT, "§bCHAT", 8, "§b< < <", 1, inv);
		invs.put("timeCodeMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§eSTAT VIEWER");
		inv = addToInv(Material.FLINT, "§bSTATS", 53, "§b< < <", 1, inv);
		invs.put("statViewMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§3STATS");
		inv = addToInv(Material.INK_SACK, "§aME", 0, "§2Personal Stats", 2, inv);
		inv = addToInv(Material.INK_SACK, "§cOTHERS", 1, "§4Other people's stats", 1, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 8, "§b< < <", 1, inv);
		invs.put("statsMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§3ACCESS THIS WITH /QC");
		inv = addToInv(Material.INK_SACK, "§aAdd a command...", 0, "§2/qc add <command>", 0, inv);
		inv = addToInv(Material.INK_SACK, "§aAdd a command...", 1, "§2/qc add <command>", 1, inv);
		inv = addToInv(Material.INK_SACK, "§aAdd a command...", 2, "§2/qc add <command>", 14, inv);
		inv = addToInv(Material.INK_SACK, "§aAdd a command...", 3, "§2/qc add <command>", 2, inv);
		inv = addToInv(Material.INK_SACK, "§aAdd a command...", 4, "§2/qc add <command>", 13, inv);
		inv = addToInv(Material.INK_SACK, "§aAdd a command...", 5, "§2/qc add <command>", 6, inv);
		inv = addToInv(Material.INK_SACK, "§aAdd a command...", 6, "§2/qc add <command>", 5, inv);
		inv = addToInv(Material.INK_SACK, "§aAdd a command...", 7, "§2/qc add <command>", 7, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 8, "§b< < <", 1, inv);
		invs.put("quickCommandsMenu", inv);
		
		inv = Bukkit.createInventory(null, 18, "§4TOGGLES");
		inv = addToInv(Material.WORKBENCH, "§aHOME SOUNDS", 0, "§2/home sounds", 2, inv);
		inv = addToInv(Material.GLOWSTONE, "§eSIDEBOARD", 2, "§6The scoreboard", 8, inv);
		inv = addToInv(Material.STICK, "§bPOKES", 3, "§9Allowing pokes", 12, inv);
		inv = addToInv(Material.DIAMOND_SWORD, "§4PVP", 4, "§cToggle PVP Mode", 9, inv);
		inv = addToInv(Material.FIREWORK, "§dFIREWORKS", 5, "§8Toggle paragon fireworks", 11, inv);
		inv = addToInv(Material.CAKE, "§3EMOTES", 6, "§aToggle auto-emotes on chat", 1, inv);
		inv = addToInv(Material.ANVIL, "§2ROOT SHORTCUT", 7, "§3Shift+left click root menu", 1, inv);
		inv = addToInv(Material.NAME_TAG, "§cNAME PLATE", 8, "§bToggle alliance nameplate", 1, inv);
		inv = addToInv(Material.REDSTONE, "§eSIDEBOARD COORDS", 1, "§9Toggle name/coords on sideboard", 1, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 13, "§b< < <", 1, inv);
		invs.put("toggleMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§dWATERCLOSET CORE v5");
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 1, "§3WCV5", 2, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 3, "§3WCV5", 2, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 10, "§3WCV5", 2, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 11, "§3WCV5", 2, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 12, "§3WCV5", 2, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 19, "§3WCV5", 2, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 20, "§3WCV5", 2, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 21, "§3WCV5", 2, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 5, "§3WCV5", 10, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 6, "§3WCV5", 10, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 7, "§3WCV5", 10, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 14, "§3WCV5", 10, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 23, "§3WCV5", 10, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 24, "§3WCV5", 10, inv);
		inv = addToInv(Material.STAINED_GLASS, "§aWCV5", 25, "§3WCV5", 10, inv);
		inv = addToInv(Material.INK_SACK, "§aCHAT", 37, "§3Chat Options", 9, inv);
		inv = addToInv(Material.INK_SACK, "§4TOGGLES", 38, "§cToggle Options", 1, inv);
		inv = addToInv(Material.INK_SACK, "§6STATS", 39, "§eStat Viewer", 5, inv);
		inv = addToInv(Material.INK_SACK, "§aTHE CLOSET", 41, "§3General Store Trading", 12, inv);
		inv = addToInv(Material.INK_SACK, "§3ALLIANCES", 42, "§aAlliance controls", 2, inv);
		inv = addToInv(Material.INK_SACK, "§4INCINERATOR", 43, "§cThrow away items", 10, inv);
		inv = addToInv(Material.INK_SACK, "§5STAFF SECTION", 46, "§dStaff only", 8, inv);
		inv = addToInv(Material.INK_SACK, "§dPARAGON SHOPPE", 47, "§5Paragon Rewards", 6, inv);
		inv = addToInv(Material.INK_SACK, "§bPATROLS", 48, "§dPatrol Menu", 11, inv);
		inv = addToInv(Material.INK_SACK, "§1QUICK COMMANDS", 50, "§5Command Menu", 14, inv);
		inv = addToInv(Material.INK_SACK, "§2CREATIVE WORLD", 51, "§aWarp to creative", 13, inv);
		inv = addToInv(Material.INK_SACK, "§eLOGOFF", 52, "§6Leave the game", 0, inv);
		inv = addToInv(Material.FLINT, "§bCLOSE", 31, "§b< < <", 1, inv);
		invs.put("mainMenu", inv);
		
		inv = Bukkit.createInventory(null, 54, "§dSTAFF OPTIONS");
		inv = addToInv(Material.STAINED_CLAY, "§4BANNING", 45, "§3View banning options", 6, inv);
		inv = addToInv(Material.GLASS, "§eWARNINGS", 46, "§3View warnings", 6, inv);
		inv = addToInv(Material.COMPASS, "§eTELEPORT", 47, "§3Teleport to player", 1, inv);
		inv = addToInv(Material.NETHER_STAR, "§eSPECTATE", 48, "§3Spectate player", 1, inv);
		inv = addToInv(Material.CHEST, "§eINVENTORY", 49, "§3View Inventory", 1, inv);
		inv = addToInv(Material.SULPHUR, "§a§oREFRESH", 52, "§3Refresh player list", 14, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 53, "§b< < <", 1, inv);
		invs.put("staffMenu", inv);
		
		inv = Bukkit.createInventory(null, 9, "§4CHAT");
		inv = addToInv(Material.INK_SACK, "§bGLOBAL COLOR", 0, "§9Change global color", 14, inv);
		inv = addToInv(Material.INK_SACK, "§bPM COLOR", 1, "§9Change pm color", 10, inv);
		inv = addToInv(Material.INK_SACK, "§bALLIANCE COLOR", 2, "§9Change alliance color", 11, inv);
		inv = addToInv(Material.INK_SACK, "§bTIME CODES", 3, "§9Toggle chat timecodes", 12, inv);
		inv = addToInv(Material.FLINT, "§bWATERCLOSET CORE v5", 8, "§b< < <", 1, inv);
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
		inv = addToInv(Material.FLINT, "§bCHAT", 17, "§b< < <", 1, inv);
		invs.put("colorMenu", inv);
		
		pl.getLogger().log(Level.INFO, "/root menu has been set up.");
	}
	
	public static void openCloset(Player p){

		inv = invs.get("closetStore");
		Location chestLoc = new Location(Bukkit.getWorld("world"), -272.0, 61.0, -134.0);
		Block block = chestLoc.getBlock();
		BlockState bs = block.getState();
		
		if(bs instanceof Chest) {
		  chest = (Chest)bs;
		  Inventory inv2 = chest.getInventory();
		  inv.setContents(inv2.getContents());
		  p.openInventory(inv);
		  invs.put("closetStore", inv);
		}
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

	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.LOW)
	public void onClick(InventoryClickEvent e){
		
	    String n = e.getInventory().getTitle().substring(2);
	    
		if (n.contains("TRADE")){ 
			p = ((Player)e.getWhoClicked());
			p.playSound(p.getLocation(), Sound.CLICK, 3F, 0.5F);
			e.setCancelled(true);
			
			if (e.getClick() == ClickType.RIGHT && p.hasPermission("wa.mod") && e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)){
				tryItemRemove(p, e.getCurrentItem(), e.getCurrentItem().getItemMeta().getLore());
			} else if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)){
				closetClick(p, e.getCurrentItem(), e.getCurrentItem().getItemMeta().getLore());
			}
		
			return;
		}
		
		if (menu.contains(n) && !n.contains("INCIN") && !n.equals("ACTIVE GEAR")){
			e.setCancelled(true);
			((Player)e.getWhoClicked()).updateInventory();
		}
		
		if (n.contains("INCIN")){
			if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()){
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("WATERCLOSET CORE")){
					e.setCancelled(true);
				}
			}
		}
			
		if (menu.contains(n) && e.getWhoClicked() instanceof Player && e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta()){
				
			p = ((Player)e.getWhoClicked());	
			p.playSound(p.getLocation(), Sound.CLICK, 3F, 0.5F);
			String d = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
				
			if (d.equals("CLOSE")){
				p.closeInventory();
				return;
			}	
			
			if (d.equals("JOIN PATROL")){
				invs.get("patrolJoinMenu").clear();
				invs.get("patrolJoinMenu").setItem(17, pl.invManager.makeItem(Utils.AS("&dPATROLS"), "§b< < <", true, Enchantment.DURABILITY, 10, 1, Material.FLINT, 1));
				for (WCPatrol wcpp : pl.api.wcPatrols.values()){
					ItemStack i = pl.invManager.makeItem(Utils.AS(wcpp.getName()), "§bClick to join...", false, Enchantment.DURABILITY, 10, 1, Material.STICK, 1);
					List<String> lore = new ArrayList<String>();
					lore.add("§bClick to join...");
					for (String s : wcpp.getMembers()){
						lore.add(Utils.AS(pl.wcm.getFullNick(s)));
					}
					ItemMeta im = i.getItemMeta();
					im.setLore(lore);
					i.setItemMeta(im);
					invs.get("patrolJoinMenu").addItem(i);
				}
				p.openInventory(invs.get("patrolJoinMenu"));
				return;
			}
			
			if (n.equals("ACCESS THIS WITH /QC") && !d.contains("WATERCLOSET CORE")){
				if (d.equals("Add a command...")){
					s(p, "Type /qc add <command>");
					return;
				} else {
					p.performCommand(d);
					return;
				}			
			}

			if (d.equals("INCINERATOR")){
				inv = Bukkit.createInventory(null, 54, "§4INCINERATORIUM");
				invs.put("incinerator", inv);
						
				if (!p.hasPermission("wa.villager")){
					s(p, "You need &3villager &dor higher!");
					return;
				}
			}
					
			if (d.equals("?!?!?!?!")){
				p.performCommand("wc fork");
				return;
			}
					
			if (d.contains("COLOR")){
				colorSelection.put(p.getName(), d);
			}
					
			if (d.contains("THE CLOSET")){
				openCloset(p);
				return;
			}
					
			if (n.equals("REQUEST TO JOIN AN ALLIANCE") && !d.equals("WATERCLOSET CORE v5")){
				allianceReq(p, d);
				return;
			}
					
			if (d.equals("ALLIANCES")){
				WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
				
				if (!wcp.getInAlliance()){
					updateVisitInventory();
					open(p, "allianceRequestMenu");
					return;
					}
			}
					
			if (d.contains("STAFF") || d.contains("OTHER") || allianceMenu.contains(d)){
				updateTools(p);
				playerSelection.put(p.getName(), d);
				
				if (d.contains("VISIT")){
					updateVisitInventory();
				}
				
				updateMonitorInventory("staffMenu");
				updateMonitorInventory("statViewMenu");
				updateMonitorInventory("allianceInviteMenu");
				updateMonitorInventory("allianceKickMenu");
				updateMonitorInventory("allianceBanMenu");
				updateMonitorInventory("allianceUnbanMenu");
				updateMonitorInventory("allianceAdminMenu");
				updateMonitorInventory("allianceAdmin-Menu");
				updateMonitorInventory("allianceLeaderMenu");
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
						
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable() { public void run(){
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gban " + a[0] + " " + a[2].toLowerCase() + " / " + a[3]); }}, 40L);
						
			} else if (e.getCurrentItem().getType() == Material.STAINED_CLAY || e.getCurrentItem().getType() == Material.CLAY){
							
				if (colorSelection.get(p.getName()).equals("GLOBAL COLOR")){
								
					colorType = "globalcolor";
					normalCommand(p, d, colorType);
								
				} else if (colorSelection.get(p.getName()).equals("PM COLOR")) {
								
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
							
					if (allianceMenu.contains(playerSelection.get(p.getName()))){
						playerTemp.put(p.getName(), d);
						updateActions(p);
						p.performCommand(actions.get(playerSelection.get(p.getName())));
					}
							
			} else if (staffTools.containsKey(e.getCurrentItem().getType().toString()) && n.equals("STAFF OPTIONS")){
							
				if (playerSelection.get(p.getName()) == null){
					WCMain.s(p, "Make a player selection first!");
					return;
				}
			
				p.performCommand(staffTools.get(e.getCurrentItem().getType().toString()));
			}
		}	
	}
	
	private void allianceReq(Player p, String n) {
		
		WCAlliance wca = pl.wcm.getWCAlliance(n);
		WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
		String leader = wca.getLeader();
		
		List<String> mail = wcp.getMail();
		
	  	mail.add("mail send " + leader + "&6system &4// &6" + p.getDisplayName() + " &6is interested in joining your alliance!.");
	  	
	  	wcp.setMail(mail);
	  	pl.wcm.updatePlayerMap(p.getName(), wcp);

	  	if (Bukkit.getPlayer(wca.getLeader()).isOnline()){
	  		Bukkit.getPlayer(wca.getLeader()).sendMessage(Utils.WC + "You've recieved a new mail! Check it with /mail read.");
	  	}	
	}

	private void updateVisitInventory() {
		
		File file = new File("./plugins/WaterCloset/Alliances/list.yml");
		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		List<String> alliances = yaml.getStringList("Alliances");
		inv = invs.get("allianceVisitMenu");
		Inventory inv2 = invs.get("allianceRequestMenu");
		int x = 0;
			for (String a : alliances){
				WCAlliance wca = pl.wcm.getWCAlliance(a);
				inv = addToInv(Material.SKULL_ITEM, "§7" + a, x, pl.wcm.getCompleted2(a, wca.getColor1(), wca.getColor2()) + " §7// " + wca.getChatUserCount() + " §7in chat", 3, inv);
				inv2 = addToInv(Material.SKULL_ITEM, "§7" + a, x, pl.wcm.getCompleted2(a, wca.getColor1(), wca.getColor2()) + " §7// " + wca.getChatUserCount() + " §7in chat", 3, inv2);
				x++;
				invs.put("allianceVisitMenu", inv);
				invs.put("allianceRequestMenu", inv2);
			}
	}

	private void tryItemRemove(Player p, ItemStack currentItem, List<String> lore) {
		
		String seller = lore.get(1);
		int x = 0;
		
		if (Bukkit.getPlayer(seller) == null){
			WCMain.s(p, "That player is not online to return the item to.");
			return;
		}
		
		Player sellerPlayer = Bukkit.getPlayer(seller);
		
		for (ItemStack item : sellerPlayer.getInventory().getContents()) {
			   if (item != null) {
				   x++;
			   }
		 }
		  
		 if (x >= 27){
			 s(p, "They have no room in their inventory.");
			 return;
		 }
		 
		 currentItem.getItemMeta().setLore(null);
		 sellerPlayer.getInventory().addItem(currentItem);
		 chest.getInventory().remove(currentItem);
		 s(sellerPlayer, "Your item was returned to you from The Closet by " + p.getDisplayName() + "&d.");
		 s(p, "Returned the item to seller!");
		 
		 for (HumanEntity a : invs.get("closetStore").getViewers()){
			 openCloset((Player)a);
			 WCMain.s((Player)a, "Store refreshed because of purchase.");
		 }	
	}

	private void closetClick(Player p, ItemStack i, List<String> lore) {
		
		if (lore == null || lore.size() == 0){
			return;
		}
		
		WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
		
		int price = Integer.parseInt(lore.get(0));
		String seller = lore.get(1);
		
		if (wcp.getBalance() < price){
			WCMain.s(p, "You don't have enough money! D:");
			return;
		}
		
		WCPlayer wcpSeller = pl.wcm.getWCPlayer(seller);
		
		wcpSeller.setBalance(wcpSeller.getBalance() + price);
		wcp.setBalance(wcp.getBalance() - price);
	
		WCMain.s(p, "Purchased for " + price + "&d!");
		
		chest.getInventory().remove(i);
			
		ItemMeta im = i.getItemMeta();
		im.setLore(null);
		i.setItemMeta(im);
		
		p.getInventory().addItem(i);
		
		List<String> mail = wcp.getMail();
		
	  	mail.add("&6system &4// &6" + p.getDisplayName() + " &6has purchased your " + i.getType().toString().toLowerCase() + "&6.");

	  	wcp.setMail(mail);
	  	pl.wcm.updatePlayerMap(p.getName(), wcp);
	  	
	  	if (Bukkit.getPlayer(seller) != null){
	  		Bukkit.getPlayer(seller).sendMessage(Utils.WC + "You've recieved a new mail! Check it with /mail read.");
	  	}
	  	
		for (HumanEntity a : invs.get("closetStore").getViewers()){
			openCloset((Player)a);
			WCMain.s((Player)a, "Store refreshed because of purchase.");
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
	
	public void updateActions(Player p){
		actions.put("ALLIANCE", "waa chat visit " + playerTemp.get(p.getName()));
		actions.put("INVITE", "waa invite " + playerTemp.get(p.getName()));
		actions.put("LEADER", "waa leader " + playerTemp.get(p.getName()));
		actions.put("CHAT -> VISIT", "waa chat visit " + playerTemp.get(p.getName()));
		actions.put("CHAT -> BAN", "waa chat ban " + playerTemp.get(p.getName()));
		actions.put("CHAT -> UNBAN", "waa chat unban " + playerTemp.get(p.getName()));
		actions.put("CHAT -> ADMIN+", "waa chat admin add " + playerTemp.get(p.getName()));
		actions.put("CHAT -> ADMIN-", "waa chat admin rem " + playerTemp.get(p.getName()));
		actions.put("CHAT -> KICK", "waa chat kick " + playerTemp.get(p.getName()));
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
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		if (!setup){
			setUp();
			setup = true;
		}
	}
}
