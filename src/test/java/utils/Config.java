package utils;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties p = new Properties();
    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            p.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Cannot load config.properties", e);
        }
    }
    public static String get(String key) {
        return System.getProperty(key, p.getProperty(key));
    }
}

