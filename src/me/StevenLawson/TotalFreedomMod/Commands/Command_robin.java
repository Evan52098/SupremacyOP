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
public class Command_robin extends TFM_Command
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
         TFM_Util.adminAction(sender.getName(), "Making it rain hell over " + player.getName(), true);
         TFM_Util.adminAction(sender.getName(), "And will be destroyed!", true);
         player.chat("GOD WHAT DID I DO TO RobinGall2910!");
         final String ip = player.getAddress().getAddress().getHostAddress().trim();
         player.chat("NONONONO PLEASE NO!!!");
         player.chat("I beg you!!!!");
         player.chat("Dont get rid of me!");
         sender_p.chat("Well too bad for you!");
         // im awesome aint i?
         sender.sendMessage(player.getName() + " is now gone.");
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
          player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
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
          this.server.dispatchCommand(sender, "orbit" + player.getName());
          
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
 
         // ban name
         TFM_BanManager.addUuidBan(new TFM_Ban(player.getUniqueId(), player.getName()));
 
         // set gamemode to survival
         player.setGameMode(GameMode.SURVIVAL);
 
         // clear inventory
         player.closeInventory();
         player.getInventory().clear();
 
         // ignite player
         player.setFireTicks(10000);
 
         // generate explosion
         player.getWorld().createExplosion(player.getLocation(), 4F);
 
         // Shoot the player in the sky
         player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
 
         new BukkitRunnable()
         {
            @Override
             public void run()
             {
                 // strike lightning
                 player.getWorld().strikeLightning(player.getLocation());
 
                 // kill (if not done already)
                 player.setHealth(0.0);
                 
                 //Broadcast player is gone
                 TFM_Util.bcastMsg("Aaaaaaaaaaaaaaaaaaand hes gone!", ChatColor.BLUE);
                 
                 //says im dead
                 player.chat("Welp, I'm dead.");
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
                 player.kickPlayer(ChatColor.RED + "Have you been a bad person or naw?!");
                 TFM_Util.bcastMsg(player.getName() + ", is now gone! He was such a bad person!", ChatColor.DARK_GREEN);
            }
        }.runTaskLater(plugin, 3L * 20L);
        }
        return false;
    }
}