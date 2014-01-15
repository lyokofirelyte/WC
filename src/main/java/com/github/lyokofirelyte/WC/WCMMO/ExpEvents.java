package com.github.lyokofirelyte.WC.WCMMO;

import static com.github.lyokofirelyte.WCAPI.WCUtils.effects;
import static com.github.lyokofirelyte.WCAPI.WCUtils.s;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Events.WCMMOLevelUpEvent;

public class ExpEvents extends WCMMO implements Listener {

	public ExpEvents(WCMain i) {
		super(i);
	}
	
	@EventHandler
	public void onLevel(WCMMOLevelUpEvent e){
		
		Player p = e.getPlayer();
		s(p, "&eYour &6" + e.getSkill().getSkill() + " &eis now level &6" + e.getLevel() + "&e.");
		effects(p);
		p.playSound(p.getLocation(), Sound.ORB_PICKUP, 3F, 3.0F);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
	
		if (pl.ss.getValidMats().contains(e.getBlock().getType())){
			pl.ss.getInvalidLocs().add(e.getBlock().getLocation());
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e){
		
		if (pl.ss.getValidMats().contains(e.getBlock().getType()) && !pl.ss.getInvalidLocs().contains(e.getBlock().getLocation())){
			addExp(e.getPlayer(), pl.ss.getSkill(e.getBlock().getType()), e.getBlock().getType());
		}
	}
	
	public void onSomething(){
		
	}
}