package com.claricetechnologies.web.impl;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.claricetechnologies.ClPhoneApplication;
import com.claricetechnologies.data.ClConstants;
import com.claricetechnologies.exception.ClException;
import com.claricetechnologies.utils.ClLogger;
import com.claricetechnologies.web.ClHttpManager;
import com.claricetechnologies.web.ClHttpRequest;
import com.claricetechnologies.web.ClStringRequest;

public class ClHttpManagerImpl implements ClHttpManager {
    private static RequestQueue queue;
    private final int SOCKET_TIMEOUT_MS = 15 * 1000;// Socket timeout of 15secs.
    private final int MAX_RETRIES = 3;// Maximum 3 retries for the API.

    public ClHttpManagerImpl() {
    }

    private synchronized RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(ClPhoneApplication.getContext());
        }
        return queue;
    }

    public <T> void execute(final ClHttpRequest request,
                            final com.claricetechnologies.web.impl.ClCallback<T> callback) {
        ClLogger.d(ClConstants.TAG, "URL: " + request.getRequestUrl());
        if (!request.getRequestUrl().contains("null")) {
            ClStringRequest stringRequest = new ClStringRequest(request
                    .getMethod().ordinal(), request.getRequestUrl(),
                    request.getHeaderParams(), request.getPostBody(),
                    request.getContentType(), new Response.Listener<String>() {
                @SuppressWarnings("unchecked")
                @Override
                public void onResponse(String response) {
                    if (null != response) {
                        //TODO
                    }
                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (null != error && error.networkResponse != null
                            && error.networkResponse.data != null) {
                        ClLogger.e(
                                ClConstants.TAG,
                                "Failure Response: "
                                        + error.getLocalizedMessage(),
                                error.getCause());
                        callback.onFailure(new ClException(
                                error.networkResponse.statusCode,
                                new String(error.networkResponse.data)));
                    } else {
                        ClUtils.hideProgressDialog();
                        callback.onFailure(new ClException(400,
                                new String("Please try again!")));
                    }
                }
            });
            getRequestQueue().add(stringRequest).setRetryPolicy(
                    new DefaultRetryPolicy(SOCKET_TIMEOUT_MS, MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } else {
            ClUtils.hideProgressDialog();
            ClUtils.showToast("Please configure server through settings.");
        }
    }
}
