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
    public static List<String> commandArguments;
    public static String noPermissionMsg;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Ugh... how do I describe this?

        final String noPermission = ZorionTP.plugin.getConfig().getString("noPermission");

        // Public variables used in other classes

        noPermissionMsg = ChatColor.translateAlternateColorCodes('&', noPermission);
        commandArguments = Arrays.asList("create");

        if (args.length == 0) {

            HelpCommand help = new HelpCommand();
            help.helpCommand(sender);

        } else {

            // Command to create a warp

            if (args[0].equals("create")) {

                CreateCommand create = new CreateCommand();
                create.createCommand(sender, args);

            // Command to display the help menu

            } else if (commandArguments.contains(args[0])) {

                HelpCommand help = new HelpCommand();
                help.helpCommand(sender);

            // Command to teleport to a warp

            } else {

                TeleportCommand teleport = new TeleportCommand();
                teleport.teleportCommand(sender, args);

            }

        }

        return false;

    }

}
