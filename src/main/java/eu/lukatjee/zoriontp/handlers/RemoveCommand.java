package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.commands.MainCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RemoveCommand {

    public RemoveCommand removeCommand(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            final Player player = ((Player) sender).getPlayer();
            final String removePermission = ZorionTP.plugin.getConfig().getString("removePermission");

            if (args.length >=2) {

                if (player.hasPermission(removePermission)) {

                    final String warpKey = args[1];

                    if (MainCommand.playerwarps_id.containsKey(warpKey)) {

                        final UUID warpPlayerUUID = MainCommand.playerwarps_owner.get(warpKey);
                        final String adminRemovePermission = ZorionTP.plugin.getConfig().getString("adminRemovePermission");

                        if (player.getUniqueId().equals(warpPlayerUUID) || player.hasPermission(adminRemovePermission)) {

                            final String warpRemoved = ChatColor.translateAlternateColorCodes('&', ZorionTP.plugin.getConfig().getString("warpRemoved").replace("{0}", warpKey));

                            MainCommand.playerwarps_id.remove(warpKey);
                            MainCommand.playerwarps_world.remove(warpKey);
                            MainCommand.playerwarps_owner.remove(warpKey);

                            player.sendMessage(warpRemoved);

                        } else {

                            String noOwnership = ChatColor.translateAlternateColorCodes('&', ZorionTP.plugin.getConfig().getString("noOwnership"));
                            player.sendMessage(noOwnership);

                        }

                    } else {

                        String warpUnknown = ChatColor.translateAlternateColorCodes('&', ZorionTP.plugin.getConfig().getString("warpUnknown"));
                        player.sendMessage(warpUnknown);

                    }

                } else {

                    player.sendMessage(MainCommand.noPermissionMsg);

                }

            } else {

                final String missingArgument = ChatColor.translateAlternateColorCodes('&', ZorionTP.plugin.getConfig().getString("missingArgument"));
                player.sendMessage(missingArgument);

            }

        } else {

            final String warpKey = args[1];
            final String warpRemoved = ChatColor.translateAlternateColorCodes('&', ZorionTP.plugin.getConfig().getString("warpRemoved").replace("{0}", warpKey));

            MainCommand.playerwarps_id.remove(warpKey);
            MainCommand.playerwarps_world.remove(warpKey);
            MainCommand.playerwarps_owner.remove(warpKey);

            sender.sendMessage(warpRemoved);

        }

        return null;

    }

}
