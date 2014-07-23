package me.StevenLawson.TotalFreedomMod.Commands;
 
 import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
 import me.StevenLawson.TotalFreedomMod.TFM_Ban;
 import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
 import me.StevenLawson.TotalFreedomMod.TFM_PlayerList;
 import me.StevenLawson.TotalFreedomMod.TFM_Util;
 import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
 import org.bukkit.ChatColor;
 import org.bukkit.GameMode;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;
 import org.bukkit.scheduler.BukkitRunnable;
 import org.bukkit.util.Vector;
 
 @CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
 @CommandParameters(description = "Robin's Command", usage = "/<command> <playername>")
 public class Command_robin extends TFM_Command
 {
     @Override
     public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
     {
     if (!sender.getName().equals("RobinGall2910"))
     {
      playerMsg(TotalFreedomMod.MSG_NO_PERMS);
     }
         if (args.length == 0)
         {
             return false;
         }
         
         if (args.length == 1)
         
         {
             return true;
         }
 
         final Player player = getPlayer(args[0]);
 
         if (player == null)
         {
             sender.sendMessage(TotalFreedomMod.PLAYER_NOT_FOUND);
             return false;
         }
 
         TFM_Util.adminAction(sender.getName(), "Making it rain hell over " + player.getName(), true);
         final String ip = player.getAddress().getAddress().getHostAddress().trim();
        /*
         // Not sure if this is aloud ;/
         // remove from superadmin
         if (TFM_AdminList.isSuperAdmin(player))
         {
             TFM_Util.adminAction(sender.getName(), "Removing " + player.getName() + " from the superadmin list.", true);
             TFM_AdminList.removeSuperadmin(player);
         }
        */
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
             }
         }.runTaskLater(plugin, 3L * 20L);
 
         return true;
     }
 }
