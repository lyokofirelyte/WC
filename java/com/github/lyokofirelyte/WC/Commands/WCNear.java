package com.github.lyokofirelyte.WC.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;

import static com.github.lyokofirelyte.WC.WCMain.s;
import static com.github.lyokofirelyte.WC.WCMain.s2;

public class WCNear implements CommandExecutor {

	WCMain pl;
	public WCNear(WCMain instance){
	pl = instance;
    }

	  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		  Player p = ((Player)sender);
		  
		  if (cmd.getName().toLowerCase().equals("near")){
			  
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
		  
		  if (cmd.getName().toLowerCase().equals("radar")){
			  
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
		  }
		  
		  return true;
	  }
}

