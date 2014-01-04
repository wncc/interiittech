package com.codecooks.template;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.codecooks.template.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FeedActivity extends ListActivity
{
	String feed_value = null;
	List<String> entries = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent feed_value_intent = getIntent();
		feed_value = feed_value_intent.getStringExtra("feed");
		if(feed_value==null)
			feed_value = "<item>Update 1</item><item>Update 2</item>";
		try {
			readXml();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 		setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_upload_attachment,entries));
		ListView list = getListView();
		list.setTextFilterEnabled(true);
		list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			{
				// TODO Auto-generated method stub						
				Log.v("UPLOAD PROGRESS",(String) ((TextView) arg1).getText());
			}
			
		});

	}
	
	private void readXml() throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		final ProgressDialog pd = new ProgressDialog(FeedActivity.this);
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        final XmlPullParser xpp = factory.newPullParser();

		class ProgressDialogTask extends AsyncTask<Void, Void, Void> 
		{
		     @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd.setMessage("Reading :: Updates");
				pd.show();
			}

			protected Void doInBackground(Void... args) {
		        // do background work here
				try {
					xpp.setInput(new StringReader (feed_value));
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         int eventType = 0;
				try {
					eventType = xpp.getEventType();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         while (eventType != XmlPullParser.END_DOCUMENT)
		         {
		          if(eventType == XmlPullParser.TEXT) {
		              entries.add(xpp.getText().toString());
		          }
		          try {
					eventType = xpp.next();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         }
		        return null;
		     }

		     protected void onPostExecute(Void result) {
		         // do UI work here
		    	 pd.dismiss();
		     }
		} 
		new ProgressDialogTask().execute();
		 
         
	}
}
