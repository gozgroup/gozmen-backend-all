package com.gozmen.common.data;

public class ObjectValue<VALUE> {

    private final VALUE value;

    public ObjectValue(VALUE value) {
        this.value = value;
    }

    public VALUE getValue() {
        return value;
    }

    public static StringValue stringVlue(String value) {
        return new StringValue(value);
    }

    public static IntegerValue intVlue(Integer value) {
        return new IntegerValue(value);
    }

    public static LongValue longVlue(Long value) {
        return new LongValue(value);
    }
}
