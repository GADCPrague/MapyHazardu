/**
 * 
 */
package cz.mapyhazardu.android.task;

import java.util.List;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import cz.mapyhazardu.android.CasinoOverlay;
import cz.mapyhazardu.android.LocationUtils;
import cz.mapyhazardu.api.MapyHazardu;
import cz.mapyhazardu.api.MapyHazarduMock;
import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.GeographicCoordinate;
import cz.mapyhazardu.api.impl.MapyHazarduImpl;
import android.location.Location;
import android.os.AsyncTask;

/**
 * @author vlasta
 *
 */
public class FetchCasinosTask extends AsyncTask<android.location.Location, Integer, List<Casino>> {

	private final CasinoOverlay casinoOverlay;
	private final MapView mapView;
	
	public FetchCasinosTask(CasinoOverlay casinoOverlay, MapView mapView) {
		super();
		this.casinoOverlay = casinoOverlay;
		this.mapView = mapView;
	}

	@Override
	protected List<Casino> doInBackground(Location... param) {
		Location location = param[0];
		GeographicCoordinate position = new GeographicCoordinate(location.getLatitude(), location.getLongitude());
		
		List<Casino> casinos = getService().findNearestCasinos(position);
		
		return casinos;
	}

	@Override
	protected void onPostExecute(List<Casino> result) {
		for (Casino casino : result) {
			casinoOverlay.addCasino(casino);
		}
		
//		mapView.invalidate();
//		mapView.postInvalidate();
	}

	public MapyHazardu getService() {
//		return new MapyHazarduMock();
		return new MapyHazarduImpl("http://stophazardu.appspot.com/api");
	}
}
