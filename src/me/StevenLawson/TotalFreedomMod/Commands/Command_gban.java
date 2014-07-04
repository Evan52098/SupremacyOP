package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Ban;
import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Super Admin Command- Ban a user for griefing!", usage = "/<command> <player>")
public class Command_gban extends TFM_Command
{

	@Override
	public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
		   if (args.length == 0)
	        {
	            return false;
	        }

	        final Player player = getPlayer(args[0]);

	        if (player == null)
	        {
	            playerMsg(TotalFreedomMod.PLAYER_NOT_FOUND, ChatColor.RED);
	            return true;
        }
        TFM_Util.bcastMsg(ChatColor.RED + sender.getName() + " - Banning " + player.getName() + " For Griefing!");

        server.dispatchCommand(sender, "/undo 15 " + player.getName());
        
        server.dispatchCommand(sender, "rollback " + player.getName());
        player.kickPlayer(ChatColor.RED + "No Griefing! Banned by " + sender.getName() + " Wrongly Banned? Appeal on freedomop.boards.net");
        player.setBanned(true);
        //IPBAN
        sender.sendMessage(ChatColor.RED + "Warning: " + player.getName() + " Isnt Ip banned!");
        return true;
	}

}