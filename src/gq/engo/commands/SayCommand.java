package gq.engo.commands;

import gq.engo.utils.C;
import gq.engo.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        String formattedStr = String.join(" ", args);

        p.sendMessage(formattedStr);
        if (s.equalsIgnoreCase("say")) {
                ServerUtil.broadcast(C.addPrefix(C.getSecondary(C.chat(s))));
        }
        return true;
    }
}

