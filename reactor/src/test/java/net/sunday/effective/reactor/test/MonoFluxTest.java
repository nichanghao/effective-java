package net.sunday.effective.reactor.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

public class MonoFluxTest {

    /**
     * reactor 线程调度 - subscribeOn
     */
    @Test
    void testReactorSubscribeOn() {
        Flux.range(1, 2)
                .log("range")
                .map(i -> 10 + i)
                .log("map1")
                .map(i -> "value " + i)
                .log("map2")
                // 只有 subscribeOn时，整条链从源头到 subscribe(...) 都跑在 subscribeOn 指定的 Scheduler 上
                // 多次调用只有第一次生效
                .subscribeOn(Schedulers.newParallel("subscribe-scheduler", 4))
                .log("subscribeOn")
                .subscribe(data -> System.out.println("[" + Thread.currentThread().getName() + "] Data: " + data));
    }

    /**
     * reactor 线程调度 - publishOn
     */
    @Test
    void testReactorPublishOn() {
        Flux.range(1, 2)
                .log("range")
                .map(i -> 10 + i)
                .log("map1")
                .map(i -> "value " + i)
                .log("map2")
                // 只有 publishOn 时，上游（Flux.range 和中间在 publishOn 之前的操作）依旧在调用 subscribe() 的线程（通常是主线程）执行；
                // publishOn 之后的部分切到指定 Scheduler。
                // 多次调用会改变调度线程池
                .publishOn(Schedulers.newParallel("publish-scheduler", 4))
                .log("publishOn")
                .subscribe(data -> System.out.println("[" + Thread.currentThread().getName() + "] Data: " + data));
    }

    /**
     * reactor 线程调度 - publishOn + subscribeOn
     */
    @Test
    void testReactorSubscribeOnPublishOn() {

        Flux.range(1, 2)
                .log("range")
                // publishOn 之前的操作都在 subscribeOn 指定的 Scheduler 上执行
                .subscribeOn(Schedulers.newParallel("subscribe-scheduler", 4))
                .map(i -> 10 + i)
                .log("map1")
                // publishOn 之后的操作都在 publishOn 指定的 Scheduler 上执行
                .publishOn(Schedulers.newParallel("publish-scheduler", 4))
                .log("publishOn")
                .map(i -> "value " + i)
                .log("map2")
                .subscribe(
                        // onNext
                        data -> System.out.println("[" + Thread.currentThread().getName() + "] Data: " + data),
                        // onError
                        error -> System.out.println("[" + Thread.currentThread().getName() + "] Error: " + error),
                        // onComplete
                        () -> System.out.println("[" + Thread.currentThread().getName() + "] Completed"));

    }

    /**
     * 测试 reactor 上下文
     */
    @Test
    void testReactorContext() {

        Mono.deferContextual(ctx ->
                        Mono.just("Hello traceId => " + ctx.get("traceId")))
                .subscribeOn(Schedulers.newParallel("subscribe-scheduler", 4))
                .transformDeferredContextual((mono, ctx) ->
                        mono.map(s -> "transform traceId => " + ctx.get("traceId") + " | " + s))
                .contextWrite(Context.of("traceId", "123456"))
                // 执行时机：整个流 正常完成、出现错误、被取消时
                .doFinally(signalType -> System.out.println("Finally executed with signal: " + signalType))
                .subscribe(System.out::println);

    }


}
