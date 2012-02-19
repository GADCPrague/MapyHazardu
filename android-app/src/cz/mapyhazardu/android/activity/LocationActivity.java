package cz.mapyhazardu.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import cz.mapyhazardu.android.MapyHazarduServiceProvider;
import cz.mapyhazardu.android.R;
import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.Owner;
import cz.mapyhazardu.api.domain.Runner;

public class LocationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
	}

	public void sendFeedback(View button) {
		EditText textName = (EditText) findViewById(R.id.location_name);
		EditText textHours = (EditText) findViewById(R.id.location_hours);
		EditText textOwner = (EditText) findViewById(R.id.location_owner);
		EditText textProvider = (EditText) findViewById(R.id.location_provider);
		
		Casino casino = new Casino();
		casino.setName(textName.getText().toString());
		casino.setOpeningHoursAsText(textHours.getText().toString());
		
		String companyNumber = textOwner.getText().toString();
		if (companyNumber != null) {
			casino.setOwner(new Owner(companyNumber));
		}
		
		companyNumber = textProvider.getText().toString();
		if (companyNumber != null) {
			casino.setRunner(new Runner(companyNumber));
		}
		
		// FIXME: doplnit pozici, kterou si vybral
//		casino.setPosition(position);
		
		try {
			MapyHazarduServiceProvider.getService().saveCasino(casino);
			
			Toast.makeText(this, R.string.message_save_ok, Toast.LENGTH_SHORT).show();
		} catch (Throwable e) {
			Toast.makeText(this, R.string.message_save_ok, Toast.LENGTH_LONG).show();
		}
		
		finish();
	}

	public void cancelFeedback(View button) {
		finish();
	}

}
