
package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level=AdminLevel.SUPER, source=SourceType.BOTH)
@CommandParameters(description="Kicks everyone and restarts the server.", usage="/<command>")
public class Command_restart extends TFM_Command
{
 public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
 {
 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say Server Restarting");
 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say now");
 server.shutdown();
     return true;
 }
}