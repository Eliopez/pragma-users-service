package co.com.pragma.logger;

import co.com.pragma.model.common.Logger;
import co.com.pragma.model.common.LogLevel;

/**
 * Factory class for creating logger instances.
 * This class provides a convenient static API for getting loggers.
 */
public final class LoggerFactory {
    
    private LoggerFactory() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Get a logger for the specified class
     * @param clazz The class to get logger for
     * @return Logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerManager.getInstance().getLogger(clazz);
    }
    
    /**
     * Get a logger for the specified name
     * @param name The logger name
     * @return Logger instance
     */
    public static Logger getLogger(String name) {
        return LoggerManager.getInstance().getLogger(name);
    }
    
    /**
     * Set the minimum log level for all loggers
     * @param level The minimum log level
     */
    public static void setMinimumLogLevel(LogLevel level) {
        LoggerManager.getInstance().setMinimumLogLevel(level);
    }
    
    /**
     * Get the current minimum log level
     * @return Current minimum log level
     */
    public static LogLevel getMinimumLogLevel() {
        return LoggerManager.getInstance().getMinimumLogLevel();
    }
}
