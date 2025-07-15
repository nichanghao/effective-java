package net.sunday.effective.dubbo.consumer;

import net.sunday.effective.dubbo.service.DemoService;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

import static net.sunday.effective.dubbo.provider.DubboProviderApplication.REGISTRY_URL;

public class DubboConsumerApplication {

    public static void main(String[] args) {
        runWithBootstrap();
    }

    private static void runWithBootstrap() {
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setInterface(DemoService.class);
        reference.setGeneric("true");
        // 关闭元数据中心后，需要设置此值
//        reference.setProvidedBy("dubbo-demo-api-provider");

        final ApplicationConfig config = new ApplicationConfig("dubbo-demo-api-consumer");
        config.setQosEnable(false);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap
            .application(config)
            .registry(new RegistryConfig(REGISTRY_URL))
            .protocol(new ProtocolConfig(CommonConstants.TRIPLE, -1))
            .reference(reference)
            .start();

        DemoService demoService = bootstrap.getCache().get(reference);
        String message = demoService.sayHello("dubbo");
        System.err.println(message);

        // generic invoke
        GenericService genericService = (GenericService) demoService;
        Object genericInvokeResult = genericService.$invoke(
            "sayHello", new String[] {String.class.getName()}, new Object[] {"dubbo generic invoke"});
        System.err.println(genericInvokeResult.toString());
    }
}
