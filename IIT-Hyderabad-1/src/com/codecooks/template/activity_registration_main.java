package com.codecooks.template;

import java.util.Random;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_registration_main extends Activity
{
	protected int l;
	EditText phoneNumber = null;
	EditText countryCode = null;
	Button sendBtn = null;
	IntentFilter intentFilter;
	String final_phone_number;
	Button alreadyBtn = null;
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//registerReceiver(intentReceiver, intentFilter);
		super.onPause();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		//unregisterReceiver(intentReceiver);
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_main);
		intentFilter = new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");
		
		sendBtn = (Button) findViewById(R.id.activity_registration_main_submitButton);
		phoneNumber = (EditText) findViewById(R.id.activity_registration_main_editPhoneNumber);
		countryCode = (EditText) findViewById(R.id.activity_registration_main_countryCode);
		alreadyBtn = (Button) findViewById(R.id.activity_registration_main_alreadyBtn);
		
		alreadyBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.codecooks.test.REGISTRATIONENTERCODE"));
			}
		});
		
		sendBtn.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				/*
				if(phoneNumber.getText().toString().length()<10 || countryCode.getText().toString().length()<3)
				{
					Toast emptyPhoneNumberField = Toast.makeText(activity_registration_main.this, "Invalid Mobile Number....Available in India only for now", Toast.LENGTH_SHORT); 
					emptyPhoneNumberField.show();
				}
				else
				if(!countryCode.getText().toString().matches("+91"))
				{
					Toast wrongCountryCode = Toast.makeText(activity_registration_main.this, "Invalid Country Code....Available in India only for now", Toast.LENGTH_SHORT); 
					wrongCountryCode.show();
				}
				else*/
				{
					// Send a text message to the phoneNumber & detect it & then input that phoneNumber in validated.pref file
					final_phone_number = countryCode.getText().toString()+phoneNumber.getText().toString();
					try {
						
						final ProgressDialog pd = new ProgressDialog(activity_registration_main.this);
						class ProgressDialogTask extends AsyncTask<Void, Void, Void> 
						{
						     @Override
							protected void onPreExecute() {
								// TODO Auto-generated method stub
								super.onPreExecute();
								pd.setMessage("Opening :: Settings");
								pd.show();
							}

							protected Void doInBackground(Void... args) {
						        // do background work here
								try {
									sendMessage(final_phone_number);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						        return null;
						     }

						     protected void onPostExecute(Void result) {
						         // do UI work here
						    	 pd.dismiss();
						     }
						} 
						new ProgressDialogTask().execute();
						/*Intent i=new Intent(getApplicationContext(),com.codecooks.template.SendMessage.class);
						i.putExtra("ph", final_phone_number);
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getApplicationContext().startActivity(i);*/
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Toast.makeText(activity_registration_main.this, "Error sending message", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
						
				}
			}
		});
		
	}
	
	private void sendMessage(String string) throws Exception 
	{
		// TODO Auto-generated method stub
		try
		{
			PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent("Message Sent"), 0);
			//PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent("Message Received"), 0);
			
			registerReceiver(new BroadcastReceiver()
			{
				@Override
				public void onReceive(Context arg0, Intent arg1) {
					// TODO Auto-generated method stub
					switch(getResultCode())
					{
					case Activity.RESULT_OK:
						Toast.makeText(getApplicationContext(), "SMS Sent", Toast.LENGTH_LONG).show();
						// Start a dialog box which shows progress that you are checking for message delivery
						startActivity(new Intent("android.intent.action.REGCODE"));
						   // Store value of l in validated.pref file
						break;
						
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						Toast.makeText(getApplicationContext(), "Failure: Generic Failure", Toast.LENGTH_LONG).show();
						break;	
						
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						Toast.makeText(getApplicationContext(), "Failure: No service", Toast.LENGTH_LONG).show();
						break;
						
					case SmsManager.RESULT_ERROR_NULL_PDU:
						Toast.makeText(getApplicationContext(), "Failure: No pdu provided", Toast.LENGTH_LONG).show();
						break;
					
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						Toast.makeText(getApplicationContext(), "Failure: Radio Off", Toast.LENGTH_LONG).show();
						break;	
					}
				}
			}, new IntentFilter("Message Sent"));
			
			
			
			
			SmsManager send_sms = SmsManager.getDefault();
			Random rand = new Random();
		    l = rand.nextInt(100000);
		    
		    String PREF_FILE = "vcode";
			SharedPreferences settings = getSharedPreferences(PREF_FILE,0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("vcode", "Codecooks Verification Code : "+l);
			editor.putString("phNo", final_phone_number);
			editor.commit();
			
			send_sms.sendTextMessage(string, null, "Codecooks verification code : "+l, sentPI, null);
		}
		catch(IllegalArgumentException e)
		{
			Toast dataEmptyError = Toast.makeText(getApplicationContext(), "An error occurred...Please try again", Toast.LENGTH_SHORT); 
			dataEmptyError.show();
		}
	}


	
	
	
}
