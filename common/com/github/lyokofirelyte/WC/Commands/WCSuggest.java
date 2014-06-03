package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.entity.Player;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;



public class WCSuggest {

	WCMain pl;
  public WCSuggest(WCMain instance){
	pl = instance;
	}

	@WCCommand(aliases = {"suggest"}, min = 1, player = true)
	public void onSuggest(Player p, String[] args){

		String consumerKeyStr = pl.getConfig().getString("twitterbot.consumerKeyStr");

		String consumerSecretStr = pl.getConfig().getString("twitterbot.consumerSecretStr");

		String accessTokenStr = pl.getConfig().getString("twitterbot.accessTokenStr");

 		String accessTokenSecretStr = pl.getConfig().getString("twitterbot.accessTokenSecretStr");
		  
		String message;
		message = "";
		for(String s : args){
			message = message + " " + s;
		}
		
	    try {

	      Twitter twitter = new TwitterFactory().getInstance();

	 

	      twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);

	      AccessToken accessToken = new AccessToken(accessTokenStr,

	          accessTokenSecretStr);

	 

	      twitter.setOAuthAccessToken(accessToken);

	 

	      long i = twitter.updateStatus(p.getName() + ": " + message).getId();

	 

	      WCUtils.s(p, "Thank you for your suggestion/report!");
	      
	      message = "";
	      
	      twitter.destroyStatus(i);
	    } catch (Exception te) {

	      te.printStackTrace();

	    }		
	}
}