package com.example.parkingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserDetailForm extends Activity {

	String name, total, available, latitude, longitude,location;
	Button ok, click;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user_detail_form);
		name = ((EditText) findViewById(R.id.name)).getText().toString();
		total = ((EditText) findViewById(R.id.total)).getText().toString();
		available = ((TextView) findViewById(R.id.available)).getText()
				.toString();
		latitude = ((TextView) findViewById(R.id.latitude)).getText()
				.toString();
		longitude = ((TextView) findViewById(R.id.longitude)).getText()
				.toString();
		longitude = ((TextView) findViewById(R.id.location)).getText()
				.toString();

		ok = (Button) findViewById(R.id.button1);
		click = (Button) findViewById(R.id.button2);

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

			}
		});

		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

			}
		});

	}

}
