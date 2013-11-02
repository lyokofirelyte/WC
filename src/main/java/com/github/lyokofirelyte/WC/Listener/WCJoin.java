package com.github.lyokofirelyte.WC.Listener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WC.WCMain;

public class WCJoin implements Listener {
  
WCMain plugin;
String completed;

public WCJoin(WCMain instance){
plugin = instance;
}

  @EventHandler(priority=EventPriority.LOW)
  public boolean onPlayerJoin(PlayerJoinEvent event) {
	  
    final Player p = event.getPlayer();
    
    List<String> userList = plugin.datacore.getStringList("Users.Total");
    
    if (!userList.contains(p.getName())){
    	userList.add(p.getName());
    	plugin.datacore.set("Users.Total", userList);
    }

	userCreate(p);

    plugin.wcm.setupPlayer(p.getName());
    
    WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
    WCAlliance wca = plugin.wcm.getWCAlliance(wcp.getAlliance());
    
    event.setJoinMessage(null);
   
	setBoard(p, wcp, wca);
	 
    List <String> joinMessages = plugin.config.getStringList("Core.JoinMessages");
    Random rand = new Random();
    int randomNumber = rand.nextInt(joinMessages.size());
    final String joinMessage = (String)joinMessages.get(randomNumber);

    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
    { public void run(){
        Bukkit.broadcastMessage("§e" + joinMessage.replace("%p", new StringBuilder("§7").append(p.getDisplayName()).append("§e").toString()).replace(" ", " §e"));
      }
    }
    , 15L);

    if (!wcp.getInAlliance()) {
      wcp.setAllianceRank("Guest");
    }
    
	if (!wcp.hasNick()){
		wcp.setNick(p.getName());
		updatePlayer(wcp, p.getName());
	}
	
	if (plugin.wcm.getWCAlliance(wcp.getAlliance()) == null){
		wcp.setAlliance("ForeverAlone");
		wcp.setInAlliance(false);
		wcp.setAllianceRank("Guest");
		updatePlayer(wcp, p.getName());
	}
    
    if (wcp.getInAlliance()){
    	completed = plugin.wcm.getCompleted(wcp.getNick(), wca.getColor1(), wca.getColor2());
    } else {
    	completed = "&7" + p.getName().substring(0, p.getName().length()/2) + "&8" + p.getName().substring(p.getName().length()/2);
    }
    
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
    { public void run(){
    	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "enick " + p.getName() + " " + completed);
      }
    }
    , 10L);
	updatePlayer(wcp, p.getName());
	
	//TagAPI.refreshPlayer(p);
	
    return true;
  }


	public void setBoard(Player p, WCPlayer wcp, WCAlliance wca){
		
		
		Objective localObjective = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		
		if (localObjective == null){
			
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
				String completed = (plugin.wcm.getCompleted2(wcp.getAlliance(), wca.getColor1(), wca.getColor2()));
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
		} else {
			p.setScoreboard(manager.getNewScoreboard());
		}
	}
	
	public void userCreate(Player p) {
		
		File file = new File("./plugins/WaterCloset/Users/" + p.getName() + ".yml");
			if (!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				plugin.getLogger().log(Level.INFO, "New user file created for " + p.getName() + "!");
			
			
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
			yaml.set("Chat.GlobalColor", "f");
			yaml.set("Chat.PMColor", "d");
			yaml.set("Inviter", "empty");
			yaml.set("Invite", "empty");
			yaml.set("AllianceRank2", "Guest");
			yaml.set("AllianceColor", "b");
			yaml.set("LastChat", "Hugh_Jasses");
			yaml.set("Alliance", "ForeverAlone");
			try {
				yaml.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	 public void updatePlayer(WCPlayer wcp, String name){
			plugin.wcm.updatePlayerMap(name, wcp);  
			wcp = plugin.wcm.getWCPlayer(name);
	 }
	
}