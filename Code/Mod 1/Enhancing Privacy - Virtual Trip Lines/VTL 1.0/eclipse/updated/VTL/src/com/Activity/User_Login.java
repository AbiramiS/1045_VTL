package com.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class User_Login extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("Make a Login");
        setContentView(textview);
    }
}