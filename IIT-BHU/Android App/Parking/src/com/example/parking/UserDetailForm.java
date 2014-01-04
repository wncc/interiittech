package com.example.parking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetailForm extends Activity {
	private ProgressDialog pDialog;
	private String name,total,available,latitude, longitude,location;
	private static String url = "http://parkingdata.comuv.com/update.php";
	Button ok,click;
	 private LocationManager locationMangaer=null;  
	  private LocationListener locationListener=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserDetailForm.this);

		alertDialog.setTitle("Confirm Exit");

		alertDialog
				.setMessage("This page is for registering your Parking Space. Do you want to continue?");


		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent main=new Intent(UserDetailForm.this,MainActivity.class);
						startActivity(main);
					}
				});

		// Showing Alert Message

		// Showing Alert Message
		alertDialog.show();
		
		setContentView(R.layout.user_detail_form);
		locationMangaer = (LocationManager)   
	    		  getSystemService(Context.LOCATION_SERVICE);
		ok = (Button)findViewById(R.id.button1);
		click = (Button) findViewById(R.id.button2);

		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				name = ((EditText) findViewById(R.id.name)).getText().toString();
				total = ((EditText) findViewById(R.id.total)).getText().toString();
				available = ((EditText) findViewById(R.id.available)).getText()
						.toString();
				latitude = ((TextView) findViewById(R.id.latitude)).getText()
						.toString();
				longitude = ((TextView) findViewById(R.id.longitude)).getText()
						.toString();
				location = ((TextView) findViewById(R.id.location)).getText()
						.toString();
				
				new SubmitNames().execute();
			}
		});
		
		click = (Button) findViewById(R.id.button2);{
			
		}

		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				locationListener = new MyLocationListener();
			    locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,locationListener);
			    
			}
		});

		
		
		
	}
	
	private class MyLocationListener implements LocationListener {  

		@Override  
	      public void onLocationChanged(Location loc) {  
			((TextView) findViewById(R.id.latitude)).setText("" +loc.getLatitude());
		    ((TextView) findViewById(R.id.longitude)).setText("" + loc.getLongitude());
		    (new GetAddressTask(UserDetailForm.this)).execute(loc);
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
	
	private class SubmitNames extends AsyncTask<Void, Void, Void> {
		  

		@Override
	      protected void onPreExecute() {
	          super.onPreExecute();
	          pDialog = new ProgressDialog(UserDetailForm.this);
	          pDialog.setMessage("Please wait...");
	          pDialog.setCancelable(false);
	          pDialog.show();

	      }

	      @Override
	      protected Void doInBackground(Void... arg0) {
	          ServiceHandler sh = new ServiceHandler();
	          
	          List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	          
	          nameValuePairs.add(new BasicNameValuePair("name", name));
	          nameValuePairs.add(new BasicNameValuePair("total", total));
	          nameValuePairs.add(new BasicNameValuePair("avail", available));
	          nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
	          nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
	          
	          String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST,nameValuePairs);

	          Log.d("Response: ", "> " + jsonStr);

	          if (jsonStr != null) {
	              //Toast.makeText(getBaseContext(),jsonStr, Toast.LENGTH_SHORT).show();  
	          } else {
	              Log.e("ServiceHandler", "Couldn't get any data from the url");
	          }

	          return null;
	      }

	      @Override
	      protected void onPostExecute(Void result) {
	    	  if (pDialog.isShowing())
	              pDialog.dismiss();
	      }
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
      pDialog = new ProgressDialog(UserDetailForm.this);
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
    	  ((TextView)findViewById(R.id.location)).setText(address);
   }
}
}
