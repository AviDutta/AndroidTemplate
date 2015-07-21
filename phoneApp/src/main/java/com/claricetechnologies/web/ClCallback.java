package com.claricetechnologies.web.impl;


import com.claricetechnologies.exception.ClException;

public interface ClCallback<T> {

    public void onSuccess(T response);

    public void onFailure(ClException exception);

}
