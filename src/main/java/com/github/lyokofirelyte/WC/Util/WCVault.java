package com.github.lyokofirelyte.WC.Util;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.github.lyokofirelyte.WC.WCMain;

public class WCVault
{
  public WCMain plugin;
  public static Economy econ = null;
  public static Permission perms = null;
  public static Chat chat = null;
  public boolean vaultHooked = false;

  public WCVault(WCMain plugin)
  {
    this.plugin = plugin;
  }

  public boolean hookSetup()
  {
    if (this.plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
      this.vaultHooked = true;
      setupEconomy();
      setupPermissions();
      setupChat();
     // setupUrl2();
    }

    return this.vaultHooked;
  }


  @SuppressWarnings("rawtypes")
private boolean setupEconomy()
  {
    RegisteredServiceProvider rsp = this.plugin.getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    econ = (Economy)rsp.getProvider();
    return econ != null;
  }

  @SuppressWarnings("rawtypes")
private boolean setupPermissions() {
    RegisteredServiceProvider rsp = this.plugin.getServer().getServicesManager().getRegistration(Permission.class);
    perms = (Permission)rsp.getProvider();
    return perms != null;
  }

  @SuppressWarnings("rawtypes")
private boolean setupChat() {
    RegisteredServiceProvider rsp = this.plugin.getServer().getServicesManager().getRegistration(Chat.class);
    chat = (Chat)rsp.getProvider();
    return chat != null;
  }
}