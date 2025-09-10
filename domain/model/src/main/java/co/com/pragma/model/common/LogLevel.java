package co.com.pragma.model.common;

/**
 * Enumeration of log levels used throughout the application.
 * Ordered from most verbose (TRACE) to least verbose (ERROR).
 */
public enum LogLevel {
    TRACE(0, "TRACE"),
    DEBUG(1, "DEBUG"),
    INFO(2, "INFO"),
    WARN(3, "WARN"),
    ERROR(4, "ERROR");
    
    private final int level;
    private final String name;
    
    LogLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }
    
    public int getLevel() {
        return level;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Check if this log level is enabled for the given minimum level
     * @param minimumLevel The minimum log level
     * @return true if this level should be logged
     */
    public boolean isEnabled(LogLevel minimumLevel) {
        return this.level >= minimumLevel.level;
    }
}
