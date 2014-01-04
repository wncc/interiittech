package com.example.social;

import com.example.social.OurViewClient;

import android.os.Bundle;
import android.webkit.WebView;
import android.app.Activity;

public class Map extends Activity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		
		WebView mapView = (WebView)findViewById(R.id.webView1);
		
		mapView.getSettings().setJavaScriptEnabled(true);
        mapView.getSettings().setLoadWithOverviewMode(true);
        mapView.getSettings().setUseWideViewPort(true);
        mapView.getSettings().setAllowFileAccess(true);

	    mapView.getSettings().setBuiltInZoomControls(true);
    try{
    	mapView.loadUrl("http://students.iitmandi.ac.in/~sehaj_duggal/superhero/bldcmp.php");
    }
    catch(Exception e){
    e.printStackTrace();
    }
    mapView.setWebViewClient(new OurViewClient());
	}
	
}
