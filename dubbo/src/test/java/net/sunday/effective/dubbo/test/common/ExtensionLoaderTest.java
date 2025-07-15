package net.sunday.effective.dubbo.test.common;

import net.sunday.effective.dubbo.service.DemoService;
import net.sunday.effective.dubbo.service.DemoServiceImpl;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.compiler.Compiler;
import org.apache.dubbo.common.compiler.support.AdaptiveCompiler;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.AdaptiveClassCodeGenerator;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.cluster.filter.FilterChainBuilder;
import org.apache.dubbo.rpc.cluster.filter.ProtocolFilterWrapper;
import org.apache.dubbo.rpc.model.*;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
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

    /**
     * {@link AdaptiveClassCodeGenerator#generate()} 生成自适应扩展点类，该类会根据运行时参数选择具体的扩展点实现类
     */
    @Test
    void testAdaptiveExtension() {
        ModuleModel model = ApplicationModel.defaultModel().getDefaultModule();
        // @Adaptive 加在实现类上，则该类为自适应扩展点类
        Protocol adaptiveExtension1 = model.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        Protocol dubbo = model.getExtensionLoader(Protocol.class).getExtension("dubbo");

        Compiler adaptiveExtension = model.getExtensionLoader(Compiler.class).getAdaptiveExtension();
        Assertions.assertSame(AdaptiveCompiler.class, adaptiveExtension.getClass());

        // @Adaptive 加在方法上，会生成自适应代理类，根据方法签名的 `url` 参数再次调用合适的扩展点类
        Protocol protocol = model.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        Assertions.assertNotNull(protocol.getClass());

        // @SPI(value = "dubbo") 表示默认的 扩展类为 dubbo
        String defaultExtensionName = model.getExtensionLoader(Protocol.class).getDefaultExtensionName();
        Assertions.assertEquals("dubbo", defaultExtensionName);

        // 获取默认扩展点时，会默认对扩展点类进行wrapper, 当一个扩展点实现类有一个构造方法，且参数类型与扩展点接口一致，则会使用该构造方法进行wrapper
        Protocol defaultProtocol = model.getExtensionLoader(Protocol.class).getDefaultExtension();
        Assertions.assertNotEquals(DubboProtocol.class, defaultProtocol.getClass());

    }

    /**
     * 测试 Dubbo 的 filter 机制
     * {@link ProtocolFilterWrapper} 在export时，会对 Invoker 进行包装 Filter 链
     * {@link FilterChainBuilder} 负责构建 Filter 链的 SPI 扩展点
     * {@link FilterChainBuilder.CopyOfFilterChainNode} 每个 Filter 都会包装一个 CopyOfFilterChainNode（Invoker类型） 节点
     */
    @Test
    void testFilterChainNode() {

        ApplicationModel application = ApplicationModel.defaultModel();
        URL url = new URL("nacos", "127.0.0.1", 8848);
        ProxyFactory proxyFactory = application.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
        Invoker<?> invoker = proxyFactory.getInvoker(new DemoServiceImpl(), DemoService.class, url);

        FilterChainBuilder filterChainBuilder = ScopeModelUtil
                .getExtensionLoader(FilterChainBuilder.class, application).getDefaultExtension();
        Invoker<?> invoker1 = filterChainBuilder.buildInvokerChain(invoker, "service.filter", CommonConstants.PROVIDER);
        Assertions.assertEquals(FilterChainBuilder.CallbackRegistrationInvoker.class, invoker1.getClass());
    }

}
