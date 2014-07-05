package me.StevenLawson.TotalFreedomMod.Commands;

import java.util.Random;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(description = "PotatoPotatoPotato", usage = "/<command>")
public class Command_potato extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        StringBuilder output = new StringBuilder();
        Random randomGenerator = new Random();

        String[] words = TotalFreedomMod.POTATO_LYRICS.split(" ");
        for (String word : words)
        {
            String color_code = Integer.toHexString(1 + randomGenerator.nextInt(14));
            output.append(ChatColor.COLOR_CHAR).append(color_code).append(word).append(" ");
        }

        ItemStack heldItem = new ItemStack(Material.POTATO);
        ItemMeta heldItemMeta = heldItem.getItemMeta();
        heldItemMeta.setDisplayName((new StringBuilder()).append(ChatColor.WHITE).append("Dillons Special").append(ChatColor.BLACK).append("Potato").toString());
        heldItem.setItemMeta(heldItemMeta);

        for (Player player : server.getOnlinePlayers())
        {
            player.getInventory().setItem(player.getInventory().firstEmpty(), heldItem);
            player.awardAchievement(Achievement.THE_END);
        }

        TFM_Util.bcastMsg(output.toString());
        return true;
    }
}
