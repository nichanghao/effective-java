package net.sunday.design.pattern.singleton;

/**
 * 单例--懒汉式
 *
 * @author NiChangHao
 */
public class LazySingle {

    /**
     * 类初始化时,不会初始化该对象,真正需要使用的时候才会创建该对象,具备懒加载功能，天生线程不安全，
     * 需解决线程安全问题，所以效率比较低。
     */
    private static LazySingle lazySingle = null;

    private LazySingle() {
        if (lazySingle != null)
            throw new RuntimeException("the object is created");
    }

    public synchronized static LazySingle getInstance() {
        if (lazySingle == null)
            lazySingle = new LazySingle();
        return lazySingle;
    }

    private Object readResolve(){
        return lazySingle;
    }

    public static void main(String[] args) {
        LazySingle lazySingle1 = getInstance();
        LazySingle lazySingle2 = getInstance();
        System.out.println(lazySingle1 == lazySingle2);
    }
}
