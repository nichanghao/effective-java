package net.sunday.effective.dubbo.provider;

import net.sunday.effective.dubbo.service.DemoService;
import net.sunday.effective.dubbo.service.DemoServiceImpl;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

public class DubboProviderApplication {

    private static final String REGISTRY_URL = "nacos://localhost:8848";

    public static void main(String[] args) {
        System.setProperty("dubbo.service.net.sunday.effective.dubbo.service.DemoService.timeout", "2000");
        System.setProperty("dubbo.service.net.sunday.effective.dubbo.service.DemoService.sayHello.timeout", "1000");

        // all, instance, interface
        System.setProperty("dubbo.application.register-mode", "instance");
        System.setProperty("dubbo.registry.use-as-metadata-center", "false");

        startWithBootstrap();
    }

    private static void startWithBootstrap() {
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap
                .application(new ApplicationConfig("dubbo-demo-api-provider"))
                .registry(new RegistryConfig(REGISTRY_URL))
                .protocol(new ProtocolConfig(CommonConstants.DUBBO, -1))
                .provider(builder -> builder.cluster("failover").loadbalance("random").retries(2).build())
                .service(service)
                .start()
                .await();
    }

}
