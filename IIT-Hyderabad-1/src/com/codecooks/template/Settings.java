package com.codecooks.template;

import com.codecooks.template.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity
{
	Button BtnSave = null;
	EditText text1 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		String SETTINGS_FILE = "settingsFile";
		SharedPreferences settings = getSharedPreferences(SETTINGS_FILE,Context.MODE_PRIVATE);
		BtnSave =  (Button) findViewById(R.id.activity_settings_BtnSave);
		text1 = (EditText) findViewById(R.id.activity_settings_EtText1);
		text1.setText(settings.getString("Timer", null));
		
		BtnSave.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				String SETTINGS_FILE = "settingsFile";
				SharedPreferences settings = getSharedPreferences(SETTINGS_FILE,Context.MODE_PRIVATE);
				SharedPreferences.Editor settingsEditor = settings.edit();
				settingsEditor.putString("Timer", text1.getText().toString());
				settingsEditor.commit();
				Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
}
