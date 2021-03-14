package eu.lukatjee.zoriontp.commands;

import eu.lukatjee.zoriontp.ZorionTP;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCommand implements CommandExecutor {

    public static Map<String, Integer[]> playerwarps_id = new HashMap<String, Integer[]>();
    public static Map<String, World> playerwarps_world = new HashMap<String, World>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final String noPermission = ZorionTP.plugin.getConfig().getString("noPermission");
        final String noPermissionMsg = ChatColor.translateAlternateColorCodes('&', noPermission);
        final List<String> commandArguments = Arrays.asList("create", "help");

        if (args.length == 0) {

            sender.sendMessage("This will be coming soon, don't worry");

        } else {

            if (args[0].equals("create")) {

                final String createPermission = ZorionTP.plugin.getConfig().getString("createPermission");

                if (sender instanceof Player) {

                    final Player player = ((Player) sender).getPlayer();

                    if (args.length >= 2) {

                        if (player.hasPermission(createPermission)) {

                            final String warpCreated = ZorionTP.plugin.getConfig().getString("warpCreated");
                            final String warpKey = args[1];

                            if (!playerwarps_id.containsKey(warpKey) && !commandArguments.contains(warpKey)) {

                                final Location location = player.getLocation();
                                final World world = player.getWorld();
                                final Integer[] coordinates = {location.getBlockX(), location.getBlockY(), location.getBlockZ()};

                                playerwarps_id.put(warpKey, coordinates);
                                playerwarps_world.put(warpKey, world);

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

            } else if (commandArguments.contains(args[0])) {

                sender.sendMessage("This will be coming soon, don't worry");

            } else {

                if (sender instanceof Player) {

                    final Player player = ((Player) sender).getPlayer();
                    final String teleportPermission = ZorionTP.plugin.getConfig().getString("teleportPermission");

                    if (player.hasPermission(teleportPermission)) {

                        final String warpKey = args[0];

                        if (playerwarps_id.containsKey(warpKey)) {

                            final String warpTeleported = ZorionTP.plugin.getConfig().getString("warpTeleported"); 
                            final World world = playerwarps_world.get(warpKey);
                            final Integer[] coordinates = playerwarps_id.get(warpKey);
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

            }

        }

        return false;

    }

}
