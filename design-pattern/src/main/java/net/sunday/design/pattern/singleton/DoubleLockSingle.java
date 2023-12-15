package net.sunday.design.pattern.singleton;

/**
 * 双重校验锁
 */
public class DoubleLockSingle {

    private volatile static DoubleLockSingle doubleLockSingle = null;

    private DoubleLockSingle() {
        if (doubleLockSingle != null)
            throw new RuntimeException("this object is created");
    }

    public static DoubleLockSingle getInstance() {
        if (null == doubleLockSingle) {
            synchronized (DoubleLockSingle.class) {
                if (null == doubleLockSingle)
                    doubleLockSingle = new DoubleLockSingle();
            }
        }
        return doubleLockSingle;
    }


}
