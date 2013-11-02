package com.github.lyokofirelyte.WC.Games.HungerGames;

import java.util.Arrays;
import java.util.List;

import com.github.lyokofirelyte.WC.WCMain;

public class CGMain {

	  WCMain plugin;
	  public CGMain(WCMain instance){
	  this.plugin = instance;
	  }
	  
	  List <String> hgHelp = Arrays.asList("&5Collision Games", 
			    "&5| &3/cg &f// Root command & help menu",
				"&5| &3arena <arena> &f// &3Select the arena to use.",
				"&5| &3join &f// &3Join the active game.",
				"&5| &3quit &f// &3Resign from the game.",
				"&5| &3start &f// &3Begin the game.",
				"&5| &3stop &f// &3Force-stop the game.",
				"&5| &3add <arena> &f// &3Arena set-up wizard.",
				"&5| &3top &f// &3View highest scores.");

	
}
