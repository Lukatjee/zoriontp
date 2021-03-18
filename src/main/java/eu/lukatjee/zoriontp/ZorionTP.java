package eu.lukatjee.zoriontp;

import eu.lukatjee.zoriontp.commands.MainCommand;
import eu.lukatjee.zoriontp.sql.MySQL;
import eu.lukatjee.zoriontp.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class ZorionTP extends JavaPlugin {

    public static ZorionTP plugin;
    public MySQL SQL;
    public SQLGetter DATA;

    @Override
    public void onEnable() {

        plugin = this;
        this.SQL = new MySQL();
        this.DATA = new SQLGetter(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("zoriontp").setExecutor(new MainCommand());

        try {
            this.SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("[ZorionTP] Couldn't connect to the database...");
        }

        if (this.SQL.isConnected()) {

            Bukkit.getLogger().info("[ZorionTP] Successfully connected to the database.");
            DATA.createTable();

        }

    }

    @Override
    public void onDisable() {

        this.SQL.disconnect();

    }

}
