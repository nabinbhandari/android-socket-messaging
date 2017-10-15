package com.nabinbhandari.android.socketmessaging;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created at 2:05 PM on 10/15/2017.
 *
 * @author Nabin Bhandari
 */

public abstract class ChatActivity extends Activity {

    View chatView;
    private EditText messageView, inputView;
    private Socket socket;
    private PrintWriter out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        chatView = findViewById(R.id.chatView);
        messageView = (EditText) findViewById(R.id.messagesView);
        inputView = (EditText) findViewById(R.id.inputView);
    }

    abstract void setLayout();

    boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    void reset() {
        Utils.closeSilently(out);
        Utils.closeSilently(socket);
        out = null;
        socket = null;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resetUI();
            }
        });
    }

    void resetUI() {
        messageView.getText().clear();
        inputView.getText().clear();
        chatView.setVisibility(View.GONE);
    }

    void onConnected(Socket socket) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatView.setVisibility(View.VISIBLE);
            }
        });
        try {
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            reset();
            return;
        }
        this.socket = socket;
        startListening();
    }

    public void sendMessage(View view) {
        String message = inputView.getText().toString();
        if (out != null && !message.trim().equals("")) {
            sendMessageImpl(message);
            messageView.getText().append("Me: ").append(message).append("\n");
        }
        inputView.getText().clear();
    }

    private void sendMessageImpl(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                out.println(message);
                out.flush();
            }
        }).start();
    }

    private void startListening() {
        new AsyncTask<Void, String, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        String message = in.readLine();
                        if (message == null) {
                            break;
                        } else {
                            publishProgress(message.trim().concat("\n"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.closeSilently(in);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... message) {
                messageView.getText().append("Remote: ").append(message[0]);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                reset();
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        reset();
        super.onDestroy();
    }

}
