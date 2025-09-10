package co.com.pragma.model.common;

/**
 * Abstract factory for creating logger instances in the domain layer.
 * This follows clean architecture principles by defining the contract in the domain
 * while allowing the infrastructure layer to provide the implementation.
 */
public abstract class LoggerFactory {
    
    private static LoggerFactory instance;
    
    /**
     * Set the factory implementation (called by infrastructure layer)
     * @param factory The concrete factory implementation
     */
    public static void setInstance(LoggerFactory factory) {
        instance = factory;
    }
    
    /**
     * Get the current factory instance
     * @return The factory instance
     */
    public static LoggerFactory getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LoggerFactory not initialized. Call setInstance() first.");
        }
        return instance;
    }
    
    /**
     * Create a logger for the specified class
     * @param clazz The class to create logger for
     * @return Logger instance
     */
    public abstract Logger getLogger(Class<?> clazz);
    
    /**
     * Create a logger for the specified name
     * @param name The logger name
     * @return Logger instance
     */
    public abstract Logger getLogger(String name);
    
    /**
     * Set the minimum log level
     * @param level The minimum log level
     */
    public abstract void setMinimumLogLevel(LogLevel level);
    
    /**
     * Get the current minimum log level
     * @return Current minimum log level
     */
    public abstract LogLevel getMinimumLogLevel();
    
    /**
     * Static convenience method to get a logger for a class
     * @param clazz The class to get logger for
     * @return Logger instance
     */
    public static Logger getLoggerFor(Class<?> clazz) {
        return getInstance().getLogger(clazz);
    }
    
    /**
     * Static convenience method to get a logger by name
     * @param name The logger name
     * @return Logger instance
     */
    public static Logger getLoggerFor(String name) {
        return getInstance().getLogger(name);
    }
}
