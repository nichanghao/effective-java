package net.sunday.effective.java.core.reactive;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * jdk 响应式流 api
 */
class FlowApi {


    public static void main(String[] args) throws InterruptedException {

        // 1. create a subscriber
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("onSubscribe 订阅成功！");
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                subscription.request(1);
                System.out.println("onNext 接收到数据：" + item);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError 出现异常：" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete 完成！");
            }
        };


        // 3. create a publisher
        class MyPublisher implements Flow.Publisher<Integer> {

            private static final ExecutorService executor = ForkJoinPool.commonPool(); // daemon-based

            // 2. create a subscription
            static class MySubscription implements Flow.Subscription {

                private final Flow.Subscriber<? super Integer> subscriber;

                MySubscription(Flow.Subscriber<? super Integer> subscriber) {
                    this.subscriber = subscriber;
                }

                private final BlockingQueue<Integer> buffers = new ArrayBlockingQueue<>(32);

                @SneakyThrows
                @Override
                public void request(long n) {
                    while (n-- >= 0) {
                        executor.submit(() -> {

                            Integer item;
                            if ((item = buffers.poll()) != null) {
                                subscriber.onNext(item);
                            }
                        });
                    }
                }

                @Override
                public void cancel() {
                    executor.shutdownNow();
                }

                public void add(Integer item) {
                    buffers.offer(item);
                }
            }


            private MySubscription subscription;

            @Override
            public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
                subscription = new MySubscription(subscriber);
                // 发布订阅事件
                subscriber.onSubscribe(subscription);
            }

            /**
             * submit an item to the publisher
             */
            public void submit(Integer item) {
                subscription.add(item);
            }

            public void close() {
                subscription.cancel();
            }

        }
        MyPublisher publisher = new MyPublisher();


        // 4. subscribe to the publisher
        publisher.subscribe(subscriber);

        // 5. submit some items to the publisher
        for (int i = 0; i < 10; i++) {
            publisher.submit(i);
        }

        Thread.currentThread().join(3000);

        publisher.close();


    }
}
