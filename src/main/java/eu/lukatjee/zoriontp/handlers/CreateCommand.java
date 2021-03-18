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

    FileConfiguration configuration = ZorionTP.plugin.getConfig();
    SQLGetter read = new SQLGetter(ZorionTP.plugin);
    SQLGetter create = new SQLGetter(ZorionTP.plugin);

    public void createCommand(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = ((Player) sender).getPlayer();

            if (args.length >= 2) {

                String createPermission = configuration.getString("createPermission");

                if (player.hasPermission(createPermission)) {

                    String warpKey = args[1];
                    List<Object> result = read.readWarp(warpKey);

                    if (result == null && !MainCommand.commandArguments.contains(warpKey)) {

                        String warpCreatedMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("warpCreated").replace("{0}", warpKey));

                        Location location = player.getLocation();

                        Integer x = location.getBlockX();
                        Integer y = location.getBlockY();
                        Integer z = location.getBlockZ();

                        World world = player.getWorld();

                        create.createWarp(player.getUniqueId(), warpKey, world.getName(), x, y, z);
                        player.sendMessage(warpCreatedMsg);

                    } else {

                        String warpTakenMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("warpTaken"));
                        player.sendMessage(warpTakenMsg);

                    }

                } else {

                    String noPermissionMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("noPermission"));
                    player.sendMessage(noPermissionMsg);

                }

            } else {

                String missingArgumentMsg = ChatColor.translateAlternateColorCodes('&', configuration.getString("missingArgument"));
                sender.sendMessage(missingArgumentMsg);

            }

        } else {

            System.out.println("Why would you want to set a playerwarp?");

        }

    }

}
