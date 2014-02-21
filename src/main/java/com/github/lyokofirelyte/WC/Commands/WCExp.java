package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WCAPI.WCUtils.*;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatExtra;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatHoverEventType;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatMessage;
import com.github.lyokofirelyte.WCAPI.Manager.WCMessageType;

public class WCExp {

	WCMain pl;
	
	public WCExp(WCMain i){
		pl = i;
    }
	
	@WCCommand(aliases = {"exp", "wcexp"}, help = "/exp", desc = "Displays the exp chart")
	public void expView(Player p, String[] args){
		
		s(p, "Default EXP Chart");
		
		JSONChatMessage msg = new JSONChatMessage("", null, null);
		JSONChatExtra extra = new JSONChatExtra("", null, null);
		
		for (int x = 1; x < 41; x++){
			
			extra = new JSONChatExtra(AS("&6" + x + " "), null, null);
			
			if (x <= 15){
				extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, (AS("&6Level " + x + "\n&a") + 17*x + ""));
			} else if (x >= 16 && x <= 31){
				extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, (AS("&6Level " + x + "\n&a") + (1.5*(Math.pow(x, 2)) - 29.5*x + 360)));
			} else {
				extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, (AS("&6Level " + x + "\n&a") + (3.5*(Math.pow(x, 2)) - 151.5*x + 2220)));
			}
			
			msg.addExtra(extra);
		}
		callChat(p, WCMessageType.JSON_PLAYER, msg);
	}
	
	@WCCommand(aliases = {"colors", "colorcodes", "wccolors"}, help = "/colors", desc = "Displays the color chart")
	public void colorView(Player p, String[] args){
		
		String colors = "&aa &bb &cc &dd &ee &ff &11 &22 &33 &44 &55 &66 &77 &88 &99 &00 &7[&ll &7&oo &7&mm &7r(eset)]";
		s(p, colors);
	}
}