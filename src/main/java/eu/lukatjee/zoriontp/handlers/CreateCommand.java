package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.commands.MainCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CreateCommand {

    public CreateCommand createCommand(CommandSender sender, String[] args) {

        final String createPermission = ZorionTP.plugin.getConfig().getString("createPermission");
        final String noPermission = ZorionTP.plugin.getConfig().getString("noPermission");
        final String noPermissionMsg = ChatColor.translateAlternateColorCodes('&', noPermission);
        final List<String> commandArguments = Arrays.asList("create", "help");

        if (sender instanceof Player) {

            final Player player = ((Player) sender).getPlayer();

            if (args.length >= 2) {

                if (player.hasPermission(createPermission)) {

                    final String warpCreated = ZorionTP.plugin.getConfig().getString("warpCreated");
                    final String warpKey = args[1];

                    if (!MainCommand.playerwarps_id.containsKey(warpKey) && !commandArguments.contains(warpKey)) {

                        final Location location = player.getLocation();
                        final World world = player.getWorld();
                        final Integer[] coordinates = {location.getBlockX(), location.getBlockY(), location.getBlockZ()};

                        MainCommand.playerwarps_id.put(warpKey, coordinates);
                        MainCommand.playerwarps_world.put(warpKey, world);

                        String warpCreatedMsg = warpCreated.replace("{0}", warpKey);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', warpCreatedMsg));

                    } else {

                        final String warpTaken = ZorionTP.plugin.getConfig().getString("warpTaken");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', warpTaken));

                    }

                } else {

                    player.sendMessage(noPermissionMsg);

                }

            } else {

                final String missingArgument = ZorionTP.plugin.getConfig().getString("missingArgument");
                final String missingArgumentMsg = ChatColor.translateAlternateColorCodes('&', missingArgument);

                sender.sendMessage(missingArgumentMsg);

            }

        } else {

            System.out.println("Why would you want to set a playerwarp?");

        }

        return null;

    }

}
