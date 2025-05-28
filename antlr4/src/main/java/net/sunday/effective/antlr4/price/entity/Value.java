package net.sunday.effective.antlr4.price.entity;

public class Value {

    public enum Type {
        DOUBLE,
        BOOL
    }

    private final Type type;
    private final double doubleValue;
    private final boolean boolValue;

    private Value(Type type, double d, boolean b) {
        this.type = type;
        this.doubleValue = d;
        this.boolValue = b;
    }

    public static Value ofDouble(double d) {
        return new Value(Type.DOUBLE, d, false);
    }

    public static Value ofBool(boolean b) {
        return new Value(Type.BOOL, 0, b);
    }

    public double asDouble() {
        if (type != Type.DOUBLE)
            throw new IllegalStateException("illegal double type");

        return doubleValue;
    }

    public boolean asBool() {
        if (type != Type.BOOL)
            throw new IllegalStateException("illegal bool type");

        return boolValue;
    }
}
