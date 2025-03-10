/*     */ package pl.gooddev.GoodDevKod.managers;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import pl.gooddev.GoodDevKod.Main;
/*     */ 
/*     */ public class CodeManager
/*     */ {
/*     */   public CodeManager(Main plugin) {
/*  17 */     this.plugin = plugin;
/*     */   }
/*     */   private final Main plugin;
/*     */   public boolean useCode(Player player, String code) {
/*  21 */     if (!this.plugin.getConfig().contains("kody." + code)) {
/*  22 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  26 */     if (this.plugin.getConfig().getBoolean("ustawienia.jednorazowe_uzycie") && 
/*  27 */       this.plugin.getDatabaseManager().hasUsedCode(player.getUniqueId(), code)) {
/*  28 */       player.sendMessage(this.plugin.getConfigManager().getMessage("kod_juz_wykorzystany"));
/*  29 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  34 */     if (this.plugin.getConfig().getBoolean("ustawienia.waluta")) {
/*  35 */       givePlayerCurrency(player, code);
/*     */     }
/*     */     
/*  38 */     if (this.plugin.getConfig().getBoolean("ustawienia.przedmioty")) {
/*  39 */       giveItems(player, code);
/*     */     }
/*     */     
/*  42 */     if (this.plugin.getConfig().getBoolean("ustawienia.komendy")) {
/*  43 */       executeCommands(player, code);
/*     */     }
/*     */     
/*  46 */     if (this.plugin.getConfig().getBoolean("ustawienia.jednorazowe_uzycie")) {
/*  47 */       this.plugin.getDatabaseManager().saveCodeUse(player, code);
/*     */     }
/*     */     
/*  50 */     return true;
/*     */   }
/*     */   
/*     */   private void givePlayerCurrency(Player player, String code) {
/*  54 */     if (this.plugin.getEconomy() == null)
/*     */       return; 
/*  56 */     double amount = this.plugin.getConfig().getDouble("kody." + code + ".waluta", 0.0D);
/*  57 */     if (amount > 0.0D) {
/*  58 */       this.plugin.getEconomy().depositPlayer((OfflinePlayer)player, amount);
/*  59 */       player.sendMessage(this.plugin.getConfigManager().getMessage("otrzymano_walute")
/*  60 */           .replace("%amount%", String.valueOf(amount)));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void giveItems(Player player, String code) {
/*  65 */     String path = "kody." + code + ".przedmiot";
/*  66 */     if (!this.plugin.getConfig().contains(path)) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  71 */       Material material = Material.valueOf(this.plugin.getConfig().getString(path + ".material", "STONE").toUpperCase());
/*  72 */       int amount = this.plugin.getConfig().getInt(path + ".ilosc", 1);
/*  73 */       ItemStack item = new ItemStack(material, amount);
/*  74 */       ItemMeta meta = item.getItemMeta();
/*     */       
/*  76 */       if (meta != null) {
/*     */         
/*  78 */         if (this.plugin.getConfig().contains(path + ".nazwa")) {
/*  79 */           meta.setDisplayName(this.plugin.getConfigManager().colorize(this.plugin
/*  80 */                 .getConfig().getString(path + ".nazwa")));
/*     */         }
/*     */ 
/*     */         
/*  84 */         if (this.plugin.getConfig().contains(path + ".lore")) {
/*  85 */           List<String> lore = new ArrayList<>();
/*  86 */           for (String line : this.plugin.getConfig().getStringList(path + ".lore")) {
/*  87 */             lore.add(this.plugin.getConfigManager().colorize(line));
/*     */           }
/*  89 */           meta.setLore(lore);
/*     */         } 
/*     */ 
/*     */         
/*  93 */         if (this.plugin.getConfig().contains(path + ".enchants")) {
/*  94 */           for (String enchantString : this.plugin.getConfig().getStringList(path + ".enchants")) {
/*     */             try {
/*  96 */               String[] parts = enchantString.split(":");
/*  97 */               Enchantment enchant = Enchantment.getByName(parts[0].toUpperCase());
/*  98 */               int level = Integer.parseInt(parts[1]);
/*  99 */               if (enchant != null) {
/* 100 */                 meta.addEnchant(enchant, level, true);
/*     */               }
/* 102 */             } catch (Exception e) {
/* 103 */               this.plugin.getLogger().warning("Błąd podczas dodawania enchantu: " + enchantString);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 108 */         item.setItemMeta(meta);
/*     */       } 
/*     */       
/* 111 */       if (player.getInventory().firstEmpty() == -1) {
/* 112 */         player.sendMessage(this.plugin.getConfigManager().getMessage("pelny_ekwipunek"));
/*     */         
/*     */         return;
/*     */       } 
/* 116 */       player.getInventory().addItem(new ItemStack[] { item });
/* 117 */       player.sendMessage(this.plugin.getConfigManager().getMessage("otrzymano_przedmioty"));
/*     */     }
/* 119 */     catch (IllegalArgumentException e) {
/* 120 */       this.plugin.getLogger().warning("Błąd podczas tworzenia przedmiotu dla kodu: " + code);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void executeCommands(Player player, String code) {
/* 125 */     List<String> commands = this.plugin.getConfig().getStringList("kody." + code + ".komendy");
/* 126 */     for (String cmd : commands)
/* 127 */       this.plugin.getServer().dispatchCommand((CommandSender)this.plugin
/* 128 */           .getServer().getConsoleSender(), cmd
/* 129 */           .replace("%player%", player.getName())); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Milos\IdeaProjects\GoodDev-Kod\target\GoodDev-GoodDevKod-1.0.jar!\pl\gooddev\GoodDevKod\managers\CodeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */