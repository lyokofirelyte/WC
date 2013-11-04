package com.github.lyokofirelyte.WC.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.kitteh.tag.TagAPI;

import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
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
    
    List<String> userList = plugin.datacore.getStringList("ALL2");
    
    if (!userList.contains(p.getName())){
    	userList.add(p.getName());
    	plugin.datacore.set("ALL2", userList);
    	userTransfer(p);
    }

	userCreate(p);
    plugin.wcm.setupPlayer(p.getName());
    
    WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
    WCAlliance wca = plugin.wcm.getWCAlliance(wcp.getAlliance());
    
    event.setJoinMessage(null);
   
	setBoard(p);
	 
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
	
	TagAPI.refreshPlayer(p);
	
    return true;
  }


	public void setBoard(Player p){
		 ScoreboardUpdateEvent scoreboardEvent = new ScoreboardUpdateEvent(p, false);
		 plugin.getServer().getPluginManager().callEvent(scoreboardEvent);
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
			yaml.set("Pokes", true);
			yaml.set("Emotes", true);
			yaml.set("ItemThrow", true);
			yaml.set("Fireworks", true);
			yaml.set("Scoreboard", true);
			yaml.set("HomeSounds", true);
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
	 

	private void userTransfer(Player p) {
		
		if (plugin.datacore.getInt("Users." + p.getName() + ".MasterExp") > 0){
			
			File file = new File("./plugins/WaterCloset/Users/" + p.getName() + ".yml");
			File file2 = new File("./plugins/WaterCloset/WAAlliances/config.yml");
			File essFile = new File("./plugins/WaterCloset/oldusers/" + p.getName().toLowerCase() + ".yml");
			
			if (!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				plugin.getLogger().log(Level.INFO, "New user file created for " + p.getName() + "!");
			}
			
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
			YamlConfiguration yaml2 = YamlConfiguration.loadConfiguration(file2);
			YamlConfiguration essYaml = YamlConfiguration.loadConfiguration(essFile);
			
			yaml.set("Chat.GlobalColor", "f");
			yaml.set("Chat.PMColor", "d");
			yaml.set("Inviter", "empty");
			yaml.set("Invite", "empty");
			yaml.set("AllianceRank2", yaml2.getString("Users." + p.getName() + ".AllianceRank2"));
			yaml.set("AllianceColor", "b");
			yaml.set("LastChat", "Hugh_Jasses");
			yaml.set("Alliance", yaml2.getString("Users." + p.getName() + ".Alliance"));
			yaml.set("Pokes", true);
			yaml.set("Emotes", true);
			yaml.set("ItemThrow", true);
			yaml.set("Fireworks", true);
			yaml.set("HomeSounds", true);
			yaml.set("SideBar", true);
			yaml.set("InAlliance", yaml2.getBoolean("Users." + p.getName() + ".InAlliance"));
			yaml.set("InChat", yaml2.getBoolean("Users." + p.getName() + "inChat"));
			yaml.set("DisHandle", yaml2.getBoolean("Users." + p.getName() + "disHandeled"));
			yaml.set("ChatGuest", yaml2.getBoolean("Users." + p.getName() + "chatGuest"));
			yaml.set("ParagonLevel", plugin.datacore.getInt("Users." + p.getName() + ".ParagonLevel"));
			yaml.set("HasNick", false);
			yaml.set("HasInvite", false);
			yaml.set("DepositExp", false);
			yaml.set("DeathCount", plugin.datacore.getInt("Users." + p.getName() + ".DeathCount"));
			yaml.set("Balance", Integer.parseInt(essYaml.getString("money")));
			yaml.set("Scoreboard", true);
			yaml.set("Exp", plugin.datacore.getInt("Users." + p.getName() + ".MasterExp"));
			
			List <String> homeList = yaml.getStringList("Homes.List");
			List<String> newHomeList = new ArrayList<>();
			int x = 0;
				for (String a : homeList){
					newHomeList.add(homeList.get(x) + " " + yaml.getString("Homes." + a));
					x++;
				}
			yaml.set("HomeList", newHomeList); 
			yaml.set("BlocksMined", 0);
			
			try {
				yaml.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			plugin.getLogger().log(Level.INFO, "Settings transfered from WCV3 -> WCV4 for " + p.getDisplayName() + "!");
			WCMain.s(p, "&6system &f-> " + p.getDisplayName() + " &dwe tried to move your data from WCV3 -> WCV4!");
			WCMain.s(p, "&6system &f-> " + p.getDisplayName() + " &dIf anything got lost, mail Hugs!");
			WCMain.s(p, "&c@log + 24 settings & " + homeList.size() + " &chomes moved with " + essYaml.getInt("money") + " &cbalance");
		}
	}
	
}