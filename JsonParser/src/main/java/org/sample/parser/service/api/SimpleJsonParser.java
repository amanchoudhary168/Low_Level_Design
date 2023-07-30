package org.sample.parser.service.api;

import org.sample.parser.constants.Phase;

import java.util.HashMap;
import java.util.Map;

public class SimpleJsonParser implements JsonParser{

    int index = 0;
    @Override
    public Map<String, Object> parse(String jsonString) {
        Map<String,Object> objectMap = recursive(jsonString);
        if(index!=jsonString.length())
            throw new RuntimeException("Cannot traverse all chars and stop at \" + index + " + jsonString.length());
        return objectMap;
    }

    public Map<String, Object> recursive(String input) {
        // Each recursion will cover all key value pairs within one {XXXXX} and return one Map object
        boolean stop = false;
        String pendingKey = null;
        Phase phase = Phase.OPEN;
        Map<String, Object> res =  new HashMap<>();
        while (index < input.length()) {
            removeWhiteSpaces(input);
            char curChar = input.charAt(index);
            switch(phase) {
                case OPEN:
                    if (curChar != '{') {
                        throw new RuntimeException("We need to open with {, but " + curChar);
                    }
                    phase = Phase.KEY;
                    break;
                case KEY:
                    if (curChar != '"') {
                        throw new RuntimeException("We need to extract key which starts from \", but " + curChar);
                    }
                    pendingKey = extractString(input).toString();
                    index++; removeWhiteSpaces(input);
                    char nextChar = input.charAt(index);
                    if (nextChar != ':') {
                        throw new RuntimeException("We need : to move to VALUE phase");
                    }

                    phase = Phase.VALUE;
                    break;
                case VALUE:
                    switch(curChar) {
                        case '"':
                            // Normal string value
                            res.put(pendingKey, extractString(input).toString());
                            pendingKey = null;
                            index++;
                            break;
                        case '{':
                            // Recursive map object
                            res.put(pendingKey, recursive(input));
                            break;
                        default:
                            throw new RuntimeException("We need to get string value which starts from \" or child map object {, but " + curChar);
                    }
                    removeWhiteSpaces(input);
                    // Check next char right after "value"
                    nextChar = input.charAt(index);
                    switch(nextChar) {
                        case ',':
                            // Multiple key value pairs in this { }
                            phase = Phase.KEY;
                            break;
                        case '}':
                            phase = Phase.CLOSE;
                            break;
                        default:
                            throw new RuntimeException("We need to get , to get next key value pair or } stop and return " + curChar);
                    }
                    break;
                case CLOSE:
                    // Break while loop
                    stop = true;
                    break;
                default:
                    throw new RuntimeException("Unknow phase");

            }
            if (stop) {
                break;
            }
            index++;
        }
        return res;
    }

    private void removeWhiteSpaces(String jsonString){
        while (index < jsonString.length() && jsonString.charAt(index) == ' ') {
            index++;
        }
    }

    private StringBuilder extractString(String input) {
        StringBuilder sb = new StringBuilder();
        int doubleQuoteCount = 0;
        while (index < input.length() && doubleQuoteCount < 2) {
            if (input.charAt(index) == '\"') {
                doubleQuoteCount++;
            } else {
                sb.append(input.charAt(index));
            }
            if (doubleQuoteCount == 2) {
                break;
            }
            index++;
        }
        return sb;
    }
}
