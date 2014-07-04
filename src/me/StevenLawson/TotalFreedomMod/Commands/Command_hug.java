package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.OP, source = SourceType.BOTH)
@CommandParameters(description = "Give someone a HUG", usage = "/<command> [player, all]")
public class Command_hug extends TFM_Command
{

	@Override
	public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
		   if (args.length == 0)
	        {
	            return true;
	        }

	        final Player player = getPlayer(args[0]);

	        if (player == null)
	        {
	            playerMsg(TotalFreedomMod.PLAYER_NOT_FOUND, ChatColor.RED);
	            return true;
       }          if (args[0].equalsIgnoreCase("all"))
       {
    	   return false;
       }
       	sender.sendMessage(ChatColor.DARK_RED + "HUGGING ALL PLAYERS <3");
       	TFM_Util.bcastMsg(sender.getName() + " Hugging all online players", ChatColor.GOLD);
       	TFM_Util.bcastMsg("You Have been hugged by " + sender.getName(), ChatColor.DARK_RED);
       	
	        sender.sendMessage(ChatColor.DARK_RED + "You have hugged " + player.getName());
	        TFM_Util.bcastMsg(ChatColor.GOLD + sender.getName() + " Has Hugged " + player.getName());
	        player.sendMessage(ChatColor.DARK_RED + "You have been hugged by " + sender.getName() + " <3");
	        {
            }
		return true;
	}
}
