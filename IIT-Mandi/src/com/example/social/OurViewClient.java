package com.example.social;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OurViewClient extends WebViewClient {

	
	@Override
	public boolean shouldOverrideUrlLoading(WebView v,String url){
		v.loadUrl(url);
		return true;
	}
	
	
	
	
}
