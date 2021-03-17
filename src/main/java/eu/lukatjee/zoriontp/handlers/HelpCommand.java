package eu.lukatjee.zoriontp.handlers;

import eu.lukatjee.zoriontp.ZorionTP;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand {

    public void helpCommand(CommandSender sender) {

        // Send help menu

        for (String text : ZorionTP.plugin.getConfig().getStringList("helpText")) {

            String message = ChatColor.translateAlternateColorCodes('&', text);
            sender.sendMessage(message);

        }

    }

}
