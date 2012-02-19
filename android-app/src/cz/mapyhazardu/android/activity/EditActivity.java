package cz.mapyhazardu.android.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import cz.mapyhazardu.android.ActionBarActivity;
import cz.mapyhazardu.android.CasinoOverlay;
import cz.mapyhazardu.android.LocationOverlay;
import cz.mapyhazardu.android.LocationUtils;
import cz.mapyhazardu.android.R;

public class EditActivity extends ActionBarActivity {

	private MapView mapView;

	private GeoPoint geoPoint;

	private LocationOverlay locationOverlay;
	private MapOverlay mapOverlay;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);

		MapController mapController = mapView.getController();
		mapController.setZoom(19);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			int latitude = bundle.getInt(LocationActivity.GEO_POINT_LATITUDE);
			int longitude = bundle.getInt(LocationActivity.GEO_POINT_LONGITUDE);
			geoPoint = new GeoPoint(latitude, longitude);
		} else {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			geoPoint = LocationUtils.getGeoPoint(lastKnownLocation);
		}
		mapController.setCenter(geoPoint);

		locationOverlay = new LocationOverlay(getResources().getDrawable(R.drawable.ic_menu_position));
		OverlayItem overlayitem = new OverlayItem(geoPoint, getResources().getString(R.string.message_your_current_loc), getResources().getString(
				R.string.message_confirm_new_loc));
		locationOverlay.addOverlay(overlayitem);
		mapView.getOverlays().add(locationOverlay);

		mapOverlay = new MapOverlay();
		mapView.getOverlays().add(mapOverlay);

		CasinoOverlay casinoOverlay = new CasinoOverlay(getResources().getDrawable(R.drawable.casino_icon), this, mapView);
		mapView.getOverlays().add(casinoOverlay);
		
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
			Intent intent = new Intent(this, LocationActivity.class);
			if (geoPoint != null) {
				intent.putExtra(LocationActivity.GEO_POINT_LATITUDE, geoPoint.getLatitudeE6());
				intent.putExtra(LocationActivity.GEO_POINT_LONGITUDE, geoPoint.getLongitudeE6());
			}
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

	class MapOverlay extends Overlay {

		@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapview) {

			if ((event.getAction() == MotionEvent.ACTION_DOWN) || (event.getAction() == MotionEvent.ACTION_POINTER_DOWN)) {
				mapView.getOverlays().remove(locationOverlay);
				mapView.getOverlays().remove(mapOverlay);
				mapView.postInvalidate();

				geoPoint = mapview.getProjection().fromPixels((int) event.getX(), (int) event.getY());
				// Toast.makeText(getBaseContext(), geoPoint.getLatitudeE6() /
				// 1E6 + "," + geoPoint.getLongitudeE6() / 1E6,
				// Toast.LENGTH_SHORT).show();

				locationOverlay = new LocationOverlay(getResources().getDrawable(R.drawable.ic_menu_position));
				OverlayItem overlayitem = new OverlayItem(geoPoint, getResources().getString(R.string.message_your_current_loc), getResources().getString(
						R.string.message_confirm_new_loc));
				locationOverlay.addOverlay(overlayitem);
				mapView.getOverlays().add(locationOverlay);

				mapOverlay = new MapOverlay();
				mapView.getOverlays().add(mapOverlay);

				mapView.postInvalidate();
			} else {

			}

			return false;
		}

	}

}
