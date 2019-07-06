package com.gozmen.im.domain.model;

public enum ConversationType{

    PRIVATE(0),
    GROUP(1),
    ;
    private final int value;

    ConversationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
