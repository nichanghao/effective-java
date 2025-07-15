package net.sunday.effective.dubbo.service;

import org.apache.dubbo.remoting.http12.HttpMethods;
import org.apache.dubbo.remoting.http12.rest.Mapping;
import org.apache.dubbo.remoting.http12.rest.Param;
import org.apache.dubbo.remoting.http12.rest.ParamType;

import java.util.concurrent.CompletableFuture;

@Mapping(path = "/demoService")
public interface DemoService {

    /*
     * curl -X GET http://127.0.0.1:8000/demoService/sayHello?name=12
     */
    @Mapping(path = "/sayHello", method = HttpMethods.GET)
    String sayHello(@Param(value = "name", type = ParamType.Param) String name);

    /**
     * Asynchronous method example.
     * <p>
     * This method returns a {@link CompletableFuture<String>} to demonstrate Dubbo's asynchronous invocation capability.
     * Developers are recommended to refer to the official sample project for complete usage:
     * <a href="https://github.com/lqscript/dubbo-samples/tree/master/2-advanced/dubbo-samples-async/dubbo-samples-async-original-future">
     * Dubbo Async Invocation Sample</a>
     * </p>
     *
     * @param name Input name parameter
     * @return Asynchronous result wrapped in CompletableFuture
     */
    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }
}
