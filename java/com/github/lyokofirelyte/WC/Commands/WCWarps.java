package com.github.lyokofirelyte.WC.Commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WC.WCMain;

public class WCWarps implements CommandExecutor {
	
	WCMain plugin;
	public WCWarps(WCMain instance){
	plugin = instance;
	}

	
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		  
		  if (cmd.getName().equalsIgnoreCase("remwarp") || cmd.getName().equalsIgnoreCase("delwarp")){
			  
			  Player p = ((Player)sender);
			  
			  if (args.length == 0){
				  WCMain.s(p, "Try /remwarp <name>.");
				  return true;
			  }
			  
			  File warpFile = new File(plugin.getDataFolder() + File.separator + "Warps", args[0].toLowerCase() + ".yml");
			  
			  if (!warpFile.exists()) {
				  WCMain.s(p, "That warp does not exist!");
				  return true;
			  }
			  
			  warpFile.delete();
			  
			  String path  = plugin.getDataFolder() + "/Warps/";
              File folder = new File(path);
              String[] fileNames = folder.list();
              
			  WCMain.s(p, "Warp removed. There are now &6" + fileNames.length + " &dwarps.");
		  }
		  
		  if (cmd.getName().equalsIgnoreCase("setwarp")){
			  
			  Player p = ((Player)sender);
			  
			  if (args.length == 0){
				  WCMain.s(p, "Try /setwarp <name>.");
				  return true;
			  }
			  
			  YamlConfiguration warpLoad = new YamlConfiguration();
			  File warpFile = new File(plugin.getDataFolder() + File.separator + "Warps", args[0].toLowerCase() + ".yml");
			  
			  if (!warpFile.exists()) {
				  try {
					warpFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			  }
		      
		      try {
		    	  warpLoad.load(warpFile);
		      } catch (Exception e) {
				  e.printStackTrace();
				  }
		      
		      warpLoad.set("world", p.getWorld().getName());
		      warpLoad.set("x", p.getLocation().getX());
		      warpLoad.set("y", p.getLocation().getY());
		      warpLoad.set("z", p.getLocation().getZ());
		      warpLoad.set("yaw", p.getLocation().getYaw());
		      warpLoad.set("pitch", p.getLocation().getPitch());
		      
		      try {
				warpLoad.save(warpFile);
		      } catch (IOException e) {
				e.printStackTrace();
		      	}
		      
		      String path  = plugin.getDataFolder() + "/Warps/";
              File folder = new File(path);
              String[] fileNames = folder.list();
		      
		      WCMain.s(p, "Set warp &6" + args[0].toLowerCase() + "&d. There are now &6" + fileNames.length + " &dwarps.");

		  }
		  
		  if (cmd.getName().equalsIgnoreCase("warp") || cmd.getName().equalsIgnoreCase("w")){
			  
			  final Player p = ((Player)sender);
			  
			  if (args.length == 0){
				    String path  = plugin.getDataFolder() + "/Warps/";
	                File folder = new File(path);
	                String[] fileNames = folder.list();
	                Arrays.sort(fileNames);
	      			SortedMap<Integer, String> map = new TreeMap<Integer, String>();
	                
	                for(int i = 0; i < fileNames.length; i++){
	    			  map.put(i, fileNames[i]);
	                }
	                
	                paginate(sender, map, 1, 20, fileNames.length);
	                return true;
			  }
			  
			  if (Utils.isInteger(args[0])){
				    String path  = plugin.getDataFolder() + "/Warps/";
	                File folder = new File(path);
	                String[] fileNames = folder.list();
	                Arrays.sort(fileNames);
	      			SortedMap<Integer, String> map = new TreeMap<Integer, String>();
	                
	                for(int i = 0; i < fileNames.length; i++){
	    			  map.put(i, fileNames[i]);
	                }
	                
	                if (Integer.parseInt(args[0]) > Math.round((double) (fileNames.length / 20))){
	                	WCMain.s(p, "There aren't that many pages!");
	                	return true;
	                }
	                
	                paginate(sender, map, Integer.parseInt(args[0]), 20, fileNames.length);
	                return true;
			  }
			  
				YamlConfiguration warpLoad = new YamlConfiguration();
				File fileToCheck = new File(plugin.getDataFolder() + File.separator + "Warps", args[0].toLowerCase() + ".yml");

				    if (!fileToCheck.exists()) {
				    	WCMain.s(p, "That warp does not exist!");
				    	return true;
				    }
				    
				    try {
				    	  warpLoad.load(fileToCheck);
				      } catch (Exception e) {
						  e.printStackTrace();
						  }
				    
				
			    World w = Bukkit.getWorld(warpLoad.getString("world"));
			    double x = warpLoad.getInt("x");
			    double y = warpLoad.getInt("y");
			    double z = warpLoad.getInt("z");
			    float yaw = warpLoad.getInt("yaw");
			    float pitch = warpLoad.getInt("pitch");
			    Location warpTo = new Location(w, x, y+1, z, yaw, pitch);
			    
			    if (args.length == 2){
			    	OfflinePlayer tpOther = Bukkit.getOfflinePlayer(args[1]);
			    	
			    		if (!tpOther.isOnline()){
			    			WCMain.s(p, "That player is not online!");
			    			return true;
			    		}
			    		
			    	Player other = Bukkit.getPlayer(args[1]);	
					double xP = other.getLocation().getX();
					double yP = other.getLocation().getY();
					double zP = other.getLocation().getZ();

					String warpSimple = Math.round(xP) + "&f, &6" + Math.round(yP) + "&f, &6" + Math.round(zP); 
				    WCPlayer wcp = plugin.wcm.getWCPlayer(other.getName());
				    wcp.setLastLocation(other.getLocation());
				    plugin.wcm.updatePlayerMap(other.getName(), wcp);
					other.teleport(warpTo);
				    WCMain.s(other, "Warped to &6" + args[0] + " &dfrom &6" + warpSimple + " &dby " + p.getDisplayName() + "&d.");
				    return true;
			    }
			    
			    double xP = p.getLocation().getX();
			    double yP = p.getLocation().getY();
			    double zP = p.getLocation().getZ();

			    String warpSimple = Math.round(xP) + "&f, &6" + Math.round(yP) + "&f, &6" + Math.round(zP) + "&d."; 
			    WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
			    wcp.setLastLocation(p.getLocation());
			    plugin.wcm.updatePlayerMap(p.getName(), wcp);
			    p.teleport(warpTo);
				List<Location> circleblocks = Utils.circle(p.getLocation(), 3, 1, true, false, 0);
				List<Location> circleblocks2 = Utils.circle(p.getLocation(), 3, 1, true, false, 1);
				
					for (Location l : circleblocks){
						p.getWorld().playEffect(l, Effect.SMOKE, 0);
						p.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
						p.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
					}
					
					for (Location l : circleblocks2){
						p.getWorld().playEffect(l, Effect.SMOKE, 0);
						p.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
						p.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
					}
					
			    WCMain.s(p, "Warped to &6" + args[0] + " &dfrom &6" + warpSimple);
		  }
		  
	  return true;
	  
	  }
	  
	  public  void paginate(CommandSender sender, SortedMap<Integer, String> map,
			  int page, int pageLength, int warps) {
		  
		  	      StringBuilder sb = new StringBuilder();
		  	      
			      WCMain.s2((Player)sender, "&dWarps &f// &dPage &6" + String.valueOf(page) + " &dof &6" + (((map.size() % pageLength) == 0) ? map.size() / pageLength : (map.size() / pageLength) + 1) + "&d. &f(&6" + warps + "&f)&d.");
			      int i = 0, k = 0;
			      page--;
			      for (final Entry<Integer, String> e : map.entrySet()) {
			          k++;
			          if ((((page * pageLength) + i + 1) == k) && (k != ((page * pageLength) + pageLength + 1))) {
			              i++;
			              sb.append(e.getValue() + "&f, &d");
			          }
			      }
			      
			      String msg = sb.toString().trim();
			      msg = msg.substring(0, msg.length() - 6) + " ";
			      WCMain.s2((Player)sender, "&d" + msg.replace(".yml", ""));
			  }
}
