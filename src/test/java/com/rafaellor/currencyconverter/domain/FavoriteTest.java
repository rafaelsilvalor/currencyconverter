package com.rafaellor.currencyconverter.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FavoriteTest {
    @Test
    void key_shouldReturnFromAndToJoinedByArrow() {
        Favorite fav = new Favorite("USD", "BRL");
        assertEquals("USD->BRL", fav.key());
    }

    @Test
    void toString_shouldReturnSameAsKey() {
        Favorite fav = new Favorite("EUR", "JPY");
        assertEquals("EUR->JPY", fav.toString());
    }

    @Test
    void equalsAndHashCode_shouldBeBasedOnComponents() {
        Favorite a = new Favorite("AUD", "CAD");
        Favorite b = new Favorite("AUD", "CAD");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
