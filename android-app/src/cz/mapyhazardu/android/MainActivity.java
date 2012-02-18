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

import com.example.android.actionbarcompat.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import cz.mapyhazardu.android.task.FetchCasinosTask;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    private boolean mAlternateTitle = false;

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private CasinoOverlay casinoOverlay;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.toggle_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlternateTitle) {
                    setTitle(R.string.app_name);
                } else {
                    setTitle(R.string.alternate_title);
                }
                mAlternateTitle = !mAlternateTitle;
            }
        });
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);
		mapController = mapView.getController();
		mapController.setZoom(19); // Zoon 1 is world view
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		mapController.setCenter(LocationUtils.getGeoPoint(lastKnownLocation));
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
				0, new GeoUpdateHandler());

		casinoOverlay = new CasinoOverlay(getResources().getDrawable(R.drawable.ic_launcher), mapView);
//		Drawable drawable = this.getResources().getDrawable(R.drawable.point);
//		itemizedoverlay = new MyOverlays(drawable);
//		createMarker();
		

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        // Calling super after populating the menu is necessary here to ensure that the
        // action bar helpers have a chance to handle this event.
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_refresh:
                Toast.makeText(this, "Fake refreshing...", Toast.LENGTH_SHORT).show();
                getActionBarHelper().setRefreshActionItemState(true);
                getWindow().getDecorView().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                getActionBarHelper().setRefreshActionItemState(false);
                            }
                        }, 1000);
                break;

            case R.id.menu_search:
                Toast.makeText(this, "Tapped search", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_share:
                Toast.makeText(this, "Tapped share", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void fetchCasinos(Location location) {
		new FetchCasinosTask(casinoOverlay).execute(location);
	}
	
	public class GeoUpdateHandler implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			//createMarker();
			mapController.animateTo(LocationUtils.getGeoPoint(location)); // mapController.setCenter(point);
//			
			fetchCasinos(location);
//			makeUseOfNewLocation(location);

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

	private void createMarker() {
//		GeoPoint p = mapView.getMapCenter();
//		OverlayItem overlayitem = new OverlayItem(p, "", "");
//		itemizedoverlay.addOverlay(overlayitem);
//		mapView.getOverlays().add(itemizedoverlay);
	}

}
