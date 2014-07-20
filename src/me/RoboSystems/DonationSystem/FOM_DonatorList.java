package me.RoboSystems.DonationSystem;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.FileUtil;

public class FOM_DonatorList
{
  private static Map<String, FOM_Donator> donatorList = new HashMap();
  private static List<String> donatorNames = new ArrayList();
  private static List<String> donatorIPs = new ArrayList();
  private static List<String> fullDonatorNames = new ArrayList();
  private static List<String> seniorDonatorNames = new ArrayList();
  private static int clean_threshold_hours = 168;
  
  private FOM_DonatorList()
  {
    throw new AssertionError();
  }
  
  public static List<String> getDonatorIPs()
  {
    return donatorIPs;
  }
  
  public static List<String> getDonatorNames()
  {
    return donatorNames;
  }
  
  public static List<String> getSuperDonatorNames()
  {
    return superDonatorNames;
  }
  
  public static List<String> getSeniorDonatorNames()
  {
    return seniorDonatorNames;
  }
  
  public static void loadDonatorList()
  {
    try
    {
      donatorList.clear();
      

      TFM_Util.createDefaultConfiguration("donator.yml");
      FileConfiguration config = YamlConfiguration.loadConfiguration(new File(TotalFreedomMod.plugin.getDataFolder(), "donator.yml"));
      
      clean_threshold_hours = config.getInt("clean_threshold_hours", clean_threshold_hours);
      ConfigurationSection section;
      if (config.isConfigurationSection("donators"))
      {
        section = config.getConfigurationSection("donators");
        for (String donator_name : section.getKeys(false))
        {
          FOM_Donator donator = new FOM_Donator(donator_name, section.getConfigurationSection(donator_name));
          donatorList.put(donator_name.toLowerCase(), donator);
        }
      }
      else
      {
        TFM_Log.warning("Missing donators section in donator.yml.");
      }
      updateIndexLists();
    }
    catch (Exception ex)
    {
      TFM_Log.severe(ex);
    }
  }
  
  public static void backupSavedList()
  {
    File a = new File(TotalFreedomMod.plugin.getDataFolder(), "donator.yml");
    File b = new File(TotalFreedomMod.plugin.getDataFolder(), "donator.yml.bak");
    FileUtil.copy(a, b);
  }
  
  public static void updateIndexLists()
  {
    donatorNames.clear();
    donatorIPs.clear();
    seniorDonatorNames.clear();
    
    Iterator<Map.Entry<String, FOM_Donator>> it = donatorList.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<String, FOM_Donator> pair = (Map.Entry)it.next();
      
      String donator_name = ((String)pair.getKey()).toLowerCase();
      FOM_Donator donator = (FOM_Donator)pair.getValue();
      if (donator.isActivated())
      {
        donatorNames.add(donator_name);
        for (String ip : donator.getIps()) {
          donatorIPs.add(ip);
        }
        if (donator.isSeniorDonator())
        {
          seniorDonatorNames.add(donator_name);
          for (String console_alias : donator.getConsoleAliases()) {
            seniorDonatorNames.add(console_alias.toLowerCase());
          }
        }
        if (donator.isFullDonator()) {
          fullDonatorNames.add(donator_name);
        }
      }
    }
    donatorNames = TFM_Util.removeDuplicates(donatorNames);
    donatorIPs = TFM_Util.removeDuplicates(donatorIPs);
    fullDonatorNames = TFM_Util.removeDuplicates(fullDonatorNames);
    seniorDonatorNames = TFM_Util.removeDuplicates(seniorDonatorNames);
  }
  
  public static void saveDonatorList()
  {
    try
    {
      updateIndexLists();
      
      YamlConfiguration config = new YamlConfiguration();
      
      config.set("clean_threshold_hours", Integer.valueOf(clean_threshold_hours));
      
      Iterator<Map.Entry<String, FOM_Donator>> it = donatorList.entrySet().iterator();
      while (it.hasNext())
      {
        Map.Entry<String, FOM_Donator> pair = (Map.Entry)it.next();
        
        String donator_name = ((String)pair.getKey()).toLowerCase();
        FOM_Donator donator = (FOM_Donator)pair.getValue();
        
        config.set("donators." + donator_name + ".ips", TFM_Util.removeDuplicates(donator.getIps()));
        config.set("donators." + donator_name + ".last_login", TFM_Util.dateToString(donator.getLastLogin()));
        config.set("donators." + donator_name + ".custom_login_message", donator.getCustomLoginMessage());
        config.set("donators." + donator_name + ".is_super_donator", Boolean.valueOf(donator.isSuperDonator()));
        config.set("donators." + donator_name + ".is_senior_donator", Boolean.valueOf(donator.isSeniorDonator()));
        config.set("donators." + donator_name + ".console_aliases", TFM_Util.removeDuplicates(donator.getConsoleAliases()));
        config.set("donators." + donator_name + ".is_activated", Boolean.valueOf(donator.isActivated()));
      }
      config.save(new File(TotalFreedomMod.plugin.getDataFolder(), "donator.yml"));
    }
    catch (Exception ex)
    {
      TFM_Log.severe(ex);
    }
  }
  
  public static FOM_Donator getDonatorEntry(String donator_name)
  {
    donator_name = donator_name.toLowerCase();
    if (donatorList.containsKey(donator_name)) {
      return (FOM_Donator)donatorList.get(donator_name);
    }
    return null;
  }
  
  public static FOM_Donator getDonatorEntry(Player p)
  {
    return getDonatorEntry(p.getName().toLowerCase());
  }
  
  public static FOM_Donator getDonatorEntryByIP(String ip)
  {
    Iterator<Map.Entry<String, FOM_Donator>> it = donatorList.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<String, FOM_Donator> pair = (Map.Entry)it.next();
      FOM_Donator donator = (FOM_Donator)pair.getValue();
      if (donator.getIps().contains(ip)) {
        return donator;
      }
    }
    return null;
  }
  
  public static void updateLastLogin(Player p)
  {
    FOM_Donator donator_entry = getDonatorEntry(p);
    if (donator_entry != null)
    {
      donator_entry.setLastLogin(new Date());
      saveDonatorList();
    }
  }
  
  public static boolean isSeniorDonator(CommandSender user)
  {
    return isSeniorDonator(user, false);
  }
  
  public static boolean isSeniorDonator(CommandSender user, boolean verify_is_donator)
  {
    if (verify_is_donator) {
      if (!isUserDonator(user)) {
        return false;
      }
    }
    String user_name = user.getName().toLowerCase();
    if (!(user instanceof Player)) {
      return seniorDonatorNames.contains(user_name);
    }
    FOM_Donator donator_entry = getDonatorEntry((Player)user);
    if (donator_entry != null) {
      return donator_entry.isSeniorDonator();
    }
    return false;
  }
  
  public static boolean isUserDonator(CommandSender user)
  {
    if (!(user instanceof Player)) {
      return true;
    }
    if (Bukkit.getOnlineMode()) {
      if (donatorNames.contains(user.getName().toLowerCase())) {
        return true;
      }
    }
    try
    {
      String user_ip = ((Player)user).getAddress().getAddress().getHostAddress();
      if ((user_ip != null) && (!user_ip.isEmpty())) {
        if (donatorIPs.contains(user_ip)) {
          return true;
        }
      }
    }
    catch (Exception ex)
    {
      return false;
    }
    return false;
  }
  
  public static boolean checkPartialdonatorIP(String user_ip, String user_name)
  {
    try
    {
      user_ip = user_ip.trim();
      if (donatorIPs.contains(user_ip)) {
        return true;
      }
      String match_ip = null;
      for (String test_ip : getDonatorIPs()) {
        if (TFM_Util.fuzzyIpMatch(user_ip, test_ip, 3))
        {
          match_ip = test_ip;
          break;
        }
      }
      if (match_ip != null)
      {
        FOM_Donator donator_entry = getDonatorEntryByIP(match_ip);
        if (donator_entry != null) {
          if (donator_entry.getName().equalsIgnoreCase(user_name))
          {
            List<String> ips = donator_entry.getIps();
            ips.add(user_ip);
            donator_entry.setIps(ips);
            saveDonatorList();
          }
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
  
  public static boolean isDonatorImpostor(CommandSender user)
  {
    if (!(user instanceof Player)) {
      return false;
    }
    Player p = (Player)user;
    if (donatorNames.contains(p.getName().toLowerCase())) {
      return !isUserDonator(p);
    }
    return false;
  }
  
  public static void addDonator(String donator_name, List<String> ips)
  {
    try
    {
      donator_name = donator_name.toLowerCase();
      if (donatorList.containsKey(donator_name))
      {
        FOM_Donator donator = (FOM_Donator)donatorList.get(donator_name);
        donator.setActivated(true);
        donator.getIps().addAll(ips);
        donator.setLastLogin(new Date());
      }
      else
      {
        Date last_login = new Date();
        String custom_login_message = "";
        boolean is_super_donator = false;
        boolean is_senior_donator = false;
        List<String> console_aliases = new ArrayList();
        
        FOM_Donator donator = new FOM_Donator(donator_name, ips, last_login, custom_login_message, is_full_donator, is_senior_donator, console_aliases, true);
        donatorList.put(donator_name.toLowerCase(), donator);
      }
      saveDonatorList();
    }
    catch (Exception ex)
    {
      TFM_Log.severe(ex);
    }
  }
  
  public static void addDonator(Player p)
  {
    String donator_name = p.getName().toLowerCase();
    List<String> ips = Arrays.asList(new String[] { p.getAddress().getAddress().getHostAddress() });
    
    addDonator(donator_name, ips);
  }
  
  public static void addDonator(String donator_name)
  {
    addDonator(donator_name, new ArrayList());
  }
  
  public static void removeDonator(String donator_name)
  {
    try
    {
      donator_name = donator_name.toLowerCase();
      if (donatorList.containsKey(donator_name))
      {
        FOM_Donator donator = (FOM_Donator)donatorList.get(donator_name);
        donator.setActivated(false);
        saveDonatorList();
      }
    }
    catch (Exception ex)
    {
      TFM_Log.severe(ex);
    }
  }
  
  public static void removeDonator(Player p)
  {
    removeDonator(p.getName());
  }
  
  public static boolean verifyIdentity(String donator_name, String ip)
    throws Exception
  {
    if (Bukkit.getOnlineMode()) {
      return true;
    }
    FOM_Donator donator_entry = getDonatorEntry(donator_name);
    if (donator_entry != null) {
      return donator_entry.getIps().contains(ip);
    }
    throw new Exception();
  }
}
