/*    */ package pl.gooddev.GoodDevKod;
/*    */ 
/*    */ import net.milkbowl.vault.economy.Economy;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.plugin.RegisteredServiceProvider;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ import pl.gooddev.GoodDevKod.commands.GKodCommand;
/*    */ import pl.gooddev.GoodDevKod.commands.KodCommand;
/*    */ import pl.gooddev.GoodDevKod.managers.CodeManager;
/*    */ import pl.gooddev.GoodDevKod.managers.ConfigManager;
/*    */ import pl.gooddev.GoodDevKod.managers.DatabaseManager;
/*    */ 
/*    */ public class Main extends JavaPlugin {
/*    */   private ConfigManager configManager;
/*    */   private CodeManager codeManager;
/*    */   private DatabaseManager databaseManager;
/*    */   private Economy economy;
/*    */   
/*    */   public void onEnable() {
/* 20 */     getLogger().info("GoodDev-Kod Jest Uruchamiany!");
/* 21 */     this.configManager = new ConfigManager(this);
/* 22 */     this.databaseManager = new DatabaseManager(this);
/* 23 */     this.codeManager = new CodeManager(this);
/*    */ 
/*    */     
/* 26 */     if (!setupEconomy()) {
/* 27 */       getLogger().warning("Vault nie został znaleziony!");
/*    */     }
/*    */ 
/*    */     
/* 31 */     getCommand("gkod").setExecutor((CommandExecutor)new GKodCommand(this));
/* 32 */     getCommand("kod").setExecutor((CommandExecutor)new KodCommand(this));
/*    */   }
/*    */   
/*    */   private boolean setupEconomy() {
/* 36 */     if (getServer().getPluginManager().getPlugin("Vault") == null) {
/* 37 */       return false;
/*    */     }
/* 39 */     RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
/* 40 */     if (rsp == null) {
/* 41 */       return false;
/*    */     }
/* 43 */     this.economy = (Economy)rsp.getProvider();
/* 44 */     return (this.economy != null);
/*    */   }
/*    */   
/*    */   public ConfigManager getConfigManager() {
/* 48 */     return this.configManager;
/*    */   }
/*    */   
/*    */   public CodeManager getCodeManager() {
/* 52 */     return this.codeManager;
/*    */   }
/*    */   
/*    */   public DatabaseManager getDatabaseManager() {
/* 56 */     return this.databaseManager;
/*    */   }
/*    */   
/*    */   public Economy getEconomy() {
/* 60 */     return this.economy;
/*    */   }
/*    */ }


/* Location:              C:\Users\Milos\IdeaProjects\GoodDev-Kod\target\GoodDev-GoodDevKod-1.0.jar!\pl\gooddev\GoodDevKod\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */