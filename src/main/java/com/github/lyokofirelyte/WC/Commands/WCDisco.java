package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.Manager.WCMessageType;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.WCUtils;

public class WCDisco {

	WCMain pl;
	public WCDisco(WCMain instance){
		pl = instance;
	}
	
	@WCCommand(aliases = {"ds"}, help = "WC Disco!")
	public void disco(Player sender, String[] args) {
		
			WCUtils.callChat(WCMessageType.BROADCAST, WCUtils.AS("&4>> &aIt's FESTIVE DISCO TIME! &4<<"));
			
			for (final Player p : Bukkit.getOnlinePlayers()){
				
				List<Location> locs = WCUtils.circle(p.getLocation(), 10, 1, false, false, 0);
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
				
						List<Location> circleblocks = WCUtils.circle(p.getLocation(), 5, 1, true, false, 1);
						long delay =	0L;
							for (final Location l : circleblocks){
								delay = delay + 2L;
								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
										public void run() {
												try {
									pl.fw.playFirework(p.getWorld(), l,
									FireworkEffect.builder().with(Type.BURST).withColor(WCUtils.getRandomColor()).build());
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (Exception e) {
									e.printStackTrace();
								}								}
									}
									, delay);
							}
			}
			
			WCSystem wcs = pl.wcm.getWCSystem("system");
			wcs.setSheepStart(System.currentTimeMillis()/1000);
			pl.wcm.updateSystem("system", wcs);
	}

	public void updateSheep(List<LivingEntity> ents){
		
		if ((pl.wcm.getWCSystem("system").getSheepStart()+10L) <= System.currentTimeMillis()/1000){
			for (LivingEntity e : ents){
				WCUtils.effects(e.getLocation());
				e.remove();
			}
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
			public void run() { clean();} }, 20L);
		} else {
			for (LivingEntity e : ents){
				Sheep sheep = (Sheep)e;
				sheep.setColor(WCUtils.getRandomDyeColor());
				e.setCustomName(WCUtils.AS(WCUtils.getRandomChatColor() + "FESTIVE DISCO!")); e.setCustomNameVisible(true);
				e.getWorld().playSound(e.getLocation(), WCUtils.getRandomNote(), 0.5F, 3F);
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