package pl.gooddev.GoodDevKod.managers;

import pl.gooddev.GoodDevKod.Main;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    private final Main plugin;
    private Connection connection;

    public DatabaseManager(Main plugin) {
        this.plugin = plugin;
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" +
                    plugin.getDataFolder().getAbsolutePath() + "/database.db");

            String sql = "CREATE TABLE IF NOT EXISTS used_codes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "player_uuid VARCHAR(36)," +
                    "player_name VARCHAR(16)," +
                    "code VARCHAR(50)," +
                    "use_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasUsedCode(UUID playerUUID, String code) {
        String sql = "SELECT 1 FROM used_codes WHERE player_uuid = ? AND code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playerUUID.toString());
            pstmt.setString(2, code);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveCodeUse(Player player, String code) {
        String sql = "INSERT INTO used_codes (player_uuid, player_name, code) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, player.getUniqueId().toString());
            pstmt.setString(2, player.getName());
            pstmt.setString(3, code);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetPlayerCodes(UUID playerUUID) {
        String sql = "DELETE FROM used_codes WHERE player_uuid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playerUUID.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPlayerUsedCodes(UUID playerUUID) {
        List<String> usedCodes = new ArrayList<>();
        String sql = "SELECT code, use_time FROM used_codes WHERE player_uuid = ? ORDER BY use_time DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playerUUID.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String code = rs.getString("code");
                Timestamp useTime = rs.getTimestamp("use_time");
                usedCodes.add(String.format("§7- §f%s §7(użyto: §f%s§7)",
                        code, useTime.toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usedCodes;
    }
}