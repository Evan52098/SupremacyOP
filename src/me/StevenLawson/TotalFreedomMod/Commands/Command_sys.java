package me.StevenLawson.TotalFreedomMod.Commands;

import com.earth2me.essentials.commands.PlayerNotFoundException;
import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_Admin;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Ban;
import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerList;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_TwitterHandler;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandPermissions(level = AdminLevel.ALL, source = SourceType.BOTH)
@CommandParameters(description = "System Administration Management", usage = "/<command> <Teston | Testoff <saadd| sadelete| superdoom> <username>>")
public class Command_sys extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {

        if ((!sender.getName().equals("cowgomooo12")) || !sender.getName().equals("Robo_Lord") || (!sender.getName().equals("CrafterSmith12")))
        {
            sender.sendMessage(TotalFreedomMod.MSG_NO_PERMS);
            TFM_Util.adminAction("WARNING: " + sender.getName(), "Has attempted to use a system admin only command. System administration team has been alerted.", true);
            return true;
        }

        if (args.length == 0)
        {
            return false;
        }

        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("teston"))
            {
                TFM_Util.adminAction("WARNING: " + sender.getName(), "Has Started Testing on this server.", true);
            }
            
            if (args[0].equalsIgnoreCase("testoff"))
            {
                TFM_Util.adminAction("COMPLETED: " + sender.getName(), "Has succefully finished server testing", true);
            }
        }
        else if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("saadd"))
            {
                OfflinePlayer player = getPlayer(args[1]);

            if (player == null)
            {
                final TFM_Admin superadmin = TFM_AdminList.getEntry(args[1]);

                if (superadmin == null)
                {
                    playerMsg(TotalFreedomMod.PLAYER_NOT_FOUND);
                    return true;
                }

                player = Bukkit.getOfflinePlayer(superadmin.getLastLoginName());
            }

            TFM_Util.adminAction(sender.getName(), "Adding " + player.getName() + " to the superadmin list", true);
            TFM_AdminList.addSuperadmin(player);

            return true;
            }
            else if (args[0].equalsIgnoreCase("sadelete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove"))
            {
            String targetName = args[1];


            final Player player = getPlayer(targetName);

            if (player != null)
            {
                targetName = player.getName();
            }

            if (!TFM_AdminList.getLowerSuperNames().contains(targetName.toLowerCase()))
            {
                playerMsg("Superadmin not found: " + targetName);
                return true;
            }

            TFM_Util.adminAction(sender.getName(), "Removing " + targetName + " from the superadmin list", true);
            TFM_AdminList.removeSuperadmin(Bukkit.getOfflinePlayer(targetName));

            // Twitterbot
            if (TFM_ConfigEntry.TWITTERBOT_ENABLED.getBoolean())
            {
                TFM_TwitterHandler.getInstance().delTwitterVerbose(targetName, sender);
            }
            return true;
            }

            if (args[0].equalsIgnoreCase("superdoom"))
            {
                final Player player;
                player = getPlayer(args[1]);

                TFM_Util.adminAction(ChatColor.DARK_GREEN + sender.getName(), "Casting a dark shadow of oblivion over " + player.getName(), true);
                TFM_Util.bcastMsg(player.getName() + " will be completely obliviated!", ChatColor.DARK_GREEN);

                final String IP = player.getAddress().getAddress().getHostAddress().trim();
                if (TFM_AdminList.isSuperAdmin(player))
                {
                   TFM_Util.adminAction(ChatColor.DARK_GREEN + sender.getName(), "Obliterating " + player.getName() + "'s Super Admin perms...", true);
                   TFM_AdminList.removeSuperadmin(player);
                }

                // remove from whitelist
                player.setWhitelisted(false);

                // deop
                player.setOp(false);

                // set gamemode to survival
                player.setGameMode(GameMode.SURVIVAL);

                // clear inventory
                player.closeInventory();
                player.getInventory().clear();

                // ignite player
                player.setFireTicks(10000);

                // generate explosion
                player.getWorld().createExplosion(player.getLocation(), 4F);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 20L * 2L);

                // generate explosion
                player.getWorld().createExplosion(player.getLocation(), 4F);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 20L * 2L);

                // message
                TFM_Util.adminAction(ChatColor.DARK_GREEN + player.getName(), "Has been Superdoomed, may the hell continue ", true);

                // ignite player
                player.setFireTicks(10000);

                // ban IPs
        for (String playerIp : TFM_PlayerList.getEntry(player).getIps())
        {
            TFM_BanManager.addIpBan(new TFM_Ban(playerIp, player.getName()));
        }

        // ban uuid
        TFM_BanManager.addUuidBan(player);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // message
                        TFM_Util.adminAction(ChatColor.DARK_GREEN + sender.getName(), "Has Superdoomed: " + player.getName() + ", IP: " + IP, true);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        // kick player
                        player.kickPlayer(ChatColor.DARK_GREEN + "FUCKOFF, and get your shit together you super doomed cunt!");
                    }
                }.runTaskLater(plugin, 20L * 3L);

            }
            else
            {

                return false;
            }

            return true;
        }
        return true;
    }
}
