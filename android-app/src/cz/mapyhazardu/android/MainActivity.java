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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.actionbarcompat.R;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import cz.mapyhazardu.android.task.FetchCasinosTask;

public class MainActivity extends ActionBarActivity {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private CasinoOverlay casinoOverlay;

	private MyLocationOverlay myLocationOverlay;
	private GeoUpdateHandler listener = new GeoUpdateHandler();;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        setTitle(R.string.app_name);
        
        mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapController = mapView.getController();
		mapController.setZoom(19); // Zoon 1 is world view
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		mapController.setCenter(LocationUtils.getGeoPoint(lastKnownLocation));

			         
		casinoOverlay = new CasinoOverlay(getResources().getDrawable(R.drawable.ic_launcher), this);
		mapView.getOverlays().add(casinoOverlay);
		
		myLocationOverlay = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(myLocationOverlay);
        mapView.postInvalidate();		
    }
    
    @Override
    protected void onResume() {
            super.onResume();
            myLocationOverlay.enableMyLocation();
    		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,	0, listener);
    }

    @Override
    protected void onPause() {
            super.onPause();
        	if (listener != null) {
        		locationManager.removeUpdates(listener);
        	}
            myLocationOverlay.disableMyLocation();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_base, menu);

        // Calling super after populating the menu is necessary here to ensure that the
        // action bar helpers have a chance to handle this event.
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
            	Toast.makeText(this, "Probíhá aktualizace lokací.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_add:               
    			Intent intent = new Intent("cz.mapyhazardu.android.activity.EDIT");
    			startActivity(intent);
                
                Toast.makeText(this, "Vyznačte novou lokaci kliknutím v mapě a potvrďte.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private void fetchCasinos(Location location) {
		new FetchCasinosTask(casinoOverlay, mapView).execute(location);
	}
	
	public class GeoUpdateHandler implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			mapController.animateTo(LocationUtils.getGeoPoint(location)); // mapController.setCenter(point);

			fetchCasinos(location);
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

}
