/**
 * 
 */
package cz.mapyhazardu.android.task;

import java.util.List;

import com.google.android.maps.OverlayItem;

import cz.mapyhazardu.android.CasinoOverlay;
import cz.mapyhazardu.android.LocationUtils;
import cz.mapyhazardu.api.MapyHazardu;
import cz.mapyhazardu.api.MapyHazarduMock;
import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.GeographicCoordinate;
import android.location.Location;
import android.os.AsyncTask;

/**
 * @author vlasta
 *
 */
public class FetchCasinosTask extends AsyncTask<android.location.Location, Integer, List<Casino>> {

	private final CasinoOverlay casinoOverlay;
	
	public FetchCasinosTask(CasinoOverlay casinoOverlay) {
		super();
		this.casinoOverlay = casinoOverlay;
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
			casinoOverlay.addItem(new OverlayItem(LocationUtils.getGeoPoint(casino.getPosition()), "", ""));
		}
		
		casinoOverlay.getMapView().postInvalidate();
	}

	public MapyHazardu getService() {
		return new MapyHazarduMock();
	}
}
