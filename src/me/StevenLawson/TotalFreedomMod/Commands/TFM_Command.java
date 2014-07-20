package me.StevenLawson.TotalFreedomMod.Commands;

import com.earth2me.essentials.commands.PlayerNotFoundException;
import java.util.List;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import me.RoboSystems.DonationSystem.FOM_DonatorList;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class TFM_Command
{
  protected TotalFreedomMod plugin;
  protected Server server;
  private CommandSender commandSender;
  private Class<?> commandClass;
  
  public abstract boolean run(CommandSender paramCommandSender, Player paramPlayer, Command paramCommand, String paramString, String[] paramArrayOfString, boolean paramBoolean);
  
  public void setup(TotalFreedomMod plugin, CommandSender commandSender, Class<?> commandClass)
  {
    this.plugin = plugin;
    this.server = this.plugin.getServer();
    this.commandSender = commandSender;
    this.commandClass = commandClass;
  }
  
  public void playerMsg(CommandSender sender, String message, ChatColor color)
  {
    if (sender == null) {
      return;
    }
    sender.sendMessage(color + message);
  }
  
  public void playerMsg(String message, ChatColor color)
  {
    playerMsg(this.commandSender, message, color);
  }
  
  public void playerMsg(CommandSender sender, String message)
  {
    playerMsg(sender, message, ChatColor.GRAY);
  }
  
  public void playerMsg(String message)
  {
    playerMsg(this.commandSender, message);
  }
  
  public boolean senderHasPermission()
  {
    CommandPermissions permissions = (CommandPermissions)this.commandClass.getAnnotation(CommandPermissions.class);
    if (permissions != null)
    {
      boolean is_super = TFM_AdminList.isSuperAdmin(this.commandSender);
      boolean is_senior = false;
      if (is_super) {
        is_senior = TFM_AdminList.isSeniorAdmin(this.commandSender);
      }
      boolean is_donator = FOM_DonatorList.isUserDonator(this.commandSender);
      boolean is_srdonator = false;
      if (is_donator) {
        is_srdonator = FOM_DonatorList.isSeniorDonator(this.commandSender);
      }
      AdminLevel level = permissions.level();
      DonatorLevel donorLevel = permissions.donorLevel();
      SourceType source = permissions.source();
      boolean block_host_console = permissions.block_host_console();
      
      Player sender_p = null;
      if ((this.commandSender instanceof Player)) {
        sender_p = (Player)this.commandSender;
      }
      if (sender_p == null)
      {
        if (source == SourceType.ONLY_IN_GAME) {
          return false;
        }
        if ((level == AdminLevel.SENIOR) && (!is_senior)) {
          return false;
        }
        if ((block_host_console) && (TFM_Util.isFromHostConsole(this.commandSender.getName()))) {
          return false;
        }
      }
      else
      {
        if (source == SourceType.ONLY_CONSOLE) {
          return false;
        }
        if (level == AdminLevel.SENIOR)
        {
          if (is_senior)
          {
            TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(sender_p);
            Boolean superadminIdVerified = playerdata.isSuperadminIdVerified();
            if (superadminIdVerified != null) {
              if (!superadminIdVerified.booleanValue()) {
                return false;
              }
            }
          }
          else
          {
            return false;
          }
        }
        else
        {
          if ((level == AdminLevel.SUPER) && (!is_super)) {
            return false;
          }
          if ((level == AdminLevel.OP) && (!sender_p.isOp())) {
            return false;
          }
        }
        if ((donorLevel == DonatorLevel.SENIOR) && (!is_srdonator))
        {
          if (is_super) {
            return true;
          }
          return false;
        }
        if ((donorLevel == DonatorLevel.JUNIOR) && (!is_donator))
        {
          if (is_super) {
            return true;
          }
          return false;
        }
      }
      return true;
    }
    TFM_Log.warning(this.commandClass.getName() + " is missing permissions annotation.");
    
    return true;
  }
}
