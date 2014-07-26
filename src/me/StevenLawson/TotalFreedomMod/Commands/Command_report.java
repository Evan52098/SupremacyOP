package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandPermissions(level = AdminLevel.ALL, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Use when someone is causing trouble to alert admins.", usage = "/<command> <playername> <reason>")
public class Command_report extends TFM_Command
{
    private String reason;
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
      if (args.length == 1)
            {
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    if (TFM_AdminList.isSuperAdmin(player))
                   {
                      player.sendMessage(TotalFreedomMod.FREEDOMOP_MODREPORT + ChatColor.DARK_GREEN + sender.getName() + " Has got a problem or is being griefed!");
                   }
                 }
                return true;
            }
            String message = "";
            for (int i = 1; i < args.length; i++)
            {
                if (i > 1)
                {
                    message += " ";
                }
                message += args[i];
            }

                for (Player player : Bukkit.getOnlinePlayers())
                {
                    if (TFM_AdminList.isSuperAdmin(player))
                   {
                      player.sendMessage(TotalFreedomMod.FREEDOMOP_MODREPORT + ChatColor.DARK_GREEN + sender.getName() + " - " + message);
                   }
                 }
            sender.sendMessage(ChatColor.GREEN + "Your message has been sent to the administration team. :)");
            return true;
    }
}
