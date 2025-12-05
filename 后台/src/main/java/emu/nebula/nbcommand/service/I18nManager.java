package emu.nebula.nbcommand.service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18nManager {
    private static I18nManager instance;
    private ResourceBundle bundle;
    private Locale currentLocale;

    private I18nManager() {
        setLocale(Locale.getDefault());
    }

    public static synchronized I18nManager getInstance() {
        if (instance == null) {
            instance = new I18nManager();
        }
        return instance;
    }

    public void setLocale(Locale locale) {
        this.currentLocale = locale;
        // 尝试加载特定语言包，如果不存在则使用默认的
        try {
            this.bundle = ResourceBundle.getBundle("lang/messages", locale);
        } catch (Exception e) {
            this.bundle = ResourceBundle.getBundle("lang/messages", Locale.ENGLISH);
        }
    }

    public String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "[" + key + "]";
        }
    }

    public String getString(String key, Object... args) {
        try {
            String pattern = bundle.getString(key);
            return MessageFormat.format(pattern, args);
        } catch (Exception e) {
            return "[" + key + "]";
        }
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
    
    public boolean isChinese() {
        return "zh".equals(currentLocale.getLanguage());
    }
    
    public boolean isEnglish() {
        return "en".equals(currentLocale.getLanguage());
    }
    
    public boolean isJapanese() {
        return "ja".equals(currentLocale.getLanguage());
    }
    
    public boolean isKorean() {
        return "ko".equals(currentLocale.getLanguage());
    }

    /**
     * 获取当前语言代码
     * @return 语言代码，例如 "zh_CN" 或 "en_US"
     */
    public static String getLanguageCode() {
        I18nManager i18n = I18nManager.getInstance();
        if (i18n.isChinese()) {
            return "zh_CN";
        } else if (i18n.isJapanese()) {
            return "ja_JP";
        } else if (i18n.isKorean()) {
            return "ko_KR";
        } else {
            return "en_US";
        }
    }
}