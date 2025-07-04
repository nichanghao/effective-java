package net.sunday.effective.dubbo.test.common;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.model.ScopeModelInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExtensionLoaderTest {

    /**
     * self 作用域的扩展点 在 framework、application、module 之间不共享
     */
    @Test
    void testExtensionScopeOfSelf() {
        FrameworkModel frameworkModel = FrameworkModel.defaultModel();
        ExtensionLoader<ScopeModelInitializer> extensionLoader = frameworkModel.getExtensionLoader(ScopeModelInitializer.class);

        ApplicationModel applicationModel = frameworkModel.defaultApplication();
        ExtensionLoader<ScopeModelInitializer> extensionLoader1 = applicationModel.getExtensionLoader(ScopeModelInitializer.class);

        Assertions.assertNotSame(extensionLoader.getExtension("dubbo-common"), extensionLoader1.getExtension("dubbo-common"));

    }

}
