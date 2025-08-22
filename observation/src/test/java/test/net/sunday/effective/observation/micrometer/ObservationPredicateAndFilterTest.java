package test.net.sunday.effective.observation.micrometer;

import io.micrometer.common.KeyValue;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class ObservationPredicateAndFilterTest {

    @Test
    void test() {
        // Example using a metrics handler - we need a MeterRegistry
        MeterRegistry meterRegistry = new SimpleMeterRegistry();

        // Create an ObservationRegistry
        ObservationRegistry registry = ObservationRegistry.create();
        // Add predicates and filter to the registry
        registry.observationConfig()
                // ObservationPredicate can decide whether an observation should be ignored or not
                .observationPredicate((observationName, context) -> {
                    // Creates a noop observation if observation name is of given name
                    if ("to.ignore".equals(observationName)) {
                        // Will be ignored
                        return false;
                    }
                    if (context instanceof MyContext) {
                        // For the custom context will ignore a user with a given name
                        return !"user to ignore".equals(((MyContext) context).getUsername());
                    }
                    // Will proceed for all other types of context
                    return true;
                })
                // ObservationFilter can modify a context
                .observationFilter(context -> {
                    // We're adding a low cardinality key to all contexts
                    context.addLowCardinalityKeyValue(KeyValue.of("low.cardinality.key", "low cardinality value"));
                    if (context instanceof MyContext myContext) {
                        // We're mutating a specific type of a context
                        myContext.setUsername("some username");
                        // We want to remove a high cardinality key value
                        return myContext.removeHighCardinalityKeyValue("high.cardinality.key.to.ignore");
                    }
                    return context;
                })
                // Example of using metrics
                .observationHandler(new DefaultMeterObservationHandler(meterRegistry));

        // Observation will be ignored because of the name
        then(Observation.start("to.ignore", () -> new MyContext("don't ignore"), registry)).isSameAs(Observation.NOOP);
        // Observation will be ignored because of the entries in MyContext
        then(Observation.start("not.to.ignore", () -> new MyContext("user to ignore"), registry))
                .isSameAs(Observation.NOOP);

        // Observation will not be ignored...
        MyContext myContext = new MyContext("user not to ignore");
        myContext.addHighCardinalityKeyValue(KeyValue.of("high.cardinality.key.to.ignore", "some value"));
        Observation.createNotStarted("not.to.ignore", () -> myContext, registry).observe(() -> {
        });
        // and will have the context mutated
        then(myContext.getLowCardinalityKeyValue("low.cardinality.key").getValue()).isEqualTo("low cardinality value");
        then(myContext.getUsername()).isEqualTo("some username");
        then(myContext.getHighCardinalityKeyValues())
                .doesNotContain(KeyValue.of("high.cardinality.key.to.ignore", "some value"));
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    static class MyContext extends Observation.Context {
        private String username;
    }

}
