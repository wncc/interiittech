package com.codecooks.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.codecooks.template.R;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ComplainActivityAttachment extends ListActivity
{
	String Uploadfilename = null;
	// NOW PROVIDE THE LIST OF ITEMS THAT USER CAN SELECT BY SEARCHING INTERNAL STORAGE file DIRECTORY
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		List<String> attachment_items = new ArrayList<String>();
		Context context = getApplicationContext(); 
		File sdcard = context.getFilesDir();
		File dirs = new File(sdcard.getAbsolutePath());

		if(dirs.exists()) {
		    String[] files = dirs.list();
		    for(String fileName:files)
		    {
		    	if(!fileName.endsWith(".codecooks") && !fileName.endsWith(".pref"))
		    		attachment_items.add(fileName);
		    }
		}
		
		setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_upload_attachment,attachment_items));
		Intent startingIntent = getIntent();
		Uploadfilename = startingIntent.getStringExtra("Uploadfilename");
		ListView list = getListView();
		list.setTextFilterEnabled(true);
		list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			{
				// TODO Auto-generated method stub
				
				System.out.println(((TextView) arg1).getText());
				Intent intent = new Intent(ComplainActivityAttachment.this, ComplainActivity.class);
				intent.putExtra("upload_filename", ((TextView) arg1).getText());
				Log.v("UPLOAD PROGRESS",(String) ((TextView) arg1).getText());
	            startActivity(intent);
	            finish();
			}
			
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(ComplainActivityAttachment.this, ComplainActivity.class);
		intent.putExtra("upload_filename", Uploadfilename);
		Log.v("UPLOAD PROGRESS",Uploadfilename);
        startActivity(intent);
        finish();
		
	}

}
