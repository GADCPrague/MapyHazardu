package cz.mapyhazardu.android;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import cz.mapyhazardu.android.task.FetchCasinosTask;
import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.GeographicCoordinate;

public class CasinoOverlay extends ItemizedOverlay<OverlayItem> {

	private static final String TAG = "CasinoOverlay";
	
	private List<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();

	private Set<GeographicCoordinate> coordinates = new HashSet<GeographicCoordinate>();
	private final Context context;
	private final MapView mapView;
	
	private static GeoPoint lastPosition = new GeoPoint(0, 0);
    private static GeoPoint currentPosition;
    protected boolean isMapMoving = false;

	public CasinoOverlay(Drawable defaultMarker, Context context, MapView mapView) {
		super(boundCenterBottom(defaultMarker));

		this.context = context;
		this.mapView = mapView;
		populate();
	}

	
	@Override
	protected OverlayItem createItem(int i) {
		return mapOverlays.get(i);
	}

	@Override
	public int size() {
		return mapOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mapOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

	public void addCasino(Casino casino) {
		GeographicCoordinate casinoCoordinate = casino.getPosition();
		
		if (!coordinates.contains(casinoCoordinate)) {
			addOverlay(new OverlayItem(LocationUtils.getGeoPoint(casinoCoordinate), casino.getName(), ""));
			coordinates.add(casinoCoordinate);
		}
	}
	
	private void addOverlay(OverlayItem overlay) {
		mapOverlays.add(overlay);
		this.populate();
	}

	public void fetchCasinos(GeographicCoordinate location) {
		Log.d(TAG, "Fetch request");
		new FetchCasinosTask(this, mapView).execute(location);
		Log.d(TAG, "Fetch task executed");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev, MapView mapView) {
        boolean result = super.onTouchEvent(ev, mapView);
        
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            // Added to example to make more complete
            isMapMoving = true;
        }
        //Fix: changed to false as it would handle the touch event and not pass back.
        return result;
    }

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		
		if (!shadow) {
			if (isMapMoving) {
				currentPosition = mapView.getProjection().fromPixels(0, 0);
				if (currentPosition.equals(lastPosition)) {
					isMapMoving = false;

					fetchCasinos(LocationUtils.getGeographicCoordinate(currentPosition));
				} else {
					lastPosition = currentPosition;
				}
			}
		}
	}
}
