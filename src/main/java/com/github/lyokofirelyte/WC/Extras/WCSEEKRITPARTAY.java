package com.github.lyokofirelyte.WC.Extras;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCCommand;

public class WCSEEKRITPARTAY implements Listener {
	
	WCMain pl;
	public WCSEEKRITPARTAY(WCMain instance){
		pl = instance;
    }
	
	@EventHandler
	public void onChange(EntityChangeBlockEvent e){
		
		if (e.getBlock().getLocation().getWorld().getName().equals("creative")){
			for (Entity ee : e.getEntity().getNearbyEntities(50D, 50D, 50D)){
				if (ee instanceof Player && ((Player) ee).getName().equals("Hugh_Jasses")){
					e.setCancelled(true);
					Block b = e.getBlock();
					b.getWorld().playEffect(b.getLocation(), Effect.ENDER_SIGNAL, 1);
					b.getWorld().playSound(b.getLocation(), Sound.LEVEL_UP, 3F, 3F);
					return;
				}
			}
		}
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onBreak(BlockBreakEvent e){
		if (e.getPlayer().getName().equals("Hugh_Jasses")){
			if (e.getPlayer().getItemInHand().getType().equals(Material.APPLE)){
				e.setCancelled(true);
				List<String> locs = pl.datacore.getStringList("PARTYWORDS");
				Location l = e.getBlock().getLocation();
				locs.add(l.getWorld().getName() + " " + l.getX() + " " + l.getY() + " " + l.getZ());
				pl.datacore.set("PARTYWORDS", locs);
				WCMain.s(e.getPlayer(), "Added");
			} else if (e.getPlayer().getItemInHand().getType().equals(Material.ARROW)){
				e.setCancelled(true);
				List<String> locs = pl.datacore.getStringList("PARTYJESSE");
				Location l = e.getBlock().getLocation();
				locs.add(l.getWorld().getName() + " " + l.getX() + " " + l.getY() + " " + l.getZ());
				pl.datacore.set("PARTYJESSE", locs);
				WCMain.s(e.getPlayer(), "Added");
			}
		}
	}
	
	@WCCommand(aliases = {"partyfw"}, desc = "nope.jpeg", help = "nope.png", perm = "wa.admin")
	public void onPartyFW(Player sender, String[] args){
		  
		if (sender.getName().equals("Hugh_Jasses")){
			if (args.length == 1 && args[0].equals("clr")){
				clr();
				return;
			}
			party();
			words();
		} else if (sender.getName().equals("Hugh_Jasses")){
			jesse();
		}
		return;
	}
	
	public void clr(){
		
		for (String s : pl.datacore.getStringList("PARTYWORDS")){
			List<String> locs = Arrays.asList(s.split(" "));
			Location l = new Location(Bukkit.getWorld(locs.get(0)), Double.parseDouble(locs.get(1)), Double.parseDouble(locs.get(2)), Double.parseDouble(locs.get(3)));
			l.getBlock().setType(Material.AIR);
		}
		for (String s : pl.datacore.getStringList("PARTYJESSE")){
			List<String> locs = Arrays.asList(s.split(" "));
			Location l = new Location(Bukkit.getWorld(locs.get(0)), Double.parseDouble(locs.get(1)), Double.parseDouble(locs.get(2)), Double.parseDouble(locs.get(3)));
			l.getBlock().setType(Material.AIR);
		}
	}
	
	public void words(){
		
		long delay = 0L;
		  
		for (String s : pl.datacore.getStringList("PARTYWORDS")){
			List<String> locs = Arrays.asList(s.split(" "));
			final Location l = new Location(Bukkit.getWorld(locs.get(0)), Double.parseDouble(locs.get(1)), Double.parseDouble(locs.get(2)), Double.parseDouble(locs.get(3)));
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
			public void run() { 
				  
				try {
					pl.fw.playFirework(l.getWorld(), l,
					FireworkEffect.builder().with(Type.BURST).withColor(Utils.getRandomColor()).build());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				Entity e = l.getWorld().spawnFallingBlock(l, Material.GOLD_BLOCK, (byte)1);
				e.setVelocity(new Vector(2, 0, 0));
				l.getBlock().setType(Material.GOLD_BLOCK);
				  
			} }, delay);
			  
			delay = delay + 2L;
		}
		
		int diamondTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
		public void run() { party();} }, 200L, 200L);
		
	    
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
		public void run() { Bukkit.getServer().getScheduler().cancelTask(pl.datacore.getInt("DIAMONDTASK")); } }, 800L);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
		public void run() { 
			
			final List<Location> overview = Utils.circle(new Location(Bukkit.getWorld("creative"), -1159, 33, 331), 10, 1, true, false, 0);	
			long delay = 0L;
			
			for (final Location l : overview){
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
				public void run() { 
					try {
						pl.fw.playFirework(l.getWorld(), l,
						FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Utils.getRandomColor()).build());
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				} }, delay);
				
				delay = delay + 2L;
			}
			
			long delay2 = 0L;
			
			Collections.reverse(overview);
				
			for (final Location l : overview){
					
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
				public void run() { 
					try {
						pl.fw.playFirework(l.getWorld(), l,
						FireworkEffect.builder().with(Type.CREEPER).withColor(Utils.getRandomColor()).build());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				} }, delay2);
					
				delay2 = delay2 + 2L;
			}

			
		} }, 80L);
		
		pl.datacore.set("DIAMONDTASK", diamondTask);
	}
	  
	public void party(){
		  
		Boolean dia = true;
		Location origin = new Location(Bukkit.getWorld("creative"), -1159, 33, 331);
		List<Location> fwlocs = Utils.circle(origin, 8, 1, true, false, 1);
		  
		for (int x = 0; x < 10; x++){
			Item diamonds = origin.getWorld().dropItemNaturally(origin, new ItemStack(Material.DIAMOND, 1));
		 	Entity e = origin.getWorld().spawnFallingBlock(origin, Material.DIAMOND_BLOCK, (byte)0);
			  
		 	if (dia){
		 		diamonds.setVelocity(new Vector(0.3, 1, 0));
		 		e.setVelocity(new Vector(-0.3, 1, 0));
		 		dia = false;
		 	} else {
		 		diamonds.setVelocity(new Vector(0, 1, 0.3));
		 		e.setVelocity(new Vector(0, 1, -0.3));
		 		dia = true;
		 	}
		}
		
		for (Location l : fwlocs){
			try {
				pl.fw.playFirework(l.getWorld(), l,
				FireworkEffect.builder().with(Type.BURST).withColor(Utils.getRandomColor()).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@WCCommand(aliases = {"jesse"}, desc = "nope.gif", help = "nope.json", perm = "wa.admin")
	public void jesse(){
		
		Location lowerRing = new Location(Bukkit.getWorld("creative"), -1159, 35, 331);
		Location upperRing = new Location(Bukkit.getWorld("creative"), -1159, 40, 331);
		Location pedestal = new Location(Bukkit.getWorld("creative"), -1159, 37, 331);
		List<Location> all = Utils.circle(lowerRing, 5, 1, true, false, 0);
		List<Location> urlocs = Utils.circle(upperRing, 5, 1, true, false, 0);
		long delay = 0L;
		pedestal.getBlock().setType(Material.GLASS);
		
		if (Bukkit.getPlayer("WinneonSword") != null){
			Bukkit.getPlayer("WinneonSword").performCommand("warp jesse");
			Bukkit.getPlayer("WinneonSword").setWalkSpeed(0F);
			Bukkit.getPlayer("WinneonSword").setFlySpeed(0F);
		}
		
		for (Location l : urlocs){
			all.add(l);
		}
		
		for (final Location l : all){
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
			public void run() { 
				  
				try {
					pl.fw.playFirework(l.getWorld(), l,
					FireworkEffect.builder().with(Type.BURST).withColor(Utils.getRandomColor()).build());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				l.getBlock().setType(Material.GLASS);
				  
			} }, delay);
			  
			delay = delay + 2L;
		}
		
		delay = delay + 20L;
		
		
		for (String s : pl.datacore.getStringList("PARTYJESSE")){
			List<String> locs = Arrays.asList(s.split(" "));
			final Location l = new Location(Bukkit.getWorld(locs.get(0)), Double.parseDouble(locs.get(1)), Double.parseDouble(locs.get(2)), Double.parseDouble(locs.get(3)));
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
			public void run() { 
				  
				try {
					pl.fw.playFirework(l.getWorld(), l,
					FireworkEffect.builder().with(Type.BURST).withColor(Utils.getRandomColor()).build());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				Entity e = l.getWorld().spawnFallingBlock(l, Material.GOLD_BLOCK, (byte)1);
				e.setVelocity(new Vector(2, 0, 0));
				l.getBlock().setType(Material.GOLD_BLOCK);
				  
			} }, delay);
			  
			delay = delay + 2L;
		}

	}
	
}
