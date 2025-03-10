package pl.gooddev.GoodDevKod.managers;

import pl.gooddev.GoodDevKod.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CodeManager {
    private final Main plugin;

    public CodeManager(Main plugin) {
        this.plugin = plugin;
    }

    public boolean useCode(Player player, String code) {
        if (!plugin.getConfig().contains("kody." + code)) {
            return false;
        }


        if (plugin.getConfig().getBoolean("ustawienia.jednorazowe_uzycie")) {
            if (plugin.getDatabaseManager().hasUsedCode(player.getUniqueId(), code)) {
                player.sendMessage(plugin.getConfigManager().getMessage("kod_juz_wykorzystany"));
                return false;
            }
        }


        if (plugin.getConfig().getBoolean("ustawienia.waluta")) {
            givePlayerCurrency(player, code);
        }

        if (plugin.getConfig().getBoolean("ustawienia.przedmioty")) {
            giveItems(player, code);
        }

        if (plugin.getConfig().getBoolean("ustawienia.komendy")) {
            executeCommands(player, code);
        }

        if (plugin.getConfig().getBoolean("ustawienia.jednorazowe_uzycie")) {
            plugin.getDatabaseManager().saveCodeUse(player, code);
        }

        return true;
    }

    private void givePlayerCurrency(Player player, String code) {
        if (plugin.getEconomy() == null) return;

        double amount = plugin.getConfig().getDouble("kody." + code + ".waluta", 0);
        if (amount > 0) {
            plugin.getEconomy().depositPlayer(player, amount);
            player.sendMessage(plugin.getConfigManager().getMessage("otrzymano_walute")
                    .replace("%amount%", String.valueOf(amount)));
        }
    }

    private void giveItems(Player player, String code) {
        String path = "kody." + code + ".przedmiot";
        if (!plugin.getConfig().contains(path)) {
            return;
        }

        try {
            Material material = Material.valueOf(plugin.getConfig().getString(path + ".material", "STONE").toUpperCase());
            int amount = plugin.getConfig().getInt(path + ".ilosc", 1);
            ItemStack item = new ItemStack(material, amount);
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {

                if (plugin.getConfig().contains(path + ".nazwa")) {
                    meta.setDisplayName(plugin.getConfigManager().colorize(
                            plugin.getConfig().getString(path + ".nazwa")));
                }


                if (plugin.getConfig().contains(path + ".lore")) {
                    List<String> lore = new ArrayList<>();
                    for (String line : plugin.getConfig().getStringList(path + ".lore")) {
                        lore.add(plugin.getConfigManager().colorize(line));
                    }
                    meta.setLore(lore);
                }


                if (plugin.getConfig().contains(path + ".enchants")) {
                    for (String enchantString : plugin.getConfig().getStringList(path + ".enchants")) {
                        try {
                            String[] parts = enchantString.split(":");
                            Enchantment enchant = Enchantment.getByName(parts[0].toUpperCase());
                            int level = Integer.parseInt(parts[1]);
                            if (enchant != null) {
                                meta.addEnchant(enchant, level, true);
                            }
                        } catch (Exception e) {
                            plugin.getLogger().warning("Błąd podczas dodawania enchantu: " + enchantString);
                        }
                    }
                }

                item.setItemMeta(meta);
            }

            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(plugin.getConfigManager().getMessage("pelny_ekwipunek"));
                return;
            }

            player.getInventory().addItem(item);
            player.sendMessage(plugin.getConfigManager().getMessage("otrzymano_przedmioty"));

        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Błąd podczas tworzenia przedmiotu dla kodu: " + code);
        }
    }

    private void executeCommands(Player player, String code) {
        List<String> commands = plugin.getConfig().getStringList("kody." + code + ".komendy");
        for (String cmd : commands) {
            plugin.getServer().dispatchCommand(
                    plugin.getServer().getConsoleSender(),
                    cmd.replace("%player%", player.getName())
            );
        }
    }
}