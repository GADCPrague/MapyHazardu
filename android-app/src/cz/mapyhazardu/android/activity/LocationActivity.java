package cz.mapyhazardu.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import cz.mapyhazardu.android.MapyHazarduServiceProvider;
import cz.mapyhazardu.android.LocationUtils;
import cz.mapyhazardu.android.R;
import cz.mapyhazardu.api.domain.GeographicCoordinate;
import cz.mapyhazardu.api.domain.Owner;
import cz.mapyhazardu.api.domain.Runner;

public class LocationActivity extends Activity {

	public static final String GEO_POINT_LATITUDE = "latitude";

	public static final String GEO_POINT_LONGITUDE = "longitude";

	private GeographicCoordinate coordinate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			int latitude = bundle.getInt(GEO_POINT_LATITUDE);
			int longitude = bundle.getInt(GEO_POINT_LONGITUDE);

			GeoPoint geoPoint = new GeoPoint(latitude, longitude);
			coordinate = LocationUtils.getGeographicCoordinate(geoPoint);

			// visibility: gone
			final TextView locationGeo = (TextView) findViewById(R.id.location_geo);
			locationGeo.setText(String.format("���ka: %s\nd�lka: %s", latitude, longitude));
		}
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
