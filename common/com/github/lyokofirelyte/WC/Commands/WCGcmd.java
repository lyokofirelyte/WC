package com.github.lyokofirelyte.WC.Commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WCAPI.WCUtils.createString;
import static com.github.lyokofirelyte.WCAPI.WCUtils.AS;

import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatClickEventType;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatColor;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatExtra;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatFormat;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatHoverEventType;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatMessage;
import com.github.lyokofirelyte.WCAPI.Manager.WCMessageType;

public class WCGcmd {

	WCMain pl;
	
	public WCGcmd (WCMain i){
		pl = i;
	}
	
	@WCCommand(aliases = {"gcmd", "globalcommand"}, help = "/gcmd <command>", desc = "Post a command in chat!", player = true)
	public void onGlobalCommand(Player p, String[] args){
		
		if (args.length == 0){
			p.sendMessage("/gcmd <command>");
			return;
		}
		
		String command = createString(args, 0);
		command = command.replaceAll("/", "");
		
		JSONChatMessage msg = new JSONChatMessage(AS("&8>> &7Command from " + p.getDisplayName() + "&7: "), null, null);
		JSONChatExtra extra = new JSONChatExtra(AS(command), JSONChatColor.DARK_PURPLE, Arrays.asList(JSONChatFormat.BOLD));
		extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS("&6Clicking this will run &d/" + command));
		extra.setClickEvent(JSONChatClickEventType.RUN_COMMAND, "/" + command);
		msg.addExtra(extra);
		for (Player pp : Bukkit.getOnlinePlayers()){
			WCUtils.callChat(pp, WCMessageType.JSON_PLAYER, msg);
		}
	}
}