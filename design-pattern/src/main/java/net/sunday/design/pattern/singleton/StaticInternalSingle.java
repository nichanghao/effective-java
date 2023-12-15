package net.sunday.design.pattern.singleton;

/**
 * 单例 --静态内部方式
 *
 * @author NiChangHao
 */
public class StaticInternalSingle {

    private StaticInternalSingle () {}

    /**
     * 静态内部类的加载不需要依附外部类，在使用时才加载。不过在加载静态内部类的过程中也会加载外部类。
     */
    private static class StaticInternalSingleInstance {
        private static final StaticInternalSingle STATIC_INTERNAL_SINGLE = new StaticInternalSingle();
    }


    public static StaticInternalSingle getInstance() {
        return StaticInternalSingleInstance.STATIC_INTERNAL_SINGLE;
    }

    public static void main(String[] args) {
        StaticInternalSingle s1 = getInstance();
        StaticInternalSingle s2 = getInstance();
        System.out.println(s1 == s2);
    }
}
