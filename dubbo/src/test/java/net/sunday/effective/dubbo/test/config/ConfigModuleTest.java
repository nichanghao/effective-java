package net.sunday.effective.dubbo.test.config;

import net.sunday.effective.dubbo.service.DemoService;
import net.sunday.effective.dubbo.service.DemoServiceImpl;
import org.apache.dubbo.common.config.CompositeConfiguration;
import org.apache.dubbo.common.config.Environment;
import org.apache.dubbo.config.MethodConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

public class ConfigModuleTest {

    @Test
    void testEnvironment() {
        ApplicationModel applicationModel = ApplicationModel.defaultModel();
        Environment environment = applicationModel.modelEnvironment();
        System.setProperty("dubbo.applications.app1.name", "app1");
        Assertions.assertEquals("app1",
                environment.getSystemConfiguration().getProperties().get("dubbo.applications.app1.name"));

        // 外部配置全局配置 例如配置中心中 config-center global/default config
        environment.setExternalConfigMap(Map.of("dubbo.applications.app1.name", "app2"));
        // 外部的应用配置 例如配置中心中执行的当前应用的配置 config-center app config
        environment.setAppExternalConfigMap(Map.of("dubbo.applications.app1.name", "app3"));
        // 来自应用中的配置例如:Spring Environment/PropertySources/application.properties
        environment.setAppConfigMap(Map.of("dubbo.applications.app1.name", "app4"));

        /*
         *组合配置，优先级：
         * -> systemConfiguration: 系统属性 (-D)
         * -> environmentConfiguration: 环境变量
         * -> appExternalConfiguration: 外部应用级配置
         * -> ExternalConfiguration: 外部全局配置
         * -> appConfiguration：spring等环境配置
         * -> propertiesConfiguration：classpath下的dubbo.properties文件配置
         */
        CompositeConfiguration configuration = environment.getConfiguration();
        Assertions.assertEquals("app1", configuration.getString("dubbo.applications.app1.name"));
    }

    @Test
    void testServiceConfig() {
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceImpl());
        service.setTimeout(3000);
        MethodConfig mc = new MethodConfig();
        mc.setName("sayHello");
        service.setMethods(Collections.singletonList(mc));
        DubboBootstrap.getInstance().application("app1").service(service).initialize();

        // 服务接口配置 dubbo.service.{interface-name}.{config-item}={config-item-value}
        System.setProperty("dubbo.service.net.sunday.effective.dubbo.service.DemoService.timeout", "2000");
        service.refresh();
        Assertions.assertEquals(2000, service.getTimeout());

        // 方法配置 dubbo.service.{interface-name}.{method-name}.{config-item}={config-item-value}
        System.setProperty("dubbo.service.net.sunday.effective.dubbo.service.DemoService.sayHello.timeout", "1000");
        service.refresh();
        Assertions.assertEquals(1000, mc.getTimeout());
    }


}
