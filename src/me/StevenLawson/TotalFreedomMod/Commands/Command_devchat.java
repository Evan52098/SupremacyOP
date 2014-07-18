package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(
        description = "DevChat - Talk privately with other developers.  Using <command> itself will toggle DevChat on and off for all messages",
        usage = "/<command> [message...]",
        aliases = "devchat")
public class Command_devchat extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
    if (!TFM_Util.DEVELOPERS.contains(sender.getName())) 
    {
        playerMsg(TotalFreedomMod.MSG_NO_PERMS);
        return true;
    }
        if (args.length == 0)
        {
            if (senderIsConsole)
            {
                playerMsg("Only in-game players can toggle Developer AdminChat.");
                return true;
            }

            TFM_PlayerData userinfo = TFM_PlayerData.getPlayerData(sender_p);
            userinfo.setAdminChat(!userinfo.inAdminChat());
            playerMsg("Toggled DevChat " + (userinfo.inAdminChat() ? "on" : "off") + ".");
        }
        else
        {
            TFM_Util.devadminchatChatMessage(sender, StringUtils.join(args, " "), senderIsConsole);
        }

        return true;
    }
}
