package net.sunday.effective.dubbo.service;

import java.util.concurrent.CompletableFuture;

public interface DemoService {

    String sayHello(String name);

    /**
     * Asynchronous method example.
     * <p>
     * This method returns a {@link CompletableFuture<String>} to demonstrate Dubbo's asynchronous invocation capability.
     * Developers are recommended to refer to the official sample project for complete usage:
     * <a href="https://github.com/lqscript/dubbo-samples/tree/master/2-advanced/dubbo-samples-async/dubbo-samples-async-original-future">
     *     Dubbo Async Invocation Sample</a>
     * </p>
     *
     * @param name Input name parameter
     * @return Asynchronous result wrapped in CompletableFuture
     */
    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }
}
