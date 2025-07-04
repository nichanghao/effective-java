package net.sunday.effective.dubbo.test.common;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.junit.jupiter.api.Test;

public class ConfigManagerTest {

    @Test
    void testConfigManager() {
        ApplicationModel applicationModel = ApplicationModel.defaultModel();
        ConfigManager configManager = applicationModel.getApplicationConfigManager();

        System.setProperty("dubbo.registries.registry1.address", "1.1.1.1");
        System.setProperty("dubbo.registries.registry2.port", "8000");

        configManager.loadConfigsOfTypeFromProps(ApplicationConfig.class);


    }


}
