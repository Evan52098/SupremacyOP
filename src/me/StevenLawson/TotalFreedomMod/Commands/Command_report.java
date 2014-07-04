package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "report", usage = "/<command> <playername> <reason>")
public class Command_report extends TFM_Command
{

	@Override
	public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
		{
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

	        String reason = "Unknown";
	        if (args.length >= 2)
	        {
	            reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
	        }
	        for (Player admins: this.server.getOnlinePlayers()){
	        	if 
	        	(TFM_AdminList.isSuperAdmin(admins))
	        			{
		admins.sendMessage(TotalFreedomMod.FREEDOMOP_MOD + ChatColor.RED + "WARNING: " + player.getName() + " Has been reported for " + reason + "!");
		return true;
	}

}
}
		return true;
	}
}
