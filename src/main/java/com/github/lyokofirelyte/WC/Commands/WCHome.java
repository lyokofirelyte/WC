package com.github.lyokofirelyte.WC.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WC.WCMain;

public class WCHome implements CommandExecutor {
	
	WCMain plugin;
	public WCHome(WCMain instance){
	plugin = instance;
    }
	
	String commands = "home sethome remhome delhome";
	
	  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		  if (commands.contains(label.toLowerCase())){
			  
			  WCPlayer wcp = plugin.wcm.getWCPlayer(sender.getName());
			  List<String> homes = wcp.getHomes();
			  Player p = ((Player)sender);
			  String pName = p.getName();
			  int homeLimit = limitCheck(p);
			  
			  switch (label.toLowerCase()){
				  case "home":
					  home(args, wcp, p, pName, homeLimit, homes);
					  break;
				  case "sethome":
				     sethome(args, wcp, p, pName, homeLimit, homes);
				     break;
				  case "remhome": case "delhome":
					 remhome(args, wcp, p, pName, homeLimit, homes);
					 break;
			  }
		  }
	  
		  return true;
	  }

	public void home(String[] args, WCPlayer wcp, Player p, String pName, int homeLimit, List<String> homes) {
		
		
		if (args.length == 0){
				if (homes.size() <= 0){
					WCMain.s(p, "You have no homes! Set one with /sethome <name>. Homes used: 0/" + homeLimit + "&d.");
				} else {
					viewHomes(pName, homes, homeLimit, p);
				}
			return;
		}
		
		for (String h : homes){
			if (h.startsWith(args[0].toLowerCase())){
				String[] hSplit = h.split(" ");
				tpHome(Utils.createString(hSplit, 1), p, hSplit[0], wcp);
				return;
			}
		}
		
		viewHomes(pName, homes, homeLimit, p);

		
	}

	private void tpHome(String loc, Player p, String homeName, WCPlayer wcp) {
		
		String[] locSplit = loc.split(",");
		
		double xF = p.getLocation().getX();
		double yF = p.getLocation().getY();
		double zF = p.getLocation().getZ();
	
		long xSimple = Math.round(xF);
		long ySimple = Math.round(yF);
		long zSimple = Math.round(zF);
		
		double x = Double.parseDouble(locSplit[0]);
		double y = Double.parseDouble(locSplit[1]);
		double z = Double.parseDouble(locSplit[2]);
		float yaw = Float.parseFloat(locSplit[3]);
		float pitch = Float.parseFloat(locSplit[4]);
		World world = Bukkit.getWorld(locSplit[5]);
		Location homeLanding = new Location(world, x, y+1.2, z, yaw, pitch);
		p.teleport(homeLanding);
		List<Location> circleblocks = Utils.circle(p, p.getLocation(), 3, 1, true, false, 0);
		List<Location> circleblocks2 = Utils.circle(p, p.getLocation(), 3, 1, true, false, 1);
		
		if (wcp.homeSounds){
			p.getWorld().playSound(p.getLocation(), Sound.PORTAL_TRAVEL, 3.0F, 0.5F);
		}
			
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
		
		WCMain.s(p, "Teleported to &6" + homeName + " &dfrom &6" + xSimple + "&f, &6" + ySimple + "&f, &6" + zSimple + "&d.");
		
	}

	private void viewHomes(String pName, List<String> homes, int homeLimit, Player p) {
		
		if (homes.size() >= homeLimit){
			WCMain.s(p, "Viewing homes for " + p.getDisplayName() + " &c(" + homes.size() + "&c/" + homeLimit + "&c)");
		} else {
			WCMain.s(p, "Viewing homes for " + p.getDisplayName() + " &a(" + homes.size() + "&a/" + homeLimit + "&a)");
		}
		
		for (String currentHome : homes){
			String[] hSplit = currentHome.split(" ");
			WCMain.s2(p, "&a| &6" + hSplit[0]);
		}
	}

	private int limitCheck(Player p) {
		
		String permissions = "wa.admin wa.staff wa.national wa.shirian wa.serf";
		String[] perms = permissions.split(" ");
		int x = 6;
			for (String a : perms){
				x--;
				if (p.hasPermission(a)){
					if (x == 5){
						return 1337;
					} else {
						return x;
					}
				}
			}
		return 0;
	}

	public void sethome(String[] args, WCPlayer wcp, Player p, String pName, int homeLimit, List<String> homes) {
		
		if (args.length == 0 && homes.size() == 0){
			WCMain.s(p, "Try /sethome <name>");
			return;
		} else if (args.length == 0){
			viewHomes(pName, homes, homeLimit, p);
			return;
		}

		if (homes.size() >= homeLimit && !homes.contains(args[0])){
			viewHomes(pName, homes, homeLimit, p);
			return;
		}
		
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		float yaw = p.getLocation().getYaw();
		float pitch = p.getLocation().getPitch();
		String world = p.getWorld().getName();
		String xyz = Math.round(x) + "," + Math.round(y) + "," + Math.round(z) + "," + Math.round(yaw) + "," + Math.round(pitch) + "," + world;
		
		wcp.addHome(args[0] + " " + xyz);
		updatePlayer(wcp, p.getName());
		
		WCMain.s(p, "Set home &6" + args[0] + " &dat &6" + Math.round(x) + "&f,&6 " + Math.round(y) + "&f,&6 " + Math.round(z) + "&d.");
		
		if (homes.size() >= homeLimit){
			WCMain.s2(p, "&cHomes remaining&f: &c0/" + homeLimit);
		} else {
			WCMain.s2(p, "&aHomes remaining&f: &a" + (homeLimit - homes.size()) + "/" + homeLimit);
		}
	} 
	
	public void remhome(String[] args, WCPlayer wcp, Player p, String pName, int homeLimit, List<String> homes) {

		if (args.length == 0 && homes.size() == 0){
			WCMain.s(p, "Try /remhome or /delhome <name>");
			return;
		} else if (args.length == 0){
			viewHomes(pName, homes, homeLimit, p);
			return;
		}
		
		int x = 0;

		for (String h : homes){
			if (!h.startsWith(args[0].toLowerCase())){
				x++;
					if (x >= homes.size()){
						viewHomes(pName, homes, homeLimit, p);
						return;
					}
			}
		}

		for (String a : homes){
			if (a.startsWith(args[0])){
				wcp.remHome(a);
				updatePlayer(wcp, p.getName());
				WCMain.s(p, "Removal successful.");
				WCMain.s2(p, "&aHomes remaining&f: &a" + (homeLimit - homes.size()) + "/" + homeLimit);	
				return;
			}
		}

	} 
	
	 public void updatePlayer(WCPlayer wcp, String name){
			plugin.wcm.updatePlayerMap(name, wcp);  
			wcp = plugin.wcm.getWCPlayer(name);
	 }
}