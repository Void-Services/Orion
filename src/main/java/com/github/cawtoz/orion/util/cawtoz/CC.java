package com.github.cawtoz.orion.util.cawtoz;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public class CC {

    public static void send(Entity entity, String... message) {
        format(Arrays.asList(message)).forEach(entity::sendMessage);
    }

    public static void sendConsole(String... message) {
        format(Arrays.asList(message)).forEach(Bukkit.getConsoleSender()::sendMessage);
    }

    public static void send(CommandSender sender, String... message) {
        if (sender instanceof Player) send((Player) sender, message);
        else sendConsole(message);
    }

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> format(List<String> message) {
        List<String> formatted = new ArrayList<>();
        message.forEach(string -> { formatted.add(format(string)); });
        return formatted;
    }

}
