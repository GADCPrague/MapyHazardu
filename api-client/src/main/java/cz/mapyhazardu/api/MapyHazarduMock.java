/**
 * 
 */
package cz.mapyhazardu.api;

import java.util.ArrayList;
import java.util.List;

import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.GeographicCoordinate;

/**
 * @author vlasta
 *
 */
public class MapyHazarduMock implements MapyHazardu {

	/* (non-Javadoc)
	 * @see cz.mapyhazardu.api.MapyHazardu#findNearestCasinos(cz.mapyhazardu.api.domain.GeographicCoordinate)
	 */
	@Override
	public List<Casino> findNearestCasinos(GeographicCoordinate position) {
		List<Casino> result = new ArrayList<Casino>();
		
		result.add(newCasino(50.07692995,14.403966350000001));
		result.add(newCasino(50.07592995,14.403966350000001));

		return result;
	}
	
	private Casino newCasino(double latitude, double longtitude) {
		Casino casino = new Casino();
		casino.setName("Mock casino");
		casino.setPosition(new GeographicCoordinate(latitude, longtitude));
		
		return casino;
	}

	/* (non-Javadoc)
	 * @see cz.mapyhazardu.api.MapyHazardu#saveCasino(cz.mapyhazardu.api.domain.Casino)
	 */
	@Override
	public void saveCasino(Casino casino) {
		// TODO Auto-generated method stub

	}

}
