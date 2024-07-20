package io.github.keishispl.skologram.other;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import io.github.keishispl.skologram.Skologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static io.github.keishispl.skologram.Skologram.*;
import static io.github.keishispl.skologram.other.GetVersion.latestVersion;
import static io.github.keishispl.skologram.other.GetVersion.latestSkriptVersion;
import static io.github.keishispl.skologram.other.UpdateChecker.updateCheck;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@SuppressWarnings("deprecation")
public class Subcommands {
    public static void cmdDependencies(CommandSender sender){
        if (!sender.hasPermission("skologram.command.dependencies")){
            sender.sendMessage(ChatColor.RED+"You don't have permission to use this command!");
            return;
        }
        StringBuilder builder = new StringBuilder("<blue>Skologram <white>Dependencies:").append("\n\n");
        for (String name : element_map.keySet().stream().sorted(Comparator.naturalOrder()).toList()){
            builder.append("  <white>").append(name).append(":</white> ");
            if (element_map.get(name)){
                builder.append("<green>Installed</green>\n");
            } else {
                builder.append("<red>Not Installed</red>\n");
            }
        }
        sender.sendMessage(miniMessage().deserialize(builder.toString()));
    }

    public static void cmdInfo(CommandSender sender, @Nullable String plugin){
        if (!sender.hasPermission("skologram.command.info")) {
            sender.sendMessage(ChatColor.RED+"You don't have permission to use this command!");
            return;
        }
        if (plugin == null) {
            String sk = latestSkriptVersion();
            String sku = latest_version;
            String msg = getString(sku, sk);
            // addons --
            List<String> msgs = new ArrayList<>();
            List<SkriptAddon> addonlist = new ArrayList<>(Skript.getAddons().stream().toList());
            // removes skologram from addon list
            addonlist.remove(Skologram.addon);
            if (!addonlist.isEmpty()) {
                for (SkriptAddon addon : Skript.getAddons()) {
                    // if the loop plugin is not skologram add a message
                    if (addon != instance.getAddonInstance()) {
                        PluginDescriptionFile d = addon.plugin.getDescription();
                        msgs.add(
                                "    <click:open_url:'<URL>'><hover:show_text:'<gold><AUTHORS>'><white><NAME>: <aqua><VERSION></hover></click>"
                                        .replaceAll("<URL>", (d.getWebsite() != null ? d.getWebsite() : ""))
                                        .replaceAll("<AUTHORS>", d.getAuthors() + "")
                                        .replaceAll("<NAME>", d.getName())
                                        .replaceAll("<VERSION>", d.getVersion())
                        );
                    }
                }
            }
            StringBuilder addons = new StringBuilder();
            for (String e : msgs) {
                addons.append(e).append("\n");
            }
            // dependencies --
            List<String> deps = new ArrayList<>();
            for (SkriptAddon addon : Skript.getAddons()) {
                for (String dep : addon.plugin.getDescription().getSoftDepend()) {
                    Plugin pl = Bukkit.getPluginManager().getPlugin(dep);
                    if (pl != null && pl != Skript.getInstance()) {
                        PluginDescriptionFile d = pl.getDescription();
                        String msgg =
                                "    <click:open_url:'<URL>'><hover:show_text:'<gold><AUTHORS>'><white><NAME>: <aqua><VERSION></hover></click>"
                                        .replaceAll("<URL>", (d.getWebsite() != null ? d.getWebsite() : ""))
                                        .replaceAll("<AUTHORS>", d.getAuthors() + "")
                                        .replaceAll("<NAME>", d.getName())
                                        .replaceAll("<VERSION>", d.getVersion());
                        if (!deps.contains(msgg)) {
                            deps.add(msgg);
                        }
                    }
                }
            }
            for (String dep : Skript.getInstance().getDescription().getSoftDepend()) {
                Plugin pl = Bukkit.getPluginManager().getPlugin(dep);
                if (pl != null) {
                    PluginDescriptionFile d = pl.getDescription();
                    deps.add(
                            "    <click:open_url:'<URL>'><hover:show_text:'<gold><AUTHORS>'><white><NAME>: <aqua><VERSION></hover></click>"
                                    .replaceAll("<URL>", (d.getWebsite() != null ? d.getWebsite() : ""))
                                    .replaceAll("<AUTHORS>", d.getAuthors() + "")
                                    .replaceAll("<NAME>", d.getName())
                                    .replaceAll("<VERSION>", d.getVersion())
                    );
                }
            }
            StringBuilder dependencies = new StringBuilder();
            for (String e : deps) {
                dependencies.append(e).append("\n");
            }
            // sending the message --
            if (dependencies.isEmpty()) {
                if (addons.isEmpty()) {
                    msg = msg + "    <red>N/A\n  <white>Dependencies:\n    <red>N/A";
                } else {
                    msg = msg + addons + "  <white>Dependencies:\n    <red>N/A";
                }
            } else {
                if (addons.compareTo(new StringBuilder()) == 0) {
                    msg = msg + "    <red>N/A\n  <white>Dependencies:\n" + dependencies;
                } else {
                    msg = msg + addons + "  <white>Dependencies:\n" + dependencies;
                }
            }
            sender.sendMessage(miniMessage().deserialize(msg));
        } else{
            Plugin p = Bukkit.getPluginManager().getPlugin(plugin);
            if (p != null) {
                PluginDescriptionFile d = p.getDescription();
                List<String> mainl = Arrays.stream(p.getClass().getProtectionDomain().getCodeSource().getLocation().getFile().split("/")).toList();
                String main = mainl.get(mainl.size()-1);
                sender.sendMessage(miniMessage().deserialize("""
                        <blue><NAME> <white>Info:<reset>
                        
                        <white>  File Name: <aqua><FILENAME>
                        <white>  Version: <aqua><VERSION>
                        <white>  Description: <aqua><DESCRIPTION>
                        
                        <white>  Website: <aqua><WEBSITE>
                        <white>  Authors: <aqua><AUTHORS>
                        <white>  Contributors: <aqua><CONTRIBUTORS>
                        
                        <white>  Main Class: <aqua><MAINCLASS>
                        <white>  API Version: <aqua><APIV>
                        
                        """
                        .replaceAll("<NAME>", d.getName())
                        .replaceAll("<FILENAME>", main.replaceAll("%20", " "))
                        .replaceAll("<VERSION>", d.getVersion())
                        .replaceAll("<WEBSITE>", (d.getWebsite() != null ? "<click:open_url:'" + d.getWebsite() + "'>" + d.getWebsite() + "</click>" : "<red>N/A"))
                        .replaceAll("<AUTHORS>", (!d.getAuthors().isEmpty() ? d.getAuthors() + "" : "<red>N/A"))
                        .replaceAll("<CONTRIBUTORS>", (!d.getContributors().isEmpty() ? d.getContributors() + "" : "<red>N/A"))
                        .replaceAll("<DESCRIPTION>", (d.getDescription() != null ? d.getDescription() : "<red>N/A"))
                        .replaceAll("<APIV>", (d.getAPIVersion() != null ? d.getAPIVersion() : "<red>N/A"))
                        .replaceAll("<PREFIX>", (d.getPrefix() != null ? d.getPrefix() : "<red>N/A"))
                        .replaceAll("<MAINCLASS>", d.getMain())
                ));
            } else{
                sender.sendMessage(miniMessage().deserialize("<red>Invalid plugin!"));
            }
        }
    }

    @NotNull
    private static String getString(String sku, String sk) {
        String skriptv = Skript.getInstance().getDescription().getVersion();
        String skologramv = instance.getDescription().getVersion();
        return """
                <blue>Skologram <white>Info:
                
                <white>  Skologram: <blue><SKOLOGRAM_VER> <gold><SKOLOGRAM_LATEST>
                <white>  Server: <blue><SERVER_VER>
                <white>  Implementation: <blue><SERVER_IMPL>
                <white>  Skript: <blue><SKRIPT_VER> <gold><SKRIPT_LATEST>
                <white>  Addons:
                """
                .replaceAll("<SKOLOGRAM_VER>", skologramv)
                .replaceAll("<SKOLOGRAM_LATEST>", (!Objects.equals(sku, skologramv) ? " [Latest: "+ sku + "]" : ""))
                .replaceAll("<SERVER_VER>", instance.getServer().getMinecraftVersion())
                .replaceAll("<SERVER_IMPL>", instance.getServer().getVersion())
                .replaceAll("<SKRIPT_VER>", skriptv)
                .replaceAll("<SKRIPT_LATEST>", (!Objects.equals(sk, skriptv) ? " [Latest: "+ sk + "]" : ""))
                ;
    }

    public static void cmdReload(CommandSender sender){
        if (!sender.hasPermission("skologram.command.reload")){
            sender.sendMessage(ChatColor.RED+"You don't have permission to use this command!");
            return;
        }
        instance.reloadConfig();
        instance.saveConfig();
        sender.sendMessage(miniMessage().deserialize("<color:#00ff00>Config reloaded!"));
    }

    public static void cmdUpdate(CommandSender sender){
        if (!sender.hasPermission("skologram.command.update")){
            sender.sendMessage(ChatColor.RED+"You don't have permission to use this command!");
            return;
        }
        String v = latestVersion();
        if (v.equals(instance.getDescription().getVersion())){
            sender.sendMessage(miniMessage().deserialize("<color:#00ff00>You are up to date!"));
        } else{
            latest_version = v;
            updateCheck(sender);
        }
    }
}
