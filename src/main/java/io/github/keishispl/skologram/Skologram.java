package io.github.keishispl.skologram;

import io.github.keishispl.skologram.other.GetVersion;
import io.github.keishispl.skologram.other.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.bstats.bukkit.Metrics;
import ch.njol.skript.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static io.github.keishispl.skologram.other.GetVersion.latestVersion;

@SuppressWarnings("deprecation")
public final class Skologram extends JavaPlugin {

    public static Skologram instance;
    public static SkriptAddon addon;
    public static long start;
    private static Metrics metrics;
    public static String latest_version;
    public static HashMap<String, Boolean> element_map = new HashMap<>();
    public static final String prefix = ChatColor.GRAY + "[" + ChatColor.BLUE + "Skologram" + ChatColor.GRAY + "] " + ChatColor.RESET;

    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new UpdateChecker(), this);
        metrics = new Metrics(this, 22718);
        Logger.info("The Server is running on Skologram " + this.getDescription().getVersion());
        if (!getServer().getPluginManager().isPluginEnabled("Skript")) {
            Logger.error("Skript is not enabled. Skologram will not work.");
        } else {
            Logger.info("Found Skript with version " + Skript.getVersion());
            try {
                addon = Skript.registerAddon(this);
                addon.setLanguageFileDirectory("lang");
                registerPluginElements("DecentHolograms", "DecentHolograms");
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

    public void registerElements(String name) throws IOException {
        element_map.put(name, false);
        addon.loadClasses("io.github.keishispl.skologram.modules."+name.toLowerCase());
        Logger.info("Loaded Module: " + name);
        element_map.put(name, true);
        metrics.addCustomChart(new SimplePie(name, () -> Boolean.toString(element_map.get(name))));
    }
    public void registerPluginElements(String name, String pluginName) throws IOException {
        element_map.put(name, false);
        if (Bukkit.getServer().getPluginManager().isPluginEnabled(pluginName)) {
            addon.loadClasses("io.github.keishispl.skologram.modules."+name.toLowerCase());
            Logger.info("Loaded Module: " + name);
            element_map.put(name, true);
        } else Logger.info("The " + name +" Plugin is not found, ignoring...");
        metrics.addCustomChart(new SimplePie(name, () -> Boolean.toString(element_map.get(name))));
    }

    public static class Logger{

        public static void success(String message){
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + message);
        }

        public static void info(String message){
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.WHITE + message);
        }

        public static void warn(String message){
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.YELLOW + message);
        }

        public static void error(String message){
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + message);
        }
    }
}
