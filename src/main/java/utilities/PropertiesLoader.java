package utilities;

import spr.exceptions.PropertyLoaderException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jiaweizhang on 10/29/2016.
 */

public class PropertiesLoader {
    public static Properties loadPropertiesFromPackage(String filePath) {
        Properties prop = new Properties();
        try (InputStream in = PropertiesLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            prop.load(in);
            return prop;
        } catch (IOException e) {
            throw new PropertyLoaderException();
        }
    }
}