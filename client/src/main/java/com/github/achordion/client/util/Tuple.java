package com.github.achordion.client.util;

public record Tuple<A, B>(A first, B second) {
    @Override
    public String toString() {
        return first + "\n" + second;
    }
}
