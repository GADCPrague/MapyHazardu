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

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
=======
import android.content.Context;
>>>>>>> 65221fbe4e5047a2d0eac57bd6b67cae17742eb4
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
<<<<<<< HEAD
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
=======
>>>>>>> 65221fbe4e5047a2d0eac57bd6b67cae17742eb4
import android.widget.Toast;

import com.example.android.actionbarcompat.R;
import com.google.android.maps.GeoPoint;
<<<<<<< HEAD
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
=======
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
>>>>>>> 65221fbe4e5047a2d0eac57bd6b67cae17742eb4
import com.google.android.maps.OverlayItem;

public class MainActivity extends ActionBarActivity {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private CasinoOverlays itemizedoverlay;
<<<<<<< HEAD
	
	private static final int latitudeE6 = 37985339;
	private static final int longitudeE6 = 23716735;
=======
	private MyLocationOverlay myLocationOverlay;
>>>>>>> 65221fbe4e5047a2d0eac57bd6b67cae17742eb4

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setTitle(R.string.app_name);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        
		mapView.setBuiltInZoomControls(true);
		mapView.setStreetView(true);
		mapController = mapView.getController();
		mapController.setZoom(19); // Zoon 1 is world view
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		mapController.setCenter(LocationUtils.getGeoPoint(lastKnownLocation));
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, new GeoUpdateHandler());

<<<<<<< HEAD
//		Drawable drawable = this.getResources().getDrawable(R.drawable.point);
//		itemizedoverlay = new MyOverlays(drawable);
//		createMarker();
		
			         
			        List<Overlay> mapOverlays = mapView.getOverlays();
			        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
			        CustomItemizedOverlay itemizedOverlay =
			             new CustomItemizedOverlay(drawable, this);
			         
//			        GeoPoint point = new GeoPoint(latitudeE6, longitudeE6);
			        OverlayItem overlayitem =
			             new OverlayItem(LocationUtils.getGeoPoint(lastKnownLocation), "Hello", "I'm in HUB!");
			         
			        itemizedOverlay.addOverlay(overlayitem);
			        mapOverlays.add(itemizedOverlay);
			         
//			        MapController mapController = mapView.getController();
//			         
//			        mapController.animateTo(LocationUtils.getGeoPoint(lastKnownLocation));
//			        mapController.setZoom(6);
			         


=======
		Drawable drawable = this.getResources().getDrawable(R.drawable.ic_maps_indicator_current_position);
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		// add this overlay to the MapView and refresh it
        mapView.getOverlays().add(myLocationOverlay);
        mapView.postInvalidate();		
>>>>>>> 65221fbe4e5047a2d0eac57bd6b67cae17742eb4

    }
    
    @Override
    protected void onResume() {
            super.onResume();
            // when our activity resumes, we want to register for location updates
            myLocationOverlay.enableMyLocation();
    }

    @Override
    protected void onPause() {
            super.onPause();
            // when our activity pauses, we want to remove listening for location updates
            myLocationOverlay.disableMyLocation();
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
	
	public class GeoUpdateHandler implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			//createMarker();
			mapController.animateTo(LocationUtils.getGeoPoint(location)); // mapController.setCenter(point);
//			
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
		GeoPoint p = mapView.getMapCenter();
		OverlayItem overlayitem = new OverlayItem(p, "", "");
		itemizedoverlay.addOverlay(overlayitem);
		mapView.getOverlays().add(itemizedoverlay);
	}
		
}
