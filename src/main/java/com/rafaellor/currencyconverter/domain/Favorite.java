package com.rafaellor.currencyconverter.domain;

public record Favorite(String from, String to) {
    public String key() {
        return from + "->" + to;
    }

    @Override
    public  String toString() {
        return from + "->" + to;
    }
}
