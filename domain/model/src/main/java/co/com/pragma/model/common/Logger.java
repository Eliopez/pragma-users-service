package co.com.pragma.model.common;

/**
 * Logger interface for the domain layer following clean architecture principles.
 * This interface defines the contract for logging operations across different log levels.
 */
public interface Logger {
    
    /**
     * Log a trace message
     * @param message The message to log
     */
    void trace(String message);
    
    /**
     * Log a trace message with arguments
     * @param message The message template
     * @param args Arguments to substitute in the message
     */
    void trace(String message, Object... args);
    
    /**
     * Log a debug message
     * @param message The message to log
     */
    void debug(String message);
    
    /**
     * Log a debug message with arguments
     * @param message The message template
     * @param args Arguments to substitute in the message
     */
    void debug(String message, Object... args);
    
    /**
     * Log an info message
     * @param message The message to log
     */
    void info(String message);
    
    /**
     * Log an info message with arguments
     * @param message The message template
     * @param args Arguments to substitute in the message
     */
    void info(String message, Object... args);
    
    /**
     * Log a warning message
     * @param message The message to log
     */
    void warn(String message);
    
    /**
     * Log a warning message with arguments
     * @param message The message template
     * @param args Arguments to substitute in the message
     */
    void warn(String message, Object... args);
    
    /**
     * Log a warning message with exception
     * @param message The message to log
     * @param throwable The exception to log
     */
    void warn(String message, Throwable throwable);
    
    /**
     * Log an error message
     * @param message The message to log
     */
    void error(String message);
    
    /**
     * Log an error message with arguments
     * @param message The message template
     * @param args Arguments to substitute in the message
     */
    void error(String message, Object... args);
    
    /**
     * Log an error message with exception
     * @param message The message to log
     * @param throwable The exception to log
     */
    void error(String message, Throwable throwable);
    
    /**
     * Check if trace level is enabled
     * @return true if trace level is enabled
     */
    boolean isTraceEnabled();
    
    /**
     * Check if debug level is enabled
     * @return true if debug level is enabled
     */
    boolean isDebugEnabled();
    
    /**
     * Check if info level is enabled
     * @return true if info level is enabled
     */
    boolean isInfoEnabled();
    
    /**
     * Check if warn level is enabled
     * @return true if warn level is enabled
     */
    boolean isWarnEnabled();
    
    /**
     * Check if error level is enabled
     * @return true if error level is enabled
     */
    boolean isErrorEnabled();
}
