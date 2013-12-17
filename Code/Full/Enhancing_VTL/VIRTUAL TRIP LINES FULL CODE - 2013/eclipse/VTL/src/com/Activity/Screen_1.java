package com.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Screen_1 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_1);
        findViewById(R.id.widget30).setSelected(true);
          
        TextView device = (TextView)findViewById(R.id.device);
        TextView model = (TextView)findViewById(R.id.model);
        TextView product = (TextView)findViewById(R.id.product);
        TextView codename = (TextView)findViewById(R.id.codename);
        TextView incremental = (TextView)findViewById(R.id.incremental);
        TextView release = (TextView)findViewById(R.id.release);
        TextView sdk = (TextView)findViewById(R.id.sdk);
        TextView sdkInt = (TextView)findViewById(R.id.sdk_int);
        TextView arch = (TextView)findViewById(R.id.arch);
        TextView name = (TextView)findViewById(R.id.name);
        TextView version = (TextView)findViewById(R.id.version);
           
        device.setText("android.os.Build.DEVICE: " + android.os.Build.DEVICE);
        model.setText("android.os.Build.MODEL: " + android.os.Build.MODEL);
        product.setText("android.os.Build.PRODUCT: " + android.os.Build.PRODUCT);
        codename.setText("android.os.Build.VERSION.CODENAME: " + android.os.Build.VERSION.CODENAME);
        incremental.setText("android.os.Build.VERSION.INCREMENTAL: " + android.os.Build.VERSION.INCREMENTAL);
        release.setText("android.os.Build.VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE);
        sdk.setText("android.os.Build.VERSION.SDK: " + android.os.Build.VERSION.SDK);
        sdkInt.setText("android.os.Build.VERSION.SDK_INT: " + String.valueOf(android.os.Build.VERSION.SDK_INT));
        arch.setText("android.os.arch: " + System.getProperty("os.arch"));
        name.setText("android.os.name: " + System.getProperty("os.name"));
        version.setText("android.os.version: " + System.getProperty("os.version"));
          
    	Toast.makeText(getApplicationContext(), "Project VTL Welcomes You", Toast.LENGTH_SHORT).show();
        Button fwd=(Button)findViewById(R.id.button1); 
        fwd.setOnClickListener(new EnterListener());
    }
    private class EnterListener implements View.OnClickListener{
	    public void onClick( View view ){
	    	//Toast.makeText(getApplicationContext(), "------------", Toast.LENGTH_SHORT).show();
	    	startActivity(new Intent(getApplicationContext(), GPSOnOff.class));
	    	//startActivity(new Intent(getApplicationContext(), Phase1.class));
	    }
    }
}