package com.github.lyokofirelyte.WC.WCMMO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.Events.WCMMOLevelUpEvent;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatClickEventType;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatExtra;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatHoverEventType;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatMessage;
import com.github.lyokofirelyte.WCAPI.Manager.SkillType;
import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class WCMMO {

	public WCMain pl;

	public WCMMO(WCMain i){
		pl = i;
	}

	WCPlayer wcp;
	
	@WCCommand(aliases = {"wcmmo"}, help = "/wcmmo", desc = "Display your WCMMO stats!")
	public void onStats(Player p, String[] args){ // I feel like I'm working with Windows95 doing these chat extra break-downs
		
		List<JSONChatMessage> toSend = new ArrayList<JSONChatMessage>();
		wcp = pl.wcm.getWCPlayer(p.getName());
		pl.wcm.recallMessages(p);
		
		if (args.length == 0){
			
			JSONChatExtra extra = null;
			JSONChatMessage message = new JSONChatMessage(AS("&dWCMMO Main Menu (click!) &6// " + p.getDisplayName()), null, null);
			toSend.add(message);
			
			message = new JSONChatMessage(AS("&8|"), null, null);
			toSend.add(message);
			
			message = new JSONChatMessage("", null, null);
			extra = new JSONChatExtra(AS("&2>> &aMY SKILLS"), null, null);
			extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/wcmmo skills");
			extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS("&6View Skills"));
			message.addExtra(extra);
			toSend.add(message);
			
			message = new JSONChatMessage(AS("&8|"), null, null);
			toSend.add(message);
			
			message = new JSONChatMessage("", null, null);
			extra = new JSONChatExtra(AS("&2>> LEADERBOARDS"), null, null);
			extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/wcmmo leaderboards");
			extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS("&6WCMMO Leaderboards"));
			message.addExtra(extra);
			toSend.add(message);
			
			message = new JSONChatMessage(AS("&8|"), null, null);
			toSend.add(message);
			
			message = new JSONChatMessage("", null, null);
			extra = new JSONChatExtra(AS("&2>> ABILITIES"), null, null);
			extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/wcmmo abilities");
			extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS("&6Ability Info"));
			message.addExtra(extra);
			toSend.add(message);
			
			message = new JSONChatMessage(AS("&8|"), null, null);
			toSend.add(message);
			
			message = new JSONChatMessage("", null, null);
			extra = new JSONChatExtra(AS("&c>> CLOSE MENU"), null, null);
			extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/wcmmo close");
			extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS("&6Close Dialog"));
			message.addExtra(extra);
			toSend.add(message);

			for (JSONChatMessage m : toSend){
				m.sendToPlayer(p);
			}
			
		} else if (args[0].equals("skills")){
			
			JSONChatExtra extra = null;
			JSONChatMessage message = new JSONChatMessage(AS("&dWCMMO SKILLS &6// " + p.getDisplayName()), null, null);
			toSend.add(message);
			message = new JSONChatMessage("", null, null);
			int x = 0;
			int z = 0;
			double base = 50;
	
			for (SkillType s : SkillType.values()){
					
				base = 50;
				
				for (int y = 0; y <= wcp.skills().get(s.name()); y++){
					base = base + (base*.10);
				}
				
				if (x == 0 || x == 1){
					extra = new JSONChatExtra(AS("&a" + s.getSkill() + " &2- "), null, null);
					extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS("&6Level " + wcp.skills().get(s.name()) + "\n&6Exp: " + wcp.skillExp().get(s.name()) + " &f/ &6" + base));
					message.addExtra(extra);
				} else if (x == 2){
					extra = new JSONChatExtra(AS("&a" + s.getSkill()), null, null);
					extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS("&6Level " + wcp.skills().get(s.name()) + "\n&6Exp: " + wcp.skillExp().get(s.name()) + " &f/ &6" + base));
					message.addExtra(extra);
					toSend.add(message);
					message = new JSONChatMessage("", null, null);
					x = -1;
				}
				
				x++;
				z++;
				
				if (z >= SkillType.values().length){
					toSend.add(message);
				}
			}
			
			message = new JSONChatMessage("", null, null);
			extra = new JSONChatExtra(AS("&c<< previous menu"), null, null);
			extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/wcmmo");
			message.addExtra(extra);
			
			for (JSONChatMessage m : toSend){
				m.sendToPlayer(p);
			}
			
			message.sendToPlayer(p);
			
			base = 50;
			
			for (int y = 0; y <= 99; y++){
				base = base + (base*.10);
			}
			
			System.out.println(base);
			
			base = 50;
			
			for (int y = 0; y <= 99; y++){
				base = base + (base*.15);
			}
			
			System.out.println(base);
			
		} else if (args[0].equals("leaderboards")){
			
			JSONChatExtra extra = null;
			JSONChatMessage message = new JSONChatMessage(AS("&dWCMMO LEADERBOARDS &6// " + p.getDisplayName()), null, null);
			toSend.add(message);
			message = new JSONChatMessage("", null, null);
			int x = 0;
			int y = 0;
			int z = 0;

			for (SkillType s : SkillType.values()){
				
				List<Integer> levelRank = new ArrayList<Integer>();
				List<WCPlayer> tops = new ArrayList<WCPlayer>();
				Map<WCPlayer, Integer> levels = new HashMap<WCPlayer, Integer>();
				
				for (WCPlayer w : pl.api.wcPlayers.values()){
					levelRank.add(w.skills().get(s.name()));
				}
				
				Collections.sort(levelRank);
				Collections.reverse(levelRank);
				
				for (int i : levelRank){
					for (WCPlayer w : pl.api.wcPlayers.values()){
						if (w.skills().get(s.name()) == i && y <= 10){
							tops.add(w);
							System.out.println("added someone to top");
						}
					}
					y++;
					if (y <= 10){
						System.out.println(i);
					}
				}
				
				StringBuilder sb = new StringBuilder();
				
				for (int xx = 0; xx <= 10; xx++){
					sb.append("&7" + tops.get(xx).getNick() + " &8@ " + levels.get(tops.get(xx)) + "\n");
				}
				
				if (x == 0 || x == 1){
					extra = new JSONChatExtra(AS("&a" + s.getSkill() + " &2- "), null, null);
					extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS(sb.toString()));
					message.addExtra(extra);
				} else if (x == 2){
					extra = new JSONChatExtra(AS("&a" + s.getSkill()), null, null);
					extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS(sb.toString()));
					message.addExtra(extra);
					toSend.add(message);
					message = new JSONChatMessage("", null, null);
					x = -1;
				}
				
				x++;
				z++;
			}
			
			if (z >= SkillType.values().length){
				toSend.add(message);
			}
			
			message = new JSONChatMessage("", null, null);
			extra = new JSONChatExtra(AS("&c<< previous menu"), null, null);
			extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/wcmmo");
			message.addExtra(extra);
			
			for (JSONChatMessage m : toSend){
				m.sendToPlayer(p);
			}
			
			message.sendToPlayer(p);

		}
	}

	public void setSkill(Player p, SkillType skill, int level){
		
		wcp = pl.wcm.getWCPlayer(p.getName());
		wcp.skills().put(skill.name(), level);
	}
	
	public void incrementSkill(Player p, SkillType skill){	
		
		wcp = pl.wcm.getWCPlayer(p.getName());
		wcp.skills().put(skill.name(), wcp.skills().get(skill.name())+1);
		pl.getServer().getPluginManager().callEvent(new WCMMOLevelUpEvent(p, skill, wcp.skills().get(skill.name())));
	}
	
	public void addExp(Player p, SkillType skill, Material m){
		
		wcp = pl.wcm.getWCPlayer(p.getName());
		
		if (wcp.skillExp().get(skill.name()) == null){
			wcp.skillExp().put(skill.name(), 0);
			wcp.skills().put(skill.name(), 0);
		}
		
		wcp.skillExp().put(skill.name(), wcp.skillExp.get(skill.name()) + pl.ss.getExp(m));
		
		double base = 50;
		
		for (int x = 0; x <= wcp.skills().get(skill.name()); x++){
			base = base + (base*.10);
		}
		
		if (wcp.skillExp().get(skill.name()) >= base){
			incrementSkill(p, skill);
			wcp.skillExp().put(skill.name(), (int) Math.round(wcp.skillExp().get(skill.name()) - base));
		}
	}
	
	public int getSkill(Player p, SkillType skill){
		return pl.wcm.getWCPlayer(p.getName()).skills().get(skill.name());
	}
	
	public int getSkillExp(Material m){
		return pl.ss.getExp(m);
	}
}