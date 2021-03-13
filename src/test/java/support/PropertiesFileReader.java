package support;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class PropertiesFileReader {

    private final Properties properties;
    private static PropertiesFileReader instance;

    private PropertiesFileReader() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("configs/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesFileReader getInstance() {
        if (instance == null) {
            instance = new PropertiesFileReader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Set<String> getAllPropertyNames() {
        return properties.stringPropertyNames();
    }
}
