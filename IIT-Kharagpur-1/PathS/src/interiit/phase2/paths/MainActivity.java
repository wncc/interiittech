package interiit.phase2.paths;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new FindRouteFragment();
                case 1:
                	return new RatePlaceFragment();
                case 2:
                	return new HelpFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
            	case 0: return "Find Routes";
            	case 1: return "Rate Area";
            	case 2: return "Help";
            	default: return "Tab";
            }
        }
    }
	
    public static class FindRouteFragment extends Fragment {
    	 EditText et1,et2;
    	 Button bi;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.find_route, container, false);
            bi=(Button)rootView.findViewById(R.id.button1);
            bi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            et1 = (EditText) rootView.findViewById(R.id.editText1);
                            et2 = (EditText) rootView.findViewById(R.id.editText2);
                        	if(et1.getText().length() == 0 || et2.getText().length() == 0){
                        		Toast.makeText(getActivity(), "Enter From and To", Toast.LENGTH_LONG).show();
                        	}
                        	else{
	                        	Intent intent = new Intent(getActivity(), showMap.class);
	                            startActivity(intent);
                        	}
                        }
                    });

            return rootView;
        }
    }
    public static class RatePlaceFragment extends Fragment {
    	@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.rate_place, container, false);
            //final String location = new String("Your current location is 27.788665, 85.347651");
            final EditText et1 = (EditText) rootView.findViewById(R.id.editText1);
            final TextView tv = (TextView) rootView.findViewById(R.id.textView1);
          //RatingBar rb = (RatingBar) rootView.findViewById(R.id.ratingBar1);
            tv.setText("Your current location is \n19.1338775, 72.9160419 ");
            rootView.findViewById(R.id.button1)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            
                            //if(rb.getRating() == 0.0){
                        		// Toast
                        	//}
                        	//else{
	                        	// Send rate and comment to server 
                        		// Toast
                        		// Reset
                        		//String location1 = "Your current location is 27.788665, 85.347651";
                        		tv.setText("Your current location is \n19.1339673, 72.9161028");
                        		et1.setText("");
                        		//rb.setRating((float) 0.0);
                        	//}
                        }
                    });

            return rootView;
        }
    }
    
    

    public static class HelpFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.help, container, false);
            //Read file in a string
            //String file = null;
            //((TextView) rootView.findViewById(android.R.id.text1)).setText("Set this");
            TextView tv = (TextView)rootView.findViewById(R.id.textView1);
            tv.setText("ABOUT THE APP: \n\n1. The App provides you the safest possible path amongst the options available between source " +
            		"and destination. In the map shown, blue is indicated as the safest path, while the grey ones are the other " +
            		"options available.\n\n2. The red areas are dangerous and their radius depends on the ratings provided by the users. You can click on the police icon to see the address and phone number of nearby police station." +
            		" You can also click on the red marker to see the crime reports of the respective place" +
            		"\n\n3. If you have report about any incident in your locality, you can rate the location as \n\t1- most dangerous to " +
            		"5- Safest \n\nYour location will be ensured by showing the GPS location. You can also give detailed information about the location.");
            return rootView;
        }
    }
}
