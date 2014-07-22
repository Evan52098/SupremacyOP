package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Util;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.ONLY_CONSOLE)
@CommandParameters(description = "Telnet command - Send a chat message with chat formatting over telnet.", usage = "/<command> <message...>")
public class Command_tabname extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
    if (sender.getName().equals("Robo_Lord") || sender.getName().equals("SupItsDillon") || sender.getName().equals("buildcarter8"))
    {
         if (args.length == 0)
        {
            return false;
        }
        if (args.length > 0)
        {
            String name = StringUtils.join(ArrayUtils.subarray(args, 0, args.length), " ");
            sender_p.setPlayerListName(name.replaceAll("&", "§"));
        }
        return true;
    }
    else
    {
      playerMsg("Ahahahahahah, No -.-");
    }
    return true;
  }
}
