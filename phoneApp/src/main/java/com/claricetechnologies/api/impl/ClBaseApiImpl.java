package com.claricetechnologies.api.impl;

import com.claricetechnologies.ClPhoneApplication;
import com.claricetechnologies.data.ClConstants;

public abstract class ClBaseApiImpl {

    protected ClConstants.WebService mWebService;

    public ClBaseApiImpl() {
        mWebService = ClPhoneApplication.getWebService();
    }
}
