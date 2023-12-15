package net.sunday.design.pattern.singleton;

/**
 * 单例--饿汉式
 *
 * @author NiChangHao
 */
public class EvilSingle {

    /**
     * 类初始化时,会立即加载该对象，线程天生安全,调用效率高
     */
    private static final EvilSingle evilSingle = new EvilSingle();

    private EvilSingle () {}

    public static EvilSingle getInstance() {
        return evilSingle;
    }

    public static void main(String[] args) {
        EvilSingle evilSingle1 = getInstance();
        EvilSingle evilSingle2 = getInstance();
        System.out.println(evilSingle1);
        System.out.println(evilSingle2);
    }

}
