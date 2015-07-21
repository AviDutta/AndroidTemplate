package com.claricetechnologies.web;

import com.claricetechnologies.web.impl.ClHttpManagerImpl;

public class ClHttpManagerFactory {
    public static ClHttpManager getHttpRequestManager(String managerType) {
        // Return based on managerType or read from config
        return new ClHttpManagerImpl();
    }
}
