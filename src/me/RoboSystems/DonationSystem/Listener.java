package me.RoboSystems.DonationSystem;

import org.bukkit.event.Listener;

public class FOM_AvalancheListener implements Listener
{
    public static void loadDonatorConfig()
    {
        try
        {
            TFM_DonatorList.backupSavedList();
            TFM_DonatorList.loadDonatorList();

            superadmins = TFM_DonatorList.getDonatorNames();
            superadmin_ips = TFM_DonatorList.getDonatorIPs();
        }
        catch (Exception ex)
        {
            TFM_Log.severe("Error loading donator list: " + ex.getMessage());
        }
    }
}
