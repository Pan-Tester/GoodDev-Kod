package pl.gooddev.GoodDevKod.managers;

import pl.gooddev.GoodDevKod.Main;
import org.bukkit.ChatColor;

public class ConfigManager {
    private final Main plugin;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public String getMessage(String path) {
        return colorize(plugin.getConfig().getString("wiadomosci." + path, ""));
    }

    public String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}