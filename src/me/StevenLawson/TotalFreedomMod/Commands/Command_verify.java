/*
package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
import me.StevenLawson.TotalFreedomMod.TFM_CommandBlocker;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TFM_PermbanList;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerList;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Verify Command- For Verifying", usage = "/<command> [reload]")
public class Command_verify extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 1)
        {
            if (args[0].equals(TFM_ConfigEntry.VERIFY_PASSWORD))
            {
                return false;
            }

            if (!TFM_AdminList.isAdminImpostor(sender_p))
            {
                playerMsg(TotalFreedomMod.YOU_ARE_NOT_IMPOSTER);
                return true;
            }
            sender_p.sendMessage(ChatColor.RED + "You have verified");
		    TFM_AdminList.addSuperadmin(sender_p);
		    TFM_Util.bcastMsg(sender_p.getName() + " Has Verified", ChatColor.RED);
		    TFM_Util.bcastMsg("Rcon - Adding " + sender_p.getName() + " to the super admin list.", ChatColor.RED);
            return true;
        }

        // Arg length 0
        playerMsg("/verify [PSW]");
        

        return true;
    }
}
*/
