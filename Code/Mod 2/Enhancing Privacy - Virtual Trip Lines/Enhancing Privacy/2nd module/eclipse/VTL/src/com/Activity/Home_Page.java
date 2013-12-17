package com.Activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Home_Page extends Activity {
	
	EditText userid,password,authen_id;
	int proxyPort;
	int count = 0;
	String hh, mm, ss;
    String s1 = "", s2 = "", s3 = "", s4 = "";
	String foruserid;
	Context con;
	String content1="",content2="";
	Button updatevtl,requestid;
	final Context context = this;
	String imeino = "", phonesimno = "";
	TelephonyManager telephonyManager;
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}*/   

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                      
        setContentView(R.layout.homepage); 
        authen_id=(EditText)findViewById(R.id.editText7);
        proxyPort=Integer.parseInt(getResources().getString(R.string.proxy_port));        
        foruserid=getIntent().getStringExtra("foruserid");
        imeino=getIntent().getStringExtra("imeino");
        phonesimno=getIntent().getStringExtra("phonesimno");
        requestid=(Button)findViewById(R.id.button2);
        requestid.setOnClickListener(new RequestListener());
        updatevtl=(Button)findViewById(R.id.button5);
        updatevtl.setOnClickListener(new DrivingListener());  
    }
    

     
    private class DrivingListener implements View.OnClickListener {
	    public void onClick( View view ) {	 
				try {
		        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		    				context);			        
		    			// set title
		    			alertDialogBuilder.setTitle("User Approval Needed");			     
		    			// set dialog message
		    			alertDialogBuilder
		    				.setMessage("Click Yes to Proceed!")
		    				.setCancelable(false)
		    				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
		    					public void onClick(DialogInterface dialog,int id) {
		    						// if this button is clicked, close
		    						// current activity
			    						
	    						// get prompts.xml view
	    						LayoutInflater li = LayoutInflater.from(context);
	    						View promptsView = li.inflate(R.layout.prompts, null);

	    						AlertDialog. Builder alertDialogBuilder = new AlertDialog.Builder(
	    								context);  
 
	    						// set prompts.xml to alertdialog builder
	    						alertDialogBuilder.setView(promptsView);

	    						final EditText userInput = (EditText) promptsView
	    								.findViewById(R.id.editTextDialogUserInput);

	    						// set dialog message
	    						alertDialogBuilder 
	    								.setCancelable(false)
	    								.setPositiveButton("OK",
	    										new DialogInterface.OnClickListener() {
	    											public void onClick(DialogInterface dialog,
	    													int id) {
	    												// get user input and set it to result
	    												// edit text
	    												
	    												if(userInput.getText().toString().equals("")) {
	    										    		Toast.makeText(getApplicationContext(), "Please Enter the Vehicle Id.", Toast.LENGTH_SHORT).show();
	    										    	}
	    												else if(!userInput.getText().toString().equals("")) {
	    													
	    													SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    										    		HttpClient httpClient = new DefaultHttpClient();
	    										    		HttpGet getRequest=new HttpGet(getResources().getString(R.string.server_url)+pref.getString("serverip", "000000000000000")+getResources().getString(R.string.server_url1)+"viewvtl.do?luserid="+foruserid.trim()+"&authenid="+userInput.getText().toString().trim());
	    													//httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(getResources().getString(R.string.proxy_ip), proxyPort));
	    											        //httpClient.getParams().setParameter("http.protocol.expect-continue", false);
	    													try{ 
	    													     
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
	    											        Toast.makeText(getApplicationContext(), "value----"+value , Toast.LENGTH_LONG).show();	    											       	
	    											         
	    											        if(value.equals("Validity Expired")){
	    											        	Toast.makeText(getApplicationContext(), "Your Validity has Expired", Toast.LENGTH_LONG).show();	
	    											        
	    											        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	    																context);
	    														// set title
	    														alertDialogBuilder.setTitle("Validity Confirmation");
	    														// set dialog message
	    														alertDialogBuilder 
	    																.setMessage("Click Yes to Confirm!")
	    																.setCancelable(false)
	    																.setPositiveButton("Yes",
	    																		new DialogInterface.OnClickListener() {
	    																			public void onClick(DialogInterface dialog,
	    																					int id) {
	    																				// if this button is clicked, close
	    																				// current activity
	    																				SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    																				HttpClient httpClient = new DefaultHttpClient();
	    																				HttpGet getRequest=new HttpGet(getResources().getString(R.string.server_url)+pref.getString("serverip", "000000000000000")+getResources().getString(R.string.server_url1)+"updatevtl.do?userid2="+foruserid.trim());
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
	    																					final String value = new String(b);
	    																					bos.close(); 
	    																			        is.close();
	    																			        if(value.equals("UPDATED SUCCESSFULLY")){
	    																			        	Toast.makeText(getApplicationContext(), "Successfully Renewed", Toast.LENGTH_LONG).show();	
		    																			        startActivity(new Intent(getApplicationContext(), VTLActivity.class));
	    																			        }
	    																			        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
	    																							context);	    																			 
	    																						// set title
	    																						alertDialogBuilder.setTitle("Renewal Successfull");	    																			 
	    																						// set dialog message 
	    																						alertDialogBuilder
	    																							.setMessage("Click to Terminate!")
	    																							.setCancelable(false)
	    																							.setPositiveButton("Logout",new DialogInterface.OnClickListener() {
	    																								public void onClick(DialogInterface dialog,int id) {
	    																									// if this button is clicked, close
	    																									// current activity	    																									
	    																									startActivity(new Intent(getApplicationContext(), VTLActivity.class));
	    																								} 
	    																							  })
	    																							.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
	    																								public void onClick(DialogInterface dialog,int id) {
	    																									// if this button is clicked, just close
	    																									// the dialog box and do nothing
	    																									dialog.cancel();
	    																								} 
	    																							});	    																			 
	    																							// create alert dialog
	    																							AlertDialog alertDialog = alertDialogBuilder.create();	    																			 
	    																							// show it
	    																							alertDialog.show(); 
		    																		    }
		    																			catch (Exception e){	    																				
		    																			}	    																				
	    																			}
	    																		}) 
	    																.setNegativeButton("No",
	    																		new DialogInterface.OnClickListener() {
	    																			public void onClick(DialogInterface dialog,
	    																					int id) {
	    																				// if this button is clicked, just close
	    																				// the dialog box and do nothing
	    																				dialog.cancel();
	    																			}
	    																		});

	    														// create alert dialog
	    														AlertDialog alertDialog = alertDialogBuilder.create();

	    														// show it
	    														alertDialog.show();	    											        
	    											        
	    											        } 
	    											        if(value.contains("Sorry")){
	    											        	Toast.makeText(getApplicationContext(), "Sorry... Trip Lines Not Updated.", Toast.LENGTH_LONG).show();
	    											        	startActivity(new Intent(getApplicationContext(), VTLActivity.class));
	    											        		
	    											        }
	    											        
	    											           
	    											        if(value.equals("INVALID AUTHENTICATION ID.")){
	    											        	count++;
	    											        	Toast.makeText(getApplicationContext(), "INVALID AUTHENTICATION ID.", Toast.LENGTH_LONG).show();	
	    											        	if(count>=2){ 			        		
	    											        		Uri packageURI = Uri.parse("package:com.Activity");
	    											        		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
	    											        		startActivity(uninstallIntent);
	    											        	}		 	        	
	    											        }	
	    											        else {	    											        	
	    											        	StringTokenizer sst = new StringTokenizer(value, "/");
		    											        while(sst.hasMoreTokens()){
		    											        	s1 = sst.nextToken();
		    											        	s2 = sst.nextToken();
		    											        	s3 = sst.nextToken();
		    											        	s4 = sst.nextToken();
		    											        }     
		    											        /*String deckey = "", pubkey = "";
		    											        StringTokenizer sst12 = new StringTokenizer(s4, ":");
		    											        while(sst12.hasMoreTokens()){
		    											        	deckey = sst12.nextToken();
		    											        	pubkey = sst12.nextToken();
		    											        } */
		    											        
		    											        //Toast.makeText(getApplicationContext(), s1+">>>>>>>>>>"+s2+">>>>>>>>>>"+s3, Toast.LENGTH_LONG).show();
		    											        /*Toast.makeText(getApplicationContext(), ">>>>>>>>>>"+s4, Toast.LENGTH_LONG).show();
		    											        
		    											        StringTokenizer sst1 = new StringTokenizer(s1, ":");
		    											        while(sst1.hasMoreTokens()){
		    											        	hh = sst1.nextToken();
		    											        	mm = sst1.nextToken();
		    											        	ss = sst1.nextToken();
		    											        } 	    	 										        	
	    											        	 //int ttotal = 0; 	    											        
		    											        int ttotal = Integer.parseInt(hh)*60*60;
		    											        int ttotal1 = Integer.parseInt(mm)*60;
		    											        int ttotalpop = ttotal + ttotal1 + Integer.parseInt(ss);
		    											        String ttotalpop1 = String.valueOf(ttotalpop);	 */
		    											        
		    											        /*INPUT PROCESS FOR DECRYPTION KEY*/		    											          
		    											        //String imeino = tm1.getDeviceId();
		    													//String phonelineno = tm1.getLine1Number();
		    											        
		    											        //Toast.makeText(getApplicationContext(), "---deckey.trim()"+deckey.trim(), Toast.LENGTH_LONG).show();
		    											        //Toast.makeText(getApplicationContext(), "---pubkey.trim()"+pubkey.trim(), Toast.LENGTH_LONG).show();
		    											       
		    											         
								        						Intent inte=new Intent(getApplicationContext(), DecKeyActivity.class); 
																inte.putExtra("deckey", s4.trim());
		    										        	inte.putExtra("phonesimno", phonesimno.trim());
		    										        	inte.putExtra("imeino", imeino.trim());
		    										        	inte.putExtra("useridvalue", foruserid.trim());
		    										        	inte.putExtra("authen_id", authen_id.getText().toString().trim());
		    										        	inte.putExtra("s2values", s2.toString().trim());
		    										        	inte.putExtra("s3values", s3.toString().trim()); 
		    										        	startActivityForResult(inte, 3);	    								
																 
		    											        /*INPUT PROCESS FOR DECRYPTION KEY*/		    											        
	    											        }
	    											            													
	    													}
	    													catch(Exception e) {	
	    														
	    													}
	    										    	}
	    												
	    												// result.setText(userInput.getText());
	    											}
	    										})
	    								.setNegativeButton("Cancel",
	    										new DialogInterface.OnClickListener() {
	    											public void onClick(DialogInterface dialog,
	    													int id) {
	    												dialog.cancel();
	    											}
	    										});

	    						// create alert dialog
	    						AlertDialog alertDialog = alertDialogBuilder.create();

	    						// show it 
	    						alertDialog.show();
	    						//Home_Page.this.finish();
		    					}
		    				  })
	    				.setNegativeButton("No",new DialogInterface.OnClickListener() {
	    					public void onClick(DialogInterface dialog,int id) {
	    						// if this button is clicked, just close
	    						// the dialog box and do nothing
	    						dialog.cancel();
	    					}
	    				});			     
	    				// create alert dialog
	    				AlertDialog alertDialog = alertDialogBuilder.create();			     
	    				// show it
	    				alertDialog.show();
		    }           
	    	catch(Exception e){
	    		 e.printStackTrace();
	    	}
	    }
    }
    
    private class RequestListener implements View.OnClickListener {
	    public void onClick( View view ) { 
	    	Intent intent=getIntent();
	    	boolean done=true;
	    	try{  	     				    	
	    		if(done) { 
		    		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    		HttpClient httpClient = new DefaultHttpClient();
		    		HttpGet getRequest=new HttpGet(getResources().getString(R.string.server_url)+pref.getString("serverip", "000000000000000")+getResources().getString(R.string.server_url1)+"requestid.do?userid="+foruserid.trim());
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
				        StringTokenizer t1234=new StringTokenizer(value,"***");
                        while(t1234.hasMoreTokens()){
                            content1=t1234.nextToken();
                            content2=t1234.nextToken();
                        }                        
                        if(!content1.equals("") && !content2.equals("")){
                        	Thread.sleep(1000);
                        	authen_id.setText(content2);
                        	requestid.setEnabled(false);
                        	updatevtl.setEnabled(true);
                        } 
                        
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