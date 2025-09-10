package co.com.pragma.api;

import co.com.pragma.api.config.UserPath;
import co.com.pragma.api.utility.UserUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class RouterRest {


    private final UserPath userPath;
    private final Handler userHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return SpringdocRouteBuilder.route()
                .GET(userPath.getUsers(), userHandler::listenGetAllUsers, UserUtility::getAllUsers)
                .POST(userPath.getUsers(), userHandler::listenSaveUser, UserUtility::saveUser)
                .PUT(userPath.getUsers(), userHandler::listenUpdateUser, UserUtility::updateUser)
                .DELETE(userPath.getUsersById(), userHandler::listenDeleteUser, UserUtility::deleteUser)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> undocumentedRouterFunction() {
        return RouterFunctions
                .route()
                .GET(userPath.getUsersById(), userHandler::listenGetTaskById)
                .build();
    }
}
