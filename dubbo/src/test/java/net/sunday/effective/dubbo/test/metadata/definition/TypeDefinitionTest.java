package net.sunday.effective.dubbo.test.metadata.definition;

import com.fasterxml.jackson.core.type.TypeReference;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import net.sunday.effective.dubbo.service.DemoService;
import org.apache.dubbo.common.utils.JsonUtils;
import org.apache.dubbo.metadata.definition.ServiceDefinitionBuilder;
import org.apache.dubbo.metadata.definition.TypeDefinitionBuilder;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.definition.model.MethodDefinition;
import org.apache.dubbo.metadata.definition.model.TypeDefinition;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * {@link TypeDefinitionBuilder} 1. 类型定义标准化：将 Java 类型（Type/Class）转为标准化的 TypeDefinition 对象 2. 通过 SPI 机制加载所有 TypeBuilder
 * 扩展，根据优先级依次匹配可以支持当前类型转换的 TypeBuilder 进行类型 definition 构建
 */
public class TypeDefinitionTest {

    @BeforeAll
    public static void setup() {
        TypeDefinitionBuilder.initBuilders(FrameworkModel.defaultModel());
    }

    @Test
    void testTypeDefinition() {

        TypeDefinitionBuilder builder = new TypeDefinitionBuilder();

        TypeDefinition td = builder.build(Result.class, Result.class);
        System.out.println(JsonUtils.toJson(td));

        final List<TypeDefinition> typeDefinitions = builder.getTypeDefinitions();
        for (final TypeDefinition typeDefinition : typeDefinitions) {
            System.out.println(JsonUtils.toJson(typeDefinition));
        }
    }

    @Test
    void testTypeDefinitionOfCollection() {

        TypeDefinitionBuilder builder = new TypeDefinitionBuilder();

        final Type type = new TypeReference<List<Map<String, Object>>>() {
        }.getType();

        TypeDefinition td = builder.build(type, List.class);
        System.out.println(JsonUtils.toJson(td));

        for (final TypeDefinition typeDefinition : builder.getTypeDefinitions()) {
            System.out.println(JsonUtils.toJson(typeDefinition));
        }
    }

    @Test
    void testServiceDefinition() {
        final FullServiceDefinition fullServiceDefinition = ServiceDefinitionBuilder.buildFullDefinition(
            DemoService.class);

        for (final MethodDefinition method : fullServiceDefinition.getMethods()) {
            System.out.println(method);
        }
    }

    @Test
    void testArrayClass() {
        Result[] results = new Result[2];

        final String canonicalName = results.getClass().getCanonicalName();
        Assertions.assertEquals(
            "net.sunday.effective.dubbo.test.metadata.definition.TypeDefinitionTest.Result[]", canonicalName);

        Assertions.assertEquals(Result.class, results.getClass().getComponentType());

    }

    private static class Result {

        private List<Integer> list;

        private Map<String, Long> map;

        private List<Map<String, Object>> complexList;

        private String message;

    }
}
