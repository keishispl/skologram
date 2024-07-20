package io.github.keishispl.skologram;

import io.github.keishispl.skologram.other.UpdateChecker;
import io.github.keishispl.skologram.other.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.bstats.bukkit.Metrics;
import ch.njol.skript.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.github.keishispl.skologram.other.Subcommands.*;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@SuppressWarnings("deprecation")
public final class Skologram extends JavaPlugin {

    public static Skologram instance;
    public static SkriptAddon addon;
    public static String latest_version;
    public static long start;
    public static HashMap<String, Boolean> element_map = new HashMap<>();
    private static Metrics metrics;
    public static final String prefix = ChatColor.GRAY + "[" + ChatColor.BLUE + "Skologram" + ChatColor.GRAY + "] " + ChatColor.RESET;

    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new UpdateChecker(), this);
        getServer().getPluginCommand("skologram").setExecutor(this);
        getServer().getPluginCommand("skologram").setTabCompleter(this);
        metrics = new Metrics(this, 22718);
        Logger.info("The Server is running on Skologram " + this.getDescription().getVersion());
        if (!getServer().getPluginManager().isPluginEnabled("Skript")) {
            Logger.error("Skript is not enabled. Skologram will not work.");
        } else {
            Logger.info("Found Skript with version " + Skript.getVersion());
            try {
                addon = Skript.registerAddon(this);
                addon.setLanguageFileDirectory("lang");
                registerPluginElements("DecentHolograms");
            } catch (IOException e) {
                Logger.error("An error occurred while loading Skologram");
                throw new RuntimeException(e);
            }
            metrics.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));
            start = System.currentTimeMillis()/50;
            Logger.success("Skologram has been loaded.");
        }
    }
    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }

    public static Skologram getInstance() {
        return instance;
    }
    public SkriptAddon getAddonInstance() {
        return addon;
    }

    private void registerElements(String name) throws IOException {
        addon.loadClasses("io.github.keishispl.skologram.modules."+name.toLowerCase());
        Logger.info("Loaded Module: " + name);
        element_map.put(name, true);
        metrics.addCustomChart(new SimplePie(name, () -> Boolean.toString(element_map.get(name))));
    }
    private void registerPluginElements(String name) throws IOException {
        if (!getServer().getPluginManager().isPluginEnabled(name)) {
            Logger.info("The " + name +" Plugin is not found, ignoring...");
            element_map.put(name, false);
        } else {
            addon.loadClasses("io.github.keishispl.skologram.modules."+name.toLowerCase());
            Logger.info("Loaded Module: " + name);
            element_map.put(name, true);
        }
        metrics.addCustomChart(new SimplePie(name, () -> Boolean.toString(element_map.get(name))));
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String builder = """

                <white>Usage: <gray>/<blue>skologram <gray>...

                <aqua>  reload <gray>- <white>Reloads the config
                <aqua>  update <gray>- <white>Check for updates
                <aqua>  info <gray>- <white>Get information related to Skologram
                <aqua>  dependencies <gray>- <white>Get information relating to dependencies
                
                """;
        if (args.length == 0){
            sender.sendMessage(miniMessage().deserialize(builder));
        } else{
            switch (args[0]) {
                case "reload" -> cmdReload(sender);
                case "update" -> cmdUpdate(sender);
                case "info" -> cmdInfo(sender, (args.length >= 2 ? args[1] : null));
                case "dependencies" -> cmdDependencies(sender);                   
                default -> sender.sendMessage(miniMessage().deserialize(builder));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            if ("dependencies".startsWith(args[0].toLowerCase())) completions.add("dependencies");
            if ("info".startsWith(args[0].toLowerCase())) completions.add("info");
            if ("reload".startsWith(args[0].toLowerCase())) completions.add("reload");
            if ("update".startsWith(args[0].toLowerCase())) completions.add("update");
            return completions;
        } else if (args.length == 2){
            if (args[0].equalsIgnoreCase("info")) {
                List<String> completions = new ArrayList<>();
                for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
                    if (p.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        completions.add(p.getName());
                    }
                }
                return completions;
            }
        }
        return null;
    }
}
