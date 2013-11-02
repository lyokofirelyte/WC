package com.github.lyokofirelyte.WC.Extras;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;

public class w implements CommandExecutor {

	WCMain plugin;
	public w(WCMain instance){
	plugin = instance;
	}
	 public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		  if (cmd.getName().equalsIgnoreCase("ww")){
			  if (args.length == 0){
				  sender.sendMessage("Add a name.");
				  return true;
			  }
			  
			Font font = new Font("Garamond", Font.PLAIN, 12);
		  
			for (int x = 0; x < args.length; x++){
			
			  BufferedImage image = null;
			try {
				image = ImageIO.read(new URL("http://puu.sh/51zlg.png"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
				    Graphics g = image.getGraphics();
				    g.setFont(font);
				    g.setColor(Color.BLACK);
				    g.drawString(args[x], 142, 148);
				    g.dispose();
	
				    try {
						ImageIO.write(image, "png", new File(args[x] + ".png"));
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			
			WCMain.s((Player)sender, "Image(s) created!");
			return true;
		  }
		return true;
	 }
}
