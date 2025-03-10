package pl.gooddev.GoodDevKod.commands;

import pl.gooddev.GoodDevKod.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GKodCommand implements CommandExecutor {
    private final Main plugin;

    public GKodCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("gooddev.kod.admin")) {
            sender.sendMessage(plugin.getConfigManager().getMessage("brak_uprawnien"));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(plugin.getConfigManager().getMessage("uzycie_gkod"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                return handleCreate(sender, args);
            case "reset":
                return handleReset(sender, args);
            case "history":
                return handleHistory(sender, args);
            default:
                sender.sendMessage(plugin.getConfigManager().getMessage("uzycie_gkod"));
                return true;
        }
    }

    private boolean handleCreate(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(plugin.getConfigManager().getMessage("uzycie_gkod"));
            return true;
        }

        String kod = args[1];
        if (plugin.getConfig().contains("kody." + kod)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("kod_istnieje"));
            return true;
        }

        plugin.getConfig().set("kody." + kod + ".waluta", 5);
        plugin.getConfig().set("kody." + kod + ".komendy", java.util.Arrays.asList("say Użyto kodu!"));
        plugin.saveConfig();

        sender.sendMessage(plugin.getConfigManager().getMessage("kod_utworzony")
                .replace("%kod%", kod));
        return true;
    }

    private boolean handleReset(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(plugin.getConfigManager().getMessage("uzycie_gkod"));
            return true;
        }

        String playerName = args[1];
        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);

        if (target == null || !target.hasPlayedBefore()) {
            sender.sendMessage(plugin.getConfigManager().getMessage("gracz_nie_istnieje")
                    .replace("%player%", playerName));
            return true;
        }

        plugin.getDatabaseManager().resetPlayerCodes(target.getUniqueId());
        sender.sendMessage(plugin.getConfigManager().getMessage("kod_zresetowany")
                .replace("%player%", playerName));
        return true;
    }

    private boolean handleHistory(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(plugin.getConfigManager().getMessage("uzycie_gkod"));
            return true;
        }

        String playerName = args[1];
        OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);

        if (target == null || !target.hasPlayedBefore()) {
            sender.sendMessage(plugin.getConfigManager().getMessage("gracz_nie_istnieje")
                    .replace("%player%", playerName));
            return true;
        }

        List<String> usedCodes = plugin.getDatabaseManager().getPlayerUsedCodes(target.getUniqueId());

        if (usedCodes.isEmpty()) {
            sender.sendMessage(plugin.getConfigManager().getMessage("brak_historii"));
            return true;
        }

        sender.sendMessage(plugin.getConfigManager().getMessage("historia_kodow")
                .replace("%player%", playerName));
        for (String codeInfo : usedCodes) {
            sender.sendMessage(codeInfo);
        }
        return true;
    }
}