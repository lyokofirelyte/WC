package com.github.lyokofirelyte.WC;

import static com.github.lyokofirelyte.WC.Util.Utils.AS;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.lyokofirelyte.WC.Commands.WCAFK;
import com.github.lyokofirelyte.WC.Commands.WCDisco;
import com.github.lyokofirelyte.WC.Commands.WCHat;
import com.github.lyokofirelyte.WC.Commands.WCHelp;
import com.github.lyokofirelyte.WC.Commands.WCHome;
import com.github.lyokofirelyte.WC.Commands.WCInvSee;
import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Commands.WCNear;
import com.github.lyokofirelyte.WC.Commands.WCPTP;
import com.github.lyokofirelyte.WC.Commands.WCPay;
import com.github.lyokofirelyte.WC.Commands.WCPowerTool;
import com.github.lyokofirelyte.WC.Commands.WCRanks;
import com.github.lyokofirelyte.WC.Commands.WCReboot;
import com.github.lyokofirelyte.WC.Commands.WCReport;
import com.github.lyokofirelyte.WC.Commands.WCSell;
import com.github.lyokofirelyte.WC.Commands.WCSoar;
import com.github.lyokofirelyte.WC.Commands.WCSpawn;
import com.github.lyokofirelyte.WC.Commands.WCSuicide;
import com.github.lyokofirelyte.WC.Commands.WCWB;
import com.github.lyokofirelyte.WC.Commands.WCWarps;
import com.github.lyokofirelyte.WC.Extras.StaticField;
import com.github.lyokofirelyte.WC.Extras.TimeStampEX;
import com.github.lyokofirelyte.WC.Extras.TraceFW;
import com.github.lyokofirelyte.WC.Extras.WCSEEKRITPARTAY;
import com.github.lyokofirelyte.WC.Gui.GuiRoot;
import com.github.lyokofirelyte.WC.Listener.WCBlockBreak;
import com.github.lyokofirelyte.WC.Listener.WCBlockPlace;
import com.github.lyokofirelyte.WC.Listener.WCDeath;
import com.github.lyokofirelyte.WC.Listener.WCEmotes;
import com.github.lyokofirelyte.WC.Listener.WCJoin;
import com.github.lyokofirelyte.WC.Listener.WCMiscEvents;
import com.github.lyokofirelyte.WC.Listener.WCObelisk;
import com.github.lyokofirelyte.WC.Listener.WCParagon;
import com.github.lyokofirelyte.WC.Listener.WCQuit;
import com.github.lyokofirelyte.WC.Listener.WCScoreboard;
import com.github.lyokofirelyte.WC.Listener.WCSignColor;
import com.github.lyokofirelyte.WC.Listener.WCTags;
import com.github.lyokofirelyte.WC.Staff.WCBal;
import com.github.lyokofirelyte.WC.Staff.WCCheats;
import com.github.lyokofirelyte.WC.Staff.WCMember;
import com.github.lyokofirelyte.WC.Staff.WCSeen;
import com.github.lyokofirelyte.WC.Staff.WCSudo;
import com.github.lyokofirelyte.WC.Staff.WCTele;
import com.github.lyokofirelyte.WC.Util.FireworkShenans;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCManager;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;
import com.github.lyokofirelyte.WCAPI.Manager.RebootManager;

public class WCMain extends JavaPlugin {
	
  public WCVault vaultMgr = new WCVault(this);
  
  public File configFile;
  public File datacoreFile;
  public File gamesFile;
  public File helpFile;
  public File file;
  public File systemFile;
  
  public YamlConfiguration config;
  public YamlConfiguration datacore;
  public YamlConfiguration games;
  public YamlConfiguration help;
  public YamlConfiguration yaml;
  public YamlConfiguration systemYaml;

  private String url;
  private String username;
  private String password;
  private Connection conn;
  
  public WCAPI api;
  public WCManager wcm;
  public RebootManager rm;
  public InventoryManager invManager;
  public WCPatrols wcpp;
  public FireworkShenans fw;
  public Map <String, List<Location>> links = new HashMap<>();
  public Map <String, List<Entity>> links2 = new HashMap<>();
  public Map <String, List<Player>> playerRide = new HashMap<>();
  public Map <String, List<String>> patrols = new HashMap<>();
  public Map <String, ItemStack[]> backupInvs = new HashMap<>();
  public Map <String, Integer> afkTimer = new HashMap<>();
  public List<Entity> carts = new ArrayList<>();
  public List<Player> afkers = new ArrayList<>();
  
  private int msg = 0;

  public void onEnable() {

	  PluginManager pm = getServer().getPluginManager();
	  pm.registerEvents(new WCJoin(this), this);
	  pm.registerEvents(new WCQuit(this), this);
	  pm.registerEvents(new StaticField(this), this);
	  pm.registerEvents(new WCChannels(this), this);
	  pm.registerEvents(new WCMobDrops(this), this);
	  pm.registerEvents(new WCBlockBreak(this), this);
	  pm.registerEvents(new WCBlockPlace(this), this);
	  pm.registerEvents(new WCExpSystem(this), this);
	  pm.registerEvents(new WCDeath(this), this);
	  pm.registerEvents(new WCMiscEvents(this), this);
	  pm.registerEvents(new WCSigns(this), this);
	  pm.registerEvents(new WCMenus(this), this);
	  pm.registerEvents(new WCMail(this),this);
	  pm.registerEvents(new WCInvSee(this),this);
	  pm.registerEvents(new WCTags(this),this);
	  pm.registerEvents(new WCParagon(this),this);
	  pm.registerEvents(new WCEmotes(this),this);
	  pm.registerEvents(new WCScoreboard(this),this);
	  pm.registerEvents(new WCTelePads(this),this);
	  pm.registerEvents(new WCObelisk(this),this);
	  pm.registerEvents(new WCPatrols(this),this);
	  pm.registerEvents(new WCPowerTool(this),this);
	  pm.registerEvents(new WCSeen(this),this);
	  pm.registerEvents(new WCSignColor(this),this);
	  pm.registerEvents(new WCMarkkit(this),this);
	  pm.registerEvents(new WCMember(this),this);
	  pm.registerEvents(new WCSEEKRITPARTAY(this),this);
	
	  vaultMgr.hookSetup();
	    
	  try {
	    firstRun();
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	
	  url = config.getString("url");
	  username = config.getString("username");
	  password = config.getString("password");
	    
	  Plugin WCAPI = Bukkit.getServer().getPluginManager().getPlugin("WCAPI");
	  
	  api = (WCAPI) WCAPI;
	    
	  wcm = api.wcm;
	  rm = new RebootManager(api);
	  invManager = new InventoryManager(api);
	  wcpp = new WCPatrols(this);
	  fw = new FireworkShenans(this);
	    
	  if ((url == null) || (username == null) || (password == null) || WCAPI == null) {
	     Bukkit.getServer().getLogger().info("You must provide a url, username, and password in the config.yml. That or the API is missing.");
	     Bukkit.getServer().getPluginManager().disablePlugin(this);
	  }
	
	  registerCommands();
	  
	  wcm.setupGui(new GuiRoot(this));
	    
	  Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
	  public void run() { updateBoard();} }, 2L, 160L);
	  
	  Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
	  public void run() { sendAnnounce();} }, 2L, 12000L);
	  
	  Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
	  public void run() { autoSave();} }, 144000L, 144000L);

	  Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
	  public void run() { wcpp.startPatrol();} }, 100L, 144000L);

	  getLogger().log(Level.INFO, "WaterCloset is ready! Hooked with WCAPI!");
	  
	  for (Player p : Bukkit.getOnlinePlayers()){
		  if (p.getDisplayName().length() > 16){
			  p.setPlayerListName(AS(p.getDisplayName()).substring(0, 16));
		  } else {
			  p.setPlayerListName(AS(p.getDisplayName()));
		  }
	  } 
	  
	  Inventory inv = Bukkit.createInventory(null, 9, "§cLOCATIONS");
	  inv = WCMenus.addToInv(Material.FLINT, "§3PATROLS", 8, "§b< < <", 1, inv);
	  WCMenus.invs.put("patrolLocationMenu", inv);
  }
  
  public void autoSave(){
	  
		saveYamls();
		
		List<String> users = systemYaml.getStringList("TotalUsers");
		
		for (String user : users){
			try {
				wcm.savePlayer(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			wcm.saveAlliances();
			wcm.saveSystem(systemYaml, systemFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

  }

  public void onDisable() {
	  
	  WCSystem wcs = wcm.getWCSystem("system");
		
	  if (wcs.getPatrolCrystal() != null && !wcs.getPatrolCrystal().isDead()){
		  for (LivingEntity e : wcs.getPatrolEnts()){
			  e.remove();
		  }
		  wcs.getPatrolCrystal().remove();
	  }
	  
	  saveYamls();

	  getServer().getScheduler().cancelTasks(this);
	    
	    try{
	      if (this.conn != null){
	        this.conn.close();
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	  getLogger().info("WaterCloset has been disabled.");
  }
  
  private void registerCommands() {
	  
    getCommand("watercloset").setExecutor(new WCCommands(this));
    getCommand("wc").setExecutor(new WCCommands(this));
    getCommand("blame").setExecutor(new WCCommands(this));
    getCommand("member").setExecutor(new WCCommands(this));
    getCommand("google").setExecutor(new WCCommands(this));
    getCommand("ping").setExecutor(new WCCommands(this));

    getCommand("p").setExecutor(new WCChannels(this));
    
    getCommand("seen").setExecutor(new WCSeen(this));
    
    getCommand("wb").setExecutor(new WCWB(this));
    getCommand("workbench").setExecutor(new WCWB(this));
    
    getCommand("partyfw").setExecutor(new WCSEEKRITPARTAY(this));
    getCommand("jesse").setExecutor(new WCSEEKRITPARTAY(this));
    
    getCommand("soar").setExecutor(new WCSoar(this));
    
    getCommand("pt").setExecutor(new WCPowerTool(this));
    getCommand("powertool").setExecutor(new WCPowerTool(this));
    
    getCommand("ptp").setExecutor(new WCPTP(this));
    
    getCommand("ds").setExecutor(new WCDisco(this));
    
    getCommand("suicide").setExecutor(new WCSuicide(this));
    
    getCommand("boots").setExecutor(new WCHat(this));
    getCommand("leggings").setExecutor(new WCHat(this));
    getCommand("chestplate").setExecutor(new WCHat(this));
    getCommand("hat").setExecutor(new WCHat(this));
    
    getCommand("waa").setExecutor(new WACommandEx(this));
    getCommand("nick").setExecutor(new WACommandEx(this));
    getCommand("rn").setExecutor(new WACommandEx(this));
    getCommand("list").setExecutor(new WACommandEx(this));
    
    getCommand("sudo").setExecutor(new WCSudo(this));

    getCommand("timestamp").setExecutor(new TimeStampEX(this));
    getCommand("getnick").setExecutor(new TimeStampEX(this));
    getCommand("stringbuilder").setExecutor(new TimeStampEX(this));
    getCommand("itemname").setExecutor(new TimeStampEX(this));

    getCommand("afk").setExecutor(new WCAFK(this));
    
    getCommand("gm").setExecutor(new WCCheats(this));
    getCommand("i").setExecutor(new WCCheats(this));
    getCommand("fly").setExecutor(new WCCheats(this));
    getCommand("more").setExecutor(new WCCheats(this));
    getCommand("ci").setExecutor(new WCCheats(this));
    getCommand("tppos").setExecutor(new WCCheats(this));
    getCommand("speed").setExecutor(new WCCheats(this));
    getCommand("back").setExecutor(new WCCheats(this));
    getCommand("feed").setExecutor(new WCCheats(this));
    getCommand("killall").setExecutor(new WCCheats(this));
    getCommand("wlist").setExecutor(new WCCheats(this));
    getCommand("world").setExecutor(new WCCheats(this));
    getCommand("v").setExecutor(new WCCheats(this));
    getCommand("vanish").setExecutor(new WCCheats(this));
    getCommand("dis").setExecutor(new WCCheats(this));
    getCommand("sm").setExecutor(new WCCheats(this));
    getCommand("sit").setExecutor(new WCCheats(this));

    getCommand("near").setExecutor(new WCNear(this));
    getCommand("radar").setExecutor(new WCNear(this));
    
    getCommand("msg").setExecutor(new WCChannels(this));
    getCommand("tell").setExecutor(new WCChannels(this));
    getCommand("t").setExecutor(new WCChannels(this));
    getCommand("r").setExecutor(new WCChannels(this));
    getCommand("l").setExecutor(new WCChannels(this));
    getCommand("o").setExecutor(new WCChannels(this));
    getCommand("wcstats").setExecutor(new WCChannels(this));
    
    getCommand("spawn").setExecutor(new WCSpawn(this));
    getCommand("s").setExecutor(new WCSpawn(this));

    getCommand("forcefield").setExecutor(new StaticField(this));
    getCommand("ff").setExecutor(new StaticField(this));
    
    getCommand("home").setExecutor(new WCHome(this));
    getCommand("sethome").setExecutor(new WCHome(this));
    getCommand("remhome").setExecutor(new WCHome(this));
    getCommand("delhome").setExecutor(new WCHome(this));
    
    getCommand("sell").setExecutor(new WCSell(this));
    
    getCommand("pay").setExecutor(new WCPay(this));
    
    getCommand("bday").setExecutor(new TraceFW(this));
    
    getCommand("warp").setExecutor(new WCWarps(this));
    getCommand("w").setExecutor(new WCWarps(this));
    getCommand("setwarp").setExecutor(new WCWarps(this));
    getCommand("remwarp").setExecutor(new WCWarps(this));
    getCommand("delwarp").setExecutor(new WCWarps(this));
    
    getCommand("rankup").setExecutor(new WCRanks(this));
    
    getCommand("report").setExecutor(new WCReport(this));
    
    getCommand("mail").setExecutor(new WCMail(this));
    
    getCommand("search").setExecutor(new WCHelp(this));

    getCommand("root").setExecutor(new WCMenus(this));

    getCommand("invsee").setExecutor(new WCInvSee(this));
   
    getCommand("reboot").setExecutor(new WCReboot(this));
    
    getCommand("bal").setExecutor(new WCBal(this));
    getCommand("balance").setExecutor(new WCBal(this));
    
    getCommand("qc").setExecutor(new WCMenus(this));
    
    getCommand("tp").setExecutor(new WCTele(this));
    getCommand("tphere").setExecutor(new WCTele(this));
    getCommand("tpa").setExecutor(new WCTele(this));
    getCommand("tpahere").setExecutor(new WCTele(this));
    getCommand("tpall").setExecutor(new WCTele(this));
    getCommand("tpaall").setExecutor(new WCTele(this));
  }

  public void saveYamls() {
	  
    try{
      config.save(configFile);
      datacore.save(datacoreFile);
      games.save(gamesFile);
      help.save(helpFile);
      systemYaml.save(systemFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadYamls() {
	  
    try{
      config.load(configFile);
      datacore.load(datacoreFile);
      games.load(gamesFile);
      help.load(helpFile);
      systemYaml.load(systemFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  File listFile = new File("./plugins/WaterCloset/system.yml");
  YamlConfiguration listYaml = YamlConfiguration.loadConfiguration(listFile);
	
  List <String> userList = listYaml.getStringList("Users.Total");

  private void firstRun() throws Exception {
	 
    String files = "config help games datacore mail system";
    String[] flatFiles = files.split(" ");
    
    for (int x = 0; x <= 4; x++){
    	file = new File("./plugins/WaterCloset/" + flatFiles[x] + ".yml");
    	if (!file.exists()){
    		file.createNewFile();
    	}
    	yaml = YamlConfiguration.loadConfiguration(file);
    	switch (x){
	    	case 0: 
	    		config = yaml;
	    		configFile = file;
	    		break;
	    	case 1:
	    		help = yaml;
	    		helpFile = file;
	    		break;
	    	case 2: 
	    		games = yaml;
	    		gamesFile = file;
	    		break;
	    	case 3: 
	    		datacore = yaml;
	    		datacoreFile = file;
	    		break;
	    	case 4:
	    		systemYaml = yaml;
	    		systemFile = file;
	    		break;
    	}
    }
  }
	  
  public static void s(Player p, String s){
		p.sendMessage(Utils.AS(WCMail.WC + s));
  }
	  
  public static void s2(Player p, String s){
	   p.sendMessage(Utils.AS(s));
  }
  
  public void sendAnnounce(){
		
	List<String> messages = config.getStringList("Announcements");
	Bukkit.broadcastMessage(AS(messages.get(msg)));
		
		if (msg == messages.size() - 1){
			msg = 0;
		} else {
			msg = msg + 1;
		}
  }
  
  public void updateBoard(){
	  
	  for (Player player : Bukkit.getOnlinePlayers()){
		  
		  int x = 0;
		  
		  for (ItemStack i : player.getInventory()){
			  if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().hasDisplayName()){
				  if (i.getItemMeta().getDisplayName().toLowerCase().contains("paragon") && i.getType().equals(Material.STAINED_CLAY)){
					  int amt = i.getAmount();
					  player.getInventory().addItem(invManager.makeItem("§e§o§lPARAGON TOKEN", "§7§oIt's currency!", true, Enchantment.DURABILITY, 10, 11, Material.INK_SACK, amt));
					  player.getInventory().setItem(x, new ItemStack(Material.AIR));
					  s(player, "Auto-sweep has found old paragons in your inventory and converted them to tokens.");
				  }
			  }
			  x++;
		  }
		  
		  if (wcm.getWCPlayer(player.getName()) == null){
			  wcm.userCreate(player);
		  }
		  
		  WCPlayer wcp = wcm.getWCPlayer(player.getName());
		  int xp = wcp.getPatrolExp();
		  int lvl = wcp.getPatrolLevel();
		  int neededXP = (int) (100 + (Math.pow(wcp.getPatrolLevel(), 2) + wcp.getPatrolLevel()));
		  
		  if (xp >= neededXP){
			  Bukkit.broadcastMessage(AS("&4>> " + player.getDisplayName() + " &dhas reached Patrol Level " + (lvl+1) + "&d! &4<<"));
			  wcp.setPatrolExp(xp - neededXP);
			  wcp.setPatrolLevel(lvl + 1);
			  wcm.updatePlayerMap(player.getName(), wcp);
		  }
		  
		  if (!wcp.getCanSoar()){
			  if (wcp.getCanSoarTimer() <= System.currentTimeMillis()/1000){
				  wcp.setCanSoar(true);
				  s(player, "Your soar timer has refreshed.");
			  }
			  if (wcp.getSoarTimer() <= System.currentTimeMillis()/1000){
				  wcp.setSoarTimer(999999999999999999L);
				  player.setFlying(false);
				  player.setAllowFlight(false);
				  s(player, "Your wings have collapsed.");
			  }
			  wcm.updatePlayerMap(player.getName(), wcp);
		  }
		  
		  getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(player));
		  
		  if (!afkTimer.containsKey(player.getName())){
			  afkTimer.put(player.getName(), 0);
		  }
		  
		  afkTimer.put(player.getName(), afkTimer.get(player.getName()) + 8);
		  
		  if (afkTimer.get(player.getName()) >= 180 && !afkers.contains(player)){
			  Bukkit.broadcastMessage(AS("&7&o" + player.getDisplayName() + " &7&ois afk."));
			  
			  if (("&7[afk] " + player.getDisplayName()).length() > 16){
				  player.setPlayerListName(AS("&7[afk] " + player.getDisplayName()).substring(0, 16));
			  } else {
				  player.setPlayerListName(AS("&7[afk] " + player.getDisplayName()));
			  }
			  
			  afkers.add(player);
		  }
	  }
  }
}