/*    */ package pl.gooddev.GoodDevKod.managers;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.entity.Player;
/*    */ import pl.gooddev.GoodDevKod.Main;
/*    */ 
/*    */ public class DatabaseManager {
/*    */   private final Main plugin;
/*    */   private Connection connection;
/*    */   
/*    */   public DatabaseManager(Main plugin) {
/* 16 */     this.plugin = plugin;
/* 17 */     initializeDatabase();
/*    */   }
/*    */   
/*    */   private void initializeDatabase() {
/*    */     
/* 22 */     try { Class.forName("org.sqlite.JDBC");
/* 23 */       this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.plugin
/* 24 */           .getDataFolder().getAbsolutePath() + "/database.db");
/*    */       
/* 26 */       String sql = "CREATE TABLE IF NOT EXISTS used_codes (id INTEGER PRIMARY KEY AUTOINCREMENT,player_uuid VARCHAR(36),player_name VARCHAR(16),code VARCHAR(50),use_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 34 */       Statement stmt = this.connection.createStatement(); 
/* 35 */       try { stmt.execute(sql);
/* 36 */         if (stmt != null) stmt.close();  } catch (Throwable throwable) { if (stmt != null)
/* 37 */           try { stmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception e)
/* 38 */     { e.printStackTrace(); }
/*    */   
/*    */   }
/*    */   
/*    */   public boolean hasUsedCode(UUID playerUUID, String code) {
/* 43 */     String sql = "SELECT 1 FROM used_codes WHERE player_uuid = ? AND code = ?"; 
/* 44 */     try { PreparedStatement pstmt = this.connection.prepareStatement(sql); 
/* 45 */       try { pstmt.setString(1, playerUUID.toString());
/* 46 */         pstmt.setString(2, code);
/* 47 */         ResultSet rs = pstmt.executeQuery();
/* 48 */         boolean bool = rs.next();
/* 49 */         if (pstmt != null) pstmt.close();  return bool; } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 50 */     { e.printStackTrace();
/* 51 */       return false; }
/*    */   
/*    */   }
/*    */   
/*    */   public void saveCodeUse(Player player, String code) {
/* 56 */     String sql = "INSERT INTO used_codes (player_uuid, player_name, code) VALUES (?, ?, ?)"; 
/* 57 */     try { PreparedStatement pstmt = this.connection.prepareStatement(sql); 
/* 58 */       try { pstmt.setString(1, player.getUniqueId().toString());
/* 59 */         pstmt.setString(2, player.getName());
/* 60 */         pstmt.setString(3, code);
/* 61 */         pstmt.executeUpdate();
/* 62 */         if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 63 */     { e.printStackTrace(); }
/*    */   
/*    */   }
/*    */   
/*    */   public void resetPlayerCodes(UUID playerUUID) {
/* 68 */     String sql = "DELETE FROM used_codes WHERE player_uuid = ?"; 
/* 69 */     try { PreparedStatement pstmt = this.connection.prepareStatement(sql); 
/* 70 */       try { pstmt.setString(1, playerUUID.toString());
/* 71 */         pstmt.executeUpdate();
/* 72 */         if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 73 */     { e.printStackTrace(); }
/*    */   
/*    */   }
/*    */   
/*    */   public List<String> getPlayerUsedCodes(UUID playerUUID) {
/* 78 */     List<String> usedCodes = new ArrayList<>();
/* 79 */     String sql = "SELECT code, use_time FROM used_codes WHERE player_uuid = ? ORDER BY use_time DESC";
/*    */     
/* 81 */     try { PreparedStatement pstmt = this.connection.prepareStatement(sql); 
/* 82 */       try { pstmt.setString(1, playerUUID.toString());
/* 83 */         ResultSet rs = pstmt.executeQuery();
/*    */         
/* 85 */         while (rs.next()) {
/* 86 */           String code = rs.getString("code");
/* 87 */           Timestamp useTime = rs.getTimestamp("use_time");
/* 88 */           usedCodes.add(String.format("§7- §f%s §7(użyto: §f%s§7)", new Object[] { code, useTime
/* 89 */                   .toString() }));
/*    */         } 
/* 91 */         if (pstmt != null) pstmt.close();  } catch (Throwable throwable) { if (pstmt != null) try { pstmt.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException e)
/* 92 */     { e.printStackTrace(); }
/*    */ 
/*    */     
/* 95 */     return usedCodes;
/*    */   }
/*    */ }


/* Location:              C:\Users\Milos\IdeaProjects\GoodDev-Kod\target\GoodDev-GoodDevKod-1.0.jar!\pl\gooddev\GoodDevKod\managers\DatabaseManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */