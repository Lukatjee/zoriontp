package eu.lukatjee.zoriontp;

import eu.lukatjee.zoriontp.commands.MainCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZorionTP extends JavaPlugin {

    public static ZorionTP plugin;

    @Override
    public void onEnable() {

        plugin = this;

        System.out.println("Yay, this plugins up and running...");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("zoriontp").setExecutor(new MainCommand());

    }

}
