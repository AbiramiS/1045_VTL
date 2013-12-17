package com.Activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class User_Login extends Activity {
	
	EditText userid,password;
	int proxyPort;
	String imeino = "", phonesimno = "", imeino1 = "", phonesimno1 = "";
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                      
        setContentView(R.layout.login);        
        EditText textDeviceIMEI = (EditText)findViewById(R.id.editText21);
        EditText textDeviceSim = (EditText)findViewById(R.id.editText22);        
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        textDeviceIMEI.setText(getDeviceIMEI(telephonyManager));
        textDeviceSim.setText(getDeviceSim(telephonyManager));        
        proxyPort=Integer.parseInt(getResources().getString(R.string.proxy_port));
        userid=(EditText)findViewById(R.id.editText5);
		password=(EditText)findViewById(R.id.editText6); 
        Button next=(Button)findViewById(R.id.button3);
		next.setOnClickListener(new EnterListener());
    }
	
	String getDeviceIMEI(TelephonyManager phonyManager){
		
		 //imeino = "12345";
		 if (imeino == null){
			 imeino = "not available";
		 }	
		 imeino = phonyManager.getDeviceId();
		 int phoneType = phonyManager.getPhoneType();
		 switch(phoneType){
		 case TelephonyManager.PHONE_TYPE_NONE:
		  return "NONE: " + imeino;
		
		 case TelephonyManager.PHONE_TYPE_GSM:
		  return imeino;
		
		 case TelephonyManager.PHONE_TYPE_CDMA:
		  return "CDMA: MEID/ESN-" + imeino;
		
		  /*
		  *   for API Level 11 or above
		  *   case TelephonyManager.PHONE_TYPE_SIP:
		  *   return "SIP";
		  */
		
		 default:
		  return "UNKNOWN: ID-" + imeino;
		 }
		
		}


	String getDeviceSim(TelephonyManager phonyManager){
		
		 //phonesimno = "67890";
		 if (phonesimno == null){
			 phonesimno = "not available";
		 }
		 phonesimno = phonyManager.getSimSerialNumber();
		 int phoneType = phonyManager.getPhoneType();
		 switch(phoneType){
		 case TelephonyManager.PHONE_TYPE_NONE:
		  return "NONE: " + phonesimno;
		
		 case TelephonyManager.PHONE_TYPE_GSM:
		  return phonesimno;
		
		 case TelephonyManager.PHONE_TYPE_CDMA:
		  return "CDMA: MEID/ESN-" + phonesimno;
		
		 /*
		  *  for API Level 11 or above
		  *  case TelephonyManager.PHONE_TYPE_SIP:
		  *   return "SIP";
		  */
		
		 default:
		  return "UNKNOWN: ID-" + phonesimno;
		 }
		
		}
    

    
    private class EnterListener implements View.OnClickListener {
	    public void onClick( View view ) { 
	    	Intent intent=getIntent();
	    	boolean done=true;
	    	

	    	//Toast.makeText(getApplicationContext(), imeino, Toast.LENGTH_SHORT).show();
	    	//Toast.makeText(getApplicationContext(), phonesimno, Toast.LENGTH_SHORT).show();
	    	
	    	try{  
	    		if(userid.getText().toString().equals("")) {
		    		done&=false;
		    		Toast.makeText(getApplicationContext(), "Please enter the User Id.", Toast.LENGTH_SHORT).show();
		    	}
		    	else if(password.getText().toString().equals("")) {
		    		done&=false;
		    		Toast.makeText(getApplicationContext(), "Please enter the Password", Toast.LENGTH_SHORT).show();
		    	}		    	
	    		if(done) { 
	    			
	    			SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	                
		    		HttpClient httpClient = new DefaultHttpClient();
		    		HttpGet getRequest=new HttpGet(getResources().getString(R.string.server_url)+pref.getString("serverip", "000000000000000")+getResources().getString(R.string.server_url1)+"userlogin.do?userid="+userid.getText().toString().trim()+"&password="+password.getText().toString().trim());
					//httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(getResources().getString(R.string.proxy_ip), proxyPort));
			        //httpClient.getParams().setParameter("http.protocol.expect-continue", false);
					try {  
						HttpResponse res= httpClient.execute(getRequest);
						InputStream is= res.getEntity().getContent();				                
				        byte[] b=null; 
				        ByteArrayOutputStream bos = new ByteArrayOutputStream();
				        int ch;   
						while ((ch = is.read()) != -1){
							bos.write(ch);
						}         
						b = bos.toByteArray();
						String value = new String(b);
						bos.close(); 
				        is.close();
				        //Toast.makeText(getApplicationContext(), "VALUE IS..."+value, Toast.LENGTH_SHORT).show();
				          
				        if(value.equals("LOGIN SUCCESS")){
				        	Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
				        	Intent inte=new Intent(getApplicationContext(),Home_Page.class);
					        inte.putExtra("foruserid", userid.getText().toString().trim());
					        inte.putExtra("imeino", imeino.trim());
					        inte.putExtra("phonesimno", phonesimno.trim());
					        startActivityForResult(inte, 3);
				        }  
				        else if(value.equals("LOGIN FAILURE")){
				        	Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
				        	startActivity(new Intent(getApplicationContext(), RegFailure.class));
				        }	 			           
					} catch (Exception e) {
						Intent inte=new Intent(getApplicationContext(),Home_Page.class);
				        inte.putExtra("foruserid", userid.getText().toString().trim());
				        inte.putExtra("imeino", imeino.trim());
				        inte.putExtra("phonesimno", phonesimno.trim());
				        startActivityForResult(inte, 3);
						//Toast.makeText(getApplicationContext(), "error..."+e.getMessage(), Toast.LENGTH_SHORT).show();
					} 
		    	}
		    }
	    	catch(Exception e){
	    		 e.printStackTrace();
	    	}
	    }
    }
}