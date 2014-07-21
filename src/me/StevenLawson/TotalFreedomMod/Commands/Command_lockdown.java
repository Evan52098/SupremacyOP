package me.StevenLawson.TotalFreedomMod.Commands;


import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



@commandpermissions(level=AdminLevel.SUPER, source=SourceType.BOTH)
@commandparameters(description="Prevents new players from joining the server", usage="/<command> <on | off>")

public class Command_lockdown extends TFM_Command

{
public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
{

if (args.length != 1) {

return false;
}
if (TFM_Util.isStopCommand(args[0]))
{

TFM_Util.adminAction(sender.getName(), "De-activating server lockdown", true);
me.StevenLawson.TotalFreedomMod.TotalFreedomMod.lockdownEnabled = false;

}

else

{

TFM_Util.adminAction(sender.getName(), "Activating server lockdown", true);

me.StevenLawson.TotalFreedomMod.TotalFreedomMod.lockdownEnabled = true;

}

return true;

}

}
