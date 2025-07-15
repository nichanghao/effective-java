package net.sunday.effective.dubbo.test.common.generate;


import com.alibaba.dubbo.rpc.service.GenericException;
import lombok.SneakyThrows;
import net.sunday.effective.dubbo.service.DemoService;
import org.apache.dubbo.common.bytecode.ClassGenerator;
import org.apache.dubbo.common.bytecode.Proxy;
import org.apache.dubbo.rpc.service.Destroyable;
import org.apache.dubbo.rpc.service.EchoService;
import org.apache.dubbo.rpc.service.GenericService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

/**
 * {@link Proxy#getProxy(Class[])}
 */

public class DemoServiceDubboProxy1
        implements ClassGenerator.DC,
        DemoService,
        EchoService,
        GenericService,
        Destroyable,
        com.alibaba.dubbo.rpc.service.GenericService {
    public static Method[] methods;
    private InvocationHandler handler;

    @SneakyThrows
    public String sayHello(String string) {
        Object[] objectArray = new Object[]{string};
        Object object = this.handler.invoke(this, methods[0], objectArray);
        return (String) object;
    }

    @SneakyThrows
    public CompletableFuture sayHelloAsync(String string) {
        Object[] objectArray = new Object[]{string};
        Object object = this.handler.invoke(this, methods[1], objectArray);
        return (CompletableFuture) object;
    }

    @SneakyThrows
    @Override
    public Object $invoke(String string, String[] stringArray, Object[] objectArray) throws GenericException {
        Object[] objectArray2 = new Object[]{string, stringArray, objectArray};
        Object object = this.handler.invoke(this, methods[2], objectArray2);
        return object;
    }

    @SneakyThrows
    public CompletableFuture $invokeAsync(String string, String[] stringArray, Object[] objectArray) throws org.apache.dubbo.rpc.service.GenericException {
        Object[] objectArray2 = new Object[]{string, stringArray, objectArray};
        Object object = this.handler.invoke(this, methods[3], objectArray2);
        return (CompletableFuture) object;
    }

    @SneakyThrows
    @Override
    public Object $echo(Object object) {
        Object[] objectArray = new Object[]{object};
        Object object2 = this.handler.invoke(this, methods[4], objectArray);
        return object2;
    }

    @SneakyThrows
    @Override
    public void $destroy() {
        Object[] objectArray = new Object[]{};
        Object object = this.handler.invoke(this, methods[5], objectArray);
    }

    public DemoServiceDubboProxy1() {
    }

    /**
     * @see Proxy#newInstance(InvocationHandler)
     */
    public DemoServiceDubboProxy1(InvocationHandler invocationHandler) {
        this.handler = invocationHandler;
    }
}