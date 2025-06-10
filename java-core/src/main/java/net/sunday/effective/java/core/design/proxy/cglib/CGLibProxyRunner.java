package net.sunday.effective.java.core.design.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sunday.effective.java.core.design.proxy.cglib.interceptor.UserServiceCGLibInterceptor;
import net.sunday.effective.java.core.design.proxy.service.UserService;
import net.sunday.effective.java.core.design.proxy.service.UserServiceImpl;

/**
 * cglib 动态代理运行类
 */

public class CGLibProxyRunner {

    /**
     * 注意： JDK9+ 需要增加 jvm 参数： `--add-opens java.base/java.lang=ALL-UNNAMED`
     * 高版本不推荐使用 cglib，使用 byte buddy 代替
     */
    public static void main(String[] args) {
        //利用 cglib 的代理类可以将内存中的 class 文件写入本地磁盘
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,
                System.getProperty("user.dir") + "/java-core/build/classes/cglib_proxy_class/");

        ((UserService) (new UserServiceCGLibInterceptor().newInstance(UserServiceImpl.class))).login();
    }

}
