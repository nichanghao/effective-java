package net.sunday.effective.dubbo.test.common;

import net.sunday.effective.dubbo.test.common.model.FooBeanWithApplicationModel;
import net.sunday.effective.dubbo.test.common.model.FooBeanWithFrameworkModel;
import org.apache.dubbo.common.beans.factory.ScopeBeanFactory;
import org.apache.dubbo.common.extension.ExtensionScope;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link ScopeBeanFactory} 为不同的 {@link ExtensionScope} 作用域 提供独立的 bean 容器管理
 */

public class ScopeBeanFactoryTest {

    @Test
    void testScopeBeanFactory() {
        // 测试 application scope 下的 scope bean factory
        ApplicationModel applicationModel = ApplicationModel.defaultModel();
        ScopeBeanFactory applicationBeanFactory = applicationModel.getBeanFactory();

        FooBeanWithApplicationModel beanWithApplicationModel = applicationBeanFactory.registerBean(FooBeanWithApplicationModel.class);
        Assertions.assertSame(applicationModel, beanWithApplicationModel.getApplicationModel());

        // 测试 framework scope 下的 scope bean factory
        FrameworkModel frameworkModel = applicationModel.getFrameworkModel();
        FooBeanWithFrameworkModel beanWithFrameworkModel = frameworkModel.getBeanFactory().registerBean(FooBeanWithFrameworkModel.class);
        Assertions.assertSame(frameworkModel, beanWithFrameworkModel.getFrameworkModel());

        // child bean factory can obtain bean from parent bean factory
        Assertions.assertSame(beanWithFrameworkModel, applicationBeanFactory.getBean(FooBeanWithFrameworkModel.class));

        // destroy
        frameworkModel.destroy();
        Assertions.assertTrue(beanWithApplicationModel.isDestroyed());
        Assertions.assertTrue(beanWithFrameworkModel.isDestroyed());

    }
}
