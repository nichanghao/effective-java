package net.sunday.effective.spring.webflux;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.net.URI;

public class WebfluxApi {


    public static void main(String[] args) {

        HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(buildHandler1())
                .bindNow()
                .onDispose()
                .block();

    }

    private static ReactorHttpHandlerAdapter buildHandler1() {

        HttpHandler handler = (request, response) -> {

            URI uri = request.getURI();
            System.out.println("[" + Thread.currentThread().getName() + "] Request: " + uri);

            DataBufferFactory factory = response.bufferFactory();

            return response.writeWith(Mono.just(factory.wrap("Hello WebFlux!".getBytes())));
        };

        return new ReactorHttpHandlerAdapter(handler);

    }


    private static ReactorHttpHandlerAdapter buildHandler2() {
        return new ReactorHttpHandlerAdapter(RouterFunctions.toHttpHandler(RouterFunctions.route()
                // curl http://localhost:8080/hello
                .GET("/hello", request ->
                        ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                                .body(BodyInserters.fromValue("Hello WebFlux!")))

                // curl -X POST -d "Hello World" http://localhost:8080/echo
                .POST("/echo", request ->
                        request.bodyToMono(String.class).flatMap(body ->
                                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue("Echo: " + body)))

                // curl http://localhost:8080/user
                .GET("user", request ->
                        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new User(1, "Alice")))
                .build()
        ));
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
