package com.nabinbhandari.android.socketmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startServerActivity(View view) {
        startActivity(new Intent(this, ServerActivity.class));
    }

    public void startClientActivity(View view) {
        startActivity(new Intent(this, ClientActivity.class));
    }

}
