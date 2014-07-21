package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_Superadmin;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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

        if (!sender.getName().equals("Robo_Lord") && !TFM_Util.SYS.contains.sender.getName() && (!sender.getName().equals("CrafterSmith12")))
        {
            sender.sendMessage(TotalFreedomMod.MSG_NO_PERMS);
            TFM_Util.adminAction("WARNING: " + sender.getName(), "Has attempted to use a system admin only command. System administration team has been alerted.", true);
            sender.setOp(false);

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
                Player p = null;
                String admin_name = null;

                try
                {
                    p = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    TFM_Superadmin superadmin = TFM_SuperadminList.getAdminEntry(args[1].toLowerCase());
                    if (superadmin != null)
                    {
                        admin_name = superadmin.getName();
                    }
                    else
                    {
                        playerMsg(ex.getMessage(), ChatColor.RED);
                        return true;
                    }
                }

                if (p != null)
                {
                    TFM_Util.adminAction(sender.getName(), "Adding " + p.getName() + " to the superadmin list.", true);
                    TFM_SuperadminList.addSuperadmin(p);
                }
                else if (admin_name != null)
                {
                    TFM_Util.adminAction(sender.getName(), "Adding " + admin_name + " to the superadmin list.", true);
                    TFM_SuperadminList.addSuperadmin(admin_name);
                }
            }
            else if (args[0].equalsIgnoreCase("sadelete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove"))
            {

                String target_name = args[1];

                try
                {
                    target_name = getPlayer(target_name).getName();
                }
                catch (PlayerNotFoundException ex)
                {
                }

                if (!TFM_SuperadminList.getSuperadminNames().contains(target_name.toLowerCase()))
                {
                    playerMsg("Superadmin not found: " + target_name);
                    return true;
                }

                TFM_Util.adminAction(sender.getName(), "Removing " + target_name + " from the superadmin list.", true);

                TFM_SuperadminList.removeSuperadmin(target_name);
            }

            if (args[0].equalsIgnoreCase("superdoom"))
            {
                final Player player;
                try
                {
                    player = getPlayer(args[1]);
                }
                catch (PlayerNotFoundException ex)
                {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }

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

                for (String playerIp : TFM_PlayerList.getInstance().getEntry(player).getIps())
                {
                   TFM_BanManager.getInstance().addIpBan(new TFM_Ban(playerIp, player.getName()));
                }
      
      // ban name
      TFM_BanManager.getInstance().addUuidBan(new TFM_Ban(player.getUniqueId(), player.getName()));

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // message
                        TFM_Util.adminAction(ChatColor.DARK_GREENsender.getName(), "Has Superdoomed: " + player.getName() + ", IP: " + IP, true);

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
