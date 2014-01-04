package com.example.testaccel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity{

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
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		if(sp.contains("phoneSaved")){
			Intent i = new Intent(Splash.this,MainActivity.class);
			finish();
			startActivity(i);	
		}
		else{
			Intent i = new Intent(Splash.this,Register.class);
			finish();
			startActivity(i);
		}
		
	}

}
