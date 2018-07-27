package com.kuangclub.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kuangclub.R;
import com.kuangclub.ui.base.BaseActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvMsg;
    private Button btnOpen;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);

        tvMsg = findViewById(R.id.tv_msg);
        btnOpen = findViewById(R.id.btn_open);
        btnClose = findViewById(R.id.btn_close);

        btnOpen.setOnClickListener(this);
        btnClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open:
                connect();
                break;
            case R.id.btn_close:
                break;
        }
    }

    private void connect() {

        EchoWebSocketListener listener = new EchoWebSocketListener();
        Request request = new Request.Builder()
//                .url("ws://echo.websocket.org")
//                .url("ws://localhost:8080/websocket")
                .url("ws://192.168.1.6:8080/websocket")
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newWebSocket(request, listener);

        client.dispatcher().executorService().shutdown();
    }

    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {

//            webSocket.send("hello world");
            webSocket.send("welcome");
//            webSocket.send(ByteString.decodeHex("adef"));
//            webSocket.close(1000, "再见");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("onMessage: " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("onMessage byteString: " + bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            output("onClosing: " + code + "/" + reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            output("onClosed: " + code + "/" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("onFailure: " + t.getMessage());
        }

        private void output(final String content){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvMsg.setText(tvMsg.getText().toString() + content + "\n");
                }
            });
        }

    }

}
