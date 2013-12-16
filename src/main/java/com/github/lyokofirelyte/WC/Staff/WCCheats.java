package com.github.lyokofirelyte.WC.Staff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

import static com.github.lyokofirelyte.WC.WCMain.s;

public class WCCheats implements CommandExecutor {

	WCMain pl;
	public WCCheats(WCMain instance){
    this.pl = instance;
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = ((Player)sender);
		
		switch (cmd.getName().toLowerCase()){
		
			case "feed":
				
				if (args.length == 0){
					p.setFoodLevel(20);
				} else if (Bukkit.getPlayer(args[0]) != null){
					Bukkit.getPlayer(args[0]).setFoodLevel(20);
				} else {
					s(p, "Player not found!");
				}
			
			break;
		
			case "gm":
	
				if (args.length == 0){
					s(p, "/gm s || c || a");
					break;
				}
				
				GameMode gm;
				Boolean fly;
				
				switch (args[0].toLowerCase()){
				
					case "s": gm = GameMode.SURVIVAL; fly = false; break;
					case "c": gm = GameMode.CREATIVE; fly = true; break;
					case "a": gm = GameMode.ADVENTURE; fly = false; break;
					default: s(p, "/gm s || c || a"); return true;
				}
				
				if (args.length == 2){
					
					if (Bukkit.getPlayer(args[1]) == null){
						s(p, "That player is not online. /gm <s || c || a> <player>");
						break;
					}
					
					Player q = Bukkit.getPlayer(args[1]);
					q.setGameMode(gm);	
					q.setAllowFlight(fly);
					q.setFlying(fly);
					s(q, "GM updated.");
					s(p, "GM updated for " + q.getDisplayName());
					return true;
				}
				
				p.setGameMode(gm);
				p.setAllowFlight(fly);
				p.setFlying(fly);
				s(p, "GM updated.");
				
			break;
			
			case "fly":
				
				Player q;
				
				if (args.length == 1){
					
					if (Bukkit.getPlayer(args[0]) == null){
						s(p, "That player is not online.");
						break;
					}
					
					q = Bukkit.getPlayer(args[0]);
					
				} else {
					q = p;
				}
				
				if (q.isFlying() || q.getAllowFlight()){
					q.setAllowFlight(false);
					q.setFlying(false);
					s(q, "Fly mode &4disabled&d.");
				} else {
					q.setAllowFlight(true);
					q.setFlying(true);
					s(q, "Fly mode &aenabled&d.");
				}
				
			break;
			
			case "i":
				
				if (args.length == 0 || p.getInventory().firstEmpty() == -1){
					s(p, "/i <item> (must have room!)");
					break;
				}

				for (Material m : Material.values()){
					if (m.name().toString().toLowerCase().contains(args[0])){
						p.getInventory().addItem(new ItemStack(m, 64));
						break;
					} else if (Utils.isInteger(args[0]) && m.getId() == Integer.parseInt(args[0])){
						p.getInventory().addItem(new ItemStack(m, 64));
						break;
					}
				}
				
			break;
			
			case "ci":
				
				if (args.length == 0){
					pl.backupInvs.put(p.getName(), p.getInventory().getContents());
					p.getInventory().clear();
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_BREAK, 3F, 0.5F);
					s(p, "Cleared! Type /ci u to restore your inventory.");
				} else if (pl.backupInvs.containsKey(p.getName())){
					p.getInventory().setContents(pl.backupInvs.get(p.getName()));
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 3F, 0.5F);
					s(p, "Inventory restored!");
				} else {
					s(p, "No backup inventory found.");
				}
				
			break;	
			
			case "more":
				
				if (p.getInventory().getItemInHand() != null){
					p.getInventory().getItemInHand().setAmount(64);
				}
				
			break;
			
			case "back":
				
				WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
				if (wcp.getLastLocation() != null){
					Location l = p.getLocation();
					p.teleport(wcp.getLastLocation());
					wcp.setLastLocation(l);
					pl.wcm.updatePlayerMap(p.getName(), wcp);
					s(p, "Teleporting!");
				} else {
					s(p, "No previous location found!");
				}
				
			break;
			
			case "tppos":
				
				if (args.length != 3){
					s(p, "/tppos x y z");
				} else if (Utils.isInteger(args[0]) && Utils.isInteger(args[1]) && Utils.isInteger(args[2])){
					p.teleport(new Location(p.getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
				} else {
					s(p, "/tppos x y z");
				}
			
			break;
			
			case "speed":
				
				if (args.length != 1 || !Utils.isInteger(args[0]) || Integer.parseInt(args[0]) > 10 || Integer.parseInt(args[0]) < 0){
					s(p, "/speed <#>");
				} else {
					if (p.isFlying()){
						p.setFlySpeed(Float.parseFloat(args[0])/10);
					} else {
						p.setWalkSpeed(Float.parseFloat(args[0])/10);
					}
					s(p, "Speed updated.");
				}
				
			break;
			
			case "killall":
				
				int radius = 1000;
				int killed = 0;
				
				if (args.length > 0 && Utils.isInteger(args[0])){
					radius = Integer.parseInt(args[0]);
				}
				
				for (Entity e : p.getNearbyEntities(radius, radius, radius)){
					if (e instanceof Player == false){
						e.remove();
						killed++;
					}
				}
				
				s(p, "Killed &6" + killed + " &dmobs!");
			
			break;
		}
		
		return true;
	}
}
