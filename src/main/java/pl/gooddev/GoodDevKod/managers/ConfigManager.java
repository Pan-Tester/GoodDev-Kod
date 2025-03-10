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

