package com.github.lyokofirelyte.WC;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.lyokofirelyte.WC.Commands.WCBack;
import com.github.lyokofirelyte.WC.Commands.WCHelp;
import com.github.lyokofirelyte.WC.Commands.WCHome;
import com.github.lyokofirelyte.WC.Commands.WCInvSee;
import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Commands.WCRanks;
import com.github.lyokofirelyte.WC.Commands.WCReboot;
import com.github.lyokofirelyte.WC.Commands.WCReport;
import com.github.lyokofirelyte.WC.Commands.WCWarps;
import com.github.lyokofirelyte.WC.Extras.StaticField;
import com.github.lyokofirelyte.WC.Extras.TimeStampEX;
import com.github.lyokofirelyte.WC.Extras.TraceFW;
import com.github.lyokofirelyte.WC.Extras.w;
import com.github.lyokofirelyte.WC.Listener.WCBlockBreak;
import com.github.lyokofirelyte.WC.Listener.WCBlockPlace;
import com.github.lyokofirelyte.WC.Listener.WCDeath;
import com.github.lyokofirelyte.WC.Listener.WCJoin;
import com.github.lyokofirelyte.WC.Listener.WCMiscEvents;
import com.github.lyokofirelyte.WC.Listener.WCQuit;
import com.github.lyokofirelyte.WC.Listener.WCTP;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.RebootManager;
import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCManager;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCMain extends JavaPlugin {
	
  public WCVault vaultMgr = new WCVault(this);
  
  public File configFile;
  public File datacoreFile;
  public File gamesFile;
  public File helpFile;
  public File file;
  
  public YamlConfiguration config = new YamlConfiguration();
  public YamlConfiguration datacore = new YamlConfiguration();
  public YamlConfiguration games = new YamlConfiguration();
  public YamlConfiguration help = new YamlConfiguration();
  public YamlConfiguration yaml;

  private String url;
  private String username;
  private String password;
  private Connection conn;
  
  public WCAPI api;
  public WCManager wcm;
  public RebootManager rm;

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
	  pm.registerEvents(new WCTP(this), this);
	  pm.registerEvents(new WCSigns(this), this);
	  pm.registerEvents(new WCMenus(this), this);
	  pm.registerEvents(new WCMail(this),this);
	  pm.registerEvents(new WCInvSee(this),this);
	  pm.registerEvents(new WCMenus(this),this);
	
	  this.vaultMgr.hookSetup();
	    
	    try {
	      firstRun();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	
	  url = config.getString("url");
	  username = config.getString("username");
	  password = config.getString("password");
	    
	  Plugin WCAPI = Bukkit.getServer().getPluginManager().getPlugin("WCAPI");
	  this.api = (WCAPI) WCAPI;
	    
	  wcm = new WCManager(api);
	  rm = new RebootManager(api);
	    
	  if ((url == null) || (username == null) || (password == null) || WCAPI == null) {
	     Bukkit.getServer().getLogger().info("You must provide a url, username, and password in the config.yml. That or the API is missing.");
	     Bukkit.getServer().getPluginManager().disablePlugin(this);
	  }
	
	  registerCommands();
	    
	  Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
	  public void run() { updateBoard();} }, 2L, 200L);

	  getLogger().log(Level.INFO, "WaterCloset is ready! Hooked with API!");
    
  }

  public void onDisable() {
	  
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

  public WCManager getWCM(){
	  return wcm;
  }
  
  public RebootManager getRebootManager(){
	  return rm;
  }
  
  private void registerCommands() {
    getCommand("watercloset").setExecutor(new WCCommands(this));
    getCommand("wc").setExecutor(new WCCommands(this));
    getCommand("blame").setExecutor(new WCCommands(this));
    getCommand("member").setExecutor(new WCCommands(this));
    getCommand("google").setExecutor(new WCCommands(this));

    getCommand("waa").setExecutor(new WACommandEx(this));
    getCommand("nick").setExecutor(new WACommandEx(this));

    getCommand("timestamp").setExecutor(new TimeStampEX(this));
    getCommand("getnick").setExecutor(new TimeStampEX(this));
    getCommand("stringbuilder").setExecutor(new TimeStampEX(this));
    getCommand("itemname").setExecutor(new TimeStampEX(this));

    getCommand("msg").setExecutor(new WCChannels(this));
    getCommand("tell").setExecutor(new WCChannels(this));
    getCommand("t").setExecutor(new WCChannels(this));
    getCommand("r").setExecutor(new WCChannels(this));
    getCommand("l").setExecutor(new WCChannels(this));
    getCommand("o").setExecutor(new WCChannels(this));
    getCommand("wcstats").setExecutor(new WCChannels(this));

    getCommand("forcefield").setExecutor(new StaticField(this));
    getCommand("ff").setExecutor(new StaticField(this));
    
    getCommand("home").setExecutor(new WCHome(this));
    getCommand("sethome").setExecutor(new WCHome(this));
    getCommand("remhome").setExecutor(new WCHome(this));
    getCommand("delhome").setExecutor(new WCHome(this));
    
    getCommand("bday").setExecutor(new TraceFW(this));
    
    getCommand("warp").setExecutor(new WCWarps(this));
    getCommand("w").setExecutor(new WCWarps(this));
    getCommand("setwarp").setExecutor(new WCWarps(this));
    getCommand("remwarp").setExecutor(new WCWarps(this));
    getCommand("delwarp").setExecutor(new WCWarps(this));
    
    getCommand("back").setExecutor(new WCBack(this));
    getCommand("bk").setExecutor(new WCBack(this));
    
    getCommand("rankup").setExecutor(new WCRanks(this));
    
    getCommand("report").setExecutor(new WCReport(this));
    
    getCommand("mail").setExecutor(new WCMail(this));
    
    getCommand("search").setExecutor(new WCHelp(this));

    getCommand("root").setExecutor(new WCMenus(this));

    getCommand("invsee").setExecutor(new WCInvSee(this));
    
    getCommand("ww").setExecutor(new w(this));
   
    getCommand("reboot").setExecutor(new WCReboot(this));
  }

  public void saveYamls() {
	  
    try{
      config.save(configFile);
      datacore.save(datacoreFile);
      games.save(gamesFile);
      help.save(helpFile);
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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void firstRun() throws Exception {
	 
    String files = "config help games datacore";
    String[] flatFiles = files.split(" ");
    
    for (int x = 0; x < 3; x++){
    	file = new File("./plugins/WaterCloset/" + flatFiles[x] + ".yml");
    	if (!file.exists()){
    		file.createNewFile();
    	}
    	yaml = YamlConfiguration.loadConfiguration(file);
    	switch (x){
	    	case 0: 
	    		config = yaml;
	    		configFile = file;
	    	case 1:
	    		help = yaml;
	    		helpFile = file;
	    	case 2: 
	    		games = yaml;
	    		gamesFile = file;
	    	case 3: 
	    		datacore = yaml;
	    		datacoreFile = file;
    	}
    }
  }
	  
  public static void s(Player p, String s){
		p.sendMessage(Utils.AS(WCMail.WC + s));
  }
	  
  public static void s2(Player p, String s){
	   p.sendMessage(Utils.AS(s));
  }
	  
	  public void updateBoard(){
		  
		  for (Player p : Bukkit.getOnlinePlayers()){
		  
			    WCPlayer wcp = wcm.getWCPlayer(p.getName());
			    WCAlliance wca = wcm.getWCAlliance(wcp.getAlliance());
			    
			    Objective localObjective = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				
				if (localObjective != null){
					
					Scoreboard board = manager.getNewScoreboard();
					
					Objective o1 = board.registerNewObjective("wa", "dummy");
					o1.setDisplaySlot(DisplaySlot.SIDEBAR);
					if (p.getDisplayName().length() > 16){
						o1.setDisplayName(p.getDisplayName().substring(0, 16));
					} else {
						o1.setDisplayName(p.getDisplayName());
					}
					
					Score balance = o1.getScore(Bukkit.getOfflinePlayer("§3Balance:"));
					Score paragons = o1.getScore(Bukkit.getOfflinePlayer("§3Paragon Lvl:"));
					Score online = o1.getScore(Bukkit.getOfflinePlayer("§9Online:"));
					Score rank = o1.getScore(Bukkit.getOfflinePlayer("§3Rank: " + Utils.AS(WCVault.chat.getPlayerPrefix(p))));
					Score options = o1.getScore(Bukkit.getOfflinePlayer("§5/root"));
					Score alliance2;
					
					if (!wcp.getInAlliance()){
						Score alliance = o1.getScore(Bukkit.getOfflinePlayer("§7Forever§8Alone"));
						alliance.setScore(1);
					} else {
						String completed = (wcm.getCompleted2(wcp.getAlliance(), wca.getColor1(), wca.getColor2()));
						if (completed.length() >= 16){
							alliance2 = o1.getScore(Bukkit.getOfflinePlayer(completed.substring(0, 16)));
						} else {
							alliance2 = o1.getScore(Bukkit.getOfflinePlayer(completed));
						}
						alliance2.setScore(wca.getMemberCount());
					}
					
					paragons.setScore(wcp.getParagonLevel());
					balance.setScore((int) WCVault.econ.getBalance(p.getName()));
					rank.setScore(1);
					online.setScore(Bukkit.getOnlinePlayers().length);
					options.setScore(0);
					p.setScoreboard(board);
				} 
	  	}
	  }	
}