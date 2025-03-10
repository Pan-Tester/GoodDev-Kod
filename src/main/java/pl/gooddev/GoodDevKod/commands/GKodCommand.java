/*     */ package pl.gooddev.GoodDevKod.commands;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import pl.gooddev.GoodDevKod.Main;
/*     */ 
/*     */ public class GKodCommand
/*     */   implements CommandExecutor {
/*     */   private final Main plugin;
/*     */   
/*     */   public GKodCommand(Main plugin) {
/*  17 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  22 */     if (!sender.hasPermission("gooddev.kod.admin")) {
/*  23 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("brak_uprawnien"));
/*  24 */       return true;
/*     */     } 
/*     */     
/*  27 */     if (args.length < 1) {
/*  28 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("uzycie_gkod"));
/*  29 */       return true;
/*     */     } 
/*     */     
/*  32 */     switch (args[0].toLowerCase()) {
/*     */       case "create":
/*  34 */         return handleCreate(sender, args);
/*     */       case "reset":
/*  36 */         return handleReset(sender, args);
/*     */       case "history":
/*  38 */         return handleHistory(sender, args);
/*     */     } 
/*  40 */     sender.sendMessage(this.plugin.getConfigManager().getMessage("uzycie_gkod"));
/*  41 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean handleCreate(CommandSender sender, String[] args) {
/*  46 */     if (args.length < 2) {
/*  47 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("uzycie_gkod"));
/*  48 */       return true;
/*     */     } 
/*     */     
/*  51 */     String kod = args[1];
/*  52 */     if (this.plugin.getConfig().contains("kody." + kod)) {
/*  53 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("kod_istnieje"));
/*  54 */       return true;
/*     */     } 
/*     */     
/*  57 */     this.plugin.getConfig().set("kody." + kod + ".waluta", Integer.valueOf(5));
/*  58 */     this.plugin.getConfig().set("kody." + kod + ".komendy", Arrays.asList(new String[] { "say Użyto kodu!" }));
/*  59 */     this.plugin.saveConfig();
/*     */     
/*  61 */     sender.sendMessage(this.plugin.getConfigManager().getMessage("kod_utworzony")
/*  62 */         .replace("%kod%", kod));
/*  63 */     return true;
/*     */   }
/*     */   
/*     */   private boolean handleReset(CommandSender sender, String[] args) {
/*  67 */     if (args.length < 2) {
/*  68 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("uzycie_gkod"));
/*  69 */       return true;
/*     */     } 
/*     */     
/*  72 */     String playerName = args[1];
/*  73 */     OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
/*     */     
/*  75 */     if (target == null || !target.hasPlayedBefore()) {
/*  76 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("gracz_nie_istnieje")
/*  77 */           .replace("%player%", playerName));
/*  78 */       return true;
/*     */     } 
/*     */     
/*  81 */     this.plugin.getDatabaseManager().resetPlayerCodes(target.getUniqueId());
/*  82 */     sender.sendMessage(this.plugin.getConfigManager().getMessage("kod_zresetowany")
/*  83 */         .replace("%player%", playerName));
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   private boolean handleHistory(CommandSender sender, String[] args) {
/*  88 */     if (args.length < 2) {
/*  89 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("uzycie_gkod"));
/*  90 */       return true;
/*     */     } 
/*     */     
/*  93 */     String playerName = args[1];
/*  94 */     OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
/*     */     
/*  96 */     if (target == null || !target.hasPlayedBefore()) {
/*  97 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("gracz_nie_istnieje")
/*  98 */           .replace("%player%", playerName));
/*  99 */       return true;
/*     */     } 
/*     */     
/* 102 */     List<String> usedCodes = this.plugin.getDatabaseManager().getPlayerUsedCodes(target.getUniqueId());
/*     */     
/* 104 */     if (usedCodes.isEmpty()) {
/* 105 */       sender.sendMessage(this.plugin.getConfigManager().getMessage("brak_historii"));
/* 106 */       return true;
/*     */     } 
/*     */     
/* 109 */     sender.sendMessage(this.plugin.getConfigManager().getMessage("historia_kodow")
/* 110 */         .replace("%player%", playerName));
/* 111 */     for (String codeInfo : usedCodes) {
/* 112 */       sender.sendMessage(codeInfo);
/*     */     }
/* 114 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Milos\IdeaProjects\GoodDev-Kod\target\GoodDev-GoodDevKod-1.0.jar!\pl\gooddev\GoodDevKod\commands\GKodCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */