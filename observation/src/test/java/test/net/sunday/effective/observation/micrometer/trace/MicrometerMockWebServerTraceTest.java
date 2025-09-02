package test.net.sunday.effective.observation.micrometer.trace;

import brave.Tracing;
import brave.propagation.B3Propagation;
import brave.propagation.CurrentTraceContext;
import brave.propagation.ThreadLocalCurrentTraceContext;
import brave.sampler.Sampler;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.brave.bridge.BraveBaggageManager;
import io.micrometer.tracing.brave.bridge.BraveCurrentTraceContext;
import io.micrometer.tracing.brave.bridge.BravePropagator;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import io.micrometer.tracing.handler.DefaultTracingObservationHandler;
import io.micrometer.tracing.handler.PropagatingSenderTracingObservationHandler;
import io.micrometer.tracing.propagation.Propagator;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public class MicrometerMockWebServerTraceTest {

    ObservationRegistry registry;
    BraveTracer micrometerTracer;

    @BeforeEach
    void setup() {
        // 1) create Brave's CurrentTraceContext (thread-local implementation)
        CurrentTraceContext braveCurrent = ThreadLocalCurrentTraceContext.newBuilder().build();

        // 2) build Brave Tracing (use B3 here; enable 128-bit ids if you prefer W3C)
        Tracing braveTracing = Tracing.newBuilder()
                .currentTraceContext(braveCurrent)
                .propagationFactory(B3Propagation.FACTORY) // or configure W3C if you want
                .sampler(Sampler.ALWAYS_SAMPLE)
                .traceId128Bit(true) // useful if you want 32-hex trace ids
                .build();

        // 3) wrap Brave's CurrentTraceContext into Micrometer's bridge CurrentTraceContext
        BraveCurrentTraceContext micrometerCurrent = new BraveCurrentTraceContext(braveCurrent);

        // 4) create Micrometer BraveTracer (note the wrapped current context)
        micrometerTracer = new BraveTracer(
                braveTracing.tracer(),      // brave.Tracer
                micrometerCurrent,         // io.micrometer.tracing.CurrentTraceContext (bridge)
                new BraveBaggageManager()  // baggage manager (bridge impl)
        );

        // 5) create a Propagator from Brave tracing and register handlers on ObservationRegistry
        Propagator propagator = new BravePropagator(braveTracing);

        registry = ObservationRegistry.create();
        // register propagation handler first (so it can inject headers), then default tracing handler
        registry.observationConfig()
                .observationHandler(new PropagatingSenderTracingObservationHandler<>(micrometerTracer, propagator))
                .observationHandler(new DefaultTracingObservationHandler(micrometerTracer));

    }

    @Test
    void shouldPropagateTraceIdThroughHttpHeaders() throws Exception {
        MockWebServer server = new MockWebServer();
        server.start();
        server.enqueue(new MockResponse().setBody("ok"));

        // 记录期望的 traceId（来自当前活跃 span）
        AtomicReference<String> expectedTraceId = new AtomicReference<>();

        // Setter：把 header 写到 HttpRequest.Builder
        var senderContext = new io.micrometer.observation.transport.SenderContext<HttpRequest.Builder>(
                (builder, key, value) -> {
                    if (builder != null) builder.header(key, value);
                });

        senderContext.setCarrier(HttpRequest.newBuilder()
                .uri(new URI(server.url("/get").toString()))
                .GET());

        Observation obs = Observation
                .createNotStarted("http.client.call", () -> senderContext, registry)
                .contextualName("http GET /get");

        obs.observe(() -> {
            // 现在 observation 已经 start 并且 in-scope，能拿到当前 span
            Span span = micrometerTracer.currentSpan();
            if (span != null) {
                expectedTraceId.set(span.context().traceId());
            }

            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpResponse<String> resp;
                try {
                    resp = client.send(
                            Objects.requireNonNull(senderContext.getCarrier()).build(),
                            HttpResponse.BodyHandlers.ofString());
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                assertThat(resp.statusCode()).isEqualTo(200);
            }

            return null;
        });

        // 验证注入到下游请求的头
        RecordedRequest recorded = server.takeRequest();
        String traceparent = recorded.getHeader("traceparent");
        String b3 = recorded.getHeader("b3");
        String xB3TraceId = recorded.getHeader("X-B3-TraceId");

        String actualTraceId = null;
        if (traceparent != null) {
            // traceparent = 00-<traceId>-<spanId>-<flags>
            String[] parts = traceparent.split("-");
            if (parts.length >= 3) actualTraceId = parts[1];
        } else if (b3 != null) {
            // b3 单头格式最前面是 traceId
            actualTraceId = b3.split("-")[0];
        } else if (xB3TraceId != null) {
            actualTraceId = xB3TraceId;
        }

        assertThat(expectedTraceId.get()).isNotBlank();
        assertThat(actualTraceId).isNotBlank();
        assertThat(expectedTraceId.get()).isEqualTo(actualTraceId);

        server.close();
        server.shutdown();
    }

}

