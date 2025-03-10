package pl.gooddev.GoodDevKod.commands;

import pl.gooddev.GoodDevKod.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KodCommand implements CommandExecutor {
    private final Main plugin;

    public KodCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("tylko_gracz"));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(plugin.getConfigManager().getMessage("uzycie_kod"));
            return true;
        }

        Player player = (Player) sender;
        String kod = args[0];

        if (!plugin.getCodeManager().useCode(player, kod)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("nieprawidlowy_kod"));
            return true;
        }

        sender.sendMessage(plugin.getConfigManager().getMessage("kod_uzyty"));
        return true;
    }
}