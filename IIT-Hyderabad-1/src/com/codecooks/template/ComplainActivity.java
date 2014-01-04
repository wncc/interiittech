package com.codecooks.template;




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.codecooks.template.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ComplainActivity extends Activity
{
	Calendar calendar = Calendar.getInstance();
	EditText activity_complain_subject;
	EditText activity_complain_description;
	Button activity_complain_attachment;
	Button activity_complain_submit;
	TextView activity_complain_attachmentfilename;
	String complain_filename ;
	String complain_description ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complain);
		
		Intent startingIntent = getIntent();
		complain_filename = startingIntent.getStringExtra("upload_filename");
		activity_complain_attachmentfilename = (TextView) findViewById(R.id.activity_complain_TViewAttachment);
		activity_complain_subject = (EditText) findViewById(R.id.activity_complain_ETxtSubject);
		activity_complain_description = (EditText) findViewById(R.id.activity_complain_ETxtDescription);
		activity_complain_attachment = (Button) findViewById(R.id.activity_complain_BtnAttachment);
		activity_complain_submit = (Button) findViewById(R.id.activity_complain_BtnSubmit);
		if(complain_filename!=null)
			activity_complain_attachmentfilename.setText(complain_filename);
		
		activity_complain_attachment.setOnClickListener(new View.OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{
			    Intent intent = new Intent(ComplainActivity.this,ComplainActivityAttachmentThreeOptions.class);
	            intent.putExtra("Uploadfilename", activity_complain_attachmentfilename.getText().toString());
	            startActivity(intent);
			}
		});
		
		activity_complain_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<String> error = new ArrayList<String>(); 
				if(activity_complain_subject.getText().toString().matches(""))
				{
					error.add("Subject empty");
				}
				if(activity_complain_description.getText().toString().matches(""))
				{
					error.add("Description empty");
				}
				if(error.size()!=0)
				{
					// Show an error alert dialog box
					AlertDialog alertDialog = new AlertDialog.Builder(ComplainActivity.this).create();
					alertDialog.setTitle("Input Error");
					alertDialog.setMessage(error.get(0));
					alertDialog.setIcon(R.drawable.ic_launcher);
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	                @Override
					public void onClick(DialogInterface dialog, int which)
	                {
	                	dialog.cancel();
	                	//Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
	                }
					});
					alertDialog.show();
				}
				if(error.size()==0)
				{
					String PREF_FILE = "vcode";
					SharedPreferences settings = getSharedPreferences(PREF_FILE,0);
					String input_file_data = "<codecooks><subject>"+activity_complain_subject.getText()+"</subject>"+"<description>"+activity_complain_description.getText()+"</description><attachment>"+activity_complain_attachmentfilename.getText().toString()+"</attachment><ph>"+settings.getString("phNo", null)+"</ph></codecooks>";
					System.out.println("File data: "+input_file_data);
					Random rand = new Random();				   
					String input_string = calendar.get(Calendar.YEAR)+"_"+calendar.get(Calendar.MONTH)+"_"+calendar.get(Calendar.DAY_OF_MONTH)+"_"+calendar.get(Calendar.MINUTE)+"_"+calendar.get(Calendar.SECOND)+"_"+ rand.nextInt(100000);
					Context context = getApplicationContext();
					File path = context.getFilesDir();
		            File file = new File(path,input_string+".codecooks");
				try
		           {
		        	   //InputStream is = input_file_data.getBytes();
		        	   OutputStream os = new FileOutputStream(file);
		        	   byte[] data = new byte[200000];
		        	   data = input_file_data.getBytes();
		        	   os.write(data);
		        	   os.close();
		        	   
		        	   
			            Intent intent = new Intent(ComplainActivity.this,ComplainActivityProgress.class);
			            intent.putExtra("tfilename", file.getAbsolutePath());
			            intent.putExtra("mfilename", path.getAbsolutePath()+"/"+activity_complain_attachmentfilename.getText());
			            Log.v("UPLOAD PREOGRESS",file.getAbsolutePath());
			            Log.v("UPLOAD PROGRESS",path.getAbsolutePath()+"/"+activity_complain_attachmentfilename.getText());
			            startActivity(intent);
		           }
		           
		           catch (IOException e){
		        	   Toast fail = Toast.makeText(ComplainActivity.this, "fail", Toast.LENGTH_LONG); 
		        	   fail.show();
		        			   }
		            		            
				}
			}
		});
	}

	
}
