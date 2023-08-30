package senla.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream stream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
           PROPERTIES.load(stream);
        }
        catch (IOException e) {
            throw new RuntimeException("Не доступен файл ресурсов");
        }

    }

    private PropertiesUtil() {

    }
}
