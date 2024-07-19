package io.github.keishispl.skologram;

import io.github.keishispl.skologram.other.UpdateChecker;
import org.bukkit.plugin.PluginManager;
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
    public static String data_path;
    public static String latest_version;
    public static HashMap<String, Boolean> element_map = new HashMap<>();
    public static final String prefix = net.md_5.bungee.api.ChatColor.of("#00ff00") + "[Skologram] " + ChatColor.RESET;

    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new UpdateChecker(), this);
        metrics = new Metrics(this, 20162);
        if (getServer().getPluginManager().isPluginEnabled("Skript")) {
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
            Logger.success("Skologram has been enabled");
            Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
                latest_version = latestVersion();
                if (getConfig().getBoolean("version-check-msg")) Logger.warn("Got latest version."); // not a warn just want yellow
            }, 0L, 144000L);
            data_path = this.getDataFolder().getAbsolutePath();
        } else {
            Logger.error("Skript is not enabled");
        }
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        if (getConfig().getBoolean("auto-update", false)){
            if (new File(getInstance().getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).delete()) {
                UpdateChecker.update();
            }
        }
    }

    public static Skologram getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    public void registerPluginElements(String pluginName, String name) throws IOException {
        element_map.put(name, false);
        if (Bukkit.getServer().getPluginManager().isPluginEnabled(pluginName)) {
            addon.loadClasses("io.github.keishispl.features."+name.toLowerCase());
            Logger.success(name+" elements loaded.");
            element_map.put(name, true);
        } else Logger.error(name+" elements not loaded.");
        metrics.addCustomChart(new SimplePie(name, () -> Boolean.toString(element_map.get(name))));
    }

    public static class Logger{

        public static void success(String message){
            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + message);
        }

        public static void log(String message){
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
