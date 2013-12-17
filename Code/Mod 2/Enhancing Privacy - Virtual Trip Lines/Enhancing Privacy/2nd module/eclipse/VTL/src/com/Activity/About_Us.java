package com.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class About_Us extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("Know something About Us");
        setContentView(textview);
    }  
}    