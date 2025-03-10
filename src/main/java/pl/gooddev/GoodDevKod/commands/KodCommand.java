/*    */ package pl.gooddev.GoodDevKod.commands;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import pl.gooddev.GoodDevKod.Main;
/*    */ 
/*    */ public class KodCommand implements CommandExecutor {
/*    */   private final Main plugin;
/*    */   
/*    */   public KodCommand(Main plugin) {
/* 13 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 18 */     if (!(sender instanceof Player)) {
/* 19 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("tylko_gracz"));
/* 20 */       return true;
/*    */     } 
/*    */     
/* 23 */     if (args.length != 1) {
/* 24 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("uzycie_kod"));
/* 25 */       return true;
/*    */     } 
/*    */     
/* 28 */     Player player = (Player)sender;
/* 29 */     String kod = args[0];
/*    */     
/* 31 */     if (!this.plugin.getCodeManager().useCode(player, kod)) {
/* 32 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("nieprawidlowy_kod"));
/* 33 */       return true;
/*    */     } 
/*    */     
/* 36 */     sender.sendMessage(this.plugin.getConfigManager().getMessage("kod_uzyty"));
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Milos\IdeaProjects\GoodDev-Kod\target\GoodDev-GoodDevKod-1.0.jar!\pl\gooddev\GoodDevKod\commands\KodCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */