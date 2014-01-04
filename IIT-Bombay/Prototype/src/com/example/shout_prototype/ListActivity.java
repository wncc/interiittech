package com.example.shout_prototype;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		Button nextActi = (Button) findViewById(R.id.button1);
		nextActi.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openStart = new Intent("com.example.shout_prototype.STARTINGPOINTA");
				startActivity(openStart);
			}
			
			});

		Button Login = (Button) findViewById(R.id.button2);
		Login.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent openStart = new Intent("com.example.shout_prototype.LOGIN");
				startActivity(openStart);
			}
			
			});

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

}
