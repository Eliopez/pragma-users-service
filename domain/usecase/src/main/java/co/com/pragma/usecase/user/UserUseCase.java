package co.com.pragma.usecase.user;

import co.com.pragma.model.common.Logger;
import co.com.pragma.model.common.LoggerFactory;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UserUseCase {
    private static final Logger logger = LoggerFactory.getLoggerFor(UserUseCase.class);
    private final UserRepository userRepository;

    public Flux<User> getUsers() {
        logger.info("Getting all users");
        return userRepository.getUsers()
                .doOnSubscribe(subscription -> logger.debug("Starting to fetch users"))
                .doOnNext(user -> logger.debug("Retrieved user with document: {}", user.getDocument()))
                .doOnComplete(() -> logger.info("Successfully retrieved all users"))
                .doOnError(error -> logger.error("Error retrieving users", error));
    }
    public Mono<User> getUserById(Long document) {
        logger.info("Getting user by document: {}", document);
        return userRepository.getUserByDocument(document)
                .doOnSubscribe(subscription -> logger.debug("Starting to fetch user with document: {}", document))
                .doOnNext(user -> logger.debug("Retrieved user: {} {}", user.getName(), user.getLastName()))
                .doOnSuccess(user -> {
                    if (user != null) {
                        logger.info("Successfully retrieved user with document: {}", document);
                    } else {
                        logger.warn("User not found with document: {}", document);
                    }
                })
                .doOnError(error -> logger.error("Error retrieving user with document: {}", document, error));
    }

    public Mono<User> editUser(User user) {
        logger.info("Editing user with document: {}", user.getDocument());
        return userRepository.editUser(user)
                .doOnSubscribe(subscription -> logger.debug("Starting to edit user: {} {}", user.getName(), user.getLastName()))
                .doOnNext(editedUser -> logger.debug("User edited successfully: {}", editedUser.getDocument()))
                .doOnSuccess(editedUser -> logger.info("Successfully edited user with document: {}", user.getDocument()))
                .doOnError(error -> logger.error("Error editing user with document: {}", user.getDocument(), error));
    }
    public Mono<User> saveUser(User user) {
        logger.info("Saving new user: {} {}", user.getName(), user.getLastName());
        return userRepository.saveUser(user)
                .doOnSubscribe(subscription -> logger.debug("Starting to save user: {} {}", user.getName(), user.getLastName()))
                .doOnNext(savedUser -> logger.debug("User saved with document: {}", savedUser.getDocument()))
                .doOnSuccess(savedUser -> logger.info("Successfully saved user with document: {}", savedUser.getDocument()))
                .doOnError(error -> logger.error("Error saving user: {} {}", user.getName(), user.getLastName(), error));
    }

    public Mono<Void> deleteUser(Long document) {
        logger.info("Deleting user with document: {}", document);
        return userRepository.deleteUser(document)
                .doOnSubscribe(subscription -> logger.debug("Starting to delete user with document: {}", document))
                .doOnSuccess(result -> logger.info("Successfully deleted user with document: {}", document))
                .doOnError(error -> logger.error("Error deleting user with document: {}", document, error));
    }
}
