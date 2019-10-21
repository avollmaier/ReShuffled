package data.config.service;

import java.io.File;


/**
 *
 * @author volalm15
 */
public class ConfigException extends Exception {

    private File configFile;
    
    public ConfigException (File configFile, String message) {
        super(message);
        this.configFile = configFile;
    }

    public ConfigException (File configFile, String message, Throwable cause) {
        super(message, cause);
        this.configFile = configFile;
    }
    
}
