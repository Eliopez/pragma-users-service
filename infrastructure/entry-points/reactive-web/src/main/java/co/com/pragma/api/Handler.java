package co.com.pragma.api;

import co.com.pragma.api.dto.UserDTO;
import co.com.pragma.api.dto.SaveUserDTO;
import co.com.pragma.api.dto.UpdateUserDTO;
import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.UserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Handler {
    private final UserUseCase userUseCase;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SaveUserDTO.class)
                .map(task -> objectMapper.convertValue(task, User.class))
                .flatMap(userUseCase::saveUser)
                .flatMap(savedTask -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTask));
    }

    public Mono<ServerResponse> listenGetAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userUseCase.getUsers()
                        .map(user -> UserDTO.builder()
                                .name(user.getName())
                                .lastName(user.getLastName())
                                .document(user.getDocument())
                                .email(user.getEmail())
                                .salary(user.getSalary())
                                .birthDate(LocalDate.parse(user.getBirthDate().toString()))
                                .build()),
                        UserDTO.class);
    }
    public Mono<ServerResponse> listenUpdateUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpdateUserDTO.class)
                .map(user -> objectMapper.convertValue(user, User.class))
                .flatMap(userUseCase::editUser)
                .flatMap(savedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUser));
    }



    public Mono<ServerResponse> listenGetTaskById(ServerRequest serverRequest) {
        return Mono.fromCallable(() -> serverRequest.pathVariable("id"))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .map(Long::getLong)
                .flatMap(userUseCase::getUserById)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listenDeleteUser(ServerRequest serverRequest) {
        return Mono.fromCallable(() -> serverRequest.pathVariable("id"))
                .map(String::trim)
                .filter(item -> !item.isBlank())
                .map(Long::getLong)
                .flatMap(id -> userUseCase.deleteUser(id)
                        .then(ServerResponse.noContent().build())
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
