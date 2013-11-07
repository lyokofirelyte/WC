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
import org.kitteh.tag.TagAPI;

import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WC.WCMain;

public class WCJoin implements Listener {
  
WCMain plugin;
String completed;
WCPlayer wcp;
WCAlliance wca;

public WCJoin(WCMain instance){
plugin = instance;
}

  @EventHandler(priority=EventPriority.NORMAL)
  public boolean onPlayerJoin(final PlayerJoinEvent event) {
	  
    final Player p = event.getPlayer();
    event.setJoinMessage(null);

	userCreate(p);
	wcp = plugin.wcm.getWCPlayer(p.getName());
	wca = plugin.wcm.getWCAlliance(wcp.getAlliance());

	List <String> joinMessages = plugin.config.getStringList("Core.JoinMessages");
	Random rand = new Random();
	int randomNumber = rand.nextInt(joinMessages.size());
	final String joinMessage = (String)joinMessages.get(randomNumber);
	
	if (!wcp.getInAlliance()) {
	  wcp.setAllianceRank("Guest");
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
	    completed = "&7" + wcp.getNick();
	}
	
	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
	{ public void run(){
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "enick " + p.getName() + " " + completed);
	    Bukkit.broadcastMessage("§e" + joinMessage.replace("%p", new StringBuilder("§7").append(p.getDisplayName()).append("§e").toString()).replace(" ", " §e"));
	  }
	}
	, 20L);
	    	    
	updatePlayer(wcp, p.getName());
	TagAPI.refreshPlayer(p);
	
	WCMain.s(p, "Welcome, " + p.getDisplayName() + "&d! We are currently running &6WaterCloset v4.1 &dby Hugs.");
	
    return true;
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
			yaml.set("Nick", p.getName());
			
			File listFile = new File("./plugins/WaterCloset/system.yml");
			YamlConfiguration listYaml = YamlConfiguration.loadConfiguration(listFile);
			
			List<String> users = listYaml.getStringList("TotalUsers");
			users.add(p.getName());
			listYaml.set("TotalUsers", users);
			
			try {
				yaml.save(file);
				listYaml.save(listFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			plugin.wcm.setupPlayer(p.getName());
		}
	}
	
	 public void updatePlayer(WCPlayer wcp, String name){
			plugin.wcm.updatePlayerMap(name, wcp);  
			wcp = plugin.wcm.getWCPlayer(name);
	 }
}