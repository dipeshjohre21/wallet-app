package com.nextuple.walletapi.model;

public enum TransactionType {
    RECHARGE("Recharge"),
    CREDIT("Credit"),
    DEBIT("Debit");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
