package net.sunday.effective.java.core.design.wrap;


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
