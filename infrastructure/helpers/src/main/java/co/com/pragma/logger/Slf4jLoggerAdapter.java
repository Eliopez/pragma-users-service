package co.com.pragma.logger;

import co.com.pragma.model.common.Logger;
import co.com.pragma.model.common.LogLevel;

/**
 * SLF4J adapter implementation of the Logger interface.
 * This class adapts SLF4J logger to our domain Logger interface.
 */
public class Slf4jLoggerAdapter implements Logger {
    
    private final org.slf4j.Logger slf4jLogger;
    private final LoggerManager loggerManager;
    
    public Slf4jLoggerAdapter(org.slf4j.Logger slf4jLogger, LoggerManager loggerManager) {
        this.slf4jLogger = slf4jLogger;
        this.loggerManager = loggerManager;
    }
    
    @Override
    public void trace(String message) {
        if (isTraceEnabled()) {
            slf4jLogger.trace(message);
        }
    }
    
    @Override
    public void trace(String message, Object... args) {
        if (isTraceEnabled()) {
            slf4jLogger.trace(message, args);
        }
    }
    
    @Override
    public void debug(String message) {
        if (isDebugEnabled()) {
            slf4jLogger.debug(message);
        }
    }
    
    @Override
    public void debug(String message, Object... args) {
        if (isDebugEnabled()) {
            slf4jLogger.debug(message, args);
        }
    }
    
    @Override
    public void info(String message) {
        if (isInfoEnabled()) {
            slf4jLogger.info(message);
        }
    }
    
    @Override
    public void info(String message, Object... args) {
        if (isInfoEnabled()) {
            slf4jLogger.info(message, args);
        }
    }
    
    @Override
    public void warn(String message) {
        if (isWarnEnabled()) {
            slf4jLogger.warn(message);
        }
    }
    
    @Override
    public void warn(String message, Object... args) {
        if (isWarnEnabled()) {
            slf4jLogger.warn(message, args);
        }
    }
    
    @Override
    public void warn(String message, Throwable throwable) {
        if (isWarnEnabled()) {
            slf4jLogger.warn(message, throwable);
        }
    }
    
    @Override
    public void error(String message) {
        if (isErrorEnabled()) {
            slf4jLogger.error(message);
        }
    }
    
    @Override
    public void error(String message, Object... args) {
        if (isErrorEnabled()) {
            slf4jLogger.error(message, args);
        }
    }
    
    @Override
    public void error(String message, Throwable throwable) {
        if (isErrorEnabled()) {
            slf4jLogger.error(message, throwable);
        }
    }
    
    @Override
    public boolean isTraceEnabled() {
        return LogLevel.TRACE.isEnabled(loggerManager.getMinimumLogLevel()) && slf4jLogger.isTraceEnabled();
    }
    
    @Override
    public boolean isDebugEnabled() {
        return LogLevel.DEBUG.isEnabled(loggerManager.getMinimumLogLevel()) && slf4jLogger.isDebugEnabled();
    }
    
    @Override
    public boolean isInfoEnabled() {
        return LogLevel.INFO.isEnabled(loggerManager.getMinimumLogLevel()) && slf4jLogger.isInfoEnabled();
    }
    
    @Override
    public boolean isWarnEnabled() {
        return LogLevel.WARN.isEnabled(loggerManager.getMinimumLogLevel()) && slf4jLogger.isWarnEnabled();
    }
    
    @Override
    public boolean isErrorEnabled() {
        return LogLevel.ERROR.isEnabled(loggerManager.getMinimumLogLevel()) && slf4jLogger.isErrorEnabled();
    }
}
