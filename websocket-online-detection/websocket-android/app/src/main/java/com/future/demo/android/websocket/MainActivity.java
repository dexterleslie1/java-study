package com.future.demo.android.websocket;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    /**
     * 是否已手动断开websocket连接，此情况不需要websocket自动重连
     */
    private boolean disconnectManually = true;

    private WebSocketClient webSocketClient = null;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(webSocketClient!=null) {
                try {
                    if(webSocketClient.isOpen()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("action", "KEEPALIVE");
                        webSocketClient.send(jsonObject.toString());
                        Log.i(TAG, "websocket发送KEEPALIVE消息");
                    } else {
                        Log.i(TAG, "websocket不为open状态"
                                + ",isClosed=" + webSocketClient.isClosed()
                                + ",isClosing=" + webSocketClient.isClosing()
                                + ",isFlushAndClose=" + webSocketClient.isFlushAndClose());
                    }
                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage()==null?"":ex.getMessage());
                }
            }
            handler.postDelayed(this, 10000);
        }
    };

    /**
     *
     */
    private Runnable runnableReconnect = new Runnable() {
        @Override
        public void run() {
            try {
                if(!disconnectManually) {
                    connect();
                }
            } catch (Exception ex) {
                handler.postDelayed(runnableReconnect, 1000);
                Log.i(TAG, "websocket尝试重新连接服务器");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button = findViewById(R.id.buttonConnect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonConnect = findViewById(R.id.buttonConnect);
                Button buttonDisconnect = findViewById(R.id.buttonDisconnect);
                buttonConnect.setEnabled(false);
                buttonDisconnect.setEnabled(true);
                disconnectManually = false;
                connect();
            }
        });

        button = findViewById(R.id.buttonDisconnect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonConnect = findViewById(R.id.buttonConnect);
                Button buttonDisconnect = findViewById(R.id.buttonDisconnect);
                buttonConnect.setEnabled(true);
                buttonDisconnect.setEnabled(false);
                disconnectManually = true;
                if(webSocketClient!=null) {
                    webSocketClient.close();
                    webSocketClient = null;
                }
            }
        });
        button.setEnabled(false);
        handler.postDelayed(runnable,10000);
    }

    /**
     *
     */
    public synchronized void connect() {
        if(webSocketClient!=null) {
            webSocketClient.close();
            webSocketClient = null;
        }

        EditText editText = findViewById(R.id.editTextWebsocketHost);
        final String websocketHost = editText.getText().toString();
        URI uri = URI.create("ws://" + websocketHost + "/websocketEndpoint");
        webSocketClient = new WebSocketClient(uri, new Draft_6455(), null, 8000) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("action", "CONNECT");
                    webSocketClient.send(jsonObject.toString());
                } catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                }
                Log.i(TAG, "已登录websocket服务器");
            }

            @Override
            public void onMessage(String message) {
                Log.i(TAG, "接收到消息：" + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e(TAG, "websocket服务器断开连接，原因代码：" + code + "，原因：" + reason + "，remote：" + remote);
                if(!disconnectManually) {
                    handler.postDelayed(runnableReconnect, 1000);
                    Log.i(TAG, "websocket尝试重新连接服务器");
                }
            }

            @Override
            public void onError(Exception ex) {
                Log.e(TAG, ex.getMessage(), ex);
            }
        };
        webSocketClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
