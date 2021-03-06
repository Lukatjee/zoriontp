package eu.lukatjee.zoriontp.commands;

import eu.lukatjee.zoriontp.handlers.CreateCommand;
import eu.lukatjee.zoriontp.handlers.HelpCommand;
import eu.lukatjee.zoriontp.handlers.RemoveCommand;
import eu.lukatjee.zoriontp.handlers.TeleportCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class MainCommand implements CommandExecutor {

    public static List<String> commandArguments;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        commandArguments = Arrays.asList("create", "remove", "''", "\"\"");

        if (args.length == 0) {

            HelpCommand help = new HelpCommand();
            help.helpCommand(sender);

        } else {

            if (args[0].equals("create")) {

                CreateCommand create = new CreateCommand();
                create.createCommand(sender, args);

            } else if (args[0].equals("remove")) {

                RemoveCommand remove = new RemoveCommand();
                remove.removeCommand(sender, args);

            } else {

                TeleportCommand teleport = new TeleportCommand();
                teleport.teleportCommand(sender, args);

            }

        }

        return false;

    }

}
