package com.example.parking;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parking.ShakeDetector.OnShakeListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationClient;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;  
import android.location.Geocoder;  
import android.location.Location;  
import android.location.LocationListener;  
import android.location.LocationManager;
import android.provider.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements OnMarkerClickListener{
  private static final String TAG = "Main";
  static final LatLng HAMBURG = new LatLng(53.558, 9.927);
  static final LatLng KIEL = new LatLng(53.551, 9.993);
  private GoogleMap map;
  private LocationManager locationMangaer=null;  
  private LocationListener locationListener=null;
  private ProgressDialog pDialog;
  private static String url = "http://parkingdata.comuv.com/view.php";
  JSONArray names = null;
  private GetNames  getName = null;
  private Location cPoint = null; 
  private Marker cMarker = null;
  private String cAdd;
  static ShakeDetector mShakeDetector;
  static SensorManager mSensorManager;
  static Sensor mAccelerometer;
  
  private static final String TAG_NAME = "name";
  private static final String TAG_TOTAL = "total";
  private static final String TAG_AVAIL = "avail";
  private static final String TAG_LAT = "latitude";
  private static final String TAG_LONG = "longitude";
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
        .getMap();
    map.setOnMarkerClickListener(this);

    locationMangaer = (LocationManager)   
    		  getSystemService(Context.LOCATION_SERVICE);
    mSensorManager = (SensorManager)getSystemService(
			Context.SENSOR_SERVICE);
	mAccelerometer = mSensorManager
			.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	mShakeDetector = new ShakeDetector(new OnShakeListener() {
		@Override
		public void onShake() {
			Log.i("Log","shake has been detected");
			sendEmail();
		}
	});
    locationListener = new MyLocationListener();
    locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,locationListener);  

	getName = new GetNames();
	getName.execute();
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  @Override
	public boolean onMenuItemSelected(int featureId, android.view.MenuItem item) {
		switch(item.getItemId()){
		case R.id.add_form:
				Intent form=new Intent(this,UserDetailForm.class);
				startActivity(form);
				return true;
		default:
				return super.onOptionsItemSelected(item);
		}
  }
  
  private void sendEmail(){
  	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

      if(cPoint!=null){
    	  emailIntent.setType("plain/text");
    	  emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"aviral1208@gmail.com"});
    	  emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
    	  (new GetAddressTask(this)).execute(cPoint);
    	  emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,cAdd);
    	  startActivity(Intent.createChooser(emailIntent, "Send mail..."));
      }
  }
  
  @Override
  protected void onResume() {
	  super.onResume();
      mSensorManager.registerListener(mShakeDetector,
              mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
              SensorManager.SENSOR_DELAY_UI);
  };
  
  @Override
  protected void onPause() {
  	mSensorManager.unregisterListener(mShakeDetector);
  	super.onPause();
  }
  
  private void sendSms(String phonenumber,String message, boolean isBinary){
	  if(cPoint!=null){
		  (new GetAddressTask(this)).execute(cPoint);
		  SmsManager.getDefault().sendTextMessage(phonenumber, null, cAdd, null,null);
	  }
  }
  
  private Boolean displayGpsStatus() {  
	  ContentResolver contentResolver = getBaseContext()  
	  .getContentResolver();  
	  boolean gpsStatus = Settings.Secure  
	  .isLocationProviderEnabled(contentResolver,   
	  LocationManager.GPS_PROVIDER);  
	  if (gpsStatus) {  
	   return true;  
	  } else {  
	   return false;  
	  }  
	 }
  
  private class MyLocationListener implements LocationListener {  

	@Override  
      public void onLocationChanged(Location loc) {  
          
          Toast.makeText(getBaseContext(),"Location changed : Lat: " + loc.getLatitude()+ " Lng: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();  
          String longitude = "Longitude: " +loc.getLongitude();    
          Log.v(TAG, longitude);  
          String latitude = "Latitude: " +loc.getLatitude();  
          Log.v(TAG, latitude);
          LatLng current = new LatLng(loc.getLatitude(), loc.getLongitude());
          //cMarker.remove();
          cMarker = map.addMarker(new MarkerOptions().position(current).title("You are here"));
          map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
          map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
          cPoint = loc;
          
          //getName.execute();
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
      public void onStatusChanged(String provider,int status, Bundle extras) {  
          // TODO Auto-generated method stub           
      }  
  }
  
  private class GetNames extends AsyncTask<Void, Void, Void> {
	  

	@Override
      protected void onPreExecute() {
          super.onPreExecute();
          // Showing progress dialog
          pDialog = new ProgressDialog(MainActivity.this);
          pDialog.setMessage("Please wait...");
          pDialog.setCancelable(false);
          pDialog.show();

      }

      @Override
      protected Void doInBackground(Void... arg0) {
          // Creating service handler class instance
          ServiceHandler sh = new ServiceHandler();

          // Making a request to url and getting response
          String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

          Log.d("Response: ", "> " + jsonStr);

          if (jsonStr != null) {
              try {
                  names = new JSONArray(jsonStr);
                                       
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          } else {
              Log.e("ServiceHandler", "Couldn't get any data from the url");
          }

          return null;
      }

      @Override
      protected void onPostExecute(Void result) {
    	  if (pDialog.isShowing())
              pDialog.dismiss();
    	// Getting JSON Array node
          //names = jsonObj.getJSONArray(null);

          // looping through All Contacts
          for (int i = 0;names!= null &&  i < names.length(); i++) {
             try{
        	  JSONObject c = names.getJSONObject(i);
               
              String name = c.getString(TAG_NAME);
              String total = c.getString(TAG_TOTAL);
              String avail = c.getString(TAG_AVAIL);
              String latitude = c.getString(TAG_LAT);
              String longitude = c.getString(TAG_LONG);
              LatLng current = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
              String title = name + "("+avail+","+total+")";
              Marker cMarker = map.addMarker(new MarkerOptions().position(current).title(title));
             } catch(JSONException e){
            	 e.printStackTrace();
             }
          }
      }
  }

@Override
public boolean onMarkerClick(Marker mark) {
	LatLng dest = mark.getPosition();
	if(cPoint!=null){
		LatLng src = new LatLng(cPoint.getLatitude(), cPoint.getLongitude());
		
	}
	return false;
}

private class GetAddressTask extends
AsyncTask<Location, Void, String> {
Context mContext;
public GetAddressTask(Context context) {
super();
mContext = context;
}
@Override
protected void onPreExecute() {
  super.onPreExecute();
  pDialog = new ProgressDialog(MainActivity.this);
  pDialog.setMessage("Please wait...");
  pDialog.setCancelable(false);
  pDialog.show();

}

@Override
protected String doInBackground(Location... params) {
Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
Location loc = params[0];
List<Address> addresses = null;
try {
    addresses = geocoder.getFromLocation(loc.getLatitude(),
            loc.getLongitude(), 1);
} catch (IOException e1) {
Log.e("LocationSampleActivity",
        "IO Exception in getFromLocation()");
e1.printStackTrace();
return ("IO Exception trying to get address");
} catch (IllegalArgumentException e2) {
String errorString = "Illegal arguments " +
        Double.toString(loc.getLatitude()) +
        " , " +
        Double.toString(loc.getLongitude()) +
        " passed to address service";
Log.e("LocationSampleActivity", errorString);
e2.printStackTrace();
return errorString;
}
if (addresses != null && addresses.size() > 0) {
    Address address = addresses.get(0);
    String addressText = String.format(
            "%s, %s, %s",
            address.getMaxAddressLineIndex() > 0 ?
                    address.getAddressLine(0) : "",
            address.getLocality(),
            address.getCountryName());
    return addressText;
} else {
    return "No address found";
}
}
protected void onPostExecute(String address) {
	  if (pDialog.isShowing())
          pDialog.dismiss();
	 cAdd = address;
}
}
}  