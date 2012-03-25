/*
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cz.mapyhazardu.android;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

import cz.mapyhazardu.android.activity.EditActivity;
import cz.mapyhazardu.android.activity.LocationActivity;

public class MainActivity extends ActionBarActivity {

	private static final boolean ONLY_ENABLED_PROVIDERS = true;
	
	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private CasinoOverlay casinoOverlay;

	private MyLocationOverlay myLocationOverlay;
	private GeoUpdateHandler listener = new GeoUpdateHandler();

	private GeoPoint geoPoint;
	
	private boolean currentPositionAcquired = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapController = mapView.getController();
		mapController.setZoom(16); // Zoom 1 is world view

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		casinoOverlay = new CasinoOverlay(getResources().getDrawable(R.drawable.casino_icon), this, mapView);
		mapView.getOverlays().add(casinoOverlay);

		myLocationOverlay = new MyLocationOverlay(this, mapView);
		myLocationOverlay.runOnFirstFix(new Runnable() {
			
			@Override
			public void run() {
				mapController.setCenter(LocationUtils.getGeoPoint(getLastKnownLocation()));
			}
			
		});
		
		mapView.getOverlays().add(myLocationOverlay);

		MapOverlay mapOverlay = new MapOverlay();
		mapView.getOverlays().add(mapOverlay);

		mapView.postInvalidate();
	}

	@Override
	protected void onResume() {
		super.onResume();
		currentPositionAcquired = false;
		myLocationOverlay.enableMyLocation();
		locationManager.requestLocationUpdates(getLocationProvider(), 5000, 100, listener);
	}

	@Override
	protected void onPause() {
		super.onPause();
		currentPositionAcquired = false;
		if (listener != null) {
			locationManager.removeUpdates(listener);
		}
		myLocationOverlay.disableMyLocation();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	private Location getLastKnownLocation() {
		String bestAvailableLocationProvider = getLocationProvider();
		Location lastKnownLocation = locationManager.getLastKnownLocation(bestAvailableLocationProvider);
		
		return lastKnownLocation;
	}

	private String getLocationProvider() {
		return locationManager.getBestProvider(accuracyIsMostImportant(), ONLY_ENABLED_PROVIDERS);
	}
	
	private Criteria accuracyIsMostImportant() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		return criteria;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_base, menu);

		// Calling super after populating the menu is necessary here to ensure
		// that the
		// action bar helpers have a chance to handle this event.

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			Toast.makeText(this, getResources().getString(R.string.message_refresh), Toast.LENGTH_SHORT).show();
			geoPoint = mapView.getProjection().fromPixels(0, 0);
			casinoOverlay.fetchCasinos(LocationUtils.getGeographicCoordinate(geoPoint));
			break;
		case R.id.menu_add:
			Intent intent = new Intent(this, EditActivity.class);
			if (geoPoint != null) {
				intent.putExtra(LocationActivity.GEO_POINT_LATITUDE, geoPoint.getLatitudeE6());
				intent.putExtra(LocationActivity.GEO_POINT_LONGITUDE, geoPoint.getLongitudeE6());
			}
			startActivity(intent);

			Toast.makeText(this, getResources().getString(R.string.message_add_new_loc), Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public class GeoUpdateHandler implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			if (!currentPositionAcquired) {
				mapController.animateTo(LocationUtils.getGeoPoint(location)); // mapController.setCenter(point);
				currentPositionAcquired = true;
				casinoOverlay.fetchCasinos(LocationUtils.getGeographicCoordinate(location));
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	class MapOverlay extends Overlay {

		@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapview) {
			if ((event.getAction() == MotionEvent.ACTION_DOWN) || (event.getAction() == MotionEvent.ACTION_POINTER_DOWN)) {
				geoPoint = mapview.getProjection().fromPixels((int) event.getX(), (int) event.getY());
			} else {

			}
			return false;
		}

	}

}
