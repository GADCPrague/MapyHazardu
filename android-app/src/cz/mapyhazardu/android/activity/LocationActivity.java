package cz.mapyhazardu.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.android.actionbarcompat.R;

public class LocationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
	}

	public void sendFeedback(View button) {
		finish();
	}

}
