package pl.gooddev.GoodDevKod;

import pl.gooddev.GoodDevKod.commands.GKodCommand;
import pl.gooddev.GoodDevKod.commands.KodCommand;
import pl.gooddev.GoodDevKod.managers.CodeManager;
import pl.gooddev.GoodDevKod.managers.ConfigManager;
import pl.gooddev.GoodDevKod.managers.DatabaseManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private ConfigManager configManager;
    private CodeManager codeManager;
    private DatabaseManager databaseManager;
    private Economy economy;

    @Override
    public void onEnable() {
        getLogger().info("GoodDev-Kod Jest Uruchamiany!");
        this.configManager = new ConfigManager(this);
        this.databaseManager = new DatabaseManager(this);
        this.codeManager = new CodeManager(this);


        if (!setupEconomy()) {
            getLogger().warning("Vault nie został znaleziony!");
        }


        getCommand("gkod").setExecutor(new GKodCommand(this));
        getCommand("kod").setExecutor(new KodCommand(this));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CodeManager getCodeManager() {
        return codeManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public Economy getEconomy() {
        return economy;
    }
}