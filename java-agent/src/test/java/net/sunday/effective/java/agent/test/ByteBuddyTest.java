package net.sunday.effective.java.agent.test;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassInjector;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.CSAuxiliaryTypeNamingStrategy;
import net.bytebuddy.implementation.CSImplementationContextFactory;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import net.sunday.effective.java.agent.entity.Person;
import net.sunday.effective.java.agent.interceptor.InstMethodInterceptor;
import net.sunday.effective.java.agent.util.AgentUtils;
import org.junit.jupiter.api.Test;

/**
 * 字节码操作技术 ByteBuddy
 *
 * @see <a href="https://bytebuddy.net/#/tutorial">ByteBuddy Tutorial</a>
 */

public class ByteBuddyTest {

    @Test
    public void testByteBuddy() throws Exception {
        // 通过 ByteBuddy 生成一个 Object 的子类
        DynamicType.Unloaded<Object> unloaded = new ByteBuddy()
            .subclass(Object.class)
            // 指定类名
            .name("net.sunday.effective.java.agent.test.CustomByteBuddy")
            // 定义方法
            .defineMethod("sayHello", String.class, Visibility.PUBLIC)
            // 定义方法的返回值
            .intercept(FixedValue.value("Hello, World!"))
            // 生成类
            .make();

        unloaded.saveIn(Paths.get(System.getProperty("user.dir"), "./build/generated").toFile());
        // 加载动态类型
        Class<?> loaded = unloaded.load(ByteBuddyTest.class.getClassLoader())
                                  .getLoaded();

        // 创建实例
        Object instance = loaded.getDeclaredConstructor().newInstance();

        // 调用方法
        Method method = loaded.getMethod("sayHello");
        String result = (String) method.invoke(instance);

        System.out.println("[execute CustomByteBuddy's sayHello()]: " + result);

        unloaded.close();

    }

    @Test
    void testAgentBuilder() {

        Instrumentation instrumentation = ByteBuddyAgent.install();

        newAgentBuilder()
            .type(ElementMatchers.named("net.sunday.effective.java.agent.entity.Person"))
            .transform(new AgentBuilder.Transformer() {
                @Override
                public DynamicType.Builder<?> transform(final DynamicType.Builder<?> builder,
                                                        final TypeDescription typeDescription,
                                                        final ClassLoader classLoader,
                                                        final JavaModule module,
                                                        final ProtectionDomain protectionDomain) {

                    return builder.method(ElementMatchers.named("sayOK"))
                                  .intercept(MethodDelegation.withDefaultConfiguration()
                                                             .to(new InstMethodInterceptor()));
                }
            })
            .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
            .installOn(instrumentation);

        Person person = new Person();
        person.sayOK();

    }

    @Test
    @SneakyThrows
    void testAgentBuilderWithBootstrap() {
        String[] bootstrapClasses = {
            "net.bytebuddy.implementation.bind.annotation.RuntimeType",
            "net.bytebuddy.implementation.bind.annotation.This",
            "net.bytebuddy.implementation.bind.annotation.AllArguments",
            "net.bytebuddy.implementation.bind.annotation.AllArguments$Assignment",
            "net.bytebuddy.implementation.bind.annotation.SuperCall",
            "net.bytebuddy.implementation.bind.annotation.Origin",
            "net.bytebuddy.implementation.bind.annotation.Morph"
        };

        Map<String, byte[]> classesTypeMap = new HashMap<>();
        for (final String edgeClass : bootstrapClasses) {
            classesTypeMap.put(edgeClass, AgentUtils.getClassBytes(edgeClass));
        }

        AgentUtils.generateInternalClass(
            "net.sunday.effective.java.agent.interceptor.InstMethodInterceptor", classesTypeMap);

        Instrumentation instrumentation = ByteBuddyAgent.install();

        /*
         * Inject the classes into bootstrap class loader by using Unsafe Strategy.
         * ByteBuddy adapts the sun.misc.Unsafe and jdk.internal.misc.Unsafe automatically.
         */
        AgentBuilder agentBuilder = newAgentBuilder();
        ClassInjector.UsingUnsafe.Factory factory = ClassInjector.UsingUnsafe.Factory.resolve(instrumentation);
        factory.make(null, null).injectRaw(classesTypeMap);
        agentBuilder = agentBuilder.with(new AgentBuilder.InjectionStrategy.UsingUnsafe.OfFactory(factory));

        for (String edgeClass : classesTypeMap.keySet()) {
            agentBuilder = agentBuilder.assureReadEdgeFromAndTo(instrumentation, Class.forName(edgeClass));
        }

        agentBuilder.type(ElementMatchers.named("java.util.concurrent.ThreadPerTaskExecutor$TaskRunner"))

                    .transform(new AgentBuilder.Transformer() {
                        @Override
                        public DynamicType.Builder<?> transform(final DynamicType.Builder<?> builder,
                                                                final TypeDescription typeDescription,
                                                                final ClassLoader classLoader,
                                                                final JavaModule module,
                                                                final ProtectionDomain protectionDomain) {

                            try {
                                return builder.method(ElementMatchers.named("run"))
                                              .intercept(MethodDelegation.withDefaultConfiguration()
                                                                         .to(Class.forName(
                                                                             "net.sunday.effective.java.agent.interceptor.InstMethodInterceptor_Internal")));
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    })
                    .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                    .installOn(instrumentation);

        final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        executorService.execute(() -> System.out.println("Hello, World!"));

    }

    private AgentBuilder newAgentBuilder() {
        return new AgentBuilder.Default(
            new ByteBuddy()
                // 设置为 true 时，ByteBuddy 会在字节码生成阶段执行严格的类型验证，但生成效率会降低，建议 debug 时使用。
                .with(TypeValidation.ENABLED)
                // 命名策略
                .with(new CSAuxiliaryTypeNamingStrategy())
                .with(new CSImplementationContextFactory("$cs$"))
        );
    }

}
