# Logging System Documentation

## Overview

This project implements a comprehensive logging system using the **Singleton pattern** following clean architecture principles. The logging system provides structured logging with multiple log levels and is designed to be easily configurable and maintainable.

## Architecture

### Clean Architecture Layers

1. **Domain Layer** (`domain/model`)
   - `Logger` interface - Defines the contract for logging operations
   - `LogLevel` enum - Defines available log levels (TRACE, DEBUG, INFO, WARN, ERROR)

2. **Infrastructure Layer** (`infrastructure/helpers`)
   - `LoggerManager` - Singleton manager for logger instances (thread-safe)
   - `Slf4jLoggerAdapter` - Concrete implementation using SLF4J
   - `LoggerFactory` - Factory class providing static API for logger creation

3. **Application Layer** (`applications/app-service`)
   - `LoggingConfig` - Spring configuration for logging system
   - `application.yaml` - Logging configuration properties
   - `logback-spring.xml` - Logback configuration

## Singleton Pattern Implementation

The `LoggerManager` class implements the Singleton pattern using:
- **Double-checked locking** for thread safety
- **Volatile keyword** to ensure visibility across threads
- **ConcurrentHashMap** for thread-safe logger caching

```java
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
```

## Usage Examples

### Basic Usage

```java
import co.com.pragma.logger.LoggerFactory;
import co.com.pragma.model.common.Logger;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    public void processUser(User user) {
        logger.info("Processing user: {} {}", user.getName(), user.getLastName());
        
        try {
            // Business logic here
            logger.debug("User processing completed successfully");
        } catch (Exception e) {
            logger.error("Error processing user: {}", user.getDocument(), e);
        }
    }
}
```

### Reactive Programming Integration

```java
public Mono<User> saveUser(User user) {
    logger.info("Saving new user: {} {}", user.getName(), user.getLastName());
    return userRepository.saveUser(user)
            .doOnSubscribe(subscription -> logger.debug("Starting to save user"))
            .doOnSuccess(savedUser -> logger.info("Successfully saved user with document: {}", savedUser.getDocument()))
            .doOnError(error -> logger.error("Error saving user", error));
}
```

## Log Levels

| Level | Description | Usage |
|-------|-------------|-------|
| TRACE | Most detailed information | Fine-grained debugging |
| DEBUG | Debugging information | Development and troubleshooting |
| INFO  | General information | Important application events |
| WARN  | Warning messages | Potential issues |
| ERROR | Error messages | Application errors and exceptions |

## Configuration

### Application Properties (`application.yaml`)

```yaml
logging:
  level:
    root: INFO
    co.com.pragma: INFO
    org.springframework: WARN
    reactor.netty: WARN
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/pragma-users-service.log
    max-size: 10MB
    max-history: 30
```

### Logback Configuration (`logback-spring.xml`)

The system uses Logback with:
- **Console Appender** for development
- **Rolling File Appender** with size and time-based rolling
- **Async Appender** for better performance
- **Profile-specific configurations** (dev, prod)

### Environment-Specific Logging

- **Development**: DEBUG level enabled
- **Production**: WARN level for better performance

## Features

### Thread Safety
- Singleton implementation with double-checked locking
- ConcurrentHashMap for logger caching
- Thread-safe logger instances

### Performance Optimizations
- Lazy logger initialization
- Async file appender
- Log level checking before message formatting

### Configurability
- Spring Boot integration
- Profile-specific configurations
- Runtime log level adjustment

### Clean Architecture Compliance
- Domain layer defines contracts
- Infrastructure layer provides implementations
- Application layer handles configuration
- No framework dependencies in domain layer

## Best Practices

1. **Use static final logger instances** in classes
2. **Check log levels** before expensive operations
3. **Use parameterized messages** for better performance
4. **Log exceptions** with appropriate context
5. **Use appropriate log levels** for different scenarios

## Dependencies

The logging system requires:
- SLF4J API
- Logback Classic
- Spring Boot Starter Logging

These are automatically included in the project's build configuration.
