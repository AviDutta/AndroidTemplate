package com.claricetechnologies.factory;

import com.claricetechnologies.parsers.ClParser;
import com.claricetechnologies.parsers.ClParserImpl;

public class ClParserFactory {
    public static ClParser getParser() {
        return new ClParserImpl();
    }
}
