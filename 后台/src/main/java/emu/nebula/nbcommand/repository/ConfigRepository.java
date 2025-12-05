package emu.nebula.nbcommand.repository;

import emu.nebula.nbcommand.service.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Repository for configuration data.
 * Handles loading and saving of application configuration.
 */
public class ConfigRepository {
    private static final Logger logger = LoggerFactory.getLogger(ConfigRepository.class);

    private DatabaseManager databaseManager;
    private String serverAddress;
    private String authToken;

    public ConfigRepository() {
        try {
            Class.forName("org.sqlite.JDBC");
            databaseManager = DatabaseManager.getInstance();
        } catch (ClassNotFoundException e) {
            logger.error("无法加载SQLite JDBC驱动", e);
            // 如果数据库初始化失败，使用默认配置
            serverAddress = "http://localhost:80";
            authToken = "";
        }

        // 从数据库加载配置
        if (databaseManager != null) {
            loadConfigFromDatabase();
        } else {
            // 数据库不可用时使用默认配置
            serverAddress = "http://localhost:80";
            authToken = "";
        }
    }

    /**
     * 从数据库加载配置
     */
    private void loadConfigFromDatabase() {
        DatabaseManager.Config config = databaseManager.loadConfig();
        serverAddress = config.serverAddress();
        authToken = config.authToken();
        logger.info("从数据库加载配置完成");
    }

    /**
     * 保存配置
     */
    public boolean saveConfig(String serverAddress, String authToken) {
        this.serverAddress = serverAddress;
        this.authToken = authToken;

        // 保存到数据库
        boolean saved = false;
        if (databaseManager != null) {
            saved = databaseManager.saveConfig(serverAddress, authToken);
        }

        if (saved) {
            logger.info("配置已保存到数据库，服务器地址: {}", serverAddress);
        } else if (databaseManager != null) {
            logger.error("配置保存到数据库失败");
        } else {
            logger.warn("数据库不可用，配置仅保存在内存中");
        }

        return saved;
    }

    /**
     * 根据规则掩码token显示
     * 如果只包含数字则显示明文，否则显示星号
     */
    public String maskToken(String token) {
        if (token == null || token.isEmpty()) {
            return "";
        }

        // 检查是否只包含数字
        if (token.matches("^\\d+$")) {
            return token;
        } else {
            return "*".repeat(Math.min(token.length(), 10));
        }
    }

    // Getters
    public String getServerAddress() {
        return serverAddress;
    }

    public String getAuthToken() {
        return authToken;
    }
}