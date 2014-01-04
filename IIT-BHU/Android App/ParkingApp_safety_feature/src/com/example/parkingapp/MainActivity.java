package com.example.parkingapp;

import java.util.ArrayList;

import com.example.parkingapp.ShakeDetector.OnShakeListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	 
    // Google Map
    private GoogleMap googleMap;
    private DataBaseHelper myDbHelper;
    private Cursor c;
    private short SMS_PORT = 8901;
    private Button sms,email;
    
    
    static ShakeDetector mShakeDetector;
	static SensorManager mSensorManager;
	static Sensor mAccelerometer;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
 
        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        sms = (Button)findViewById(R.id.button2);
        email = (Button)findViewById(R.id.button1);
        
        email.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				sendEmail();
				// TODO Auto-generated method stub
				
			}
		});
        sms.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				sendSms("9465973850", "hello I need help", true);
				// TODO Auto-generated method stub
				
			}
		});
        
        mSensorManager = (SensorManager)getSystemService(
				Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector(new OnShakeListener() {
			@Override
			public void onShake() {
				
				Log.i("Log","shake has been detected");

				sendEmail();

				// Do stuff!
			}
		});
        
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
         // latitude and longitude
            double latitude = 10 ;
            double longitude =25 ;
             
            // create marker
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");
             
            // adding marker
            googleMap.addMarker(marker);
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        initilizeMap();
    }
    
    private void sendSms(String phonenumber,String message, boolean isBinary)
    {
//        SmsManager manager = SmsManager.getDefault();
//
//        PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
//        PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
//
//        if(isBinary)
//        {
//                byte[] data = new byte[message.length()];
//
//                for(int index=0; index<message.length() && index < 160; ++index)
//                {
//                        data[index] = (byte)message.charAt(index);
//                }
//
//                manager.sendDataMessage(phonenumber, null, (short) SMS_PORT, data,piSend, piDelivered);
//        }
//        else
//        {
//                int length = message.length();
//
//                if(length > 160)
//                {
//                        ArrayList<String> messagelist = manager.divideMessage(message);
//
//                        manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
//                }
//                else
//                {
//                        manager.sendTextMessage(phonenumber, null, message, piSend, piDelivered);
//                }
//        }
    	SmsManager.getDefault().sendTextMessage(phonenumber, null, message, null,null);
    }
    
    private void sendEmail(){
    	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        /* Fill it with Data */
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"aviral1208@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "help");
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
         
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	mSensorManager.unregisterListener(mShakeDetector);
    	super.onPause();
    }
    
    
 
}
