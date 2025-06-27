package net.sunday.effective.java.core.test.reflection.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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