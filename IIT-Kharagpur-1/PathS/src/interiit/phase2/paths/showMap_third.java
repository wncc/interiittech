package interiit.phase2.paths;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class showMap_third extends Activity{
	ImageView iw;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_third);
		
		iw=(ImageView)findViewById(R.id.imageView1);
		iw.setBackgroundResource(R.drawable.report_detail);
	}
}
