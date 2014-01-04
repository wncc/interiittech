package com.example.bleedred;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class LoggedInActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logged_in);
		
		 TabHost tabHost = getTabHost();
         
        // Tab for Nearby
        TabSpec nearbyspec = tabHost.newTabSpec("Nearby");
        // setting Title and Icon for the Tab
        nearbyspec.setIndicator("Nearby");
        Intent nearbyIntent = new Intent(this, NearbyActivity.class);
        nearbyspec.setContent(nearbyIntent);
         
        // Tab for Requests
        TabSpec requestspec = tabHost.newTabSpec("Requests");       
        requestspec.setIndicator("Requests");
        Intent requestIntent = new Intent(this, RequestsActivity.class);
        requestspec.setContent(requestIntent);
         
        // Tab for Profile
        TabSpec profilespec = tabHost.newTabSpec("Profile");       
        profilespec.setIndicator("Profile");
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        profilespec.setContent(profileIntent);
        
        // Tab for Leaders
        TabSpec leaderspec = tabHost.newTabSpec("Leaders");       
        leaderspec.setIndicator("Leaders");
        Intent leaderIntent = new Intent(this, LeadersActivity.class);
        leaderspec.setContent(leaderIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(nearbyspec); // Adding nearby tab
        tabHost.addTab(requestspec); // Adding requests tab
        tabHost.addTab(profilespec); // Adding profile tab
        tabHost.addTab(leaderspec);
	    }
	
	}


