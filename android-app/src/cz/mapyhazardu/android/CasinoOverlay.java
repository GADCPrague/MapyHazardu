package cz.mapyhazardu.android;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.GeographicCoordinate;

public class CasinoOverlay extends ItemizedOverlay<OverlayItem> {

	private List<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();

	private Set<GeographicCoordinate> coordinates = new HashSet<GeographicCoordinate>();
	private Context context;

	private CasinoOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		this.populate();
	}

	public CasinoOverlay(Drawable defaultMarker, Context context) {
		this(defaultMarker);
		this.context = context;
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
	}

}
