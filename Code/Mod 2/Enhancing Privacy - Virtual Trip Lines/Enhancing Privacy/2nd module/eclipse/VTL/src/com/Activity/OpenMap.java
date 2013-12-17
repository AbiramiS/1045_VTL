package com.Activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.StringTokenizer;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;

public class OpenMap extends MapActivity {
	
	protected static final int FLAG_ACTIVITY_CLEAR_TOP = 0;
	private MapController mapController;
	private MyLocationOverlay myLocation;
	static boolean status=true;
	Button loginok1,logincancel1;
	EditText place;
	RelativeLayout login;
	int proxyPort;
	float round,round1;
	String curlat1="",carlon1="",sealat1="",sealon1="",distance="";
	private static String NAMESPACE=null;
	private static String URL =null;
	private static String METHOD_NAME =null;
	private static String SOAP_ACTION = null;
	private static String METHOD_NAME1 =null;
	private static String SOAP_ACTION1 = null;
	TextView latvalue,lonvalue;
	public Thread thread=null;
	Button fwd1, fwd2;
	String value11="", llat="", llon="";
	TextView mButtonLabel;
	int vv = 0; 
	int sample = 0;
	private TextView mTimeLabel,mTimerLabel;
	String l1val = null,l2val = null,l1val1 = null,l2val1 = null,l1val2 = null,l2val2 = null;
	String newlat="" ,newlon="" , value1="", totvalue="", s2values = "", s3values = "", display = "", useridvalue = "", authen_id = "";
	TextView tm;
    String imeino = "", phonesimno = "", keyvalue = "", pubkey = "";
        
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.ontrip);  
        
        keyvalue = getIntent().getStringExtra("keyvalue");
        phonesimno = getIntent().getStringExtra("phonesimno");
        imeino = getIntent().getStringExtra("imeino");
        useridvalue = getIntent().getStringExtra("useridvalue");
        authen_id = getIntent().getStringExtra("authen_id");
        s2values = getIntent().getStringExtra("s2values");
        s3values = getIntent().getStringExtra("s3values");
        
        latvalue=(TextView)findViewById(R.id.textView7);
        lonvalue=(TextView)findViewById(R.id.textView8);
        mTimeLabel = (TextView) findViewById(R.id.countDownTv);
        mButtonLabel = (TextView) findViewById(R.id.countDown);        
        mButtonLabel = (TextView) findViewById(R.id.countDown);        
        tm = (TextView) findViewById(R.id.textView9);
        
        proxyPort=Integer.parseInt(getResources().getString(R.string.proxy_port));    
        display = s2values+" day(s) to go...";
        tm.setText(display);             
        // get IMEI
 
        Button updatevtl=(Button)findViewById(R.id.button6);
        updatevtl.setOnClickListener(new UpdateListener());
         
        Button countDownButton = (Button) findViewById(R.id.countDown);       
        countDownButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		       		
        		CountDownTimer timer1 = new CountDownTimer(90000,1000){
        			@Override   
        			public void onFinish() {        				
        				mTimeLabel.setText("Done!");
        			}
          
        			@Override
        			public void onTick(long millisUntilFinished) {        				
        				
        				int seconds = (int) (millisUntilFinished / 1000);  
        				
        				//int hours = Integer.parseInt(strvaluesmm) / 60;
        				//int minutes = Integer.parseInt(strvaluesss) / 60;
        				//seconds = Integer.parseInt(strvaluesss) % 60;
        				
        				//Toast.makeText(getApplicationContext(), hours+"-"+minutes+"-"+seconds, Toast.LENGTH_LONG).show();
        				
        				int minutes = seconds / 60;
        				int hours = minutes / 60;
        				seconds = seconds % 60;        				 
        				mTimeLabel.setText(hours + ":" + minutes + ":" + String.format("%02d", seconds));        				  
        			}        	
        		}.start();         		
        	}
        });  
                        
		// Get Mapping Controllers etc
		MapView mapView = (MapView) findViewById(R.id.map_view);
		mapController = mapView.getController();
		mapController.setZoom(11);
		mapView.setBuiltInZoomControls(true);
		
		//Drawable marker=getResources().getDrawable(android.R.drawable.star_big_on);
		Drawable marker=getResources().getDrawable(R.drawable.triplinesnew);
	    int markerWidth = marker.getIntrinsicWidth();  
	    int markerHeight = marker.getIntrinsicHeight();
	    marker.setBounds(0, markerHeight, markerWidth, 0);
	    
	    MyItemizedOverlay myItemizedOverlay
	     = new MyItemizedOverlay(marker, OpenMap.this);
	    mapView.getOverlays().add(myItemizedOverlay);
	         
	    //String path=13.03968+","+80.21096+" "+13.071201+","+80.191083+" "+13.002132+","+80.256364;
	    String pairs[] = s3values.split("\n");
		
		for (String pair : pairs) {					    
			String coordinates[] = pair.split(",");
			myItemizedOverlay.addItem(new GeoPoint((int)(Double.parseDouble(coordinates[0]) * 1E6), (int)(Double.parseDouble(coordinates[1]) * 1E6)), "myPoint1", "myPoint2");
		} 
		 
		// Add the MyLocationOverlay
		myLocation = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocation);   
		        
		myLocation.enableMyLocation();
		
		myLocation.runOnFirstFix(new Runnable() {
		    public void run() {
		    	mapController.animateTo(myLocation.getMyLocation());
		    	
		     }
		});
		 
		/**/
		final Handler handler=new Handler();
        final Runnable r = new Runnable()
        {
	        
            @SuppressWarnings("deprecation")
			public void run() 
            {            		
            		try {  
            			Encryption enc = new Encryption();     
            			value11=myLocation.getMyLocation().toString().trim(); 
            			StringTokenizer st=new StringTokenizer(value11, ",");
        	    		while(st.hasMoreTokens()){
        	    			l1val=st.nextToken();
        	    			l2val=st.nextToken();
        	    		}
        	    		l1val1=l1val.substring(0, 2);
        	    		l2val1=l2val.substring(0, 2);
        	    		l1val2=l1val.substring(2, 8);
        	    		l2val2=l2val.substring(2, 8);			    		
        	    		newlat=l1val1+"."+l1val2;
        	    		newlon=l2val1+"."+l2val2;           	    		
     	 	    		latvalue.setText(newlat);   
     		    		lonvalue.setText(newlon); 
     		    		
     		    		String latlonvalues = new String();
     		    		latlonvalues = newlat.trim()+"&&&&&"+newlon.trim();            			       			
        	    		String totval = enc.encrypt(latlonvalues);
     		    		
     		    		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        	    		HttpClient httpClient = new DefaultHttpClient();
        	    		
        	    		String ip = pref.getString("serverip", "No Ip. Address Found");
        	            String str="http://"+ip+":9999/VTL/";
        	            
        	            HttpGet getRequest=new HttpGet(getResources().getString(R.string.server_url)+pref.getString("serverip", "000000000000000")+getResources().getString(R.string.server_url1)+"launchapp.do?authenid="+authen_id+"&phonelineno="+phonesimno+"&imeino="+imeino+"&userid="+useridvalue+"&keyvalue="+keyvalue.trim()+"&totval="+totval.trim());
        				//httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(getResources().getString(R.string.proxy_ip), proxyPort));
        		        //httpClient.getParams().setParameter("http.protocol.expect-continue", false);  
        		                      	 	        		   
     		    		round = Round(Float.valueOf(newlat),4); 
     		    		round1 = Round(Float.valueOf(newlon),4); 
     		    		                  
     		    		String pairs[] = s3values.split("\n");     		   		
     		    		float triplineidvalue; 
     		    		float triplineidvalue1;
     		    		float additionalvaluelan1;
     		    		float decrementalvaluelan2;  
     		    		float additionalvaluelon1;   
     		    		float decrementalvaluelon2;     		    		
     		    		float round11, round12;   
     		    		       
	     		   		for (String pair : pairs) {					    
	     		   			String coordinates[] = pair.split(",");
	     		   			triplineidvalue = Round(Float.valueOf(coordinates[0]),4);
	     		   			triplineidvalue1 = Round(Float.valueOf(coordinates[1]),4);
	     		   			
	     		   			round11 = (float) (round1 + 0.0001);
	     		   			round12 = (float) (round1 + 0.00008);
	     		   			 
	     		   			additionalvaluelan1 = (float) (round + 0.0001);
	     		   			//additionalvaluelon1 = (float) (round1 + 0.0001);	     		   			
	     		   			    
		     		   		if(round==triplineidvalue && (round1==triplineidvalue1)){
		     		   		 	            
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
					        String content1="",content2="",content3="",content4="";
					        StringTokenizer t1234=new StringTokenizer(value,"***");
                            while(t1234.hasMoreTokens()){
                                content1=t1234.nextToken();
                                content2=t1234.nextToken();
                                content3=t1234.nextToken();
                                content4=t1234.nextToken();
                            }                                  
                           //hm.put(content2,content3); 
					         
						        if(content1.equals("PROCESS TERMINATED")) {
						        	Toast.makeText(getApplicationContext(), "ALERT:\n\nTerminating Process", Toast.LENGTH_SHORT).show();
						            android.os.Process.killProcess(android.os.Process.myPid());
			     		   		}  
						        else if(content1.equals("PROCESS EXIT")) {
						        	Toast.makeText(getApplicationContext(), "Terminating Process", Toast.LENGTH_SHORT).show();
						            android.os.Process.killProcess(android.os.Process.myPid());
			     		   		}      
						        else {     
						        	Toast.makeText(getApplicationContext(), "CROSSING TRIP LINE", Toast.LENGTH_SHORT).show();
			     		   		}    
		     		   		}	  
	     		   		}  
	  	    		  }
            		  catch(Exception e){
            			  
	  	    		  }            
                }

            public float Round(float Rval, int Rpl) {
          	  float p = (float)Math.pow(10,Rpl);
          	  Rval = Rval * p;
          	  float tmp = Math.round(Rval);
          	  return (float)tmp/p;
          	  }  
        };
     
        thread = new Thread()
        {
            @Override
            public void run() {
               
                    while(true) {
                    	
                        try { 
							sleep(10000);   
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						} 
                        handler.post(r); 
                    }
               }
        };

        thread.start();
		/**/
		
    }

	
	@Override
	protected void onResume() {
		super.onResume();
		myLocation.enableMyLocation();
	}
	
	protected void onDraw(Canvas canvas) {
		 
		canvas.drawColor(Color.BLACK);
		Paint p = new Paint();
		// smooths 
		p.setAntiAlias(true);
		p.setColor(Color.RED);
		p.setStrokeWidth(4.5f);
		// opacity
		p.setAlpha(0x80); //
		// drawLine (float startX, float startY, float stopX, float stopY,
		// Paint paint)			
		canvas.drawLine(0, 0, 200, 200, p);
		canvas.drawLine(200, 220, 0, 400, p);
       
	} 
	
	private class UpdateListener implements View.OnClickListener {
		    public void onClick( View view ) { 
		    	
		    	SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    		HttpClient httpClient = new DefaultHttpClient();
	    		
	    		String ip = pref.getString("serverip", "No Ip. Address Found");
	            String str="http://"+ip+":9999/VTL/";
	            
	            HttpGet getRequest=new HttpGet(getResources().getString(R.string.server_url)+pref.getString("serverip", "000000000000000")+getResources().getString(R.string.server_url1)+"updatevtl.do?userid2="+useridvalue.trim());
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
		    	
		    }
			catch(Exception e){
				
			}
		} 
   }  
	

	   
	
	  public void showRouteRequirement(){
	    	status=false;		
	    }
	  	
	@Override 
	protected void onPause() {  
		super.onPause();
		myLocation.disableMyLocation();
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();  // Always call the superclass	    
	    // Stop method tracing that the activity started during onCreate()
	    android.os.Debug.stopMethodTracing();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}