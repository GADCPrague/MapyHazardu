package cz.mapyhazardu.android.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.actionbarcompat.R;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import cz.mapyhazardu.android.ActionBarActivity;
import cz.mapyhazardu.android.LocationOverlay;
import cz.mapyhazardu.android.LocationUtils;

public class EditActivity extends ActionBarActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);

		MapController mapController = mapView.getController();
		mapController.setZoom(19);

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		mapController.setCenter(LocationUtils.getGeoPoint(lastKnownLocation));

		LocationOverlay locationOverlay = new LocationOverlay(getResources().getDrawable(R.drawable.ic_menu_position));
		OverlayItem overlayitem = new OverlayItem(LocationUtils.getGeoPoint(lastKnownLocation), "Vaše aktuální poloha", "Potvrďte novou pozici v horním menu.");
		locationOverlay.addOverlay(overlayitem);
		mapView.getOverlays().add(locationOverlay);

		mapView.postInvalidate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_edit, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_ok:
			Intent intent = new Intent("cz.mapyhazardu.android.activity.LOCATION");
			startActivity(intent);
			break;
		case R.id.menu_cancel:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
