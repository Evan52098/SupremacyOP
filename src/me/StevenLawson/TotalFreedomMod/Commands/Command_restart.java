package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.scheduler.BukkitRunnable;

@CommandPermissions(level=AdminLevel.SUPER, source=SourceType.BOTH)
@CommandParameters(description="Kicks everyone and restarts the server.", usage="/<command>")
public class Command_restart extends TFM_Command
{
 public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
 {
 TFM_Util.bcastMsg("Server is restarting by" player.getName());
 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say in 5");


 new BukkitRunnable()
 {
 public void run()
 {
 TFM_Util.adminAction(sender.getName(), " Restarting Server");
 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say in 4");
 }
 }

 .runTaskLater(this.plugin, 100L);

 new BukkitRunnable()
 {
 public void run()
 {
 TFM_Util.adminAction(sender.getName(), " Restarting Server");
 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say in 3");
 }
 }

 .runTaskLater(this.plugin, 100L);

 new BukkitRunnable()
 {
 public void run()
 {
 TFM_Util.adminAction(sender.getName(), " Restarting Server");
 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say in 2");
 }
 }

 .runTaskLater(this.plugin, 100L);

 new BukkitRunnable()
 {
 public void run()
 {
 TFM_Util.adminAction(sender.getName(), " Restarting Server");
 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say Server is shutting down. NOW!");
 server.shutdown();
 }

 .runTaskLater(this.plugin, 100L);

 new BukkitRunnable()
 {
 public void run() {}
 }






 .runTaskLater(this.plugin, 190L);
 return true;
 }
}
