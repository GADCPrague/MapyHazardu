package cz.mapyhazardu.api.impl;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
			
			return casinos;
			
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}	
	

	@Override
	public void saveCasino(Casino casino) {
		
		CasinoSaveRequest saveRequest = new CasinoSaveRequest();
		saveRequest.setLatitude(String.valueOf(casino.getPosition().getLatitude()));
		saveRequest.setLongitude(String.valueOf(casino.getPosition().getLongtitude()));
		saveRequest.setMajitel(casino.getOwner().getCompanyNumber());
		saveRequest.setProvozovatel(casino.getRunner().getCompanyNumber());
		saveRequest.setOteviraciDoba(casino.getOpeningHoursAsText());
		
		Gson gson = new Gson();
		String json = gson.toJson(saveRequest);
		
		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("data", "12345"));
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
	        AbstractHttpClient httpClient = new DefaultHttpClient();
	        httpClient.execute(httpPost);
	        
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        
        
	}
	
	

}
