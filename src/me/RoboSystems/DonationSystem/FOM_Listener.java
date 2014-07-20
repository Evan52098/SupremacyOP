package me.RoboSystems.DonationSystem;

import org.bukkit.event.Listener;
import me.StevenLawson.TotalFreedomMod.*;

public class FOM_Listener implements Listener
{
    public static void loadDonatorConfig()
    {
        try
        {
            FOM_DonatorList.backupSavedList();
            FOM_DonatorList.loadDonatorList();
            
            FOM_DonatorList.getDonatorIPs();
            FOM_DonatorList.getDonatorNames();
        }
        catch (Exception ex)
        {
            TFM_Log.severe("Error loading donator list: " + ex.getMessage());
        }
    }
}
