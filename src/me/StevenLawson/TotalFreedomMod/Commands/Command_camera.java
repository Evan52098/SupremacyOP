package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Use the camera", usage = "/<command> <selfie | throw>")
public class Command_camera extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        if (args.length == 1)
        {
            if (args[0].equals("selfie"))
            {
                TFM_Util.adminAction(ChatColor.GOLD + "WARNING: " + sender.getName(), "has started taking selfies on the server, Tell them that they are pretty!!", false);
                return true;
            }

            else if (args[0].equals("throw"))
            {
                TFM_Util.adminAction(ChatColor.GREEN + "WARNING: " + sender.getName(), "has thrown the camera they are a bad person they didnt take a selfie!", false);
                return true;
            }
            else if (args[0].equals("camera"))
            {
                sender.sendMessage("Please use the usages above. which is Selfie or Throw.");
                return true;
            }
        }

        return true;
    }
}
