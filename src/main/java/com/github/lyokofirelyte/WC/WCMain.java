package com.github.lyokofirelyte.WC;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.github.lyokofirelyte.WC.Commands.WCAFK;
import com.github.lyokofirelyte.WC.Commands.WCDisco;
import com.github.lyokofirelyte.WC.Commands.WCGcmd;
import com.github.lyokofirelyte.WC.Commands.WCHat;
import com.github.lyokofirelyte.WC.Commands.WCHome;
import com.github.lyokofirelyte.WC.Commands.WCInvSee;
import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Commands.WCNear;
import com.github.lyokofirelyte.WC.Commands.WCNewMember;
import com.github.lyokofirelyte.WC.Commands.WCPTP;
import com.github.lyokofirelyte.WC.Commands.WCPay;
import com.github.lyokofirelyte.WC.Commands.WCPowerTool;
import com.github.lyokofirelyte.WC.Commands.WCRanks;
import com.github.lyokofirelyte.WC.Commands.WCReport;
import com.github.lyokofirelyte.WC.Commands.WCSell;
import com.github.lyokofirelyte.WC.Commands.WCSoar;
import com.github.lyokofirelyte.WC.Commands.WCSpawn;
import com.github.lyokofirelyte.WC.Commands.WCSuicide;
import com.github.lyokofirelyte.WC.Commands.WCThis;
import com.github.lyokofirelyte.WC.Commands.WCWB;
import com.github.lyokofirelyte.WC.Commands.WCWarps;
import com.github.lyokofirelyte.WC.Extras.StaticField;
import com.github.lyokofirelyte.WC.Extras.TimeStampEX;
import com.github.lyokofirelyte.WC.Extras.TraceFW;
import com.github.lyokofirelyte.WC.Extras.WCSEEKRITPARTAY;
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
import com.github.lyokofirelyte.WC.Util.LagUtils;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCManager;
import com.github.lyokofirelyte.WCAPI.WCNode;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;
import com.github.lyokofirelyte.WCAPI.Manager.InventoryManager;
import com.github.lyokofirelyte.WCAPI.Manager.RebootManager;
import com.github.lyokofirelyte.WCAPI.Manager.WCMessageType;

public class WCMain extends WCNode {
	
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
  public Map <String, Inventory> markkitInvs = new HashMap<>();
  public Map <String, Integer> afkTimer = new HashMap<>();
  public List<Entity> carts = new ArrayList<>();
  public List<Player> afkers = new ArrayList<>();
  public Map <String, WCLiftFloor> elevatorMap = new HashMap<>();
  public Map <ShapedRecipe, ItemStack> wcRecipies = new HashMap<>();
  
  private int id = 0;
  
  public Map <String, WCLiftFloor> getElevatorMap(){
	  return elevatorMap;
  }
	
  public void setElevatorMap(Map <String, WCLiftFloor> a){
	  elevatorMap = a;
  }
  
  private int msg = 0;

  public void onEnable() {

	  Plugin WCAPI = Bukkit.getServer().getPluginManager().getPlugin("WCAPI");
	  api = (WCAPI) WCAPI;

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
	  pm.registerEvents(new WCLift(this), this);
	  pm.registerEvents(new WCMineNDash(this), this);
	  
	  newCrafting();
	  
	  vaultMgr.hookSetup();
	    
	  try {
	    firstRun();
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	
	  url = config.getString("url");
	  username = config.getString("username");
	  password = config.getString("password");

	    
	  wcm = new WCManager(api);
	  rm = new RebootManager(api);
	  invManager = new InventoryManager(api);
	  wcpp = new WCPatrols(this);
	  fw = new FireworkShenans(this);
	  
	  pm.registerEvents(wcm, this);
	    
	  if ((url == null) || (username == null) || (password == null) || WCAPI == null) {
	     Bukkit.getServer().getLogger().info("You must provide a url, username, and password in the config.yml. That or the API is missing.");
	     Bukkit.getServer().getPluginManager().disablePlugin(this);
	  }
	
	  registerCommands();
	  
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
	  
	  id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new LagUtils(), 100L, 1L);
	  
	  Inventory inv = Bukkit.createInventory(null, 9, "§cLOCATIONS");
	  inv = WCMenus.addToInv(Material.FLINT, "§3PATROLS", 8, "§b< < <", 1, inv);
	  WCMenus.invs.put("patrolLocationMenu", inv);
	  loadMarkkitInvs();
  }
  
  public void saveMarkkitInvs(){
	  
	  Inventory inven = Bukkit.createInventory(null, 54, AS("&dMarkkit"));
	  List<String> invs = new ArrayList<String>();
	  
	  for (String s : markkitInvs.keySet()){ 
		  for (int i = 0; i < inven.getSize(); i++){
			  datacore.set("Markkit." + s + ".item." + i, markkitInvs.get(s).getContents()[i]);
		  }
		  invs.add(s);
	  }
	  
	  datacore.set("Markkit.LIST", invs);
  }
  
  public void loadMarkkitInvs(){
	  
	  for (String s : datacore.getStringList("Markkit.LIST")){ 
		  Inventory inv = Bukkit.createInventory(null, 54, AS("&dMarkkit"));
		  ItemStack[] contents = new ItemStack[inv.getSize()];
		  for (int i = 0; i < inv.getSize(); i++){
			  contents[i] = datacore.getItemStack("Markkit." + s + ".item." + i); 
		  }
		  inv.setContents(contents);
		  markkitInvs.put(s, inv);
	  }
  }
  
  public void autoSave(){
	 
		saveMarkkitInvs();
		saveYamls();
		
		List<String> users = systemYaml.getStringList("TotalUsers");
		
		for (String user : users){
			try {
				wcm.savePlayer(user);
			} catch (IOException e) {
				getLogger().log(Level.WARNING, "Could not save " + user + "!");
			}
		}
		
		try {
			wcm.saveAlliances();
			wcm.saveSystem(systemYaml, systemFile);
		} catch (IOException e) {
			getLogger().log(Level.WARNING, "Could not save an alliance!");
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
	  
	  saveMarkkitInvs();
	  saveYamls();
	  
	  getServer().clearRecipes();
	  getServer().getScheduler().cancelTasks(this);

	    try{
	      if (conn != null){
	        conn.close();
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
	    
	  Bukkit.getServer().getScheduler().cancelTask(id);
	  getLogger().info("WaterCloset has been disabled.");
  }

  private void registerCommands() {  
	  List<Class<?>> commandClasses = new ArrayList<Class<?>>(Arrays.asList(WCCommandsFixed.class, TimeStampEX.class, TraceFW.class, StaticField.class, WACommandEx.class, WCAFK.class, WCBal.class, WCChannels.class, WCCheats.class, WCCommands.class, WCDisco.class, WCHat.class, WCHome.class, WCInvSee.class, WCMail.class, WCMenus.class, WCNear.class, WCNewMember.class, WCPay.class, WCPowerTool.class, WCPTP.class, WCRanks.class, WCReport.class, WCSEEKRITPARTAY.class, WCSeen.class, WCSell.class, WCSoar.class, WCSudo.class, WCSuicide.class, WCSpawn.class, WCTele.class, WCWarps.class, WCWB.class, WCThis.class, WCGcmd.class));
	  api.reg.registerCommands(commandClasses, this);
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
	 
    String files = "config help games datacore system";
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
  
  public void sendAnnounce(){
		
	List<String> messages = config.getStringList("Announcements");
	callChat(WCMessageType.BROADCAST, AS(messages.get(msg)));
		
		if (msg == messages.size() - 1){
			msg = 0;
		} else {
			msg = msg + 1;
		}
  }
  
  public void updateBoard(){
	  
	  for (Player player : Bukkit.getOnlinePlayers()){
		  
		  if (wcm.getWCPlayer(player.getName()) == null){
			  wcm.userCreate(player);
		  }
		  
		  WCPlayer wcp = wcm.getWCPlayer(player.getName());
		  int xp = wcp.getPatrolExp();
		  int lvl = wcp.getPatrolLevel();
		  int neededXP = (int) (100 + (Math.pow(wcp.getPatrolLevel(), 2) + wcp.getPatrolLevel()));  
		  
		  player.setDisplayName(AS(wcm.getFullNick(player.getName()))); 
		  
		  if (xp >= neededXP){
			  callChat(WCMessageType.BROADCAST, AS("&4>> " + player.getDisplayName() + " &dhas reached Patrol Level " + (lvl+1) + "&d! &4<<"));
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
		  
		  if (afkTimer.get(player.getName()) >= 180 && afkTimer.get(player.getName()) <= 900 && !afkers.contains(player)){
			  
			  callChat(WCMessageType.BROADCAST, AS("&7&o" + player.getDisplayName() + " &7&ois afk."));
			  
			  if (("&7[afk] " + player.getDisplayName()).length() > 16){
				  player.setPlayerListName(AS("&7[afk] " + player.getDisplayName()).substring(0, 16));
			  } else {
				  player.setPlayerListName(AS("&7[afk] " + player.getDisplayName()));
			  }
			  
			  afkers.add(player);
			  
		  } else if (afkTimer.get(player.getName()) >= 900 && afkers.contains(player) && !wcp.isSuperAfk()){
			  
			  callChat(WCMessageType.BROADCAST, AS("&7&o" + player.getDisplayName() + " &7&ois super afk."));
			  
			  if (("&7[afk+] " + player.getDisplayName()).length() > 16){
				  player.setPlayerListName(AS("&7[afk+] " + player.getDisplayName()).substring(0, 16));
			  } else {
				  player.setPlayerListName(AS("&7[afk+] " + player.getDisplayName()));
			  }
			  
			  wcp.setAfkFreeze(true);
			  wcp.setSuperAfk(true);
			  wcp.setAfkSpot(player.getLocation());
			  player.performCommand("spawn");
			  api.ls.callDelay(this, this, "afkUnFreeze", player);
		  }
	  }
  }
  
  @WCDelay(time = 40L)
  public void afkUnFreeze(Player p){
	  wcm.getWCPlayer(p.getName()).setAfkFreeze(false);
  }
  
  public void newCrafting(){
	  

	  
	  i = InventoryManager.createItem("&aMajjykk Wand", new String[] {"&2It's pretty sharp!", "&7&oWCMMO Item"}, Material.STICK, 1);
	  r = new ShapedRecipe(i).shape(
			  "000", 
			  "0b0", 
			  "000").setIngredient('0', Material.BLAZE_POWDER).setIngredient('b', Material.STICK);
	  getServer().addRecipe(r);
	  
	  i = InventoryManager.createItem("&bSlayer Box", new String[] {"&3View & obtain slayer tasks", "&7&oWCMMO Item"}, Material.MOB_SPAWNER, 1);
	  r = new ShapedRecipe(i).shape(
			  "000", 
			  "0b0", 
			  "000").setIngredient('0', Material.LAPIS_ORE).setIngredient('b', Material.CHEST);
	  getServer().addRecipe(r);
	  
	  i = InventoryManager.createItem("&eTHE HARVESTER", new String[] {"&3It's a &oreally good axe.", "&7&oWCMMO Item"}, Material.DIAMOND_AXE, 1);
	  r = new ShapedRecipe(i).shape(
			  "000", 
			  "121", 
			  "000").setIngredient('0', Material.WOOD_AXE).setIngredient('1', Material.IRON_AXE).setIngredient('2', Material.DIAMOND_AXE);
	  getServer().addRecipe(r);
	  
	  i = InventoryManager.createItem("&4ROD OF DISCORD (ZOMBIE)", new String[] {"&cSummon minions to do your bidding!", "&8Right-click to change pet", "&8Left-click to summon", "&7&oWCMMO Item"}, Material.BLAZE_ROD, 1);
	  r = new ShapedRecipe(i).shape(
			  "000", 
			  "050", 
			  "000").setIngredient('5', Material.LAVA_BUCKET).setIngredient('0', Material.WATER_BUCKET);
	  getServer().addRecipe(r);
<<<<<<< HEAD
=======
	  
	  

>>>>>>> FETCH_HEAD
  }
}