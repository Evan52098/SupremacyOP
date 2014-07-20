package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.RoboSystems.DonationSystem.FOM_DonatorWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Goto the DonatorWorld. Donors only!", usage = "/<command>")
public class Command_donatorworld extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (TFM_ConfigEntry.FLATLANDS_GENERATE.getBoolean())
        {
            FOM_DonatorWorld.getInstance().sendToWorld(sender_p);
        }
        else
        {
            playerMsg("The world is currently disabled.");
        }
        return true;
    }
}
