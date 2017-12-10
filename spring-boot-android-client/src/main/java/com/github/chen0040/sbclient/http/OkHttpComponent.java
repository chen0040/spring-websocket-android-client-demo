package com.github.chen0040.sbclient.http;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by chen0 on 19/8/2017.
 */

public class OkHttpComponent implements WebClient {

    private OkHttpClient client;
    private String token;
    private String sessionId;

    public OkHttpComponent() {
        client = new OkHttpClient.Builder().build();
    }

    private static final String REQUEST_MEDIA_TYPE_JSON = "application/json; charset=utf-8";
    private static final String REQUEST_MEDIA_TYPE_TEXT = "text/plain; charset=utf-8";

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpClient getClient() {
        return this.client;
    }

    @Override
    public String post(String url, String body, Map<String, String> headers) {

        String type = REQUEST_MEDIA_TYPE_TEXT;
        if(headers.containsKey("Content-Type") && headers.get("Content-Type").equalsIgnoreCase("application/json")){
            type = REQUEST_MEDIA_TYPE_JSON;
        } else if(headers.containsKey("content-type") && headers.get("content-type").equalsIgnoreCase("application/json")){
            type = REQUEST_MEDIA_TYPE_JSON;
        }

        MediaType textPlainMT = MediaType.parse(type);

        Request.Builder requestBuilder = new Request.Builder().url(url)
                .post(RequestBody.create(textPlainMT, body));

        for(Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.build();

        String resp = null;
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {


                String header = response.header("Set-Cookie");
                if(header != null) {
                    String[] parts = header.split(";");
                    for(String part : parts) {
                        String[] pair = part.split("=");
                        String pair_name = pair[0];
                        String pair_value = "";
                        if(pair.length > 1) {
                            pair_value = pair[1];
                        }
                        if(pair_name.equals("XSRF-TOKEN")) {
                            token = pair_value;
                            break;
                        } else if(pair_name.equals("JSESSIONID")) {
                            sessionId = pair_value;
                        }
                    }
                }

                ResponseBody b = response.body();
                if(b != null) {
                    resp = b.string();
                }
            }
            response.close();
        } catch (IOException e) {
            resp = e.getMessage();
        }

        return resp;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String get(String url) throws Exception {
        return get(url, new HashMap<String, String>());
    }

    @Override
    public String put(String url, String body, Map<String, String> headers) {

        String type = REQUEST_MEDIA_TYPE_TEXT;
        if(headers.containsKey("Content-Type") && headers.get("Content-Type").equalsIgnoreCase("application/json")){
            type = REQUEST_MEDIA_TYPE_JSON;
        } else if(headers.containsKey("content-type") && headers.get("content-type").equalsIgnoreCase("application/json")){
            type = REQUEST_MEDIA_TYPE_JSON;
        }

        MediaType textPlainMT = MediaType.parse(type);

        Request.Builder requestBuilder = new Request.Builder().url(url)
                .put(RequestBody.create(textPlainMT, body));

        for(Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.build();

        String resp = null;
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                ResponseBody b = response.body();
                if(b != null) {
                    resp = b.string();
                }
            }
            response.close();
        } catch (IOException e) {
            resp = e.getMessage();
        }

        return resp;
    }

    @Override
    public String delete(String url, Map<String, String> headers) {

        Request.Builder requestBuilder = new Request.Builder()
                .url(url).delete();

        for(Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder
                .build();

        String resp = null;
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                ResponseBody b = response.body();
                if(b != null) {
                    resp = b.string();
                }
            }
            response.close();
        } catch (IOException e) {
            resp = e.getMessage();
        }

        return resp;
    }

    @Override
    public String get(String uri, Map<String, String> headers) {

        Request.Builder requestBuilder = new Request.Builder()
                .url(uri).get();

        for(Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder
                .build();

        String resp = null;
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {

                String header = response.header("Set-Cookie");
                if(header != null) {
                    String[] parts = header.split(";");
                    for(String part : parts) {
                        String[] pair = part.split("=");
                        String pair_name = pair[0];
                        String pair_value = "";
                        if(pair.length > 1) {
                            pair_value = pair[1];
                        }
                        if(pair_name.equals("XSRF-TOKEN")) {
                            token = pair_value;
                            break;
                        }
                    }
                }

                ResponseBody b = response.body();
                if(b != null) {
                    resp = b.string();
                }
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
            resp = e.getMessage();
        }

        return resp;

    }

    @Override
    public String jsonPost(String uri, Map<String, String> data) {
        Map<String, String> headers = new HashMap<>();
        String body = JSON.toJSONString(data);
        headers.put("Content-Type", "application/json");
        return post(uri, body, headers);
    }
}
