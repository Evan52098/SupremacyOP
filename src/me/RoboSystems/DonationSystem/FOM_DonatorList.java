/*
package me.RoboSystems.DonationSystem;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import me.StevenLawson.TotalFreedomMod.Commands.Command_logs;
import me.StevenLawson.TotalFreedomMod.Config.TFM_Config;
import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.Config.TFM_MainConfig;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import net.minecraft.util.com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.FileUtil;

public class FOM_DonatorList
{
    private static final Map<UUID, FOM_Donator> donatorList;
    private static final Set<UUID> superUUIDs;
    private static final Set<UUID> telnetUUIDs;
    private static final Set<UUID> seniorUUIDs;
    private static final Set<String> seniorConsoleAliases;
    private static final Set<String> superIps;
    private static int cleanThreshold = 24 * 7; // 1 Week in hours

    static
    {
        donatorList = new HashMap<UUID, FOM_Donator>();
        superUUIDs = new HashSet<UUID>();
        telnetUUIDs = new HashSet<UUID>();
        seniorUUIDs = new HashSet<UUID>();
        seniorConsoleAliases = new HashSet<String>();
        superIps = new HashSet<String>();
    }

    private FOM_DonatorList()
    {
        throw new AssertionError();
    }

    public static Set<UUID> getSuperUUIDs()
    {
        return Collections.unmodifiableSet(superUUIDs);
    }

    public static Set<UUID> getTelnetUUIDs()
    {
        return Collections.unmodifiableSet(telnetUUIDs);
    }

    public static Set<UUID> getSeniorUUIDs()
    {
        return Collections.unmodifiableSet(seniorUUIDs);
    }

    public static Set<String> getSeniorConsoleAliases()
    {
        return Collections.unmodifiableSet(seniorConsoleAliases);
    }

    public static Set<String> getDonatorIps()
    {
        return Collections.unmodifiableSet(superIps);
    }

    public static Set<String> getSuperNames()
    {
        final Set<String> names = new HashSet<String>();

        for (FOM_Donator donator : donatorList.values())
        {
            if (!donator.isActivated())
            {
                continue;
            }

            names.add(donator.getLastLoginName());
        }

        return Collections.unmodifiableSet(names);
    }

    public static Set<String> getLowerSuperNames()
    {
        final Set<String> names = new HashSet<String>();

        for (FOM_Donator donator : donatorList.values())
        {
            if (!donator.isActivated())
            {
                continue;
            }

            names.add(donator.getLastLoginName().toLowerCase());
        }

        return Collections.unmodifiableSet(names);
    }

    public static Set<FOM_Donator> getAllDonators()
    {
        return Sets.newHashSet(donatorList.values());
    }

    public static void load()
    {
        donatorList.clear();

        final TFM_Config config = new TFM_Config(TotalFreedomMod.plugin, TotalFreedomMod.DONATOR_FILE, true);
        config.load();

        cleanThreshold = config.getInt("clean_threshold_hours", cleanThreshold);

        // Parse old donators
        if (config.isConfigurationSection("donators"))
        {
            parseOldConfig(config);
        }

        if (!config.isConfigurationSection("donators"))
        {
            TFM_Log.warning("Missing donator section in dontarors.yml.");
            return;
        }

        final ConfigurationSection section = config.getConfigurationSection("donators");

        for (String uuidString : section.getKeys(false))
        {
            if (!TFM_Util.isUniqueId(uuidString))
            {
                TFM_Log.warning("Invalid Unique ID: " + uuidString + " in donators.yml, ignoring");
                continue;
            }

            final UUID uuid = UUID.fromString(uuidString);

            final FOM_Donator donator = new FOM_Donator(uuid, section.getConfigurationSection(uuidString));
            donatorList.put(uuid, donator);
        }

        updateIndexLists();

        TFM_Log.info("Loaded " + donatorList.size() + " donators (" + superUUIDs.size() + " active) and " + superIps.size() + " IPs.");
    }

    public static void updateIndexLists()
    {
        superUUIDs.clear();
        telnetUUIDs.clear();
        seniorUUIDs.clear();
        seniorConsoleAliases.clear();
        superIps.clear();

        for (FOM_Donator donator : donatorList.values())
        {
            if (!donator.isActivated())
            {
                continue;
            }

            final UUID uuid = donator.getUniqueId();

            superUUIDs.add(uuid);

            for (String ip : donator.getIps())
            {
                superIps.add(ip);
            }

            if (donator.isFullDonator())
            {
                telnetUUIDs.add(uuid);

                for (String alias : donator.getConsoleAliases())
                {
                    seniorConsoleAliases.add(alias.toLowerCase());
                }
            }


            if (donator.isSeniorDonator())
            {
                seniorUUIDs.add(uuid);
            }
        }

        FOM_DonatorWorld.getInstance().wipeAccessCache();
    }

    private static void parseOldConfig(TFM_Config config)
    {
        TFM_Log.info("Old donator configuration found, parsing...");

        final ConfigurationSection section = config.getConfigurationSection("donators");

        int counter = 0;
        int errors = 0;

        for (String donator : config.getConfigurationSection("donators").getKeys(false))
        {
            final OfflinePlayer player = Bukkit.getOfflinePlayer(donator);

            if (player == null || player.getUniqueId() == null)
            {
                errors++;
                TFM_Log.warning("Could not convert donator " + donator + ", UUID could not be found!");
                continue;
            }

            final String uuid = player.getUniqueId().toString();

            config.set("donators." + uuid + ".last_login_name", player.getName());
            config.set("donators." + uuid + ".is_activated", section.getBoolean(donator + ".is_activated"));
            config.set("donators." + uuid + ".is_full_donator", section.getBoolean(donator + ".is_fullt_donator"));
            config.set("donators." + uuid + ".is_senior_donator", section.getBoolean(donator + ".is_senior_donator"));
            config.set("donators." + uuid + ".last_login", section.getString(donator + ".last_login"));
            config.set("donators." + uuid + ".custom_login_message", section.getString(donator + ".custom_login_message"));
            config.set("donators." + uuid + ".console_aliases", section.getStringList(donator + ".console_aliases"));
            config.set("donators." + uuid + ".ips", section.getStringList(donator + ".ips"));

            counter++;
        }

        config.set("donators", null);
        config.save();

        TFM_Log.info("Done! " + counter + " donators parsed, " + errors + " errors");
    }

    public static void save()
    {
        final TFM_Config config = new TFM_Config(TotalFreedomMod.plugin, TotalFreedomMod.DONATOR_FILE, true);
        config.load();

        config.set("clean_threshold_hours", cleanThreshold);

        Iterator<Entry<UUID, FOM_Donator>> it = donatorList.entrySet().iterator();
        while (it.hasNext())
        {
            Entry<UUID, FOM_Donator> pair = it.next();

            UUID uuid = pair.getKey();
            FOM_Donator donator = pair.getValue();

            config.set("donators." + uuid + ".last_login_name", donator.getLastLoginName());
            config.set("donators." + uuid + ".is_activated", donator.isActivated());
            config.set("donators." + uuid + ".is_full_donator", donator.isFullDonator());
            config.set("donators." + uuid + ".is_senior_donator", donator.isSeniorDonator());
            config.set("donators." + uuid + ".last_login", TFM_Util.dateToString(donator.getLastLogin()));
            config.set("donators." + uuid + ".custom_login_message", donator.getCustomLoginMessage());
            config.set("donators." + uuid + ".console_aliases", TFM_Util.removeDuplicates(donator.getConsoleAliases()));
            config.set("donators." + uuid + ".ips", TFM_Util.removeDuplicates(donator.getIps()));
        }

        config.save();
    }

    public static FOM_Donator getEntry(Player player)
    {
        final UUID uuid = player.getUniqueId();

        if (Bukkit.getOnlineMode())
        {
            if (donatorList.containsKey(uuid))
            {
                return donatorList.get(uuid);
            }
        }

        return getEntryByIp(TFM_Util.getIp(player));
    }

    public static FOM_Donator getEntry(UUID uuid)
    {
        return donatorList.get(uuid);
    }

    @Deprecated
    public static FOM_Donator getEntry(String name)
    {
        for (UUID uuid : donatorList.keySet())
        {
            if (donatorList.get(uuid).getLastLoginName().equalsIgnoreCase(name))
            {
                return donatorList.get(uuid);
            }
        }
        return null;
    }

    public static FOM_Donator getEntryByIp(String ip)
    {
        return getEntryByIp(ip, false);
    }

    public static FOM_Donator getEntryByIp(String needleIp, boolean fuzzy)
    {
        Iterator<Entry<UUID, FOM_Donator>> it = donatorList.entrySet().iterator();
        while (it.hasNext())
        {
            final Entry<UUID, FOM_Donator> pair = it.next();
            final FOM_Donator donator = pair.getValue();

            if (fuzzy)
            {
                for (String haystackIp : donator.getIps())
                {
                    if (TFM_Util.fuzzyIpMatch(needleIp, haystackIp, 3))
                    {
                        return donator;
                    }
                }
            }
            else
            {
                if (donator.getIps().contains(needleIp))
                {
                    return donator;
                }
            }
        }
        return null;
    }

    public static void updateLastLogin(Player player)
    {
        final FOM_Donator donator = getEntry(player);
        if (donator == null)
        {
            return;
        }
        donator.setLastLogin(new Date());
        donator.setLastLoginName(player.getName());
        save();
    }

    public static boolean isSeniorDonator(CommandSender sender)
    {
        return isSeniorDonator(sender, false);
    }

    public static boolean isSeniorDonator(CommandSender sender, boolean verifyDonator)
    {
        if (verifyDonator)
        {
            if (!isDonator(sender))
            {
                return false;
            }
        }


        if (!(sender instanceof Player))
        {
            return seniorConsoleAliases.contains(sender.getName())
                    || (TFM_MainConfig.getInstance().getBoolean(TFM_ConfigEntry.CONSOLE_IS_SENIOR) && sender.getName().equals("CONSOLE"));
        }

        final FOM_Donator entry = getEntry((Player) sender);
        if (entry != null)
        {
            return entry.isSeniorDonator();
        }

        return false;
    }

    public static boolean isSuperDonator(CommandSender sender)
    {
        if (!(sender instanceof Player))
        {
            return true;
        }

        if (Bukkit.getOnlineMode() && superUUIDs.contains(((Player) sender).getUniqueId()))
        {
            return true;
        }


        if (superIps.contains(TFM_Util.getIp((Player) sender)))
        {
            return true;
        }

        return false;
    }

    public static boolean isFullDonator(CommandSender sender, boolean verifyDonator)
    {
        if (verifyDonator)
        {
            if (!isDonator(sender))
            {
                return false;
            }
        }

        final FOM_Donator entry = getEntry((Player) sender);
        if (entry != null)
        {
            return entry.isFullDonator();
        }

        return false;
    }

    public static boolean isIdentityMatched(Player player)
    {
        if (!isDonator(player))
        {
            return false;
        }

        if (Bukkit.getOnlineMode())
        {
            return true;
        }

        final FOM_Donator entry = getEntry(player);
        if (entry == null)
        {
            return false;
        }

        return entry.getUniqueId().equals(player.getUniqueId());
    }

    @Deprecated
    public static boolean checkPartialDonatorIp(String ip, String name)
    {
        ip = ip.trim();

        if (donatorIps.contains(ip))
        {
            return true;
        }

        try
        {
            String matchIp = null;
            for (String testIp : superIps)
            {
                if (TFM_Util.fuzzyIpMatch(ip, testIp, 3))
                {
                    matchIp = testIp;
                    break;
                }
            }

            if (matchIp != null)
            {
                final FOM_Donator entry = getEntryByIp(matchIp);

                if (entry == null)
                {
                    return true;
                }

                if (entry.getLastLoginName().equalsIgnoreCase(name))
                {
                    if (!entry.getIps().contains(ip))
                    {
                        entry.addIp(ip);
                    }
                    save();
                }
                return true;

            }
        }
        catch (Exception ex)
        {
            TFM_Log.severe(ex);
        }

        return false;
    }

    public static boolean isDonatorImpostor(Player player)
    {
        if (superUUIDs.contains(player.getUniqueId()))
        {
            return !isDonator(player);
        }

        return false;
    }

    public static void addDonator(OfflinePlayer player)
    {
        final UUID uuid = player.getUniqueId();
        final String ip = TFM_Util.getIp(player);

        if (donatorList.containsKey(uuid))
        {
            final FOM_Donator donator = donatorList.get(uuid);
            donator.setActivated(true);

            if (player instanceof Player)
            {
                donator.setLastLogin(new Date());
                donator.addIp(ip);
            }
            save();
            updateIndexLists();
            return;
        }

        if (ip == null)
        {
            TFM_Log.severe("Cannot add donator: " + TFM_Util.formatPlayer(player));
            TFM_Log.severe("Could not retrieve IP!");
            return;
        }

        final FOM_Donator donator = new FOM_Donator(
                uuid,
                player.getName(),
                new Date(),
                "",
                false,
                false,
                true);
        donator.addIp(ip);

        donatorList.put(uuid, donator);

        save();
        updateIndexLists();
    }

    public static void removeDonater(OfflinePlayer player)
    {
        final UUID uuid = player.getUniqueId();

        if (!donatorList.containsKey(uuid))
        {
            TFM_Log.warning("Could not remove donator: " + TFM_Util.formatPlayer(player));
            TFM_Log.warning("Player is not an donator!");
            return;
        }

        final FOM_Donator donator = donatorList.get(uuid);
        donator.setActivated(false);
        
        save();
        updateIndexLists();
    }

    public static void cleanDonatorList(boolean verbose)
    {
        Iterator<Entry<UUID, FOM_Donator>> it = donatorList.entrySet().iterator();
        while (it.hasNext())
        {
            final Entry<UUID, FOM_Donator> pair = it.next();
            final FOM_Donator donator = pair.getValue();

            if (!donator.isActivated() || donator.isSeniorDonator())
            {
                continue;
            }

            final Date lastLogin = donator.getLastLogin();
            final long lastLoginHours = TimeUnit.HOURS.convert(new Date().getTime() - lastLogin.getTime(), TimeUnit.MILLISECONDS);

            if (lastLoginHours > cleanThreshold)
            {
                if (verbose)
                {
                    TFM_Util.adminAction("TotalFreedomMod", "Deactivating donator " + donator.getLastLoginName() + ", inactive for " + lastLoginHours + " hours.", true);
                }

                donator.setActivated(false);
                }
        }

        save();
        updateIndexLists();
    }
}
*/
