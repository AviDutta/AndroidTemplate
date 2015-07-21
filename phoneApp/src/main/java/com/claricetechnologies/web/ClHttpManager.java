package com.claricetechnologies.web;

import com.claricetechnologies.web.impl.ClCallback;

public interface ClHttpManager {
    public <T> void execute(ClHttpRequest request, ClCallback<T> callback);
}
