package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;



public class WCSuggestion {

  static String consumerKeyStr = "lYmPVPACftOAEZhZC73je1FXU";

  static String consumerSecretStr = "NnnJCUKcMp60N6tf9HK7sSat2q0sEivw2yKX3aAGPaYyNI4vBY";

  static String accessTokenStr = "2537806596-jKC7NekmCbY3oJ1Rts2RDgnfP8AD0pudpt2WxPT";

  static String accessTokenSecretStr = "KH26U3RIjkN1vIxyHm3jzEriy4coCddg7Idrk92JeoZHS";

  private String message;
  
	WCMain pl;
	public WCSuggestion(WCMain instance){
	pl = instance;
	}
  
	@WCCommand(aliases = {"suggest"}, min = 1, max = 5, player = true, perm = "wa.staff")
	public void onSuggest(Player p, String[] args){
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

	 

	      twitter.updateStatus(message);

	 

	      p.sendMessage("Succesfully send the message!");
	      
	      message = "";
	      
	    } catch (TwitterException te) {

	      te.printStackTrace();

	    }		
	}
}