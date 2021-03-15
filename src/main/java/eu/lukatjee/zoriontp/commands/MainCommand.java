package eu.lukatjee.zoriontp.commands;

import eu.lukatjee.zoriontp.ZorionTP;
import eu.lukatjee.zoriontp.handlers.CreateCommand;
import eu.lukatjee.zoriontp.handlers.HelpCommand;
import eu.lukatjee.zoriontp.handlers.TeleportCommand;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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

            HelpCommand help = new HelpCommand();
            help.helpCommand(sender);

        } else {

            if (args[0].equals("create")) {

                CreateCommand create = new CreateCommand();
                create.createCommand(sender, args);

            } else if (commandArguments.contains(args[0])) {

                HelpCommand help = new HelpCommand();
                help.helpCommand(sender);

            } else {

                TeleportCommand teleport = new TeleportCommand();
                teleport.teleportCommand(sender, args);

            }

        }

        return false;

    }

}
