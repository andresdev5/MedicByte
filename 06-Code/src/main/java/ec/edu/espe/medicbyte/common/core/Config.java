package ec.edu.espe.medicbyte.common.core;

import ec.edu.espe.medicbyte.util.PathUtils;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;


/**
 *
 * @author Andres Jonathan J.
 */
public class Config {
    private static Config instance;
    private Configuration config;
    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;
    
    private Config() {
        setup();
    }
    
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        
        return instance;
    }
    
    public Object get(String key) {
        try {
            return config.getProperty(key);
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public <T> T get(String key, Class<T> propertyClass) {
        try {
            Object value;
            
            if (propertyClass.isAssignableFrom(Integer.class)) {
                value = config.getInt(key);
            } else if (propertyClass.isAssignableFrom(String.class)) {
                value = config.getString(key);
            } else if (propertyClass.isAssignableFrom(BigDecimal.class)) {
                value = config.getBigDecimal(key);
            } else if (propertyClass.isAssignableFrom(BigInteger.class)) {
                value = config.getBigInteger(key);
            } else if (propertyClass.isAssignableFrom(Boolean.class)) {
                value = config.getBoolean(key);
            } else if (propertyClass.isAssignableFrom(Byte.class)) {
                value = config.getByte(key);
            } else if (propertyClass.isAssignableFrom(Double.class)) {
                value = config.getDouble(key);
            } else if (propertyClass.isAssignableFrom(Float.class)) {
                value = config.getFloat(key);
            } else if (propertyClass.isAssignableFrom(Long.class)) {
                value = config.getFloat(key);
            } else if (propertyClass.isAssignableFrom(Short.class)) {
                value = config.getShort(key);
            } else {
                value = config.get(propertyClass, key);
            }
            
            return config.get(propertyClass, key);
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void set(String key, Object value) {
        config.setProperty(key, value);
        
        try {
            builder.save();
        } catch(ConfigurationException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setup() {
        File propertiesFile = PathUtils.currentPath("config.properties").toFile();
        Parameters params = new Parameters();
        boolean setInitialConfig = false;
        
        if (!propertiesFile.exists()) {
            PathUtils.ensureFiles(propertiesFile);
            setInitialConfig = true;
        }
        
        builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
            .configure(params.fileBased()
            .setFile(propertiesFile));
        
        try {
            config = builder.getConfiguration();
            // config contains all properties read from the file
            
            if (setInitialConfig) {
                config.addProperty("language", "en");
                config.addProperty("dbname", "medicbyte");
                config.addProperty("dbhost", "200.105.253.153");
                config.addProperty("dbport", 25017);
                builder.save();
            }
        } catch(ConfigurationException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
