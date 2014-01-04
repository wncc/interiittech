package com.codecooks.template;

import com.codecooks.template.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ComplainActivityAttachmentThreeOptions extends Activity
{

	Button TakePicture = null;
	Button TakeVideo = null;
	Button FromGallery = null;
	String uploadFilename = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_attachment_threeoptions);
		
		Intent startingIntent = getIntent();
		uploadFilename = startingIntent.getStringExtra("Uploadfilename");
		
		TakePicture = (Button) findViewById(R.id.activity_upload_attachment_threeoptions_BtnTakePicture);
		TakeVideo = (Button) findViewById(R.id.activity_upload_attachment_threeoptions_BtnTakeVideo);
		FromGallery = (Button) findViewById(R.id.activity_upload_attachment_threeoptions_BtnFromGallery);
		
		TakePicture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ComplainActivityAttachmentThreeOptions.this,ImageActivity.class);
	            intent.putExtra("Uploadfilename", uploadFilename);
	            startActivity(intent);
			}
		});
		
		TakeVideo.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(ComplainActivityAttachmentThreeOptions.this,VideoActivity.class);
			            intent.putExtra("Uploadfilename", uploadFilename);
			            startActivity(intent);
					}
				});
		
		FromGallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ComplainActivityAttachmentThreeOptions.this,ComplainActivityAttachment.class);
	            intent.putExtra("Uploadfilename", uploadFilename);
	            startActivity(intent);
			}
		});
	}
	
}
