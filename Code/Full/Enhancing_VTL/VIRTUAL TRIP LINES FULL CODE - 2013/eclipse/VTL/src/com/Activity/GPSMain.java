package com.Activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

public class GPSMain extends Activity implements LocationListener {

LocationManager locationManager;
LocationListener locationListener;

//text views to display latitude and longitude
TextView latituteField;
TextView longitudeField;
TextView currentSpeedField;
TextView kmphSpeedField;
TextView avgSpeedField;
TextView avgKmphField;

//objects to store positional information
protected double lat;
protected double lon;

//objects to store values for current and average speed
protected double currentSpeed;
protected double kmphSpeed;
protected double avgSpeed;
protected double avgKmph;
protected double totalSpeed;
protected double totalKmph;
String imeino = "", phonesimno = "", s2values = "", s3values = "", useridvalue = "", authen_id = "";

//counter that is incremented every time a new position is received, used to calculate average speed
int counter = 0;
int proxyPort;
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.speedupdates);

    proxyPort=Integer.parseInt(getResources().getString(R.string.proxy_port));
    phonesimno = getIntent().getStringExtra("phonesimno");
    imeino = getIntent().getStringExtra("imeino");
    useridvalue = getIntent().getStringExtra("useridvalue");
    authen_id = getIntent().getStringExtra("authen_id");
    s2values = getIntent().getStringExtra("s2values");
    s3values = getIntent().getStringExtra("s3values");
    run();
}

@Override
public void onResume() {
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
    super.onResume();
}

@Override
public void onPause() {
    locationManager.removeUpdates(this);
    super.onPause();
}

private void run(){

    final Criteria criteria = new Criteria();

    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    criteria.setSpeedRequired(true);
    criteria.setAltitudeRequired(false);
    criteria.setBearingRequired(false);
    criteria.setCostAllowed(true);
    criteria.setPowerRequirement(Criteria.POWER_LOW);
    //Acquire a reference to the system Location Manager

    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    // Define a listener that responds to location updates
    locationListener = new LocationListener() {

        public void onLocationChanged(Location newLocation) {

            counter++;

            //current speed fo the gps device
            currentSpeed = round(newLocation.getSpeed(),3,BigDecimal.ROUND_HALF_UP);
            kmphSpeed = round((currentSpeed*3.6),3,BigDecimal.ROUND_HALF_UP);

            //all speeds added together
            totalSpeed = totalSpeed + currentSpeed;
            totalKmph = totalKmph + kmphSpeed;

            //calculates average speed
            avgSpeed = round(totalSpeed/counter,3,BigDecimal.ROUND_HALF_UP);
            avgKmph = round(totalKmph/counter,3,BigDecimal.ROUND_HALF_UP);

            //gets position
            lat = round(((double) (newLocation.getLatitude())),6,BigDecimal.ROUND_HALF_UP);
            lon = round(((double) (newLocation.getLongitude())),6,BigDecimal.ROUND_HALF_UP);

            latituteField = (TextView) findViewById(R.id.lat);
            longitudeField = (TextView) findViewById(R.id.lon);             
            currentSpeedField = (TextView) findViewById(R.id.speed);
            kmphSpeedField = (TextView) findViewById(R.id.kmph);
            avgSpeedField = (TextView) findViewById(R.id.avgspeed);
            avgKmphField = (TextView) findViewById(R.id.avgkmph);

            latituteField.setText("Current Latitude:  "+String.valueOf(lat));
            longitudeField.setText("Current Longitude:  "+String.valueOf(lon)); 
            currentSpeedField.setText("Current Speed (m/s):  "+String.valueOf(currentSpeed));
            kmphSpeedField.setText("Current Speed (kmph):  "+String.valueOf(kmphSpeed));
            avgSpeedField.setText("Average Speed (m/s):  "+String.valueOf(avgSpeed));
            avgKmphField.setText("Average Speed (kmph):  "+String.valueOf(avgKmph));
            
            Toast.makeText(getApplicationContext(), "Current Latitude:  "+String.valueOf(lat)+"\n"+"Current Longitude:  "+String.valueOf(lon)+"\n"+"Current Speed (m/s):  "+String.valueOf(currentSpeed)+"\n"+"Current Speed (kmph):  "+String.valueOf(kmphSpeed)+"\n"+"Average Speed (m/s):  "+String.valueOf(avgSpeed)+"\n"+"Average Speed (kmph):  "+String.valueOf(avgKmph), Toast.LENGTH_LONG).show();
            
            HttpClient httpClient = new DefaultHttpClient();
            SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            
            
            HttpGet getRequest=new HttpGet(getResources().getString(R.string.server_url)+pref.getString("serverip", "000000000000000")+getResources().getString(R.string.server_url1)+"speedupdates.do?authenid="+authen_id.trim()+"&useridvalue="+useridvalue.trim()+"&latitude="+String.valueOf(lat).trim()+"&longitude="+String.valueOf(lon).trim()+"&currentSpeed="+String.valueOf(currentSpeed).trim()+"&kmphSpeed="+String.valueOf(kmphSpeed).trim()+"&avgSpeed="+String.valueOf(avgSpeed).trim()+"&avgKmph="+String.valueOf(avgKmph).trim());
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
		        
		        Toast.makeText(getApplicationContext(), "value is-----"+value, Toast.LENGTH_SHORT).show();
			}
			catch(Exception e){
				e.printStackTrace();
			}
  
        }

        //not entirely sure what these do yet
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}

    };

    // Register the listener with the Location Manager to receive location updates
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
}

//Method to round the doubles to a max of 3 decimal places 
public static double round(double unrounded, int precision, int roundingMode)
{
    BigDecimal bd = new BigDecimal(unrounded);
    BigDecimal rounded = bd.setScale(precision, roundingMode);
    return rounded.doubleValue();
}


@Override
public void onLocationChanged(Location location) {
    // TODO Auto-generated method stub

}

@Override
public void onProviderDisabled(String provider) {
    // TODO Auto-generated method stub

}

@Override
public void onProviderEnabled(String provider) {
    // TODO Auto-generated method stub

}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

}

}

