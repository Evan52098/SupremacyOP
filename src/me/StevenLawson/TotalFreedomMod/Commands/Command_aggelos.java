package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Ban;
import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TFM_UuidResolver;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "For the bad Superadmins", usage = "/<command> <playername>")
public class Command_aggelos extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
    if (!sender.getName().equals("aggelosQQ"))
    {
      playerMsg(TotalFreedomMod.MSG_NO_PERMS);
    }
        if (args.length == 0)
        {
            return true;
        }
        if (args.length == 1)
        {
        final Player player = getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(TotalFreedomMod.PLAYER_NOT_FOUND);
            return true;
        }
        TFM_Util.adminAction(sender.getName(), "aggelosQQ is mad with " + player.getName(), true);
        TFM_Util.bcastMsg(sender.getName() + " will fuck " + player.getName() + " up!", ChatColor.GREEN);
        TFM_Util.bcastMsg(sender.getName() + " - From where did you get those clothes " + player.getName() + "? from the toilet store....!!?!??!?!?!?!?!?", ChatColor.BLUE);
        TFM_Util.bcastMsg(sender.getName() + " is aggelos'ing " + player.getName() + "!!!!", ChatColor.GREEN);
        TFM_Util.bcastMsg(player.getName() + " is being aggelos'd by " + sender.getName() + " Because he is a stupid!", ChatColor.GREEN);
        TFM_Util.bcastMsg(player.getName() + ", hey you know what aggelosQQ is saying right now for you?? HE SAYS YOU ARE A LITTLE BITCH! ASSHOLE!", ChatColor.DARK_RED);
        TFM_Util.bcastMsg(player.getName() + " is a bitch! ", ChatColor.GOLD);
        TFM_Util.bcastMsg(player.getName() + " is a bitch! ", ChatColor.RED);
        TFM_Util.bcastMsg(player.getName() + " is a bitch! ", ChatColor.BLUE);
        TFM_Util.bcastMsg(player.getName() + " is a bitch! ", ChatColor.GREEN);
        TFM_Util.bcastMsg(player.getName() + " is a bitch! ", ChatColor.DARK_PURPLE);
        TFM_Util.bcastMsg(player.getName() + " is a bitch! ", ChatColor.LIGHT_PURPLE);

        final String ip = player.getAddress().getAddress().getHostAddress().trim();

        // remove from superadmin
        if (TFM_AdminList.isSuperAdmin(player))
        {
            TFM_Util.adminAction(sender.getName(), "Removing " + player.getName() + " from the superadmin list.", true);
            TFM_AdminList.removeSuperadmin(player);
        }

        // remove from whitelist
        player.setWhitelisted(false);

        // deop
        player.setOp(false);

        // ban IPs
        for (String playerIp : TFM_PlayerList.getEntry(player).getIps())
        {
            TFM_BanManager.addIpBan(new TFM_Ban(playerIp, player.getName()));
        }

        // ban uuid
        TFM_BanManager.addUuidBan(player);

        // set gamemode to survival
        player.setGameMode(GameMode.SURVIVAL);
        player.setGameMode(GameMode.CREATIVE);
        player.setGameMode(GameMode.SURVIVAL);
        player.setGameMode(GameMode.CREATIVE);
        player.setGameMode(GameMode.SURVIVAL);
        player.setGameMode(GameMode.CREATIVE);
        player.setGameMode(GameMode.SURVIVAL);

        // clear inventory
        player.closeInventory();
        player.getInventory().clear();

        // ignite player
        player.setFireTicks(10000);
        player.setFireTicks(10000);
        player.setFireTicks(10000);
        player.setFireTicks(10000);
        player.setFireTicks(10000); 
        player.setFireTicks(10000);
        player.setFireTicks(10000);
        player.setFireTicks(10000);
        player.setFireTicks(10000);
        player.setFireTicks(10000);
        player.setFireTicks(10000);
        player.setFireTicks(10000); 
        player.setFireTicks(10000);
        player.setFireTicks(10000);
        player.setFireTicks(10000);

        // generate explosion
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        player.getWorld().createExplosion(player.getLocation(), 4F);
        
        // strike lightning
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        
        // Shoot the player in the sky
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                // strike lightning
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());
                player.getWorld().strikeLightning(player.getLocation());                

                // kill (if not done already)
                player.setHealth(0.0);
            }
        }.runTaskLater(plugin, 2L * 20L);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                // message
                TFM_Util.adminAction(sender.getName(), "Banning " + player.getName() + ", IP: " + ip, true);

                // generate explosion
                player.getWorld().createExplosion(player.getLocation(), 4F);

                // kick player
                player.kickPlayer(ChatColor.RED + "aggelosQQ is very angry with you! You are a DUMBASS! DEAL WITH IT!");
            }
        }.runTaskLater(plugin, 3L * 20L);
        }
        return false;
    }
}
        