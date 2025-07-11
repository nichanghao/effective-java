package net.sunday.effective.dubbo.test.config;

import lombok.*;
import org.apache.dubbo.config.AbstractConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.support.Parameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link org.apache.dubbo.config.AbstractConfig}
 */

public class AbstractConfigTest {

    /**
     * 根据 class 名称获取 tag 名称
     */
    @Test
    void testGetTagName() {
        String tagName = AbstractConfig.getTagName(ConfigCenterConfig.class);

        Assertions.assertEquals("config-center", tagName);
    }

    /**
     * 根据 class 名称获取复数的 tag 名称
     */
    @Test
    void testGetPluralTagName() {
        String tagName = AbstractConfig.getPluralTagName(ConfigCenterConfig.class);

        Assertions.assertEquals("config-centers", tagName);
    }

    /**
     * {@link AbstractConfig#getPrefixes()} 获取属性前缀
     */
    @Test
    void testGetPrefixes() {
        AppConfig appConfig = new AppConfig();
        List<String> prefixes = appConfig.getPrefixes();
        // 最低优先级的前缀为 `dubbo.` + tag 名称
        Assertions.assertEquals("dubbo.app", prefixes.getLast());
        // 第二优先级的前缀为 `dubbo.` + pluralTag 名称 + name
        Assertions.assertEquals("dubbo.apps.app1", prefixes.getFirst());

        appConfig.setId("app2");
        prefixes = appConfig.getPrefixes();
        // 第一优先级的前缀为 `dubbo.` + pluralTag 名称 + id
        Assertions.assertEquals("dubbo.apps.app2", prefixes.getFirst());
    }

    @Test
    void testAppendParameters() {
        AppConfig appConfig = new AppConfig("nacos", 8848, "127.0.0.1");

        Map<String, String> parameters = new HashMap<>();
        AbstractConfig.appendParameters(parameters, appConfig);
        // 当 @Parameter(excluded = true) 时， 不会被添加
        Assertions.assertEquals(2, parameters.size());

        Map<String, String> attributes = new HashMap<>();
        AbstractConfig.appendAttributes(attributes, appConfig);
        Assertions.assertEquals(3, attributes.size());
    }

    @Test
    void testRefresh() {
        AppConfig appConfig = new AppConfig();
        System.setProperty("dubbo.app.protocol", "dubbo");

        Assertions.assertNull(appConfig.getProtocol());
        appConfig.refresh();
        Assertions.assertEquals("dubbo", appConfig.getProtocol());
        System.clearProperty("dubbo.app.protocol");

        appConfig.setId("test");
        System.setProperty("dubbo.apps.test.port", "8888");
        appConfig.refresh();
        Assertions.assertEquals(8888, appConfig.getPort());

    }

    /**
     * 测试 java bean {@link Introspector}
     */
    @Test
    @SneakyThrows
    void testIntrospector() {
        BeanInfo beanInfo = Introspector.getBeanInfo(AppConfig.class);
        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            if (!propertyDescriptor.getName().equals("protocol")) {
                continue;
            }
            Method writeMethod = propertyDescriptor.getWriteMethod();
            Assertions.assertEquals("setProtocol", writeMethod.getName());
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppConfig extends AbstractConfig {

        @Setter
        private String protocol;
        @Setter
        private Integer port;
        private String host;

        @Parameter(excluded = true)
        public String getProtocol() {
            return protocol;
        }

        @SuppressWarnings("unused")
        public String getName() {
            return "app1";
        }

    }


}
