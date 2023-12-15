package net.sunday.design.pattern.wrap;


public class WrapTest {

    public static void main(String[] args) {
        Phone phone = new MiPhone();
        // 包装添加了手机壳
        phone = new PhoneCaseWrap(phone);
        phone.run();
    }
}
