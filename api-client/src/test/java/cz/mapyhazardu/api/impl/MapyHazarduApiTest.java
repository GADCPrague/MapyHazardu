package cz.mapyhazardu.api.impl;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cz.mapyhazardu.api.MapyHazardu;
import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.GeographicCoordinate;
import cz.mapyhazardu.api.domain.Owner;
import cz.mapyhazardu.api.domain.Runner;


public class MapyHazarduApiTest {
	
	private static final String GAE_MOCK_API_URL = "http://stophazardu.appspot.com/api";
	private static final String LOCAL_MOCK_API_URL = "http://localhost:8888/api";
	

	@Test
	public void find_nearest_casinos_should_return_right_casinos() {
		
		MapyHazardu api = getMapyHazarduService();
		List<Casino> nearestCasinos = 
				api.findNearestCasinos(new GeographicCoordinate().setLatitude(49.470587).setLongtitude(17.9676526));
		
		List<Casino> right = new ArrayList<Casino>();
		for(Casino c : nearestCasinos) {
			
			if (c.getPosition().equals(new GeographicCoordinate().setLatitude(49.470587).setLongtitude(17.9676526))) {
				right.add(c);
			}
			
		}
		
		Assert.assertTrue("It seems it doesn't contains right data", right.size() > 0);
		
	}
	
	@Test
	public void find_nearest_casinos_should_return_right_name() {
		
		MapyHazardu api = getMapyHazarduService();
		List<Casino> nearestCasinos = 
				api.findNearestCasinos(new GeographicCoordinate().setLatitude(49.470587).setLongtitude(17.9676526));
		
		Casino firstFoundedCasino = nearestCasinos.get(0);
		Assert.assertTrue("It seems casino.name is not setted properly", firstFoundedCasino.getName() != null);
		Assert.assertTrue("It seems casino.name is not setted properly", firstFoundedCasino.getName().length() > 0);
		
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
		GeographicCoordinate position = new GeographicCoordinate().setLatitude(49.467202).setLongtitude(17.9676215);
		casino.setPosition(position);
		
		api.saveCasino(casino);
		
	}
	
	@Test
	public void save_casino_is_successfull() {
		
		MapyHazardu api = getMapyHazarduService();
		Casino casino = new Casino();
		casino.setName("name of casino 2");
		casino.setOpeningHoursAsText("po-pa 8-20 so-ne 10-24");
		casino.setOwner(new Owner("name of owner of casino"));
		casino.setRunner(new Runner("name of runner of casino"));
		GeographicCoordinate position = new GeographicCoordinate().setLatitude(34.44).setLongtitude(20.20);
		casino.setPosition(position);
		
		List<Casino> casinos = api.findNearestCasinos(position);
		int countOfCasinosBeforeSave = casinos.size();
		
		api.saveCasino(casino);
		
		List<Casino> casinosAfterSave = api.findNearestCasinos(position);
		
		Assert.assertTrue(casinosAfterSave.size() > countOfCasinosBeforeSave);
		
	}
	
	@Test
	public void save_casino_is_successfull_2() {
		
		MapyHazardu api = getMapyHazarduService();
		Casino casino = new Casino();
		casino.setName("kasino");
		casino.setOpeningHoursAsText("8-23");
		GeographicCoordinate position = new GeographicCoordinate().setLatitude(34.44).setLongtitude(20.20);
		casino.setPosition(position);
		
		List<Casino> casinos = api.findNearestCasinos(position);
		int countOfCasinosBeforeSave = casinos.size();
		
		api.saveCasino(casino);
		
		List<Casino> casinosAfterSave = api.findNearestCasinos(position);
		
		Assert.assertTrue(casinosAfterSave.size() > countOfCasinosBeforeSave);
		
	}
	
	
	private MapyHazarduImpl getMapyHazarduService() {
		return new MapyHazarduImpl(System.getProperty("api_url", LOCAL_MOCK_API_URL /* as default */));
	}
	
	
}
