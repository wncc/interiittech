package com.example.testaccel;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements  SensorEventListener, OnClickListener{

	private SensorManager sensorManager;
	private long lastUpdate;
	private Sensor gsensor,asensor;
	float[] mGravity;
	  float[] mGeomagnetic;
	  float prevSqareRoot=0;
	  int counter =0;
	  TextView curPhone;
	  Button changePhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    lastUpdate = System.currentTimeMillis();
	    curPhone =(TextView) findViewById(R.id.curPhone);
	    changePhone = (Button) findViewById(R.id.changePhone);
	    changePhone.setOnClickListener(this);
	    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	    curPhone.setText(sp.getString("phoneSaved",""));
	}
	  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {
		      getAccelerometer(event);
		      Log.d("Counter",counter+++"");
		    }
	}


	private void getAccelerometer(SensorEvent event) {
		// TODO Auto-generated method stub
		float[] values = event.values;
	    
	    float x = values[0];
	    float y = values[1];
	    float z = values[2];

	    float accelationSquareRoot = (x * x + y * y + z * z)
	        / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
	    long actualTime = System.currentTimeMillis();	
	    if (accelationSquareRoot-prevSqareRoot >= 3 ) 
	    {
	    	prevSqareRoot = accelationSquareRoot;
	      if (actualTime - lastUpdate < 200) {
	        return;
	      }
	      lastUpdate = actualTime;
	      Intent i = new Intent(MainActivity.this,Send.class);
			startActivity(i);	      
	    }
	    
	}

	@Override
	  protected void onResume() {
	    super.onResume();
	    // register this class as a listener for the orientation and
	    // accelerometer sensors

	    gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        asensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    sensorManager.registerListener(this,
	        gsensor,
	        SensorManager.SENSOR_DELAY_NORMAL);
	    sensorManager.registerListener(this,
		        asensor,
		        SensorManager.SENSOR_DELAY_NORMAL);
	  }
	
	@Override
	  protected void onPause() {
	    // unregister listener
	    super.onPause();
	    sensorManager.unregisterListener(this);
	  }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(MainActivity.this,Register.class);
		finish();
		startActivity(i);
	}



	
	
}
