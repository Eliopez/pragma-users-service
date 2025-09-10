package co.com.pragma.logger;

import co.com.pragma.model.common.Logger;
import co.com.pragma.model.common.LogLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the logging system implementation
 */
class LoggerTest {
    
    private Logger logger;
    private LoggerManager loggerManager;
    
    @BeforeEach
    void setUp() {
        loggerManager = LoggerManager.getInstance();
        loggerManager.clearLoggers(); // Clear cache for clean tests
        logger = LoggerFactory.getLogger(LoggerTest.class);
    }
    
    @Test
    @DisplayName("Should create singleton LoggerManager instance")
    void testSingletonPattern() {
        LoggerManager instance1 = LoggerManager.getInstance();
        LoggerManager instance2 = LoggerManager.getInstance();
        
        assertSame(instance1, instance2, "LoggerManager should be a singleton");
    }
    
    @Test
    @DisplayName("Should create logger instances for different classes")
    void testLoggerCreation() {
        Logger logger1 = LoggerFactory.getLogger(LoggerTest.class);
        Logger logger2 = LoggerFactory.getLogger(String.class);
        Logger logger3 = LoggerFactory.getLogger(LoggerTest.class);
        
        assertNotNull(logger1, "Logger should not be null");
        assertNotNull(logger2, "Logger should not be null");
        assertSame(logger1, logger3, "Same class should return same logger instance");
        assertNotSame(logger1, logger2, "Different classes should return different logger instances");
    }
    
    @Test
    @DisplayName("Should respect minimum log level settings")
    void testLogLevelFiltering() {
        // Set minimum level to WARN
        LoggerFactory.setMinimumLogLevel(LogLevel.WARN);
        
        // These should be disabled
        assertFalse(logger.isTraceEnabled(), "TRACE should be disabled when minimum is WARN");
        assertFalse(logger.isDebugEnabled(), "DEBUG should be disabled when minimum is WARN");
        assertFalse(logger.isInfoEnabled(), "INFO should be disabled when minimum is WARN");
        
        // These should be enabled (depending on underlying SLF4J configuration)
        // Note: The actual enabling also depends on SLF4J logger configuration
        
        // Reset to INFO for other tests
        LoggerFactory.setMinimumLogLevel(LogLevel.INFO);
    }
    
    @Test
    @DisplayName("Should handle log level enumeration correctly")
    void testLogLevelEnum() {
        assertTrue(LogLevel.ERROR.isEnabled(LogLevel.INFO), "ERROR should be enabled when minimum is INFO");
        assertTrue(LogLevel.WARN.isEnabled(LogLevel.INFO), "WARN should be enabled when minimum is INFO");
        assertTrue(LogLevel.INFO.isEnabled(LogLevel.INFO), "INFO should be enabled when minimum is INFO");
        assertFalse(LogLevel.DEBUG.isEnabled(LogLevel.INFO), "DEBUG should be disabled when minimum is INFO");
        assertFalse(LogLevel.TRACE.isEnabled(LogLevel.INFO), "TRACE should be disabled when minimum is INFO");
    }
    
    @Test
    @DisplayName("Should not throw exceptions when logging")
    void testLoggingOperations() {
        assertDoesNotThrow(() -> {
            logger.trace("Test trace message");
            logger.debug("Test debug message");
            logger.info("Test info message");
            logger.warn("Test warn message");
            logger.error("Test error message");
        }, "Logging operations should not throw exceptions");
    }
    
    @Test
    @DisplayName("Should handle parameterized messages")
    void testParameterizedMessages() {
        assertDoesNotThrow(() -> {
            logger.info("Test message with parameters: {} and {}", "param1", "param2");
            logger.error("Error with parameters: {} - {}", "error", 123);
        }, "Parameterized logging should not throw exceptions");
    }
    
    @Test
    @DisplayName("Should handle exceptions in error logging")
    void testExceptionLogging() {
        Exception testException = new RuntimeException("Test exception");
        
        assertDoesNotThrow(() -> {
            logger.error("Error occurred", testException);
            logger.warn("Warning with exception", testException);
        }, "Exception logging should not throw exceptions");
    }
}
