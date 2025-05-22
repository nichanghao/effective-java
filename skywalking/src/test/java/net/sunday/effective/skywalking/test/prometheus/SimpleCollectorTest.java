package net.sunday.effective.skywalking.test.prometheus;

import io.prometheus.client.Counter;
import io.prometheus.client.SimpleCollector;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * test class for {@link SimpleCollector}
 */

public class SimpleCollectorTest {

    @Test
    public void testCounter() {

        List<String[]> labelNames = List.of(new String[]{"GET", "/api/user"}, new String[]{"POST", "/api/product"});

        Counter request = Counter.build().name("http_requests_total")
                .help("Total HTTP requests")
                .labelNames("method", "path").create();

        for (int i = 0; i < 100; i++) {
            request.labels(labelNames.get(i % 2)).inc();
        }

        assertEquals(50, request.labels(labelNames.getFirst()).get());
    }
}
