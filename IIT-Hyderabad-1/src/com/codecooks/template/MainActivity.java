package com.codecooks.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.codecooks.template.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button feed_button = null;
	Button speak_button = null;
	
	final String feed_value = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        feed_button = (Button) findViewById(R.id.activity_main_BtnFeed);
        speak_button = (Button) findViewById(R.id.activity_main_BtnSpeak);
        
        feed_button.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				final ProgressDialog pd = new ProgressDialog(MainActivity.this);
				class ProgressDialogTask extends AsyncTask<Void, Void, Void> 
				{
				     @Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						pd.setMessage("Opening :: Feeds");
						pd.show();
					}

					protected Void doInBackground(Void... args) {
				        // do background work here
						try {
							fetchXMLFile();
						} catch (XmlPullParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Intent feeds_intent = new Intent("android.intent.action.FEEDS");
						feeds_intent.putExtra("feed", feed_value);
						startActivity(feeds_intent);
				        return null;
				     }

				     private void fetchXMLFile() throws XmlPullParserException, IOException {
						// In case of Net Connection
				    	/* 
				    	String pHost = "";
				 		String pPort = "";				 		
				 		final String authUser = "";
				 		final String authPassword = "";
				 		final String auth_token = "";
				 		
				 		System.setProperty("https.proxySet", "true");
				 		System.setProperty("https.proxyHost", pHost);
				 		System.setProperty("https.proxyPort", pPort); 

				         Authenticator.setDefault(new Authenticator() 
				         {
				             public PasswordAuthentication getPasswordAuthentication() 
				             {
				                 return new PasswordAuthentication(authUser, authPassword.toCharArray());
				             }
				         });
				         URL url = new URL("https://codecooks.co.nf/feed?access_token="+auth_token+"&limit=100");
				         String result = "";
				         HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
				         try
				         {
				              BufferedReader in = null;
				              try 
				              {
				                  String line;
				                  in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				                  while ((line = in.readLine()) != null) 
				                  {
				                      result += line;
				                  }
				              }
				              catch(Exception e)
				              {
				                    e.printStackTrace();
				                    System.out.println(connection.getResponseCode());
				              }
				              finally
				              {
				                 if (in != null)
				                 {
				                    in.close();
				                 }
				              }
				         }
				         catch(Exception e)
				         {
				             e.printStackTrace();
				             System.out.println("Connection error");
				         }
				         finally 
				         {
				             connection.disconnect();
				             System.out.println("Feeds: ");
				             System.out.println(result);
				         }
				     */
				    	 /*FOR Simplicity let's read a file from the app only*/
				    	 
					}

					

					protected void onPostExecute(Void result) {
				         // do UI work here
				    	 pd.dismiss();
				     }
				} 
				new ProgressDialogTask().execute();
			}
		});
        
        speak_button.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v) 
			{
				final ProgressDialog pd = new ProgressDialog(MainActivity.this);
				class ProgressDialogTask extends AsyncTask<Void, Void, Void> 
				{
				     @Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						pd.setMessage("Opening :: Complaint");
						pd.show();
					}

					protected Void doInBackground(Void... args) {
				        // do background work here
						startActivity(new Intent("android.intent.action.COMPLAIN"));
				        return null;
				     }

				     protected void onPostExecute(Void result) {
				         // do UI work here
				    	 pd.dismiss();
				     }
				} 
				new ProgressDialogTask().execute();				
			}
		});
    }


    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_open_settings:
			final ProgressDialog pd = new ProgressDialog(MainActivity.this);
			class ProgressDialogTask extends AsyncTask<Void, Void, Void> 
			{
			     @Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					pd.setMessage("Opening :: Settings");
					pd.show();
				}

				protected Void doInBackground(Void... args) {
			        // do background work here
					startActivity(new Intent("android.intent.action.SETTINGS"));
			        return null;
			     }

			     protected void onPostExecute(Void result) {
			         // do UI work here
			    	 pd.dismiss();
			     }
			} 
			new ProgressDialogTask().execute();
			return true;
		}
		return false;		
	}
}
