package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.commands.MainCommand;
import eu.lukatjee.zoriontp.sql.SQLGetter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class RemoveCommand {

    public RemoveCommand removeCommand(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            final Player player = ((Player) sender).getPlayer();
            final String removePermission = ZorionTP.plugin.getConfig().getString("removePermission");

            if (args.length >=2) {

                if (player.hasPermission(removePermission)) {

                    final String warpKey = args[1];

                    SQLGetter read = new SQLGetter(ZorionTP.plugin);
                    List<Object> result = read.readWarp(warpKey);

                    if (result != null) {

                        final UUID warpPlayerUUID = UUID.fromString(result.get(0).toString());
                        final String adminRemovePermission = ZorionTP.plugin.getConfig().getString("adminRemovePermission");

                        if (player.getUniqueId().equals(warpPlayerUUID) || player.hasPermission(adminRemovePermission)) {

                            final String warpRemoved = ChatColor.translateAlternateColorCodes('&', ZorionTP.plugin.getConfig().getString("warpRemoved").replace("{0}", warpKey));

                            SQLGetter remove = new SQLGetter(ZorionTP.plugin);
                            remove.removeWarp(warpKey);

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

            SQLGetter remove = new SQLGetter(ZorionTP.plugin);
            remove.removeWarp(warpKey);

            sender.sendMessage(warpRemoved);

        }

        return null;

    }

}
