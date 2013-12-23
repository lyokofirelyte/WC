package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCSystem;

public class WCDisco implements CommandExecutor {

	WCMain pl;
	public WCDisco(WCMain instance){
	pl = instance;
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().toLowerCase().equals("ds")){
			
			Bukkit.broadcastMessage(Utils.AS("&4>> &aIt's DISCO TIME! &4<<"));
			
			for (final Player p : Bukkit.getOnlinePlayers()){
				
				List<Location> locs = Utils.circle(p.getLocation(), 10, 1, false, false, 0);
				List<Location> finalLocs = new ArrayList<Location>();
				final List<LivingEntity> ents = new ArrayList<LivingEntity>();
				
				Random rand = new Random();
				for (int x = 0; x < 11; x++){
					finalLocs.add(locs.get(rand.nextInt(locs.size()-1)));
				}
				
				for (Location l : finalLocs){
					ents.add((LivingEntity) l.getWorld().spawnEntity(l, EntityType.SHEEP));
				}
				
				int task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
				public void run() { updateSheep(ents);} }, 0L, 10L);
				
				WCSystem wcs = pl.wcm.getWCSystem("system");
				List<Integer> tasks = wcs.getSheepTasks();
				tasks.add(task);
				wcs.setSheepTasks(tasks);
				pl.wcm.updateSystem("system", wcs);
				
	        	List<Location> circleblocks = Utils.circle(p.getLocation(), 5, 1, true, false, 1);
		        long delay =  0L;
		        	for (final Location l : circleblocks){
		        		delay = delay + 2L;
		        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
		        	      public void run() {
		        	        	try {
									pl.fw.playFirework(p.getWorld(), l,
									FireworkEffect.builder().with(Type.BURST).withColor(Utils.getRandomColor()).build());
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (Exception e) {
									e.printStackTrace();
								}        	      }
		        	    }
		        	    , delay);
		        	}
			}
			
			WCSystem wcs = pl.wcm.getWCSystem("system");
			wcs.setSheepStart(System.currentTimeMillis()/1000);
			pl.wcm.updateSystem("system", wcs);
		}
		
		return true;
	}

	public void updateSheep(List<LivingEntity> ents){
		
		if ((pl.wcm.getWCSystem("system").getSheepStart()+10L) <= System.currentTimeMillis()/1000){
			for (LivingEntity e : ents){
				Utils.effects(e.getLocation());
				e.remove();
			}
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
			public void run() { clean();} }, 20L);
		} else {
			for (LivingEntity e : ents){
				Sheep sheep = (Sheep)e;
				sheep.setColor(Utils.getRandomDyeColor());
				e.setCustomName(Utils.AS(Utils.getRandomChatColor() + "DISCO!")); e.setCustomNameVisible(true);
				e.getWorld().playSound(e.getLocation(), Utils.getRandomNote(), 0.5F, 3F);
				e.setNoDamageTicks(3000);
			}
		}		
	}
	
	public void clean(){
		for (int i : pl.wcm.getWCSystem("system").getSheepTasks()){
			Bukkit.getServer().getScheduler().cancelTask(i);
		}
	}
}
