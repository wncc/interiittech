package com.example.bleedred;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onSignPushed(View view){
		
		//DO SOMETHING WITH TEXT ENTERED - CHECK WITH DATABAS
		Intent loggedIntent = new Intent(this,LoggedInActivity.class);
		startActivity(loggedIntent);
	}
	
	public void onRegPushed(View view){
		Intent regIntent = new Intent(this,RegActivity.class);
		startActivity(regIntent);
	}

}
