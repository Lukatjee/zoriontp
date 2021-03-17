package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.commands.MainCommand;
import eu.lukatjee.zoriontp.sql.SQLGetter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CreateCommand {

    // Don't touch

    FileConfiguration configuration = ZorionTP.plugin.getConfig();
    SQLGetter read = new SQLGetter(ZorionTP.plugin);
    SQLGetter create = new SQLGetter(ZorionTP.plugin);

    public void createCommand(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            // Player

            Player player = ((Player) sender).getPlayer();

            if (args.length >= 2) {

                // Permission

                String createPermission = configuration.getString("createPermission");

                if (player.hasPermission(createPermission)) {

                    // Read sql result

                    String warpKey = args[1];
                    List<Object> result = read.readWarp(warpKey);

                    if (result == null && !MainCommand.commandArguments.contains(warpKey)) {

                        // Messages

                        String warpCreatedMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("warpCreated").replace("{0}", warpKey));

                        // Location variables

                        Location location = player.getLocation();

                        Integer x = location.getBlockX();
                        Integer y = location.getBlockY();
                        Integer z = location.getBlockZ();

                        World world = player.getWorld();

                        // Create warp

                        create.createWarp(player.getUniqueId(), warpKey, world.getName(), x, y, z);
                        player.sendMessage(warpCreatedMsg);

                    } else {

                        // Name is taken

                        String warpTakenMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("warpTaken"));
                        player.sendMessage(warpTakenMsg);

                    }

                } else {

                    // No permission

                    String noPermissionMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("noPermission"));
                    player.sendMessage(noPermissionMsg);

                }

            } else {

                // Argument missing

                String missingArgumentMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("missingArgument"));
                sender.sendMessage(missingArgumentMsg);

            }

        } else {

            // Tell console they're dumb

            System.out.println("Why would you want to set a playerwarp?");

        }

    }

}
