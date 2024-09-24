package com.gbtec.av532.model;

public enum EmailState {

    DRAFT(1),
    SENT(2),
    DELETED(3),
    SPAM(4);

    private int value;

    private EmailState(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

    public EmailState fromInt(int i) {
        return switch (i) {
            case 1 -> DRAFT;
            case 2 -> SENT;
            case 3 -> DELETED;
            case 4 -> SPAM;
            default -> null;
        };
    }
}
