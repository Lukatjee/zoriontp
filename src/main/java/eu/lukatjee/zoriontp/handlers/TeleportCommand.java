package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.commands.MainCommand;
import eu.lukatjee.zoriontp.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TeleportCommand {

    public TeleportCommand teleportCommand(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            final Player player = ((Player) sender).getPlayer();
            final String teleportPermission = ZorionTP.plugin.getConfig().getString("teleportPermission");

            if (player.hasPermission(teleportPermission)) {

                String warpKey = args[0];

                SQLGetter read = new SQLGetter(ZorionTP.plugin);
                List<Object> result = read.readWarp(warpKey);

                if (result != null) {

                    String warpTeleported = ZorionTP.plugin.getConfig().getString("warpTeleported");
                    String worldString = result.get(2).toString();
                    Integer x = Integer.parseInt(result.get(3).toString());
                    Integer y = Integer.parseInt(result.get(4).toString());
                    Integer z = Integer.parseInt(result.get(5).toString());

                    Location location = new Location(Bukkit.getServer().getWorld(worldString), x, y, z);

                    player.teleport(location);
                    String warpTeleportedMsg = warpTeleported.replace("{0}", warpKey);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', warpTeleportedMsg));

                } else {

                    final String warpUnknown = ZorionTP.plugin.getConfig().getString("warpUnknown");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', warpUnknown));

                }

            } else {

                player.sendMessage(MainCommand.noPermissionMsg);

            }

        }

        return null;

    }

}
