package com.websmithing.gpstracker;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class LoopjHttpClient {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        client.post(url, requestParams, responseHandler);
    }

    public static void postCSV(Context context, String url, HashMap<String, String> data, AsyncHttpResponseHandler responseHandler) {
        String postData = "";
        for(Map.Entry<String, String> entry: data.entrySet()) {
            postData += "0,"+entry.getKey()+","+entry.getValue()+" ";
        }

        try {
            StringEntity entity = new StringEntity(postData);
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/text"));
            client.post(context, url, entity, "application/text", responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void debugLoopJ(String TAG, String methodName,String url, RequestParams requestParams, byte[] response, cz.msebera.android.httpclient.Header[] headers, int statusCode, Throwable t) {

        Log.d(TAG, client.getUrlWithQueryString(false, url, requestParams));

        if (headers != null) {
            Log.e(TAG, methodName);
            Log.d(TAG, "Return Headers:");
            /*
            for (Header h : headers) {
                String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
                Log.d(TAG, _h);
            }
            */

            if (t != null) {
                Log.d(TAG, "Throwable:" + t);
            }

            Log.e(TAG, "StatusCode: " + statusCode);

            if (response != null) {
                Log.d(TAG, "Response: " + new String(response));
            }

        }
    }
}
