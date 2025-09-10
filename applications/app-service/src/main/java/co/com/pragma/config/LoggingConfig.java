package co.com.pragma.config;

import co.com.pragma.logger.DomainLoggerFactory;
import co.com.pragma.logger.LoggerManager;
import co.com.pragma.model.common.LogLevel;
import co.com.pragma.model.common.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Configuration class for logging system.
 * Configures the LoggerManager singleton with application-specific settings.
 */
@Configuration
public class LoggingConfig {
    
    @Value("${logging.level.root:INFO}")
    private String rootLogLevel;
    
    @Value("${logging.level.co.com.pragma:INFO}")
    private String applicationLogLevel;
    
    /**
     * Configure the LoggerManager with the appropriate log level
     */
    @PostConstruct
    public void configureLogging() {
        // Initialize the domain logger factory
        LoggerFactory.setInstance(new DomainLoggerFactory());
        
        // Set the minimum log level
        LogLevel level = parseLogLevel(applicationLogLevel);
        LoggerManager.getInstance().setMinimumLogLevel(level);
    }
    
    /**
     * Provide LoggerManager as a Spring bean
     * @return LoggerManager singleton instance
     */
    @Bean
    public LoggerManager loggerManager() {
        return LoggerManager.getInstance();
    }
    
    /**
     * Parse string log level to LogLevel enum
     * @param levelString String representation of log level
     * @return LogLevel enum value
     */
    private LogLevel parseLogLevel(String levelString) {
        try {
            return LogLevel.valueOf(levelString.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Default to INFO if invalid level provided
            return LogLevel.INFO;
        }
    }
}
