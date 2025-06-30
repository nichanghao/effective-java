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
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.listener.AgentListener;
import net.bytebuddy.listener.RedefinitionListener;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import net.sunday.effective.java.agent.entity.Person;
import net.sunday.effective.java.agent.interceptor.InstMethodInterceptor;
import net.sunday.effective.java.agent.util.AgentUtils;
import org.junit.jupiter.api.Test;

import static net.bytebuddy.matcher.ElementMatchers.hasSuperType;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

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

        AgentUtils.newAgentBuilder()
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
                  .with(new RedefinitionListener())
                  .with(new AgentListener())
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

        String templateClassName = "net.sunday.effective.java.agent.interceptor.BootstrapInstMethodInterceptor";

        AgentUtils.generateInternalClass(templateClassName, classesTypeMap);

        Instrumentation instrumentation = ByteBuddyAgent.install();

        AgentBuilder agentBuilder = AgentUtils.newAgentBuilder()
                                              // 不忽略任何类，默认会忽略jdk相关的类
                                              .ignore(ElementMatchers.none());

        /*
         * Inject the classes into bootstrap class loader by using Unsafe Strategy.
         * ByteBuddy adapts the sun.misc.Unsafe and jdk.internal.misc.Unsafe automatically.
         */
        ClassInjector.UsingUnsafe.Factory factory = ClassInjector.UsingUnsafe.Factory.resolve(instrumentation);
        factory.make(null, null).injectRaw(classesTypeMap);
        agentBuilder = agentBuilder.with(new AgentBuilder.InjectionStrategy.UsingUnsafe.OfFactory(factory));

        for (String edgeClass : classesTypeMap.keySet()) {
            agentBuilder = agentBuilder.assureReadEdgeFromAndTo(instrumentation, Class.forName(edgeClass));
        }

        agentBuilder.type(hasSuperType(named("java.lang.Runnable")))
                    .transform(new AgentBuilder.Transformer() {

                        @Override
                        @SneakyThrows
                        public DynamicType.Builder<?> transform(final DynamicType.Builder<?> builder,
                                                                final TypeDescription typeDescription,
                                                                final ClassLoader classLoader,
                                                                final JavaModule module,
                                                                final ProtectionDomain protectionDomain) {

                            return builder.method(ElementMatchers.named("run").and(takesArguments(0)))
                                          .intercept(MethodDelegation
                                                         .withDefaultConfiguration()
                                                         .to(Class.forName(
                                                             AgentUtils.getInternalClassName(templateClassName))));

                        }
                    })
                    .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                    .with(new RedefinitionListener())
                    .with(new AgentListener())
                    .installOn(instrumentation);

        final ExecutorService executorService = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setName("http-get-thread");
            return thread;
        });
        executorService.execute(() -> System.out.println("Hello, World!"));

    }

}
