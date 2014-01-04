package interiit.phase2.paths;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class showMap_second extends Activity {
	ImageButton map;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_second);
		
		map=(ImageButton)findViewById(R.id.imageButton2);
		map.setBackgroundResource(R.drawable.police_details);
		
		map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent intent = new Intent(getApplicationContext(), showMap_third.class);
                startActivity(intent);
            }
        });
      }

}
