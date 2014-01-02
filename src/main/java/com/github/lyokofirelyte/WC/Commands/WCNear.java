package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCCommand;

import static com.github.lyokofirelyte.WC.WCMain.s;
import static com.github.lyokofirelyte.WC.WCMain.s2;

public class WCNear{

	WCMain pl;
	public WCNear(WCMain instance){
	pl = instance;
    }

	@WCCommand(aliases = {"near"}, help = "List of players within 200 blocks", max = 0, perm = "wa.divine")
	  public void onNear(Player sender, String[] args){
		  
		  Player p = ((Player)sender);
			  
			  List<String> entities = new ArrayList<String>();
			  
			  for (Entity e : p.getNearbyEntities(200D, 200D, 200D)){
				  if (e instanceof Player){
					  entities.add(((Player)e).getDisplayName());
				  }
			  }
			  
			  s(p, "List of players within 200 blocks");
			  
			  StringBuilder sb = new StringBuilder();
			  
			  if (entities.size() > 0){
			  
				  for (String s : entities){
					  sb.append(s + "&6, ");
				  }
				  
				  sb.append("~");
				  s2(p, Utils.AS(sb.toString().replace(",  ~", "")));
			  } else {
				  s2(p, "none");
			  }
	}
	
	@WCCommand(aliases = {"radar"}, help = "List of monsters within 20 blocks", max = 0, perm = "wc.townsman")
	public void onRadar(Player p, String[] args){
		
			  List<String> entities = new ArrayList<String>();
			  
			  for (Entity e : p.getNearbyEntities(20D, 20D, 20D)){
				  if (e instanceof Player == false){
					  entities.add(e.getType().name().toString().toLowerCase());
				  }
			  }
			  
			  s(p, "List of monsters within 20 blocks");
			  
			  StringBuilder sb = new StringBuilder();
			  
			  if (entities.size() > 0){
				  
				  for (String s : entities){
					  sb.append("&d" + s + "&6, ");
				  }
				  
				  sb.append("~");
				  s2(p, Utils.AS(sb.toString().replace(",  ~", "")));
			  } else {
				  s2(p, "none");
			  }
		
		  return;
	}
}

