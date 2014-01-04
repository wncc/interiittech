package com.example.testaccel;


import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Send extends Activity implements OnClickListener{
	
	private CountDownTimer countDownTimer;
	private Button startB;
	public TextView text;
	public Uri notification ;
	 private final long startTime = 10 * 1000;
	 private final long interval = 1 * 1000;
	 private Ringtone r;
	private GPSTracker gpss;
	private double latitude,longitude;


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send);
		startB = (Button) findViewById(R.id.stopTimer);
		startB.setOnClickListener(this);
		text = (TextView) this.findViewById(R.id.timer);
		countDownTimer = new MyCountDownTimer(startTime, interval);
		text.setText(text.getText() + String.valueOf(startTime / 1000));
		countDownTimer.start();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		r.stop();
		countDownTimer.cancel();
		finish();		
	}
	 public class MyCountDownTimer extends CountDownTimer {
		  public MyCountDownTimer(long startTime, long interval) {
		   super(startTime, interval);		   
		  }

		  @Override
		  public void onFinish() {
		   text.setText("0");
		   gpss = new GPSTracker(Send.this);
		   if(gpss.canGetLocation()){
              
              latitude = gpss.getLatitude();
              longitude = gpss.getLongitude();
               
              // \n is for new line
             
              
             /* Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
             */
              
          }else{
              // can't get location
              // GPS or Network is not enabled
              // Ask user to enable GPS/network in settings
              gpss.showSettingsAlert();
          }
		   SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		   sendSms(sp.getString("phoneSaved",""),"I am in trouble my coordinates are - \nLat: " + latitude + "\nLong: " + longitude,false);
		   
		  }

		  @Override
		  public void onTick(long millisUntilFinished) {
		   text.setText("" + millisUntilFinished / 1000);
		   notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
           r = RingtoneManager.getRingtone(getApplicationContext(),notification);
           r.play();
		  }
		 }

	 private void sendSms(String phonenumber,String message, boolean isBinary)
		{
		    SmsManager manager = SmsManager.getDefault();

		   // PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
		   // PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

		    if(isBinary)
		    {
		            byte[] data = new byte[message.length()];

		            for(int index=0; index<message.length() && index < 160; ++index)
		            {
		                    data[index] = (byte)message.charAt(index);
		            }

		            manager.sendDataMessage(phonenumber, null, (short) 3128, data, null, null);
		    }
		    else
		    {
		            int length = message.length();

		            if(length > 160)
		            {
		                    ArrayList<String> messagelist = manager.divideMessage(message);

		                    manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
		            }
		            else
		            {
		                    manager.sendTextMessage(phonenumber, null, message, null, null);
		            }
		    }
		}
}
