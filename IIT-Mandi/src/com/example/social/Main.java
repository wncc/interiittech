package com.example.social;
import com.example.social.OurViewClient;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;


public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        	WebView mapView = (WebView)findViewById(R.id.web);
		
		mapView.getSettings().setJavaScriptEnabled(true);
        mapView.getSettings().setLoadWithOverviewMode(true);
        mapView.getSettings().setUseWideViewPort(true);
        mapView.getSettings().setAllowFileAccess(true);

	    mapView.getSettings().setBuiltInZoomControls(true);
      
    try{
    	mapView.loadUrl("file:///android_asset/main.html");
    }
    catch(Exception e){
    e.printStackTrace();
    }
    mapView.setWebViewClient(new OurViewClient());
	
	
        
        Button bcamps = (Button)findViewById(R.id.button1);
        Button bbanks = (Button)findViewById(R.id.button2);
        Button form = (Button)findViewById(R.id.button3);
         
        form.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Class newClass;
				try {
					newClass = Class.forName("com.example.social.Form");
					Intent myIntent = new Intent(Main.this,newClass);
					startActivity(myIntent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} );
        
		bcamps.setOnClickListener(new View.OnClickListener() {
			
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Class newClass;
						try {
							newClass = Class.forName("com.example.social.Map");
							Intent myIntent = new Intent(Main.this,newClass);
							startActivity(myIntent);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} );
        
		bbanks.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Class newClass;
				try {
					newClass = Class.forName("com.example.social.Map2");
					Intent myIntent = new Intent(Main.this,newClass);
					startActivity(myIntent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} );

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
