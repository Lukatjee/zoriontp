package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.commands.MainCommand;
import eu.lukatjee.zoriontp.sql.SQLGetter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CreateCommand {

    public CreateCommand createCommand(CommandSender sender, String[] args) {

        final String createPermission = ZorionTP.plugin.getConfig().getString("createPermission");

        if (sender instanceof Player) {

            final Player player = ((Player) sender).getPlayer();

            if (args.length >= 2) {

                if (player.hasPermission(createPermission)) {

                    final String warpCreated = ZorionTP.plugin.getConfig().getString("warpCreated");
                    final String warpKey = args[1];

                    SQLGetter read = new SQLGetter(ZorionTP.plugin);
                    List<Object> result = read.readWarp(warpKey);

                    if (result == null && !MainCommand.commandArguments.contains(warpKey)) {

                        final Location location = player.getLocation();
                        Integer x = location.getBlockX();
                        Integer y = location.getBlockY();
                        Integer z = location.getBlockZ();

                        final World world = player.getWorld();

                        SQLGetter create = new SQLGetter(ZorionTP.plugin);
                        create.createWarp(player.getUniqueId(), warpKey, world.getName(), x, y, z);

                        String warpCreatedMsg = warpCreated.replace("{0}", warpKey);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', warpCreatedMsg));

                    } else {

                        final String warpTaken = ZorionTP.plugin.getConfig().getString("warpTaken");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', warpTaken));

                    }

                } else {

                    player.sendMessage(MainCommand.noPermissionMsg);

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
