package io.github.keishispl.skologram.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static io.github.keishispl.skologram.Skologram.prefix;

public class Logger {
    public static void success(String message) { Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + message); }
    public static void info(String message) { Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + message); }
    public static void warn(String message) { Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.YELLOW + message); }
    public static void error(String message) { Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + message); }
}