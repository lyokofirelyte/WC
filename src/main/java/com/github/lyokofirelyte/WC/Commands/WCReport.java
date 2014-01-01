package com.github.lyokofirelyte.WC.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.github.lyokofirelyte.WC.WCMain;

	public class WCReport implements CommandExecutor {
		
	 WCMain plugin;
	 public WCReport(WCMain instance){
	 this.plugin = instance;
	 }

	  
	  private Connection conn;
	  private PreparedStatement pst;
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		  
	    if ((cmd.getName().equalsIgnoreCase("report")) && (args.length >= 2)) {
	    	
	        String url = plugin.config.getString("url");
	        String username = plugin.config.getString("username");
	        String password = plugin.config.getString("password");
	    	
	      StringBuilder sb = new StringBuilder();
	      for (int i = 1; i < args.length; i++)
	      {
	        if ((i != args.length) && (i != 1))
	        {
	          sb.append(" ");
	        }
	        sb.append(args[i]);
	      }
	      String message = sb.toString();
	      try
	      {
	        this.conn = DriverManager.getConnection(url, username, password);

	        this.pst = this.conn.prepareStatement("INSERT INTO Reports(Timestamp, SenderName, ReportedName, Message) VALUES(?, ?, ?, ?)");

	        long timestamp = System.currentTimeMillis() / 1000L;

	        this.pst.setInt(1, (int)timestamp);
	        this.pst.setString(2, sender.getName());
	        this.pst.setString(3, args[0]);
	        this.pst.setString(4, message);

	        this.pst.executeUpdate();
	        this.pst.close();
	        this.conn.close();

	        sender.sendMessage(new StringBuilder().append("[ waOS ] Your report has been received! ").append(args[0]).append(" Report: ").append(message).toString());
	        return true;
	      }
	      catch (SQLException e)
	      {
	        e.printStackTrace();
	        sender.sendMessage("[ waOS ] Something went wrong! Contact a System Administrator!");
	      }
	    }
	    return false;
	  }
	}	