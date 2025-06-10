package net.sunday.effective.java.core.design.wrap;


public class WrapTest {

    public static void main(String[] args) {
        Phone phone = new MiPhoneImpl();
        // 包装添加了手机壳
        phone = new PhoneCaseWrap(phone);
        phone.run();
    }
}
