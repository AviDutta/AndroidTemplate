package com.claricetechnologies.parsers;

public interface ClParser {
    public <T> T parseObject(String data, Class<T> cls);
}
