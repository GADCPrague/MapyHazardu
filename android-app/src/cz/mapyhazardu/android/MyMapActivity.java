package cz.mapyhazardu.android;

import android.os.Bundle;

import com.example.android.actionbarcompat.R;
import com.google.android.maps.MapActivity;

public class MyMapActivity extends MapActivity {
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.map_fragment);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
