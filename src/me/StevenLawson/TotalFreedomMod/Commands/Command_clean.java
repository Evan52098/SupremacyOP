package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "Runs the cleanup system.", usage = "/<command>")
public class Command_clean extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        TFM_Util.bcastMsg(ChatColor.RED + "Starting The Clean Up");

        if (senderIsConsole)
        {
            server.dispatchCommand(sender, "opall");
            server.dispatchCommand(sender, "rd");
            server.dispatchCommand(sender, "purgeall");
            server.dispatchCommand(sender, "invis smite");
            server.dispatchCommand(sender, "setl");
            server.dispatchCommand(sender, "mp");
        TFM_Util.bcastMsg(ChatColor.BLUE + "Clean Up Completed.");
        }

        else
        {
            playerMsg(TotalFreedomMod.MSG_NO_PERMS);
            return true;
        }

        return true;
    }
