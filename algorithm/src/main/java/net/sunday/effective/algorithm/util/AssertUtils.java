package net.sunday.effective.algorithm.util;

/**
 * 断言工具类
 */
public class AssertUtils {

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError();
        }
    }

    public static void assertFalse(boolean condition) {
        assertTrue(!condition);
    }

    public static void assertThrows(String msg, Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (!e.getMessage().equals(msg)) {
                throw new AssertionError(e.getMessage());
            }
        }
    }

    public static <T extends Throwable> void assertThrows(Class<T> throwableCls, Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable actualException) {
            if (!throwableCls.isInstance(actualException)) {
                throw new AssertionError();
            }
        }
    }
}
