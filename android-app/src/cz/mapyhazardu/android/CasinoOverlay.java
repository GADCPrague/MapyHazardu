package cz.mapyhazardu.android;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

public class CasinoOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private List<OverlayItem> items = new ArrayList<OverlayItem>();
	private final MapView mapView;
	
	public CasinoOverlay(Drawable defaultMarker, MapView mapView) {
		super(defaultMarker, mapView);
		this.mapView = mapView;
	}

	public void addItem(OverlayItem item) {
		items.add(item);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int index) {
		return items.get(index);
	}

	@Override
	public int size() {
		return items.size();
	}

	public MapView getMapView() {
		return mapView;
	}

}
