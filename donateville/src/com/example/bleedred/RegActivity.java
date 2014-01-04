package com.example.bleedred;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class RegActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reg, menu);
		return true;
	} 
	
	public void onSignPushed(View view){
		
		//Obtain the text data for each of the 4 fields
		EditText editUsrNameText = (EditText) findViewById(R.id.enterUsr);
		String username = editUsrNameText.getText().toString();

		EditText editNameText = (EditText) findViewById(R.id.enterName);
		String name = editNameText.getText().toString();
		
		EditText editPwdText = (EditText) findViewById(R.id.enterPwd);
		String password = editPwdText.getText().toString();
		
		EditText editPhoneText = (EditText) findViewById(R.id.enterPhNo);
		String phNo = editPhoneText.getText().toString();
		
		EditText editAgeText = (EditText) findViewById(R.id.enterAge);
		String age = editAgeText.getText().toString();
		
		String gr;
		char sg;
		
		
		//YOU CAN CHANGE DATATYPES OF PH NO AND AGE IF DESIRED. NOW CHECK THAT NO VALUE IS NULL AND UPDATE DB 
		
		//YOU CAN MAINTAIN A SINGLE UNIQUE KEY VALUE (USERNAME) AND PASS IT TO REMAINING ACTIVITIES SO AS TO IDENTIFY THE CURRENT USER
		//YOU CAN ALSO STORE IN INTERNAL MEM
		
		Intent regIntent = new Intent(this,LoggedInActivity.class);
		startActivity(regIntent);
	}

}
