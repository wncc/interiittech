package com.example.shout_prototype;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SubmitActivity extends Activity {

	Button sub;
	TextView display, name;
	@Override
	protected void onCreate(Bundle meraBundle) {
		super.onCreate(meraBundle);
		setContentView(R.layout.activity_submit);
	}

}
