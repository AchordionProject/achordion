package com.github.achordion.client.protocol;

import java.util.List;

@FunctionalInterface
public interface BodyHandler {
    List<Note> handle(byte[] body);
}
