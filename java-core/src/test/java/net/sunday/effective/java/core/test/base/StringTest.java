package net.sunday.effective.java.core.test.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * 控制位规则：
 * 单字节字符：0xxx xxxx
 * 双字节字符：110x xxxx 10xx xxxx
 * 三字节字符：1110 xxxx 10xx xxxx 10xx xxxx
 * 四字节字符：1111 0xxx 10xx xxxx 10xx xxxx 10xx xxxx
 */
public class StringTest {

    /**
     * 3字节结构分解（共24位）
     * 字节1：1110 xxxx
     * 字节2：10xx xxxx
     * 字节3：10xx xxxx
     */
    @Test
    void testChineseCodec() {
        String str = "中";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        // 第一个字节的前三位（111）表示这是一个3字节的字符
        Assertions.assertEquals("11100100", String.format("%8s", Integer.toBinaryString(Byte.toUnsignedInt(bytes[0]))));
        // 接下来的每个字节的前两位（10）表示这是后续字节
        Assertions.assertEquals("10111000", String.format("%8s", Integer.toBinaryString(Byte.toUnsignedInt(bytes[1]))));
        Assertions.assertEquals("10101101", String.format("%8s", Integer.toBinaryString(Byte.toUnsignedInt(bytes[2]))));

        // 解码，取第一个字节后4位 + 第二个字节后6位 + 第三个字节后6位
        int i = Integer.parseUnsignedInt("0100111000101101", 2);
        String str2 = new String(new int[]{i}, 0, 1);
        Assertions.assertEquals(str, str2);
    }

    @Test
    void testEnglishCodec() {
        String str = "a";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        // 控制位: 最高位固定为 0
        Assertions.assertEquals("01100001", String.format("%8s",
                Integer.toBinaryString(Byte.toUnsignedInt(bytes[0]))).replace(' ', '0'));

        // 解码
        String str2 = new String(new int[]{Integer.parseUnsignedInt("01100001", 2)}, 0, 1);
        Assertions.assertEquals(str, str2);
    }

    @Test
    void testIntern() {
        String s1 = "test";

        // new String("test") 的构造过程会同时在堆和常量池创建对象
        String s2 = new String("test");
        Assertions.assertNotSame(s1, s2);
        // intern()会返回常量池中的引用
        Assertions.assertSame(s1, s2.intern());
    }
}
