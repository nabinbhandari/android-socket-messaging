package com.nabinbhandari.android.socketmessaging;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.Socket;

/**
 * Created at 2:05 PM on 10/15/2017.
 *
 * @author Nabin Bhandari
 */

public class ClientActivity extends ChatActivity {

    private Button connectButton;
    private EditText ipEditText;

    @Override
    void setLayout() {
        setContentView(R.layout.activity_client);
        connectButton = (Button) findViewById(R.id.connectButton);
        ipEditText = (EditText) findViewById(R.id.ipEditText);
    }

    @Override
    void resetUI() {
        super.resetUI();
        connectButton.setEnabled(true);
        connectButton.setText(R.string.connect);
    }

    public void connect(View view) {
        if (isConnected()) {
            reset();
            return;
        }
        connectButton.setText(R.string.connecting);
        connectButton.setEnabled(false);
        final String ip = ipEditText.getText().toString().trim();
        if (!ip.equals("")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket(ip, Utils.PORT);
                        updateButton();
                        onConnected(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                        showToastOnUI("Failed to connect: " + e.getMessage());
                        reset();
                    }
                }
            }).start();
        }
    }

    private void updateButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectButton.setEnabled(true);
                connectButton.setText(R.string.disconnect);
            }
        });
    }

}
