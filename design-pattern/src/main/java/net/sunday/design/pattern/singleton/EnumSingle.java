package net.sunday.design.pattern.singleton;

/**
 * 单例 --枚举方式
 * 使用枚举实现单例模式 优点:实现简单、调用效率高，枚举本身就是单例，由jvm从根本上提供保障!
 * 避免通过反射和反序列化的漏洞， 缺点没有延迟加载。
 *
 * @author NiChangHao
 */
public enum EnumSingle {

    /**
     * 单利对象枚举
     */
    INSTANCE;

    private Object obj;

    EnumSingle() {
        this.obj = new Object();
    }

    public static void main(String[] args) {
        Object o1 = EnumSingle.INSTANCE.obj;
        Object o2 = EnumSingle.INSTANCE.obj;
        System.out.println(o1);
        System.out.println(o2);
        //System.out.println(EnumSingle.valueOf(EnumSingle.class,"INSTANCE"));
    }

}
