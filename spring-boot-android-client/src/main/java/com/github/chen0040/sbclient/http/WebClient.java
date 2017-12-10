package com.github.chen0040.sbclient.http;


import java.util.Map;


/**
 * Created by xschen on 6/4/2017.
 */
public interface WebClient {
   String get(String url) throws Exception;

   String put(String url, String body, Map<String, String> headers);

   String delete(String url, Map<String, String> headers);

   String get(String url, Map<String, String> headers);

   String post(String url, String body, Map<String, String> headers);

   String getToken();
   String getSessionId();

   String jsonPost(String uri, Map<String, String> data);
}

