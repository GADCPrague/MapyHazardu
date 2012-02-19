package cz.mapyhazardu.api.impl;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cz.mapyhazardu.api.MapyHazardu;
import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.GeographicCoordinate;
import cz.mapyhazardu.api.domain.Owner;
import cz.mapyhazardu.api.domain.Runner;
import cz.mapyhazardu.api.impl.MapyHazarduImpl;


public class MapyHazarduApiTest {
	
	private static final String GAE_MOCK_API_URL = "http://stophazardu.appspot.com/api";
	private static final String MOCK_API_URL = "http://localhost:8888/api";
	

	@Test
	public void find_nearest_casinos_should_return_right_values() {
		
		MapyHazardu api = getMapyHazarduService();
		List<Casino> nearestCasinos = 
				api.findNearestCasinos(new GeographicCoordinate().setLatitude(49.470587).setLongtitude(17.9676526));
		
		Casino casino = new Casino();
		casino.setPosition(new GeographicCoordinate().setLatitude(49.470587).setLongtitude(17.9676526));
		
		Assert.assertTrue(nearestCasinos.contains(casino));
		
	}
	
	
	@Test
	public void find_nearest_casinos_should_returns_nothing_as_position_is_somewhere_out_of_czech_republic() {
		
		MapyHazardu api = getMapyHazarduService();
		List<Casino> nearestCasinos = 
				api.findNearestCasinos(new GeographicCoordinate().setLatitude(39.28).setLongtitude(-104.15));
		
		Casino casino = new Casino();
		casino.setPosition(new GeographicCoordinate().setLatitude(17.9697538).setLongtitude(49.4660408));
		
		Assert.assertTrue("On coordinates (39.28, -104.15) shouldn't exist any casino", nearestCasinos.size() == 0);
		
	}
	
	@Test
	public void save_casino_partial_data() {
		
		MapyHazardu api = getMapyHazarduService();
		Casino casino = new Casino();
		casino.setName("name of casino");
		
		api.saveCasino(casino);
		
	}
	
	@Test
	public void save_casino_all_data() {
		
		MapyHazardu api = getMapyHazarduService();
		Casino casino = new Casino();
		casino.setName("name of casino 2");
		casino.setOpeningHoursAsText("po-pa 8-20 so-ne 10-24");
		casino.setOwner(new Owner("name of owner of casino"));
		casino.setRunner(new Runner("name of runner of casino"));
		casino.setPosition(new GeographicCoordinate().setLatitude(34.44).setLongtitude(20.20));
		
		api.saveCasino(casino);
		
	}
	
	
	private MapyHazarduImpl getMapyHazarduService() {
		return new MapyHazarduImpl(System.getProperty("api_url", GAE_MOCK_API_URL /* as default */));
	}
	
	
}
