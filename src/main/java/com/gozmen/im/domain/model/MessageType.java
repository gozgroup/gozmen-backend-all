package com.gozmen.im.domain.model;

public enum  MessageType {

    TXT("Goz:txt"),
    IMG("Goz:img"),
    ;
    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
