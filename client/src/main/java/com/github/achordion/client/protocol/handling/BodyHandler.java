package com.github.achordion.client.protocol.handling;

import java.util.List;

@FunctionalInterface
public interface BodyHandler {
    List<Note> handle(byte[] body);
}
