package me.StevenLawson.TotalFreedomMod.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "Click my almighty dragons >:)", usage = "/<command>")
public class Command_dragons extends TFM_Command
{

    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        
            playerMsg(ChatColor.AQUA + "Please check out Robo's dragons so he doesn't bitch ingame");
            playerMsg(ChatColor.AQUA + "They're call little dragons");
            playerMsg(ChatColor.AQUA + "CrafterSmith: https://pravi.us/2Ah");
            playerMsg(ChatColor.AQUA + "DarkLynx: http://dragcave.net/view/vLcWa");
            return true;
    }
}
