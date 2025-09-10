package co.com.pragma.logger;

import co.com.pragma.model.common.Logger;
import co.com.pragma.model.common.LogLevel;
import co.com.pragma.model.common.LoggerFactory;

/**
 * Concrete implementation of LoggerFactory for the infrastructure layer.
 * This class bridges the domain layer's abstract factory with the concrete implementation.
 */
public class DomainLoggerFactory extends LoggerFactory {
    
    private final LoggerManager loggerManager;
    
    public DomainLoggerFactory() {
        this.loggerManager = LoggerManager.getInstance();
    }
    
    @Override
    public Logger getLogger(Class<?> clazz) {
        return loggerManager.getLogger(clazz);
    }
    
    @Override
    public Logger getLogger(String name) {
        return loggerManager.getLogger(name);
    }
    
    @Override
    public void setMinimumLogLevel(LogLevel level) {
        loggerManager.setMinimumLogLevel(level);
    }
    
    @Override
    public LogLevel getMinimumLogLevel() {
        return loggerManager.getMinimumLogLevel();
    }
}
