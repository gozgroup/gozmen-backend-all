package com.gozmen.blog.domain.model;

public enum PostStatus {

    DRAFT(0),
    AUDITING(10),
    AUDITE_FAILED(20),
    PUBLISHED(30),
    ;
    private final int value;

    PostStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
