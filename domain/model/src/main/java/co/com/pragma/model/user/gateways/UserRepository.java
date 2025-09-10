package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<User> getUsers();
    Mono<User> getUserByDocument(Long document);
    Mono<User> editUser(User user);
    Mono<User> saveUser(User user);
    Mono<Void> deleteUser(Long document);
}
