package com.fpd.api.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.fpd.api.R;
import com.fpd.basecore.application.BaseApplication;

/**
 * Created by t450s on 2016/6/1.
 */
public class ErrorHelper {
    public static String getMessage(Object error) {
        if (error instanceof TimeoutError) {
            return BaseApplication.getApplication().getResources().getString(R.string.netWorkTimeOut);
        } else if (error instanceof NoConnectionError) {
            return BaseApplication.getApplication().getResources().getString(R.string.no_internet);
        } else if (error instanceof NetworkError) {
            return BaseApplication.getApplication().getResources().getString(R.string.networkerror);
        } else if (error instanceof ParseError) {
            return BaseApplication.getApplication().getResources().getString(R.string.network_response_parse_error);
        } else if (isServerProblem(error)) {
            return handleServerError(error, BaseApplication.getApplication());
        }
        return BaseApplication.getApplication().getResources().getString(R.string.slow_network_speed);
    }

    /**
     * Determines whether the error is related to network
     *
     * @param error
     * @return
     */
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError)
                || (error instanceof NoConnectionError);
    }

    /**
     * Determines whether the error is related to server
     *
     * @param error
     * @return
     */
    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError)
                || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to show a message retrieved from
     * the server.
     *
     * @param err
     * @param context
     * @return
     */
    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;
        NetworkResponse response = error.networkResponse;

        if (response != null) {
            return String.format(
                    BaseApplication.getApplication().getResources().getString(R.string.generic_server_error),
                    response.statusCode);
        }
        return BaseApplication.getApplication().getResources().getString(R.string.networkerror);
    }
}
