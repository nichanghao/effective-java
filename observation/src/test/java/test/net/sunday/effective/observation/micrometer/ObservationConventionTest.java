package test.net.sunday.effective.observation.micrometer;

import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import io.micrometer.common.docs.KeyName;
import io.micrometer.common.lang.Nullable;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.search.RequiredSearch;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.*;
import io.micrometer.observation.docs.ObservationDocumentation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static test.net.sunday.effective.observation.micrometer.ObservationConventionTest.TaxObservationDocumentation.TaxHighCardinalityKeyNames.USER_ID;
import static test.net.sunday.effective.observation.micrometer.ObservationConventionTest.TaxObservationDocumentation.TaxLowCardinalityKeyNames.TAX_TYPE;


/**
 * ObservationConvention 是 Micrometer 可观测性模型中的策略抽象接口，其核心作用是为观测行为（Observation）提供统一的上下文转换规则
 * convention 优先级：自定义 > 全局 > 默认
 */

public class ObservationConventionTest {


    @Test
    void test() {
        // Registry setup
        ObservationRegistry observationRegistry = ObservationRegistry.create();
        // add metrics
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        observationRegistry.observationConfig().observationHandler(new DefaultMeterObservationHandler(registry));
        observationRegistry.observationConfig().observationConvention(new GlobalTaxObservationConvention());
        // This will be applied to all observations
        observationRegistry.observationConfig().observationFilter(new CloudObservationFilter());

        // In this case we're overriding the default convention by passing the custom one
        TaxCalculator taxCalculator = new TaxCalculator(observationRegistry, new CustomTaxObservationConvention());
        // run the logic you want to observe
        taxCalculator.calculateTax("INCOME_TAX", "1234567890");

        Assertions.assertNotNull(registry.get("tax.calculate").timer());
    }


    /**
     * A dedicated {@link Observation.Context} used for taxing.
     */
    static class TaxContext extends Observation.Context {

        private final String taxType;

        private final String userId;

        TaxContext(String taxType, String userId) {
            this.taxType = taxType;
            this.userId = userId;
        }

        String getTaxType() {
            return taxType;
        }

        String getUserId() {
            return userId;
        }

    }

    /**
     * An example of an {@link ObservationFilter} that will add the key-values to all
     * observations.
     */
    static class CloudObservationFilter implements ObservationFilter {

        @Override
        public Observation.Context map(Observation.Context context) {
            return context.addLowCardinalityKeyValue(KeyValue.of("cloud.zone", CloudUtils.getZone()))
                    .addHighCardinalityKeyValue(KeyValue.of("cloud.instance.id", CloudUtils.getCloudInstanceId()));
        }

    }

    /**
     * An example of an {@link ObservationConvention} that renames the tax related
     * observations and adds cloud related tags to all contexts. When registered via the
     * `ObservationRegistry#observationConfig#observationConvention` will override the
     * default {@link TaxObservationConvention}. If the user provides a custom
     * implementation of the {@link TaxObservationConvention} and passes it to the
     * instrumentation, the custom implementation wins.
     * <p>
     * In other words
     * <p>
     * 1) Custom {@link ObservationConvention} has precedence 2) If no custom convention
     * was passed and there's a matching {@link GlobalObservationConvention} it will be
     * picked 3) If there's no custom, nor matching global convention, the default
     * {@link ObservationConvention} will be used
     * <p>
     * If you need to add some key-values regardless of the used
     * {@link ObservationConvention} you should use an {@link ObservationFilter}.
     */
    static class GlobalTaxObservationConvention implements GlobalObservationConvention<TaxContext> {

        // this will be applicable for all tax contexts - it will rename all the tax
        // contexts
        @Override
        public boolean supportsContext(Observation.Context context) {
            return context instanceof TaxContext;
        }

        @Override
        public String getName() {
            return "global.tax.calculate";
        }

    }

    // Interface for an ObservationConvention related to calculating Tax
    interface TaxObservationConvention extends ObservationConvention<TaxContext> {

        @Override
        default boolean supportsContext(Observation.Context context) {
            return context instanceof TaxContext;
        }

    }

    /**
     * Default convention of tags related to calculating tax. If no user one or global
     * convention will be provided then this one will be picked.
     */
    static class DefaultTaxObservationConvention implements TaxObservationConvention {

        @Override
        public KeyValues getLowCardinalityKeyValues(TaxContext context) {
            return KeyValues.of(TAX_TYPE.withValue(context.getTaxType()));
        }

        @Override
        public KeyValues getHighCardinalityKeyValues(TaxContext context) {
            return KeyValues.of(USER_ID.withValue(context.getUserId()));
        }

        @Override
        public String getName() {
            return "default.tax.name";
        }

    }

    /**
     * If micrometer-docs-generator is used, we will automatically generate documentation
     * for your observations. Check this URL
     * https://github.com/micrometer-metrics/micrometer-docs-generator#documentation for
     * setup example and read the {@link ObservationDocumentation} javadocs.
     */
    enum TaxObservationDocumentation implements ObservationDocumentation {

        CALCULATE {
            @Override
            public Class<? extends ObservationConvention<? extends Observation.Context>> getDefaultConvention() {
                return DefaultTaxObservationConvention.class;
            }

            @Override
            public String getContextualName() {
                return "calculate tax";
            }

            @Override
            public String getPrefix() {
                return "tax";
            }

            @Override
            public KeyName[] getLowCardinalityKeyNames() {
                return TaxLowCardinalityKeyNames.values();
            }

            @Override
            public KeyName[] getHighCardinalityKeyNames() {
                return TaxHighCardinalityKeyNames.values();
            }
        };

        enum TaxLowCardinalityKeyNames implements KeyName {

            TAX_TYPE {
                @Override
                public String asString() {
                    return "tax.type";
                }
            }

        }

        enum TaxHighCardinalityKeyNames implements KeyName {

            USER_ID {
                @Override
                public String asString() {
                    return "tax.user.id";
                }
            }

        }

    }

    /**
     * Our business logic that we want to observe.
     */
    static class TaxCalculator {

        private final ObservationRegistry observationRegistry;

        // If the user wants to override the default they can override this. Otherwise,
        // it will be {@code null}.
        @Nullable
        private final TaxObservationConvention observationConvention;

        TaxCalculator(ObservationRegistry observationRegistry,
                      @Nullable TaxObservationConvention observationConvention) {
            this.observationRegistry = observationRegistry;
            this.observationConvention = observationConvention;
        }

        void calculateTax(String taxType, String userId) {
            // Create a new context
            TaxContext taxContext = new TaxContext(taxType, userId);
            // Create a new observation
            TaxObservationDocumentation.CALCULATE
                    .observation(this.observationConvention, new DefaultTaxObservationConvention(), () -> taxContext,
                            this.observationRegistry)
                    // Run the actual logic you want to observe
                    .observe(this::calculateInterest);
        }

        private void calculateInterest() {
            // do some work
        }

    }

    /**
     * Example of user changing the default conventions.
     */
    static class CustomTaxObservationConvention extends DefaultTaxObservationConvention {

        @Override
        public KeyValues getLowCardinalityKeyValues(TaxContext context) {
            return super.getLowCardinalityKeyValues(context)
                    .and(KeyValue.of("additional.low.cardinality.tag", "value"));
        }

        @Override
        public KeyValues getHighCardinalityKeyValues(TaxContext context) {
            return KeyValues.of("this.would.override.the.default.high.cardinality.tags", "value");
        }

        @Override
        public String getName() {
            return "tax.calculate";
        }

    }

    /**
     * A utility class to set cloud related arguments.
     */
    static class CloudUtils {

        static String getZone() {
            return "...";
        }

        static String getCloudInstanceId() {
            return "...";
        }

    }

}
