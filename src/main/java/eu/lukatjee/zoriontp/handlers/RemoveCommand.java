package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.sql.SQLGetter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class RemoveCommand {

    FileConfiguration configuration = ZorionTP.plugin.getConfig();
    SQLGetter read = new SQLGetter(ZorionTP.plugin);
    SQLGetter remove = new SQLGetter(ZorionTP.plugin);
    
    public RemoveCommand removeCommand(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = ((Player) sender).getPlayer();

            if (args.length >=2) {

                String removePermission = configuration.getString("removePermission");

                if (player.hasPermission(removePermission)) {

                    String warpKey = args[1];
                    List<Object> result = read.readWarp(warpKey);

                    if (result != null) {

                        UUID warpPlayerUUID = UUID.fromString(result.get(0).toString());
                        String adminRemovePermission = configuration.getString("adminRemovePermission");

                        if (player.getUniqueId().equals(warpPlayerUUID) || player.hasPermission(adminRemovePermission)) {

                            String warpRemovedMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("warpRemoved").replace("{0}", warpKey));

                            remove.removeWarp(warpKey);
                            player.sendMessage(warpRemovedMsg);

                        } else {

                            String noOwnership = ChatColor.translateAlternateColorCodes('&', configuration.getString("noOwnership"));
                            player.sendMessage(noOwnership);

                        }

                    } else {

                        String warpUnknownMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("warpUnknown"));
                        player.sendMessage(warpUnknownMsg);

                    }

                } else {

                    String noPermissionMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("noPermission"));
                    player.sendMessage(noPermissionMsg);

                }

            } else {

                final String missingArgument = ChatColor.translateAlternateColorCodes('&', configuration.getString("missingArgument"));
                player.sendMessage(missingArgument);

            }

        } else {

            String warpKey = args[1];
            String warpRemoved = ChatColor.translateAlternateColorCodes('&', configuration.getString("warpRemoved").replace("{0}", warpKey));

            remove.removeWarp(warpKey);
            sender.sendMessage(warpRemoved);

        }

        return null;

    }

}
