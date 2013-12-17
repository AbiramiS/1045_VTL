package com.Activity;

import java.math.BigInteger;
import java.util.Random;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DecKeyActivity extends Activity {

	final Context context = this;
	private Button button;
	private EditText result;
	String value = "";
	String newlat="" ,newlon="" , value1="", totvalue="", s2values = "", s3values = "", display = "", useridvalue = "", authen_id = "";
	String strvalueshh = "", strvaluesmm = "", strvaluesss = "", ttotal = "";
	TextView tm;
	BigInteger N ;
	BigInteger E ;
    String imeino = "", phonesimno = "", pubkey = "", deckey = "";
    //RSA rsa = new RSA();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.prompts1); 
		result = (EditText) findViewById(R.id.editTextDialogUserInput11);	
		
		deckey = getIntent().getStringExtra("deckey"); 
		phonesimno = getIntent().getStringExtra("phonesimno");
        imeino = getIntent().getStringExtra("imeino");
        useridvalue = getIntent().getStringExtra("useridvalue");
        authen_id = getIntent().getStringExtra("authen_id");
        s2values = getIntent().getStringExtra("s2values");
        s3values = getIntent().getStringExtra("s3values");
		
		Button fwd=(Button)findViewById(R.id.button11);  
		fwd.setOnClickListener(new EnterListener());		
		Button fwd1=(Button)findViewById(R.id.button12);  
		fwd1.setOnClickListener(new InboxListener());
		
	}
	  
	private class EnterListener implements View.OnClickListener{
	    public void onClick( View view ){
	    	
	    	if(result.getText().toString().trim().equals(deckey)){
	    		Toast.makeText(getApplicationContext(), result.getText().toString().trim()+"---equal---"+deckey.trim(), Toast.LENGTH_LONG).show();
	    		
	    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	    				context);
	     
	    			// set title
	    			alertDialogBuilder.setTitle("Location, Speed and Direction Updates");
	     
	    			// set dialog message
	    			alertDialogBuilder
	    				.setMessage("Choose Your Option ;)")
	    				.setCancelable(false)
	    				.setPositiveButton("Location Updates",new DialogInterface.OnClickListener() {
	    					public void onClick(DialogInterface dialog,int id) {
	    						//Toast.makeText(getApplicationContext(), "Processing......", Toast.LENGTH_LONG).show();
	    			    		// if this button is clicked, close
	    						// current activity
	    						/*Intent inte=new Intent(getApplicationContext(), OpenMap.class); 
	    						inte.putExtra("keyvalue", deckey.trim());
	    			        	inte.putExtra("phonesimno", phonesimno.trim());
	    			        	inte.putExtra("imeino", imeino.trim());
	    			        	inte.putExtra("useridvalue", useridvalue.trim());
	    			        	inte.putExtra("authen_id", authen_id.toString().trim());
	    			        	inte.putExtra("s2values", s2values.toString().trim());
	    			        	inte.putExtra("s3values", s3values.toString().trim());
	    				        startActivityForResult(inte, 3);*/	    						
	    					} 
	    				  })
	    				.setNegativeButton("Speed Updates",new DialogInterface.OnClickListener() {
	    					public void onClick(DialogInterface dialog,int id) {
	    						// if this button is clicked, just close
	    						// the dialog box and do nothing
	    						//dialog.cancel();
	    						
	    						/*Intent inte=new Intent(getApplicationContext(), GPSMain.class); 
	    						inte.putExtra("keyvalue", deckey.trim());
	    			        	inte.putExtra("phonesimno", phonesimno.trim());
	    			        	inte.putExtra("imeino", imeino.trim());
	    			        	inte.putExtra("useridvalue", useridvalue.trim());
	    			        	inte.putExtra("authen_id", authen_id.toString().trim());
	    			        	inte.putExtra("s2values", s2values.toString().trim());
	    			        	inte.putExtra("s3values", s3values.toString().trim());
	    				        startActivityForResult(inte, 3);*/
	    					}
	    				}); 
	     
	    				// create alert dialog
	    				AlertDialog alertDialog = alertDialogBuilder.create();
	     
	    				// show it
	    				alertDialog.show();		        
	    	}
	    } 
    }  
	
	private class InboxListener implements View.OnClickListener{
	    public void onClick( View view ){	    	
	    	Intent intent = new Intent("android.intent.action.MAIN");
	    	intent.setComponent(new ComponentName("com.android.mms","com.android.mms.ui.ConversationList"));
	    	startActivity(intent);
	    } 
    }
}