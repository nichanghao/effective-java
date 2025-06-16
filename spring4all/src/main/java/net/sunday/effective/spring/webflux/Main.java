package net.sunday.effective.spring.webflux;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

public class Main {
    public static void main(String[] args) {

        HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(new ReactorHttpHandlerAdapter(RouterFunctions.toHttpHandler(RouterFunctions.route()
                        // curl http://localhost:8080/hello
                        .GET("/hello", request ->
                                ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("Hello WebFlux!")))

                        // curl -X POST -d "Hello World" http://localhost:8080/echo
                        .POST("/echo", request ->
                                request.bodyToMono(String.class).flatMap(body ->
                                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue("Echo: " + body)))

                        // curl http://localhost:8080/user
                        .GET("user", request ->
                                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(new User(1, "Alice")))
                        .build()
                )))
                .bindNow()
                .onDispose()
                .block();

    }

    // 实体类
    @Getter
    @Setter
    private static class User {

        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

    }
}