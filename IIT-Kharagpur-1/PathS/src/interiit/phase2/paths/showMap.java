package interiit.phase2.paths;

import interiit.phase2.paths.MainActivity.AppSectionsPagerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class showMap extends Activity {

	ImageButton map;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		map=(ImageButton)findViewById(R.id.imageButton1);
		map.setBackgroundResource(R.drawable.path_with_police_n_report);
		
		map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent intent = new Intent(getApplicationContext(), showMap_second.class);
                startActivity(intent);
            }
        });
      }
}
