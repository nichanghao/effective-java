package net.sunday.effective.skywalking.test.reflection;

import net.sunday.effective.skywalking.test.reflection.annotation.CompositeIndex;
import net.sunday.effective.skywalking.test.reflection.annotation.CompositeIndices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

public class JavaReflectTest {

    @CompositeIndex(withColumns = {"column1"})
    @CompositeIndex(withColumns = {"column"})
    private List<String> arrayList;

    /**
     * test fields of classes
     */
    @Test
    void testFields() throws NoSuchFieldException {

        Field field = JavaReflectTest.class.getDeclaredField("arrayList");
        System.out.println("字段类型: " + field.getType());
        System.out.println("泛型类型: " + field.getGenericType());

        // 多个 CompositeIndex 注解 被收集到 CompositeIndices 容器注解中
        Assertions.assertFalse(field.isAnnotationPresent(CompositeIndex.class));
        Assertions.assertTrue(field.isAnnotationPresent(CompositeIndices.class));

    }

}
