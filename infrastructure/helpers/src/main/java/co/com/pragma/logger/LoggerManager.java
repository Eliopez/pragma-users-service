package co.com.pragma.logger;

import co.com.pragma.model.common.Logger;
import co.com.pragma.model.common.LogLevel;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Singleton Logger Manager that provides logger instances for different classes.
 * This implementation uses SLF4J as the underlying logging framework.
 * Thread-safe implementation using double-checked locking pattern.
 */
public class LoggerManager {
    
    private static volatile LoggerManager instance;
    private final ConcurrentMap<String, Logger> loggers;
    private volatile LogLevel minimumLogLevel;
    
    private LoggerManager() {
        this.loggers = new ConcurrentHashMap<>();
        this.minimumLogLevel = LogLevel.INFO; // Default log level
    }
    
    /**
     * Get the singleton instance of LoggerManager
     * @return LoggerManager instance
     */
    public static LoggerManager getInstance() {
        if (instance == null) {
            synchronized (LoggerManager.class) {
                if (instance == null) {
                    instance = new LoggerManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Get a logger for the specified class
     * @param clazz The class to get logger for
     * @return Logger instance
     */
    public Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
    
    /**
     * Get a logger for the specified name
     * @param name The logger name
     * @return Logger instance
     */
    public Logger getLogger(String name) {
        return loggers.computeIfAbsent(name, this::createLogger);
    }
    
    /**
     * Set the minimum log level for all loggers
     * @param level The minimum log level
     */
    public void setMinimumLogLevel(LogLevel level) {
        this.minimumLogLevel = level;
    }
    
    /**
     * Get the current minimum log level
     * @return Current minimum log level
     */
    public LogLevel getMinimumLogLevel() {
        return minimumLogLevel;
    }
    
    /**
     * Create a new logger instance
     * @param name The logger name
     * @return New logger instance
     */
    private Logger createLogger(String name) {
        return new Slf4jLoggerAdapter(LoggerFactory.getLogger(name), this);
    }
    
    /**
     * Clear all cached loggers (useful for testing)
     */
    public void clearLoggers() {
        loggers.clear();
    }
}
