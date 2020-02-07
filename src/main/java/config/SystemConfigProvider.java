package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SystemConfigProvider {

    public static String getProperty(String propertyName){
        InputStream is = SystemConfigProvider.class.getResourceAsStream("/my.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(propertyName);
    }

}
