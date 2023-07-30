package org.sample.parser;

import org.sample.parser.service.api.SimpleJsonParser;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Object> json = new SimpleJsonParser().parse("{\"abc\":{\"bcde\":\"fg\",\"xml\":\"asd\"}}"); // Successful to get map object
        new SimpleJsonParser().parse("{\"zabc\":{zbcde\":\"fg\",\"xml\":\"asd\"}}"); // Exception because  of wrong key, zbcde
        new SimpleJsonParser().parse("{{\"abc\":{\"zbcde\":\"fg\",\"xml\":\"asd\"}}"); // Exception because of one more {
        new SimpleJsonParser().parse("{\"abc\":{\"zbcde\":\"fg\",\"xml\":\"asd\"}}}"); // Exception because of one more }
    }
}
