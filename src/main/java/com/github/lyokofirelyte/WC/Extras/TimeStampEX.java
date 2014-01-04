package com.github.lyokofirelyte.WC.Extras;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCCommand;
import com.github.lyokofirelyte.WC.WCMain;

public class TimeStampEX{
	
  public WCMain plugin;
  private Long unixTime;

  public TimeStampEX(WCMain plugin)
  {
    this.plugin = plugin;
  }

  @WCCommand(aliases = {"timestamp"}, desc = "Consol command", help = "TIMESTAMP", max = 0)
  public void onTimeStamp(Player sender, String[] args){

      if ((sender instanceof Player)) {
        sender.sendMessage(ChatColor.RED + "This is a console command.");
      } else {
        this.unixTime = (System.currentTimeMillis() / 1000L);
        Date time = new Date(this.unixTime * 1000L);
        String time2 = String.valueOf(time);
        Bukkit.dispatchCommand(sender, "vt setstr time current " + time2);
      }
    }

  @WCCommand(aliases = {"getnick"}, desc = "Consol command", help = "GETNICK", max = 0)
  public void onGetNick(Player sender, String[] args){

      if ((sender instanceof Player)) {
        sender.sendMessage(ChatColor.RED + "This is a console command.");
      }
      else if (args.length > 0) {
        Player pcheck = Bukkit.getPlayerExact(args[0]);
        if (pcheck != null) {
          Bukkit.dispatchCommand(sender, "vtrigger setstr nick " + args[0] + " " + pcheck.getDisplayName());
        }
        if (pcheck == null)
          sender.sendMessage(ChatColor.RED + "That player is not online.");
      }
      else {
        sender.sendMessage(ChatColor.RED + "Usage is @CMDCON getnick <player>.");
      }
    }

  @WCCommand(aliases = {"stringbuilder"}, desc = "Consol command", help = "STRINGBUILDER", max = 0)
  public void onStringBuilder(Player sender, String[] args){
	  
      if ((sender instanceof Player)) {
        sender.sendMessage(ChatColor.RED + "This is a console command.");
      } else if (args.length > 0) {
        String lengthcheck = args[0];
        if (Utils.isInteger(lengthcheck)) {
          int lc2 = Integer.parseInt(lengthcheck) + 1;
          String message = Utils.createString(args, lc2);
          Bukkit.dispatchCommand(sender, "vtrigger setstr string current " + message);
        } else {
          sender.sendMessage(ChatColor.RED + "You were supposed to put a number there, not whatever it is that you put.");
        }
      }
      else {
        sender.sendMessage(ChatColor.RED + "Usage is @CMDCON stringbuilder <args> <message>");
      }

    }

  @WCCommand(aliases = {"itemname"}, desc = "Fetch item name", help = "/itemname", min = 0)
  public void onItemName(Player sender, String[] args){

      if (!(sender instanceof Player)) {
        sender.sendMessage(ChatColor.RED + "Use @CMDOP!");
      }
      else
      {
        Player player = (Player)sender;
        ItemStack item = player.getItemInHand();

        String name = item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().name().replace("_", " ").toLowerCase();
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vtrigger setstr itemname current " + name);
      }

    }
  }