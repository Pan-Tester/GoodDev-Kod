/*    */ package pl.gooddev.GoodDevKod.managers;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import pl.gooddev.GoodDevKod.Main;
/*    */ 
/*    */ public class ConfigManager {
/*    */   private final Main plugin;
/*    */   
/*    */   public ConfigManager(Main plugin) {
/* 10 */     this.plugin = plugin;
/* 11 */     plugin.saveDefaultConfig();
/*    */   }
/*    */   
/*    */   public String getMessage(String path) {
/* 15 */     return colorize(this.plugin.getConfig().getString("wiadomosci." + path, ""));
/*    */   }
/*    */   
/*    */   public String colorize(String message) {
/* 19 */     return ChatColor.translateAlternateColorCodes('&', message);
/*    */   }
/*    */ }


/* Location:              C:\Users\Milos\IdeaProjects\GoodDev-Kod\target\GoodDev-GoodDevKod-1.0.jar!\pl\gooddev\GoodDevKod\managers\ConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */