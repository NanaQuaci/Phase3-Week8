package com.projects.util;

public class CheckoutInfo {
    private final String name;
    private final String creditCard;

    public CheckoutInfo(String name, String creditCard) {
        this.name = name;
        this.creditCard = creditCard;
    }
    public String getName() { return name; }
    public String getCreditCard() { return creditCard; }
}
