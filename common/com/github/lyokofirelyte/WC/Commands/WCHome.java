package com.github.lyokofirelyte.WC.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WC.WCMain;

public class WCHome{
	
	WCMain plugin;
	public WCHome(WCMain instance){
	plugin = instance;
    }
	
	String bleh;
	
	String commands = "home sethome remhome delhome";
	
	  @WCCommand(aliases = {"home"}, desc = "Teleports you back home", help = "/home <home name>", player = true)
	  public void onHome(Player p, String[] args){

			  WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
			  List<String> homes = wcp.getHomes();
			  String pName = p.getName();
			  int homeLimit = limitCheck(p);

					  home(args, wcp, p, pName, homeLimit, homes);
		}
		  
		@WCCommand(aliases = {"sethome"}, desc = "Set a new home", help = "/sethome <name>", min = 1, max = 1, player = true)
		public void onSetHome(Player p, String[] args){
			
			  WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
			  List<String> homes = wcp.getHomes();
			  String pName = p.getName();
			  int homeLimit = limitCheck(p);
			  
				     sethome(args, wcp, p, pName, homeLimit, homes);
		}
		
		@WCCommand(aliases = {"remhome", "delhome"}, desc = "Remove a home", help = "/remhome <name>", min = 1, max = 1, player = true)
		public void onRemHome(Player p, String[] args){
			
			  WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
			  List<String> homes = wcp.getHomes();
			  String pName = p.getName();
			  int homeLimit = limitCheck(p);
			  
					 remhome(args, wcp, p, pName, homeLimit, homes);

		}

	public void home(String[] args, WCPlayer wcp, Player p, String pName, int homeLimit, List<String> homes) {
		
		
		if (args.length == 0){
				if (homes.size() <= 0){
					WCUtils.s(p, "You have no homes! Set one with /sethome <name>. Homes used: 0/" + homeLimit + "&d.");
				} else {
					viewHomes(pName, homes, homeLimit, p);
				}
			return;
		}
		
		for (String h : homes){
			if (h.toLowerCase().startsWith(args[0].toLowerCase())){
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
		List<Location> circleblocks = Utils.circle(p.getLocation(), 3, 1, true, false, 0);
		List<Location> circleblocks2 = Utils.circle(p.getLocation(), 3, 1, true, false, 1);
		
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
		
		WCUtils.s(p, "Teleported to &6" + homeName + " &dfrom &6" + xSimple + "&f, &6" + ySimple + "&f, &6" + zSimple + "&d.");
		
	}

	private void viewHomes(String pName, List<String> homes, int homeLimit, Player p) {
		
		if (homes.size() >= homeLimit){
			WCUtils.s(p, "Viewing homes for " + p.getDisplayName() + " &c(" + homes.size() + "&c/" + homeLimit + "&c)");
		} else {
			WCUtils.s(p, "Viewing homes for " + p.getDisplayName() + " &a(" + homes.size() + "&a/" + homeLimit + "&a)");
		}
		
		for (String currentHome : homes){
			String[] hSplit = currentHome.split(" ");
			WCUtils.s2(p, "&a| &6" + hSplit[0]);
		}
	}

	private int limitCheck(Player p) {
		
		String permissions = "wa.admin wa.staff wa.elysian wa.national wa.shirian wa.serf";
		String[] perms = permissions.split(" ");
		int x = 7;
			for (String a : perms){
				x--;
				if (p.hasPermission(a)){
					if (x == 6){
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
			WCUtils.s(p, "Try /sethome <name>");
			return;
		} else if (args.length == 0){
			viewHomes(pName, homes, homeLimit, p);
			return;
		}
		
		int xX = 0;
		
		for (String a : homes){
			if (a.startsWith(args[0])){
				xX++;
				bleh = a;
			}
		}
		
		if (xX == 0){
			
			if (homes.size() >= homeLimit){
				viewHomes(pName, homes, homeLimit, p);
				return;
			} 
			
		} else {
			
			homes.remove(bleh);
			wcp.remHome(args[0]);
			updatePlayer(wcp, p.getName());
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
		
		WCUtils.s(p, "Set home &6" + args[0] + " &dat &6" + Math.round(x) + "&f,&6 " + Math.round(y) + "&f,&6 " + Math.round(z) + "&d.");
		
		if (homes.size() >= homeLimit){
			WCUtils.s2(p, "&cHomes remaining&f: &c0/" + homeLimit);
		} else {
			WCUtils.s2(p, "&aHomes remaining&f: &a" + (homeLimit - homes.size()) + "/" + homeLimit);
		}
	} 
	
	public void remhome(String[] args, WCPlayer wcp, Player p, String pName, int homeLimit, List<String> homes) {

		if (args.length == 0 && homes.size() == 0){
			WCUtils.s(p, "Try /remhome or /delhome <name>");
			return;
		} else if (args.length == 0){
			viewHomes(pName, homes, homeLimit, p);
			return;
		}
		
		int x = 0;

		for (String h : homes){
			if (!h.toLowerCase().startsWith(args[0].toLowerCase())){
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
				WCUtils.s(p, "Removal successful.");
				WCUtils.s2(p, "&aHomes remaining&f: &a" + (homeLimit - homes.size()) + "/" + homeLimit);	
				return;
			}
		}

	} 
	
	 public void updatePlayer(WCPlayer wcp, String name){
			plugin.wcm.updatePlayerMap(name, wcp);  
			wcp = plugin.wcm.getWCPlayer(name);
	 }
}