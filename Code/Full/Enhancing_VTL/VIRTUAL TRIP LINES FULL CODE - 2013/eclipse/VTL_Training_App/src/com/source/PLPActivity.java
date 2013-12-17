package com.source;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PLPActivity extends Activity {
    /** Called when the activity is first created. */
    @Override 
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main); 
        findViewById(R.id.widget29).setSelected(true);
        Button fwd=(Button)findViewById(R.id.status);         
        fwd.setOnClickListener(new EnterListener());
    }  
       
    private class EnterListener implements View.OnClickListener{
	    public void onClick( View view ){     
	    	//startActivity(new Intent(getApplicationContext(), GPSLocatorActivity.class));
	    	startActivity(new Intent(getApplicationContext(), Phase1.class));
	    }     
    }         
}                