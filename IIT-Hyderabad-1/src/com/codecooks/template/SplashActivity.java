package com.codecooks.template;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashActivity extends Activity
{
	ProgressBar splashProgress = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		splashProgress = (ProgressBar) findViewById(R.id.activity_splash_progressBar);
		splashProgress.setMax(20);
		splashProgress.getProgressDrawable().setColorFilter(Color.argb(100,00,88,220), Mode.SRC_IN);
		Thread splashScreenTimer = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					int splashScreenTimer = 0;
					while(splashScreenTimer<2000)
					{
						sleep(100);
						splashScreenTimer = splashScreenTimer + 100;
						splashProgress.setProgress(splashScreenTimer/100);
					}
					
					startItNow();
					
				}
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finally
				{
					finish();
				}
			}
		};
		splashScreenTimer.start();
		
	}
	
	
	public void startItNow()
	{
		String PREF_FILE = "verified";
		SharedPreferences settings = getSharedPreferences(PREF_FILE,0);
		String verified = settings.getString("Verified", null);
        if((verified == null)){
        	//Toast.makeText(getApplicationContext(), "Codecooks: No Registration Found", Toast.LENGTH_SHORT).show();
        	Intent i=new Intent(getApplicationContext(),com.codecooks.template.activity_registration_main.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplicationContext().startActivity(i);
        }
        else
        if(verified.matches("true"))
        {
        	Intent i=new Intent(getApplicationContext(),MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplicationContext().startActivity(i);
        }
		
	}

}
