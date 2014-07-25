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
                      player.sendMessage("[" + ChatColor.AQUA + "Report" + ChatColor.WHITE + "] " + ChatColor.DARK_GREEN + sender.getName() + " Has got a problem or is being griefed!");
                      player.sendMessage(TotalFreedomMod.FREEDOMOP_MOD + ChatColor.WHITE + "Here's " + sender.getName() + "thier reason!");
                      player.sendMessage("[" + ChatColor.AQUA + "Report" + ChatColor.WHITE + "] " + reason);
                   }
                 }
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Your message to report is " + reason + "and has been sent to the administration team. If you have not put thier name (if griefed) put it in the next message.");
        return true;
    }
}