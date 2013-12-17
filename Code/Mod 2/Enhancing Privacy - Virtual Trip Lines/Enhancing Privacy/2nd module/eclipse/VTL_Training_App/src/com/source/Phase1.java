package com.source;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Phase1 extends Activity {
    /** Called when the activity is first created. */
    @Override 
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.phase1); 
        Button fwd=(Button)findViewById(R.id.status);         
        fwd.setOnClickListener(new EnterListener());
    }  
       
    private class EnterListener implements View.OnClickListener{
	    public void onClick( View view ){
	    	//startActivity(new Intent(getApplicationContext(), GPSLocatorActivity.class));
	    	startActivity(new Intent(getApplicationContext(), OpenMap.class));
	    }     
    }         
}                 