package com.nabinbhandari.android.socketmessaging;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

    public void downloadFull(View view) {
        Uri uri = Uri.parse("market://details?id=com.nabinbhandari.lam.android");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        myAppLinkToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Play store not found!", Toast.LENGTH_SHORT).show();
        }
    }

}
