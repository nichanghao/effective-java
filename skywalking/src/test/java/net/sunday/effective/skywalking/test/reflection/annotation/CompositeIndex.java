package net.sunday.effective.skywalking.test.reflection.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
// repeatable 可以将 多个 CompositeIndex 注解 收集到一个 CompositeIndices 容器注解中
@Repeatable(CompositeIndices.class)
public @interface CompositeIndex {

    /**
     * @return list of other column should be add into the unified index.
     */
    String[] withColumns();
}