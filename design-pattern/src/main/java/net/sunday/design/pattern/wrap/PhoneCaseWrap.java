package net.sunday.design.pattern.wrap;

/**
 * @author NiChangHao
 * @since 2020/8/11
 */
public class PhoneCaseWrap implements Phone {

    private Phone phone;

    public PhoneCaseWrap(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void run() {
        System.out.println("phone case wrap...");
        phone.run();
    }

}
