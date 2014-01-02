package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCCommand;

public class WCWB{

	WCMain pl;
	public WCWB(WCMain instance){
	pl = instance;
    }

	@WCCommand(aliases = {"wb", "workbench"}, help = "Open a crafting table", max = 0, perm = "wa.statesman")
	  public void WB(Player sender, String[] args){
		  ((Player)sender).openWorkbench(null, true);
		  return;
	  }
}
