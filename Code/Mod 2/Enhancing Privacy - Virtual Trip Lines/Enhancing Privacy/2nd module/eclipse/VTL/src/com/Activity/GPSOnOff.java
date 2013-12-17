package com.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GPSOnOff extends Activity {
 
 TextView textGpsStatus;
 Button buttonSetGPS_ACTION_LOCATION_SOURCE_SETTINGS;
 Button buttonSetGPS_ACTION_SECURITY_SETTINGS;
 Button proceed, proceed1;
 EditText ip;
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.gpsonoff);
      textGpsStatus = (TextView)findViewById(R.id.gpsstatus);
    
      buttonSetGPS_ACTION_LOCATION_SOURCE_SETTINGS
       = (Button)findViewById(R.id.setgps_ACTION_LOCATION_SOURCE_SETTINGS);  
      
      proceed=(Button)findViewById(R.id.PROCEEDHOME);  
      proceed.setOnClickListener(new EnterListener());
      
      ip=(EditText)findViewById(R.id.editText101);
      proceed1=(Button)findViewById(R.id.button100);  
      proceed1.setOnClickListener(new SystemIP());
    
      buttonSetGPS_ACTION_LOCATION_SOURCE_SETTINGS.setOnClickListener(new Button.OnClickListener(){

   @Override
   public void onClick(View arg0) {
    // TODO Auto-generated method stub
    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
       startActivity(intent);
   }});
    
      buttonSetGPS_ACTION_SECURITY_SETTINGS
   = (Button)findViewById(R.id.setgps_ACTION_SECURITY_SETTINGS);  

      buttonSetGPS_ACTION_SECURITY_SETTINGS.setOnClickListener(new Button.OnClickListener(){

  @Override
  public void onClick(View arg0) {
   // TODO Auto-generated method stub
   Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
      startActivity(intent);
  }});

  }

  @Override
 protected void onResume() {
  // TODO Auto-generated method stub
  super.onResume();
  displayGpsStatus();
 }
  
  private class EnterListener implements View.OnClickListener{
	    public void onClick( View view ){
	    	startActivity(new Intent(getApplicationContext(), HomeScreen.class));
	    }
  }
  
  private class SystemIP implements View.OnClickListener{
	    public void onClick( View view ){
	    	boolean done=true;
	    	if(ip.getText().toString().equals("")) {
	    		done&=false;
	    		Toast.makeText(getApplicationContext(), "Please Enter the Server IP.", Toast.LENGTH_SHORT).show();
	    	}
	    	if(done) { 
	    		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		        Editor edit=pref.edit();
		        edit.putString("serverip", ip.getText().toString());
		        edit.commit();
		        
		        String str = pref.getString("serverip", "No Ip. Address Found");
		        Toast.makeText(getApplicationContext(), "Your Server Ip. is: "+str+"\nServer IP. stored in SQLite Database", Toast.LENGTH_LONG).show();
	    	}
	    }
  }

 private void displayGpsStatus(){
   ContentResolver contentResolver = getBaseContext().getContentResolver();
      boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
      if(gpsStatus){
       textGpsStatus.setText("GPS STATUS: ON");
       proceed.setEnabled(true);
      }else{
       textGpsStatus.setText("GPS STATUS: OFF");
      }
  }
}