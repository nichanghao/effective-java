package net.sunday.effective.dubbo.test.common.generate;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import net.sunday.effective.dubbo.service.DemoServiceImpl;
import org.apache.dubbo.common.bytecode.ClassGenerator;
import org.apache.dubbo.common.bytecode.NoSuchMethodException;
import org.apache.dubbo.common.bytecode.NoSuchPropertyException;
import org.apache.dubbo.common.bytecode.Wrapper;

/**
 * {@link Wrapper#getWrapper(Class)}
 */
public class DemoServiceImplDubboWrap0 extends Wrapper implements ClassGenerator.DC {
    public static String[] pns;
    public static Map pts;
    public static String[] mns;
    public static String[] dmns;
    public static Class[] mts0;
    public static Class[] mts1;

    @Override
    public String[] getPropertyNames() {
        return pns;
    }

    @Override
    public boolean hasProperty(String string) {
        return pts.containsKey(string);
    }

    public Class getPropertyType(String string) {
        return (Class) pts.get(string);
    }

    @Override
    public String[] getMethodNames() {
        return mns;
    }

    @Override
    public String[] getDeclaredMethodNames() {
        return dmns;
    }

    @Override
    public void setPropertyValue(Object object, String string, Object object2) {
        try {
            DemoServiceImpl demoServiceImpl = (DemoServiceImpl) object;
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
        throw new NoSuchPropertyException(new StringBuffer().append("Not found property \"").append(string).append("\" field or setter method in class net.sunday.effective.dubbo.service.DemoServiceImpl.").toString());
    }

    @Override
    public Object getPropertyValue(Object object, String string) {
        try {
            DemoServiceImpl demoServiceImpl = (DemoServiceImpl) object;
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
        throw new NoSuchPropertyException(new StringBuffer().append("Not found property \"").append(string).append("\" field or getter method in class net.sunday.effective.dubbo.service.DemoServiceImpl.").toString());
    }

    public Object invokeMethod(Object object, String string, Class[] classArray, Object[] objectArray) throws InvocationTargetException {
        DemoServiceImpl demoServiceImpl;
        try {
            demoServiceImpl = (DemoServiceImpl) object;
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
        try {
            if ("sayHello".equals(string) && classArray.length == 1) {
                return demoServiceImpl.sayHello((String) objectArray[0]);
            }
            if ("sayHelloAsync".equals(string) && classArray.length == 1) {
                return demoServiceImpl.sayHelloAsync((String) objectArray[0]);
            }
        } catch (Throwable throwable) {
            throw new InvocationTargetException(throwable);
        }
        throw new NoSuchMethodException(new StringBuffer().append("Not found method \"").append(string).append("\" in class net.sunday.effective.dubbo.service.DemoServiceImpl.").toString());
    }
}
