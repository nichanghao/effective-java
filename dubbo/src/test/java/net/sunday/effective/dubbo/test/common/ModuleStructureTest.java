package net.sunday.effective.dubbo.test.common;

import net.sunday.effective.dubbo.service.DemoService;
import net.sunday.effective.dubbo.service.DemoServiceImpl;
import org.apache.dubbo.common.config.Environment;
import org.apache.dubbo.common.utils.ClassUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.rpc.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 模型结构 test
 * {@link org.apache.dubbo.rpc.model.FrameworkModel}
 * {@link org.apache.dubbo.rpc.model.ApplicationModel}
 * {@link org.apache.dubbo.rpc.model.ModuleModel}
 */

public class ModuleStructureTest {

    private FrameworkModel frameworkModel;
    private ApplicationModel applicationModel;
    private ModuleModel moduleModel;

    @BeforeEach
    public void before() {
        frameworkModel = FrameworkModel.defaultModel();
        applicationModel = frameworkModel.defaultApplication();
        moduleModel = applicationModel.getDefaultModule();
    }

    @Test
    void testFrameworkModel() {
        // 持有所有的 FrameworkModel 实例
        List<FrameworkModel> allInstances = FrameworkModel.getAllInstances();
        Assertions.assertSame(frameworkModel, allInstances.getFirst());

        // 提供了对全局 ProviderModel 的注册和查找功能
        FrameworkServiceRepository serviceRepository = frameworkModel.getServiceRepository();
        ProviderModel providerModel = buildProviderModel();
        serviceRepository.registerProvider(providerModel);
        Assertions.assertSame(providerModel, serviceRepository.lookupExportedService(providerModel.getServiceKey()));

        // allApplicationModels 包含了 pubApplicationModels 和 内部的 internalApplicationModel
        List<ApplicationModel> pubApplicationModels = frameworkModel.getApplicationModels();
        List<ApplicationModel> allApplicationModels = frameworkModel.getAllApplicationModels();
        Assertions.assertTrue(allApplicationModels.size() > pubApplicationModels.size());
    }

    @Test
    void testApplicationModel() {
        Assertions.assertSame(applicationModel.getFrameworkModel(), frameworkModel);

        Environment environment = applicationModel.modelEnvironment();
        Map<String, String> externalMap = new LinkedHashMap<>();
        externalMap.put("zookeeper.address", "127.0.0.1");
        externalMap.put("zookeeper.port", "2181");
        environment.updateAppExternalConfigMap(externalMap);
        System.setProperty("zookeeper.address", "localhost");
        String address = environment.resolvePlaceholders("zookeeper://${zookeeper.address}:${zookeeper.port}");
        Assertions.assertEquals("zookeeper://localhost:2181", address);
    }

    @Test
    void testApplicationModelOfConfigManager() {
        ConfigManager configManager = applicationModel.getApplicationConfigManager();
        System.setProperty("dubbo.applications.app1.name", "app-demo1");
        System.setProperty("dubbo.applications.app1.protocol", "dubbo");

        // 将配置加载到内部的 `configsCache`<application <app1, ApplicationConfig>> 中
        // 只有一个 ApplicationConfig
        configManager.loadConfigsOfTypeFromProps(ApplicationConfig.class);
        configManager.getApplication().ifPresent(app -> {
            Assertions.assertEquals("app-demo1", app.getName());
            Assertions.assertEquals("dubbo", app.getProtocol());
        });

        System.setProperty("dubbo.config-centers.app1.address", "nacos://127.0.0.1:8848");
        System.setProperty("dubbo.config-centers.app2.address", "zookeeper://127.0.0.1:2181");
        configManager.loadConfigsOfTypeFromProps(ConfigCenterConfig.class);
        // 1个应用可以有多个 `ConfigCenterConfig`
        Assertions.assertEquals(2, configManager.getConfigCenters().size());

    }

    private ProviderModel buildProviderModel() {
        ServiceMetadata serviceMetadata =
                new ServiceMetadata(DemoService.class.getName(), "GROUP", "1.0.0", DemoService.class);
        String serviceKey = serviceMetadata.getServiceKey();
        ServiceDescriptor serviceDescriptor = moduleModel.getServiceRepository().registerService(DemoService.class);

        return new ProviderModel(serviceKey,
                new DemoServiceImpl(), serviceDescriptor, ClassUtils.getClassLoader(DemoService.class));
    }
}
