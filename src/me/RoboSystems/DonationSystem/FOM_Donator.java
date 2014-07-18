package me.RoboSystems.DonationSystem;

import java.util.Date;
import java.util.List;
import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;

public class FOM_Donator
{
  private final String name;
  private final String custom_login_message;
  private final boolean is_full_donator;
  private final boolean is_senior_donator;
  private final List<String> console_aliases;
  private List<String> ips;
  private Date last_login;
  private boolean is_activated;
  
  public FOM_Donator(String name, List<String> ips, Date last_login, String custom_login_message, boolean is_full_donator, boolean is_senior_donator, List<String> console_aliases, boolean is_activated)
  {
    this.name = name.toLowerCase();
    this.ips = ips;
    this.last_login = last_login;
    this.custom_login_message = custom_login_message;
    this.is_full_donator = is_full_donator;
    this.is_senior_donator = is_senior_donator;
    this.console_aliases = console_aliases;
    this.is_activated = is_activated;
  }
  
  public FOM_Donator(String name, ConfigurationSection section)
  {
    this.name = name.toLowerCase();
    this.ips = section.getStringList("ips");
    this.last_login = TFM_Util.stringToDate(section.getString("last_login", TFM_Util.dateToString(new Date(0L))));
    this.custom_login_message = section.getString("custom_login_message", "");
    this.is_full_donator = section.getBoolean("is_full_donator", false);
    this.is_senior_donator = section.getBoolean("is_senior_donator", false);
    this.console_aliases = section.getStringList("console_aliases");
    this.is_activated = section.getBoolean("is_activated", true);
  }
  
  public String toString()
  {
    StringBuilder output = new StringBuilder();
    try
    {
      output.append("Name: ").append(this.name).append("\n");
      output.append("- IPs: ").append(StringUtils.join(this.ips, ", ")).append("\n");
      output.append("- Last Login: ").append(TFM_Util.dateToString(this.last_login)).append("\n");
      output.append("- Custom Login Message: ").append(this.custom_login_message).append("\n");
      output.append("- Is Full Donator: ").append(this.is_full_donator).append("\n");
      output.append("- Is Senior Donator: ").append(this.is_senior_donator).append("\n");
      output.append("- Console Aliases: ").append(StringUtils.join(this.console_aliases, ", ")).append("\n");
      output.append("- Is Activated: ").append(this.is_activated);
    }
    catch (Exception ex)
    {
      TFM_Log.severe(ex);
    }
    return output.toString();
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public List<String> getIps()
  {
    return this.ips;
  }
  
  public Date getLastLogin()
  {
    return this.last_login;
  }
  
  public String getCustomLoginMessage()
  {
    return this.custom_login_message;
  }
  
  public boolean isFullDonator()
  {
    return this.is_full_donator;
  }
  
  public boolean isSeniorDonator()
  {
    return this.is_senior_donator;
  }
  
  public List<String> getConsoleAliases()
  {
    return this.console_aliases;
  }
  
  public void setIps(List<String> ips)
  {
    this.ips = ips;
  }
  
  public void setLastLogin(Date last_login)
  {
    this.last_login = last_login;
  }
  
  public boolean isActivated()
  {
    return this.is_activated;
  }
  
  public void setActivated(boolean is_activated)
  {
    this.is_activated = is_activated;
  }
}
