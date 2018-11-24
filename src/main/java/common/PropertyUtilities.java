package common;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Define PropertyUtilities class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class PropertyUtilities {

    /**
     * Get Bot property
     * @param property property name
     * @return property value
     */
    public String getProperty(String property) {
        InputStream inputStream;
        String result = "";

        try {
            Properties prop = new Properties();
            String propFileName = "horbot.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // Get the property value and print it out
            result = prop.getProperty(property);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
