package co.com.pragma.logger;

import co.com.pragma.model.common.Logger;
import co.com.pragma.model.common.LogLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for logging system with Spring Boot context
 */
@SpringBootTest(classes = {LoggerManager.class})
@TestPropertySource(properties = {
    "logging.level.co.com.pragma=DEBUG",
    "logging.level.root=INFO"
})
class LoggingIntegrationTest {
    
    @Test
    @DisplayName("Should demonstrate complete logging workflow")
    void testCompleteLoggingWorkflow() {
        // Simulate a service class using the logging system
        Logger serviceLogger = LoggerFactory.getLogger("co.com.pragma.service.TestService");
        
        // Test all log levels
        serviceLogger.trace("This is a trace message - should be filtered out");
        serviceLogger.debug("This is a debug message - should appear in logs");
        serviceLogger.info("Processing started for user: {}", "john.doe@example.com");
        serviceLogger.warn("Potential issue detected: {}", "slow database response");
        
        try {
            // Simulate an error scenario
            throw new RuntimeException("Simulated error for testing");
        } catch (Exception e) {
            serviceLogger.error("An error occurred during processing", e);
        }
        
        // Verify logger instances are cached
        Logger sameServiceLogger = LoggerFactory.getLogger("co.com.pragma.service.TestService");
        assertSame(serviceLogger, sameServiceLogger, "Logger instances should be cached");
        
        // Test different logger for different class
        Logger controllerLogger = LoggerFactory.getLogger("co.com.pragma.controller.TestController");
        assertNotSame(serviceLogger, controllerLogger, "Different loggers for different classes");
        
        controllerLogger.info("Request received: GET /api/users");
        controllerLogger.info("Response sent: 200 OK with {} users", 5);
    }
    
    @Test
    @DisplayName("Should handle concurrent logger access")
    void testConcurrentAccess() throws InterruptedException {
        final int threadCount = 10;
        final Thread[] threads = new Thread[threadCount];
        final Logger[] loggers = new Logger[threadCount];
        
        // Create multiple threads requesting loggers
        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                loggers[threadIndex] = LoggerFactory.getLogger("co.com.pragma.concurrent.Test" + threadIndex);
                loggers[threadIndex].info("Thread {} created logger", threadIndex);
            });
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Verify all loggers were created
        for (Logger logger : loggers) {
            assertNotNull(logger, "Logger should be created in concurrent environment");
        }
    }
    
    @Test
    @DisplayName("Should demonstrate reactive logging pattern")
    void testReactiveLoggingPattern() {
        Logger reactiveLogger = LoggerFactory.getLogger("co.com.pragma.reactive.UserService");
        
        // Simulate reactive operation logging
        String userId = "user123";
        
        reactiveLogger.info("Starting user retrieval for ID: {}", userId);
        
        // Simulate subscription
        reactiveLogger.debug("Subscribed to user retrieval stream");
        
        // Simulate data processing
        reactiveLogger.debug("Processing user data: name={}, email={}", "John Doe", "john@example.com");
        
        // Simulate completion
        reactiveLogger.info("Successfully retrieved user: {}", userId);
        
        // Simulate error scenario
        try {
            throw new IllegalArgumentException("Invalid user ID format");
        } catch (Exception e) {
            reactiveLogger.error("Error in reactive stream for user: {}", userId, e);
        }
    }
}
