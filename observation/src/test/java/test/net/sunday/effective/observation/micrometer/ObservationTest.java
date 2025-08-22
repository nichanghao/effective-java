package test.net.sunday.effective.observation.micrometer;

import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @see <a href="https://docs.micrometer.io/micrometer/reference/observation/introduction.html">micrometer</a>
 */
@Slf4j
public class ObservationTest {

    /**
     * observation 注册表
     */
    private static final ObservationRegistry registry = ObservationRegistry.create();

    @Test
    void testObservationBasic() {
        setObservationHandler();

        // 手动开启一个观测（Observation）
        Observation.createNotStarted("user.service.call", registry)
                // 低基数标签
                .lowCardinalityKeyValue("service.type", "rest-api")
                // 高基数上下文信息
                .highCardinalityKeyValue("user.id", "123456")
                .observe(() -> {
                    // 被观测逻辑
                    log.info("Executing observed business logic...");

                    simulateCall();
                });
    }

    @Test
    void testObservationWithEvents() {
        setObservationHandler();

        Observation observation = Observation.start(
                "database.query",
                () -> new Observation.Context().put(String.class, "test"),
                registry);

        try (Observation.Scope ignored = observation.openScope()) {
            observation.event(Observation.Event.of("query.started"));

            // 模拟数据库操作
            simulateCall();

            observation.event(Observation.Event.of("query.completed"));
            observation.highCardinalityKeyValue("query.rows.returned", "100");
        } catch (Exception e) {
            observation.error(e);
            throw e;
        } finally {
            observation.stop();
        }
    }

    @Test
    void testObservationMetrics() {
        // 创建内存存储的 MeterRegistry
        SimpleMeterRegistry meterRegistry = new SimpleMeterRegistry();
        registry.observationConfig().observationHandler(new DefaultMeterObservationHandler(meterRegistry));

        testObservationWithEvents();
        testObservationWithEvents();

        Timer timer = meterRegistry.find("database.query").timer();
        assert timer != null;
        log.info("count: {}", timer.count());
        log.info("totalTime: {} ms", timer.totalTime(TimeUnit.MILLISECONDS));
        log.info("avgTime: {} ms", timer.mean(TimeUnit.MILLISECONDS));
    }

    private void setObservationHandler() {
        registry.observationConfig().observationHandler(new ObservationHandler<>() {
            @Override
            public boolean supportsContext(Observation.Context context) {
                return true;
            }

            @Override
            public void onStart(Observation.Context context) {
                log.info("[OBSERVATION START] {}", context.getName());
            }

            @Override
            public void onStop(Observation.Context context) {
                log.info("[OBSERVATION STOP] {}", context.getName());
            }

            @Override
            public void onError(Observation.Context context) {
                log.info("[OBSERVATION ERROR] {}", context.getName());
            }

            @Override
            public void onEvent(Observation.Event event, Observation.Context context) {
                log.info("[OBSERVATION EVENT] {}", context.getName());
            }

            @Override
            public void onScopeOpened(Observation.Context context) {
                log.info("[OBSERVATION SCOPE OPENED] {}", context.getName());
            }

            @Override
            public void onScopeClosed(Observation.Context context) {
                log.info("[OBSERVATION SCOPE CLOSED] {}", context.getName());
            }

            @Override
            public void onScopeReset(Observation.Context context) {
                log.info("[OBSERVATION SCOPE RESET] {}", context.getName());
            }
        });
    }


    private void simulateCall() {
        // 模拟调用
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
