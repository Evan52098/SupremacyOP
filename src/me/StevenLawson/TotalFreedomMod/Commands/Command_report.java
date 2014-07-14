package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CommandPermissions(level = AdminLevel.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Use when someone is causing trouble to alert admins.", usage = "/<command> <playername>")
public class Command_report extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length != 1)
        {
            return false;
        }

        final Player p = getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(TotalFreedomMod.PLAYER_NOT_FOUND);
            return true;
        }

       for (Player admins: this.server.getOnlinePlayers()){
       if (TFM_AdminList.isSuperAdmin(admins))
	{
		admins.sendMessage(TotalFreedomMod.FREEDOMOP_MOD + ChatColor.RED + " WARNING: " + player.getName() + " Has been reported for " + reason + "!");
		return true;
	}

}
        

        return true;
    }
}
