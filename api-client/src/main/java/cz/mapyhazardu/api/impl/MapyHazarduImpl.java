package cz.mapyhazardu.api.impl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import cz.mapyhazardu.api.MapyHazardu;
import cz.mapyhazardu.api.domain.Casino;
import cz.mapyhazardu.api.domain.GeographicCoordinate;


public class MapyHazarduImpl implements MapyHazardu {
	
	private final String apiUrl;



	public MapyHazarduImpl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	@Override
	public List<Casino> findNearestCasinos(GeographicCoordinate position) {
	
		List<Casino> casinos = new ArrayList<Casino>();

		String url = String.format(apiUrl + "?lng=%s&lat=%s", position.getLongtitude(), position.getLatitude());
		
		URL mapyHazarduApi;
		try {
			mapyHazarduApi = new URL(url);
			
			String json = IOUtils.toString(mapyHazarduApi);
			
			Gson gson = new Gson();
			MapyHazarduFindNearestResult[] results = gson.fromJson(json, MapyHazarduFindNearestResult[].class);
			
			for(MapyHazarduFindNearestResult result : results) {
				Casino casino = new Casino();
				Double[] positionTuple = result.getPosition();
				Double longitude = positionTuple[0];
				Double latitude = positionTuple[1];
				casino.setPosition(new GeographicCoordinate().setLatitude(latitude).setLongtitude(longitude));
				
				casinos.add(casino);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return casinos;
		
	}	
	
	

	@Override
	public void saveCasino(Casino casino) {

	}
	
	

}
