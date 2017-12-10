package com.github.chen0040.sbclient;

import android.os.Build;
import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.Response;

/**
 * Created by chen0 on 9/12/2017.
 */

public class SpringBootWebSocketClient {
    private WebSocketClient mWebSocketClient;


    public void connect(String address) {
        URI uri;
        try {
            uri = new URI(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.print("open");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                System.out.println(message);
                Log.i("Websocket", "Message: " + message);
                /*
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = (TextView)findViewById(R.id.messages);
                        textView.setText(textView.getText() + "\n" + message);
                    }
                });*/
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.i("Websocket", "Error " + e.getMessage());
            }


        };
        try {
            mWebSocketClient.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("reach here");
    }

    public void disconnect() {
        if(mWebSocketClient != null){
            mWebSocketClient.close();
            mWebSocketClient = null;
        }
    }

    public boolean isConnected() {
        return mWebSocketClient != null;
    }

}
