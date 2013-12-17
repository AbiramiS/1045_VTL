package com.Activity;

import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Terminator extends Activity {
	
    /** Called when the activity is first created. */
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	        return true;
	    } 
	    return super.onKeyDown(keyCode, event);
	}
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminator);
        findViewById(R.id.widget29).setSelected(true);
        Button fwd=(Button)findViewById(R.id.button13);  
        fwd.setOnClickListener(new EnterListener());
    } 
      
    private class EnterListener implements View.OnClickListener{
	    public void onClick( View view ){
	    	startActivity(new Intent(getApplicationContext(), VTLActivity.class));
	    } 
    }
}