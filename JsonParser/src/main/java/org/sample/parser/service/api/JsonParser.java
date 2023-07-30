package org.sample.parser.service.api;

import java.util.Map;

public interface JsonParser {
    public Map<String,Object> parse(String jsonString);
}
