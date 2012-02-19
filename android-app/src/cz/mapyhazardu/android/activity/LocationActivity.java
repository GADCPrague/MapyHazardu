package cz.mapyhazardu.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;

import cz.mapyhazardu.android.LocationUtils;
import cz.mapyhazardu.android.R;
import cz.mapyhazardu.api.domain.GeographicCoordinate;

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
			locationGeo.setText(String.format("šířka: %s\ndélka: %s", latitude, longitude));
		}
	}

	public void sendFeedback(View button) {
		finish();
	}

	public void cancelFeedback(View button) {
		finish();
	}

}
