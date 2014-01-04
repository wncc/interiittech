package com.codecooks.template;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_registration_enterCode extends Activity
{

	Button verificationNextBtn = null;
	EditText verificationCodeEntry = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_entercode);
		Log.v("START_ENTERCODE","Check output");
		verificationCodeEntry = (EditText) findViewById(R.id.activity_registration_enterCode_editVerificationCode);
		verificationNextBtn = (Button) findViewById(R.id.activity_registration_enterCode_submitButton);
		verificationNextBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				String enteredCode = verificationCodeEntry.getText().toString();
				if(enteredCode.matches(""))
				{
					Toast.makeText(activity_registration_enterCode.this, "Empty Verification Code", Toast.LENGTH_SHORT).show();
				}
				else
				if(enteredCode.length()>0)	
				{
					try
				    {
						String PREF_FILE = "vcode";
						SharedPreferences settings = getSharedPreferences(PREF_FILE,0);
						String read = settings.getString("vcode", null);
						
				        if((read == null)){
				        	Toast.makeText(getApplicationContext(), "Codecooks: No Phone number Found", Toast.LENGTH_SHORT).show();
				        }
				        else
				        if(read!=null)
				        {
				        	read = (read.substring(30)).trim(); 
						 Log.v("ACTIVITY_REGISTRATION","READ FROM FILE : "+read);
						 Log.v("ACTIVITY_REGISTRATION","READ FROM FIELD : "+enteredCode);
						 if(enteredCode.matches(read))
						 {
							 
							    String PREF_FILE2 = "verified";
								SharedPreferences settings2 = getSharedPreferences(PREF_FILE2,0);
								SharedPreferences.Editor editor2 = settings2.edit();
								editor2.putString("Verified", "true");
								editor2.putString("Timer", "5");
								editor2.commit();
								
					     		Log.v("ACTIVITY_REGISTRATION","Start Activity: THANKS");
					     		Toast.makeText(activity_registration_enterCode.this, "Registration Complete", Toast.LENGTH_LONG).show();
					     		/*Intent intent = new Intent(com.codecooks.test.activity_registration_enterCode.this, com.codecooks.test.thanks.class);  
					     	     startActivity(intent);*/
					     		Intent intent = new Intent(Intent.ACTION_MAIN);
					    		intent.addCategory(Intent.CATEGORY_HOME);
					    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					    		startActivity(intent);
					    		finish();
					    		return;
						 }
						 else
						 {
							 Toast.makeText(activity_registration_enterCode.this, "Code doesn't match", Toast.LENGTH_SHORT).show();
						 }
				        }
					}
				    catch (Exception e) 
					{
				    	e.printStackTrace();
						Toast.makeText(activity_registration_enterCode.this, "Error reading file", Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
	}
	
}
