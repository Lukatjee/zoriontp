package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.commands.MainCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand {

    public TeleportCommand teleportCommand(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            final Player player = ((Player) sender).getPlayer();
            final String teleportPermission = ZorionTP.plugin.getConfig().getString("teleportPermission");
            final String noPermission = ZorionTP.plugin.getConfig().getString("noPermission");
            final String noPermissionMsg = ChatColor.translateAlternateColorCodes('&', noPermission);

            if (player.hasPermission(teleportPermission)) {

                final String warpKey = args[0];

                if (MainCommand.playerwarps_id.containsKey(warpKey)) {

                    final String warpTeleported = ZorionTP.plugin.getConfig().getString("warpTeleported");
                    final World world = MainCommand.playerwarps_world.get(warpKey);
                    final Integer[] coordinates = MainCommand.playerwarps_id.get(warpKey);
                    final Location location = new Location(world, coordinates[0], coordinates[1], coordinates[2]);

                    player.teleport(location);
                    String warpTeleportedMsg = warpTeleported.replace("{0}", warpKey);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', warpTeleportedMsg));

                } else {

                    final String warpUnknown = ZorionTP.plugin.getConfig().getString("warpUnknown");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', warpUnknown));

                }

            } else {

                player.sendMessage(noPermissionMsg);

            }

        }

        return null;

    }

}
