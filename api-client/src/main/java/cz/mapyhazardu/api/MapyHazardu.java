package cz.mapyhazardu.api;

import java.util.List;

import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.GeographicCoordinate;

public interface MapyHazardu {

	public List<Casino> findNearestCasinos(GeographicCoordinate position);
	
	public void saveCasino(Casino casino);
	
}
