package emu.nebula.nbcommand.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;

/**
 * SQLite数据库管理类
 * 用于存储连接配置等数据
 */
public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static final String DB_FILE = "config.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_FILE;
    
    private static DatabaseManager instance;
    private Connection connection;
    
    private DatabaseManager() {
        initDatabase();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    /**
     * 初始化数据库
     */
    private void initDatabase() {
        try {
            // 确保数据库文件所在目录存在
            File dbFile = new File(DB_FILE);
            File parentDir = dbFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            // 建立数据库连接
            connection = DriverManager.getConnection(DB_URL);
            logger.info("成功连接到SQLite数据库: {}", DB_FILE);
            
            // 创建配置表
            createConfigTable();
        } catch (SQLException e) {
            logger.error("初始化数据库时出错", e);
        }
    }
    
    /**
     * 创建配置表
     */
    private void createConfigTable() {
        String sql = "CREATE TABLE IF NOT EXISTS config (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "server_address TEXT NOT NULL," +
                "auth_token TEXT NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            logger.info("配置表已创建或已存在");
        } catch (SQLException e) {
            logger.error("创建配置表时出错", e);
        }
    }

    /**
     * 保存配置
     */
    public boolean saveConfig(String serverAddress, String authToken) {
        String sql = "REPLACE INTO config(id, server_address, auth_token) VALUES(1, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, serverAddress);
            pstmt.setString(2, authToken);
            pstmt.executeUpdate();
            logger.info("配置已保存到数据库, serverAddress:{} authToken:{}", serverAddress, authToken);
            return true;
        } catch (SQLException e) {
            logger.error("保存配置时出错", e);
            return false;
        }
    }

    
    /**
     * 删除所有配置
     */
    private void deleteAllConfig() {
        String sql = "DELETE FROM config";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            logger.error("删除旧配置时出错", e);
        }
    }
    
    /**
     * 加载配置
     */
    public Config loadConfig() {
        String sql = "SELECT server_address, auth_token FROM config ORDER BY created_at DESC LIMIT 1";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                String serverAddress = rs.getString("server_address");
                String authToken = rs.getString("auth_token");
                logger.info("从数据库加载配置成功, serverAddress:{} authToken:{}", serverAddress, authToken);
                return new Config(serverAddress, authToken);
            }
        } catch (SQLException e) {
            logger.error("加载配置时出错", e);
        }
        
        // 返回默认配置
        return new Config("http://localhost:80", "");
    }
    
    /**
     * 关闭数据库连接
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("数据库连接已关闭");
            }
        } catch (SQLException e) {
            logger.error("关闭数据库连接时出错", e);
        }
    }

    /**
     * 配置数据类
     */
    public record Config(String serverAddress, String authToken) {}
}