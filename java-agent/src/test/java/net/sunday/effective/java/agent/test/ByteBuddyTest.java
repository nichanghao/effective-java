package net.sunday.effective.java.agent.test;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
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

}
