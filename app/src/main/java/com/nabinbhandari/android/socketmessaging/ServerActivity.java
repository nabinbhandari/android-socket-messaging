package com.nabinbhandari.android.socketmessaging;

import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created at 2:05 PM on 10/15/2017.
 *
 * @author Nabin Bhandari
 */

public class ServerActivity extends ChatActivity {

    private Button startButton;
    private ServerSocket serverSocket;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_server);
        startButton = (Button) findViewById(R.id.startButton);
    }

    @Override
    void reset() {
        super.reset();
        Utils.closeSilently(serverSocket);
        serverSocket = null;
    }

    @Override
    void resetUI() {
        super.resetUI();
        startButton.setText(R.string.start_server);
    }

    public void startServer(View view) {
        if (serverSocket != null) {
            reset();
            return;
        }
        startButton.setText(R.string.disconnect);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(Utils.PORT);
                    Socket socket = serverSocket.accept();
                    onConnected(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                    reset();
                }
            }
        }).start();
    }

}
