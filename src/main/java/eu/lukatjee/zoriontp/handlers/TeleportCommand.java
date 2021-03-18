package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class TeleportCommand {

    FileConfiguration configuration = ZorionTP.plugin.getConfig();
    SQLGetter read = new SQLGetter(ZorionTP.plugin);

    public void teleportCommand(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = ((Player) sender).getPlayer();
            String teleportPermission = configuration.getString("teleportPermission");

            if (player.hasPermission(teleportPermission)) {

                String warpKey = args[0];
                List<Object> result = read.readWarp(warpKey);

                if (result != null) {

                    String warpTeleported = ZorionTP.plugin.getConfig().getString("warpTeleported");
                    String worldString = result.get(2).toString();
                    Integer x = Integer.parseInt(result.get(3).toString());
                    Integer y = Integer.parseInt(result.get(4).toString());
                    Integer z = Integer.parseInt(result.get(5).toString());

                    Location location = new Location(Bukkit.getServer().getWorld(worldString), x, y, z);
                    player.teleport(location);

                    String warpTeleportedMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("warpTeleported").replace("{0}", warpKey));
                    player.sendMessage(warpTeleportedMsg);

                } else {

                    String warpUnknownMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("warpUnknown"));
                    player.sendMessage(warpUnknownMsg);

                }

            } else {

                String noPermissionMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("noPermission"));
                player.sendMessage(noPermissionMsg);

            }

        }

    }

}
