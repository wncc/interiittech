package com.example.testaccel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener{

	/**
	 * @param args
	 */
	

	  Button savePhone;
	  EditText phoneNum;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		savePhone =(Button) findViewById(R.id.savePhone);
	    phoneNum =(EditText) findViewById(R.id.phoneNum);
	    savePhone.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor edit=pref.edit();
		String phno=phoneNum.getText().toString();
		boolean valid=PhoneNumberUtils.isGlobalPhoneNumber(phno);
		if(valid)
			edit.putString("phoneSaved", phno);
		else
			Toast.makeText(Register.this, phno + " is not a valid Phone Number", Toast.LENGTH_LONG).show();
		edit.commit();
		
		Intent i = new Intent(Register.this,MainActivity.class);
		finish();
		startActivity(i);
	}
}
