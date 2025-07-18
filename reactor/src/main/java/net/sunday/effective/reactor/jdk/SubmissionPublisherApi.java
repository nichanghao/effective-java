package net.sunday.effective.reactor.jdk;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

class SubmissionPublisherApi {

    public static void main(String[] args) throws InterruptedException {

        try (SubmissionPublisher<String> publisher = new SubmissionPublisher<>()) {

            Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {

                private Flow.Subscription subscription;

                @Override
                public void onSubscribe(Flow.Subscription subscription) {
                    System.out.println(Thread.currentThread().getName() + " onSubscribe");
                    this.subscription = subscription;
                    // request one item to start the flow
                    subscription.request(1);
                }

                @Override
                public void onNext(String item) {
                    System.out.println(Thread.currentThread().getName() + " onNext: " + item);
                    subscription.request(1);
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println(Thread.currentThread().getName() + " onError: " + throwable.getMessage());
                }

                @Override
                public void onComplete() {
                    System.out.println(Thread.currentThread().getName() + " onComplete");
                }
            };

            // 订阅关系
            publisher.subscribe(subscriber);

            for (int i = 0; i < 10; i++) {
                publisher.submit("item-" + i);
            }
        }


        Thread.currentThread().join();

    }
}
