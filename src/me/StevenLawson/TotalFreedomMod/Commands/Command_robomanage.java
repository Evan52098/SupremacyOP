package me.StevenLawson.TotalFreedomMod.Commands;

import com.earth2me.essentials.commands.PlayerNotFoundException;
import me.StevenLawson.TotalFreedomMod.Bridge.TFM_EssentialsBridge;
import me.StevenLawson.TotalFreedomMod.Bridge.TFM_WorldEditBridge;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_Ban;
import me.StevenLawson.TotalFreedomMod.TFM_BanManager;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerList;
import me.StevenLawson.TotalFreedomMod.TFM_RollbackManager;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(description = "Robo's manging command", usage = "/<command> <power> <argument>", aliases = "rm")
public class Command_robomanage extends TFM_Command
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "Usage: /robomanage <power> [arg]");
        }

        else if (args[0].equalsIgnoreCase("menu"))
        {
            sender.sendMessage(ChatColor.GREEN + "=====Robomanage Help Page=====");
            sender.sendMessage(ChatColor.GREEN + "Please do not abuse any commands or over-use them. Thanks.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage obliviate|obv <player> - Superadmin command - Obliviate a bad player. Just for the really bad ones.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage nope <player> - Superadmin command - Nope a bad player.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage smite <player> <message> - Superadmin command - Smite a bad player WITH your own smite message!");
            sender.sendMessage(ChatColor.BLUE + "/robomanage bc <message...> - Superadmin command - Broadcast to the server Essentials style.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage ride <player> - Superadmin command - Ride a suspicous player - may want to be invis.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage machat <player <message...> - Superadmin command - Take someones chat and embarrass them.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage thinice <player> - Superadmin command - For the people on thin ice.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage warn <player> - Superadmin command - Warn a player for permban.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage facepalm - Superadmin command - Facepalm. All I have to say.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage report [custommsg...] - Report a player for breaking a rule.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage savinghelp [-a] - Learn how to save structures with WorldEdit - only admins can use the -a switch.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage explode - Superadmin command - Create an explosion at your area.");
            sender.sendMessage(ChatColor.BLUE + "/robomanage fireball [type] - Superadmin command - Create. A fucking. Fireball.");
            sender.sendMessage(ChatColor.GREEN + "Please do not abuse any commands or over-use them. Thanks.");
            sender.sendMessage(ChatColor.GREEN + "=====Robomanage Help Page=====");
        }

        else if (args[0].equalsIgnoreCase("obliviate") || args[0].equalsIgnoreCase("obv"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /robomanage obliviate|obv <player>");
                    return true;
                }

                final Player player;
                player = getPlayer(args[1]);

                TFM_Util.adminAction(sender.getName(), "Casting complete holy obliviation over " + player.getName(), true);
                TFM_Util.bcastMsg(player.getName() + " will be completely obliviated using thy satanic holy powers!", ChatColor.RED);
                TFM_Util.bcastMsg(player.getName() + " has been a VERY naughty, naughty boy.", ChatColor.RED);

                final String IP = player.getAddress().getAddress().getHostAddress().trim();
                if (TFM_AdminList.isSuperAdmin(player))
                {
                    TFM_Util.adminAction(sender.getName(), "Removing " + player.getName() + " from the superadmin list.", true);
                    TFM_AdminList.removeSuperadmin(player);
                 }

                // remove from whitelist
                player.setWhitelisted(false);

                // deop
                if (player.isOp())
                {
                    player.setOp(false);
                }

                // ban IP
                String ip = TFM_Util.getFuzzyIp(player.getAddress().getAddress().getHostAddress());
                // ban IPs
               for (String playerIp : TFM_PlayerList.getEntry(player).getIps())
               {
                  TFM_BanManager.addIpBan(new TFM_Ban(playerIp, player.getName()));
               }

                // ban uuid
                TFM_BanManager.addUuidBan(player);

                // set gamemode to survival
                player.setGameMode(GameMode.SURVIVAL);

                // clear inventory
                player.closeInventory();
                player.getInventory().clear();

                // ignite player
                player.setFireTicks(10000);

                // rollback + undo
                TFM_WorldEditBridge.getInstance().undo(player, 15);

                TFM_RollbackManager.rollback(player.getName());

                // generate explosion
                player.getWorld().createExplosion(player.getLocation(), 7F);

                // go up into the sky
                player.setVelocity(new Vector(0, 70, 0));

                // runnables
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // ask the player a question
                        TFM_Util.bcastMsg("Hey, " + player.getName() + ", what's the difference between jelly and jam?", ChatColor.LIGHT_PURPLE);
                    }
                }.runTaskLater(plugin, 40L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // answer it
                        TFM_Util.bcastMsg("I can't jelly my banhammer down your ass!", ChatColor.LIGHT_PURPLE);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 100L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());

                        // kill (if not done already)
                        player.setHealth(0.0);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // go up into the sky
                        player.setVelocity(new Vector(0, 1000, 0));
                    }
                }.runTaskLater(plugin, 140L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());

                        // kill (if not done already)
                        player.setHealth(0.0);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // go up into the sky
                        player.setVelocity(new Vector(0, 1000, 0));
                    }
                }.runTaskLater(plugin, 160L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // message
                        TFM_Util.adminAction(sender.getName(), "Banning: " + player.getName() + ", IP: " + IP, true);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // kick player
                        player.kickPlayer(ChatColor.RED + "FUCKOFF, and get your MOTHER FUCKING shit together!");
                    }
                }.runTaskLater(plugin, 190L);

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("nope"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /robomanage nope <player>");
                    return true;
                }

                final Player player;
                player = getPlayer(args[1]);

                final String IP = player.getAddress().getAddress().getHostAddress().trim();
                TFM_Util.adminAction(sender.getName(), "Starting a huge nope fest over " + player.getName(), true);

                // go up into the sky
                player.setVelocity(new Vector(0, 8000, 0));

                // blow up
                player.getWorld().createExplosion(player.getLocation(), 4F);

                // strike lightning
                player.getWorld().strikeLightning(player.getLocation());

                // ban IPs
                  for (String playerIp : TFM_PlayerList.getEntry(player).getIps())
                 {
                   TFM_BanManager.addIpBan(new TFM_Ban(playerIp, player.getName()));
                 }

                // ban uuid
                TFM_BanManager.addUuidBan(player);

                // rollback + undo
                TFM_WorldEditBridge.getInstance().undo(player, 15);

                TFM_RollbackManager.rollback(player.getName());

                // runnables
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // go up into the sky
                        player.setVelocity(new Vector(0, 8000, 0));

                        // blow up
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 50L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // go up into the sky
                        player.setVelocity(new Vector(0, 8000, 0));

                        // blow up
                        player.getWorld().createExplosion(player.getLocation(), 4F);

                        // strike lightning
                        player.getWorld().strikeLightning(player.getLocation());
                    }
                }.runTaskLater(plugin, 90L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // message
                        TFM_Util.adminAction(sender.getName(), "Banning: " + player.getName() + ", IP: " + IP, true);

                        // generate explosion
                        player.getWorld().createExplosion(player.getLocation(), 7F);

                        // kick player
                        player.kickPlayer(ChatColor.RED + "NOPE!\nAppeal at totalfreedom.boards.net\nAnd make sure you follow the rules at totalfreedom.me!");
                    }
                }.runTaskLater(plugin, 120L);

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("smite"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                if (args.length < 3)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /robomanage smite <player> <message>");
                    return true;
                }

                final Player player;
                String message = " ";
                for (int i = 2; i < args.length; i++)
                {
                    if (i > 2)
                    {
                        message += " ";
                    }
                    message += args[i];
                }
                player = getPlayer(args[1]);

                TFM_Util.bcastMsg(ChatColor.RED + player.getName() + " - " + message);

                //Deop
                player.setOp(false);

                //Set gamemode to survival:
                player.setGameMode(GameMode.SURVIVAL);

                //Clear inventory:
                player.getInventory().clear();

                //Strike with lightning effect:
                final Location targetPos = player.getLocation();
                final World world = player.getWorld();
                for (int x = -1; x <= 1; x++)
                {
                    for (int z = -1; z <= 1; z++)
                    {
                        final Location strike_pos = new Location(world, targetPos.getBlockX() + x, targetPos.getBlockY(), targetPos.getBlockZ() + z);
                        world.strikeLightning(strike_pos);
                    }
                }

                //Kill:
                player.setHealth(0.0);

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("bc"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /robomanage bc <message...>");
                    return true;
                }

                String message = "";
                for (int i = 1; i < args.length; i++)
                {
                    if (i > 1)
                    {
                        message += " ";
                    }
                    message += args[i];
                }
                for (Player player : server.getOnlinePlayers())
                {
                    BarAPI.setMessage(message.replaceAll("&", "§"), 60);
                }
                TFM_Util.bcastMsg(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Broadcast" + ChatColor.DARK_GRAY + "] " + ChatColor.AQUA + message);
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }
        else if (args[0].equalsIgnoreCase("ride"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_") || sender instanceof ConsoleCommandSender)
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command, or you are runnning this command from the console.");
            }
            else
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /robomanage ride <player>");
                    return true;
                }

                Player player = Bukkit.getPlayer(args[1]);
                if (player.isOnline())
                {
                    if (!player.getName().equalsIgnoreCase(sender.getName()))
                    {
                        if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
                        {
                            if (player.isEmpty())
                            {
                                player.setPassenger(sender_p);
                                sender.sendMessage(ChatColor.GREEN + "You are now riding: " + player.getName());
                            }
                            else
                            {
                                sender.sendMessage(ChatColor.RED + "That player is riding someone - you cannot ride him.");
                            }
                        }
                        else
                        {
                            sender.sendMessage(ChatColor.RED + "Unfortunetely, you cannot ride admins.");
                        }
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.RED + "You cannot ride yourself.");
                    }
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "That player is not online.");
                }
            }
        }

        else if (args[0].equalsIgnoreCase("machat"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /robomanage machat <player> <message...>");
                    return true;
                }

                final Player player;
                player = getPlayer(args[1]);

                String message = "";
                for (int i = 2; i < args.length; i++)
                {
                    if (i > 2)
                    {
                        message += " ";
                    }
                    message += args[i];
                }

                if (message.startsWith("/"))
                {
                    sender.sendMessage(ChatColor.RED + "You cannot start with a command, please use /gcmd for commands.");
                }
                else
                {
                    player.chat(message);
                }

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }
        else if (args[0].equalsIgnoreCase("thinice"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /robomanage thinice <player>");
                    return true;
                }

                final Player player;
                player = getPlayer(args[1]);

                TFM_Util.adminAction(sender.getName(), "Giving " + player.getName() + " the thin ice treatment", true);
                TFM_Util.bcastMsg("You are on thin fucking ice, my pedigree chum, and I shall be under it when it breaks. Now fuck off!", ChatColor.RED);

                player.setOp(false);
                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().clear();

                sender_p.getWorld().strikeLightning(sender_p.getLocation());
                sender_p.getWorld().strikeLightning(sender_p.getLocation());

                Location loc = player.getLocation();
                loc.setY(loc.getY() - 10);
                player.teleport(loc);
                player.setHealth(0.0);
                player.setVelocity(new Vector(0, -3000, 0));
                player.setVelocity(new Vector(0, 7000, 0));

                // runnables
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // show the player the moon
                        player.setVelocity(new Vector(0, 7000, 0));
                    }
                }.runTaskLater(plugin, 40L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // kill
                        player.setHealth(0.0);

                        // lightning
                        sender_p.getWorld().strikeLightning(sender_p.getLocation());
                    }
                }.runTaskLater(plugin, 60L);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        // tempban
                        TFM_Util.bcastMsg(player.getName() + " fell from the thin ice", ChatColor.RED);
                        // ban IP
                        String ip = TFM_Util.getFuzzyIp(player.getAddress().getAddress().getHostAddress());
                        for (String playerIp : TFM_PlayerList.getEntry(player).getIps())
                        {
                        TFM_BanManager.addIpBan(new TFM_Ban(playerIp, player.getName()));
                        }
                        // ban name
                        TFM_BanManager.addUuidBan(new TFM_Ban(player.getUniqueId(), player.getName()));
                        // Kick
                        player.kickPlayer(ChatColor.RED + "You fell from the thin ice! Maybe you should read the rules at totalfreedom.me.");
                    }
                }.runTaskLater(plugin, 80L);

                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("warn"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                if (args.length == 1)
                {
                    sender.sendMessage(ChatColor.RED + "Usage: /robomanage warn <player>");
                    return true;
                }

                final Player player;
                player = getPlayer(args[1]);

                TFM_Util.adminAction(sender.getName(), "Warning " + player.getName() + " of permban", true);

                player.setOp(false);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);

                sender_p.getWorld().strikeLightning(sender_p.getLocation());
                sender_p.getWorld().strikeLightning(sender_p.getLocation());
                player.getWorld().strikeLightning(sender_p.getLocation());
                player.getWorld().strikeLightning(sender_p.getLocation());
                player.setHealth(0.0);

                player.sendMessage(ChatColor.DARK_RED + player.getName() + ", you are at high risk of being permanently banned (name and IP) from the Total Freedom server. Please immediately review all rules listed at www.totalfreedom.me and comply with them.");
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("facepalm"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                TFM_Util.bcastMsg(sender.getName() + " really needs to have a facepalm ragequit moment here...", ChatColor.RED);
                sender_p.chat("FACEDESK");
                return true;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("report"))
        {
            if (args.length == 1)
            {
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    if (TFM_AdminList.isSuperAdmin(player))
                   {
                      player.sendMessage("[" + ChatColor.AQUA + "Report" + ChatColor.WHITE + "] " + ChatColor.DARK_GREEN + sender.getName() + " Has got a problem or is being griefed!");
                   }
                 }
                return true;
            }

            String message = "";
            for (int i = 1; i < args.length; i++)
            {
                if (i > 1)
                {
                    message += " ";
                }
                message += args[i];
            }

                for (Player player : Bukkit.getOnlinePlayers())
                {
                    if (TFM_AdminList.isSuperAdmin(player))
                   {
                      player.sendMessage("[" + ChatColor.AQUA + "Report" + ChatColor.WHITE + "] " + ChatColor.DARK_GREEN + sender.getName() + " - " + message);
                   }
                 }
            sender.sendMessage(ChatColor.GREEN + "Your message has been sent to the administration team. :)");
        }

        else if (args[0].equalsIgnoreCase("savinghelp"))
        {
            if (args.length == 1)
            {
                sender.sendMessage(ChatColor.RED + "1.) Do //wand (or use the //pos commands).");
                sender.sendMessage(ChatColor.RED + "2.) Select the two outermost angles of your build.");
                sender.sendMessage(ChatColor.RED + "3.) Do //copy in order to copy your build.");
                sender.sendMessage(ChatColor.RED + "4.) Use: //schematic save yourschematicname in order to save your build.");
                sender.sendMessage(ChatColor.RED + "5.) Use: //schematic load yourschematicname in order to load it again. Then, you can use //paste to paste it into the world.");
            }
            else if (args[1].equals("-a"))
            {
                if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
                {
                    TFM_Util.bcastMsg("1.) Do //wand (or use the //pos commands).", ChatColor.RED);
                    TFM_Util.bcastMsg("2.) Select the two outermost angles of your build.", ChatColor.RED);
                    TFM_Util.bcastMsg("3.) Do //copy in order to copy your build.", ChatColor.RED);
                    TFM_Util.bcastMsg("4.) Use: //schematic save yourschematicname in order to save your build.", ChatColor.RED);
                    TFM_Util.bcastMsg("5.) Use: //schematic load yourschematicname in order to load it again. Then, you can use //paste to paste it into the world.", ChatColor.RED);
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                }
            }
            return true;
        }

        else if (args[0].equalsIgnoreCase("explode"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                sender_p.getWorld().createExplosion(sender_p.getLocation(), 5F);

                sender_p.getWorld().strikeLightning(sender_p.getLocation());
                sender.sendMessage(ChatColor.RED + "Exploded!");
                sender.sendMessage(ChatColor.YELLOW + "Location: " + sender_p.getLocation());
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else if (args[0].equalsIgnoreCase("fireball"))
        {
            if (sender.getName().equalsIgnoreCase("Robo_Lord") || sender.getName().equalsIgnoreCase("R3CH4RG3D_"))
            {
                Class<? extends org.bukkit.entity.Entity> type = org.bukkit.entity.Fireball.class;
                int speed = 2;
                if (args.length > 1)
                {
                    if (args[1].equalsIgnoreCase("small"))
                    {
                        type = org.bukkit.entity.SmallFireball.class;
                    }
                    else if (args[1].equalsIgnoreCase("arrow"))
                    {
                        type = org.bukkit.entity.Arrow.class;
                    }
                    else if (args[1].equalsIgnoreCase("skull"))
                    {
                        type = WitherSkull.class;
                    }
                    else if (args[1].equalsIgnoreCase("egg"))
                    {
                        type = org.bukkit.entity.Egg.class;
                    }
                    else if (args[1].equalsIgnoreCase("snowball"))
                    {
                        type = org.bukkit.entity.Snowball.class;
                    }
                    else if (args[1].equalsIgnoreCase("expbottle"))
                    {
                        type = org.bukkit.entity.ThrownExpBottle.class;
                    }
                    else if (args[1].equalsIgnoreCase("large"))
                    {
                        type = org.bukkit.entity.LargeFireball.class;
                    }
                }
                Vector direction = sender_p.getEyeLocation().getDirection().multiply(speed);
                Projectile projectile = (Projectile) sender_p.getWorld().spawn(sender_p.getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), type);
                projectile.setShooter(sender_p);
                projectile.setVelocity(direction);
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        }

        else
        {
            sender.sendMessage(ChatColor.RED + "Usage: /robomanage <power> [arg]");
        }

        return true;
    }
}
