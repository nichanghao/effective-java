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

        System.setProperty("dubbo.applications.app1.name", "app-demo1");
        System.setProperty("dubbo.applications.app1.protocol", "dubbo");

        configManager.loadConfigsOfTypeFromProps(ApplicationConfig.class);


    }


}
