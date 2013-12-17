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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class User_Registration extends Activity {
	
	EditText username,password,mobno,emailid;
	RadioGroup rg;
	int proxyPort;
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        //TextView textview = new TextView(this);
        //textview.setText("Welcome to User Registration");
        setContentView(R.layout.registration);
        proxyPort=Integer.parseInt(getResources().getString(R.string.proxy_port));
        username=(EditText)findViewById(R.id.editText1);
		password=(EditText)findViewById(R.id.editText2);
		mobno=(EditText)findViewById(R.id.editText3);
		emailid=(EditText)findViewById(R.id.editText4);
		rg=(RadioGroup)findViewById(R.id.radioGroup1); 
		Button next=(Button)findViewById(R.id.button3);
		next.setOnClickListener(new EnterListener());   
    }
    private class EnterListener implements View.OnClickListener {
	    public void onClick( View view ) { 
	    	Intent intent=getIntent();
	    	boolean done=true;
	    	String regExpn =
	        	"^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
	        	    +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
	        	      +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
	        	      +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
	        	      +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
	        	      +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
	        Pattern patternObj = Pattern.compile(regExpn);
	        Matcher matcherObj = patternObj.matcher(emailid.getText().toString());
	        Pattern pattern = Pattern.compile("\\d{10}");
	        Matcher matcher = pattern.matcher(mobno.getText().toString());
	        
	    	try{  
	    		RadioButton rb=(RadioButton)findViewById(rg.getCheckedRadioButtonId());
	    		if(username.getText().toString().equals("")) {
		    		done&=false;
		    		Toast.makeText(getApplicationContext(), "Please enter the username", Toast.LENGTH_SHORT).show();
		    	}
		    	else if(password.getText().toString().equals("")) {
		    		done&=false;
		    		Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
		    	}
		    	else if(rb.getText().toString().equals("")) {
		    		done&=false;
		    		Toast.makeText(getApplicationContext(), "Please Choose a Gender", Toast.LENGTH_SHORT).show();
		    	}
		    	else if(mobno.getText().toString().equals("")) {
		    		done&=false;
		    		Toast.makeText(getApplicationContext(), "Please enter your Mobile No.", Toast.LENGTH_SHORT).show();
		    	}
		    	else if(emailid.getText().toString().equals("")) {
		    		done&=false;
		    		Toast.makeText(getApplicationContext(), "Please enter the email", Toast.LENGTH_SHORT).show();
		    	}
		    	else if (!matcher.matches()) {
		        	done&=false;
		    		Toast.makeText(getApplicationContext(), "Mobile Number should be 10 digits", Toast.LENGTH_SHORT).show();
		        }
		    	else if(!matcherObj.matches()) {
		    		done&=false;
					Toast.makeText(getApplicationContext(), "Please enter valid email id...", Toast.LENGTH_SHORT).show();
				}		    	
	    		if(done) { 
		    		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    		HttpClient httpClient = new DefaultHttpClient();
					HttpGet getRequest=new HttpGet(getResources().getString(R.string.server_url)+"userregistration.do?username="+username.getText().toString()+"&password="+password.getText().toString()+"&gender="+rb.getText().toString().trim()+"&mobileno="+mobno.getText().toString().trim()+"&emailid="+emailid.getText().toString().trim());
					httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(getResources().getString(R.string.proxy_ip), proxyPort));
			        httpClient.getParams().setParameter("http.protocol.expect-continue", false);
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
				        if(value.equals("ALREADY EXISTING")){
				        	startActivity(new Intent(getApplicationContext(), RegFailure.class));
				        }
				        else if(value.equals("REGISTRATION SUCCESSFULL")){
				        	startActivity(new Intent(getApplicationContext(), RegSuccess.class));
				        }				           
				        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show(); 
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "error..."+e.getMessage(), Toast.LENGTH_SHORT).show();
					} 
		    	}
		    }
	    	catch(Exception e){
	    		 e.printStackTrace();
	    	}
	    }
    }
}