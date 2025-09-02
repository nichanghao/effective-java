package net.sunday.effective.dubbo.provider;

import net.sunday.effective.dubbo.service.DemoService;
import net.sunday.effective.dubbo.service.DemoServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

public class DubboProviderApplication {

    public static final String REGISTRY_URL = "zookeeper://localhost:2181";

    public static void main(String[] args) {

        /*
         * 注册模式-可选值：all, instance, interface
         * 判断是否需要应用级注册和接口级注册：org.apache.dubbo.config.utils.ConfigValidationUtils.loadRegistries
         */
        System.setProperty("dubbo.application.register-mode", "all");

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
                .provider(builder -> builder.cluster("failover").loadbalance("random").retries(2).build())
                .service(service)
                .start()
                .await();
    }

}
