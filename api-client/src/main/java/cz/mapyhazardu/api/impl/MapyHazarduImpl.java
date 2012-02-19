package cz.mapyhazardu.api.impl;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
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
import cz.mapyhazardu.api.domain.Owner;
import cz.mapyhazardu.api.domain.Runner;


public class MapyHazarduImpl implements MapyHazardu {
	
	private final String apiUrl;

	public MapyHazarduImpl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	@Override
	public List<Casino> findNearestCasinos(GeographicCoordinate position) {
	
		List<Casino> casinos = new ArrayList<Casino>();

		String url = String.format(apiUrl + "?lng=%s&lat=%s", position.getLongtitude(), position.getLatitude());
		
		URL mapyHazarduApiUrl;
		
		try {
			
			mapyHazarduApiUrl = new URL(url);
			
			String json = toString(mapyHazarduApiUrl);
			
			Gson gson = new Gson();
			MapyHazarduFindNearestResult[] results = gson.fromJson(json, MapyHazarduFindNearestResult[].class);
			
			for(MapyHazarduFindNearestResult result : results) {
				Casino casino = new Casino();
				Double[] positionTuple = result.getPosition();
				Double longitude = positionTuple[0];
				Double latitude = positionTuple[1];
				casino.setPosition(new GeographicCoordinate().setLatitude(latitude).setLongtitude(longitude));
				casino.setName(result.getTitle());
				
				casinos.add(casino);	
			}
			
			return casinos;
			
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} 		
	}	
	

	@Override
	public void saveCasino(Casino casino) {
		
		CasinoSaveRequest saveRequest = transformToSaveRequest(casino);
		String json = transformToJson(saveRequest);
		
		sendSaveCasinoRequestToApi(apiUrl, json);
        
        
	}

	/**
	 * @param apiUrl base 
	 * @param json to send
	 */
	private void sendSaveCasinoRequestToApi(String apiUrl, String json) {
		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("data", json));
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
	        AbstractHttpClient httpClient = new DefaultHttpClient();
	        HttpResponse response = httpClient.execute(httpPost);
	        int responseStatusCode = response.getStatusLine().getStatusCode();
	        
	        if (responseStatusCode != 201 /* created */) {
	        	throw new RuntimeException("Casino was not created. Something goes wrong :(");
	        }
	        
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String transformToJson(CasinoSaveRequest saveRequest) {
		Gson gson = new Gson();
		String json = gson.toJson(saveRequest);
		return json;
	}

	private CasinoSaveRequest transformToSaveRequest(Casino casino) {
		CasinoSaveRequest saveRequest = new CasinoSaveRequest();
		
		/* name */
		saveRequest.setNazev(casino.getName());
		
		/* position */
		GeographicCoordinate casinoPosition = casino.getPosition();
		if (casinoPosition != null) {
			saveRequest.setLatitude(String.valueOf(casinoPosition.getLatitude()));
			saveRequest.setLongitude(String.valueOf(casinoPosition.getLongtitude()));
		}
		
		/* owner */
		Owner owner = casino.getOwner();
		if (owner != null) {
			saveRequest.setMajitel(owner.getCompanyNumber());
		}
		
		/* runner */
		Runner runner = casino.getRunner();
		if (runner != null) {
			saveRequest.setProvozovatel(runner.getCompanyNumber());
		}
		
		/* openning hours */
		String openingHoursAsText = casino.getOpeningHoursAsText();
		if (openingHoursAsText != null) {
			saveRequest.setOteviraciDoba(openingHoursAsText);
		}
		return saveRequest;
	}
	
	
	private String toString(URL url) {
		try {
			final char[] buffer = new char[0x10000];
			StringBuilder out = new StringBuilder();
			Reader in = new InputStreamReader((InputStream) url.getContent(), "UTF-8");
			int read;
			do {
				read = in.read(buffer, 0, buffer.length);
				if (read>0) {
					out.append(buffer, 0, read);
				}
			} while (read>=0);
			String result = out.toString();
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
