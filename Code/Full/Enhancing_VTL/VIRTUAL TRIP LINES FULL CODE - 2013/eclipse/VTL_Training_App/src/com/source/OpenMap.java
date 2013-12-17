package com.source;

import java.util.StringTokenizer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;
import com.google.android.maps.MapView;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;

public class OpenMap extends MapActivity {
	
	private MapController mapController;
	private MyLocationOverlay myLocation;
	static boolean status=true;
	Button loginok1,logincancel1;
	EditText place;
	RelativeLayout login; 
	String curlat1="",carlon1="",sealat1="",sealon1="",distance="";
	private static String NAMESPACE=null;
	private static String URL =null;
	private static String METHOD_NAME =null;
	private static String SOAP_ACTION = null;
	private static String METHOD_NAME1 =null;
	private static String SOAP_ACTION1 = null;
	EditText latvalue,lonvalue;
	public Thread thread=null;
	PropertyInfo propInfo; 
	Button fwd1, fwd2;
	String value11="", llat="", llon="";
	
	String l1val = null,l2val = null,l1val1 = null,l2val1 = null,l1val2 = null,l2val2 = null;
	String newlat="" ,newlon="" , value1="", totvalue="";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.ontrip);          
        latvalue=(EditText)findViewById(R.id.editText15);
        lonvalue=(EditText)findViewById(R.id.editText16);
        
		// Get Mapping Controllers etc
		MapView mapView = (MapView) findViewById(R.id.map_view);
		mapController = mapView.getController();
		mapController.setZoom(11);
		mapView.setBuiltInZoomControls(true);
		
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
	  	    		  }catch(Exception e){}
            
                }
        };
     
        thread = new Thread()
        {
            @Override
            public void run() {
               
                    while(true) {
                    	
                        try {
							sleep(5000);
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
	
	  	
	@Override 
	protected void onPause() {  
		super.onPause();
		myLocation.disableMyLocation();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}