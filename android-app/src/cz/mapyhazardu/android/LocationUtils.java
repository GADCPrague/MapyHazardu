package cz.mapyhazardu.android;

import android.location.Location;

import com.google.android.maps.GeoPoint;

public class LocationUtils {

	public static GeoPoint getGeoPoint(Location location) {
		int lat = (int) (location.getLatitude() * 1E6);
		int lng = (int) (location.getLongitude() * 1E6);
		GeoPoint point = new GeoPoint(lat, lng);
		return point;
	}
}
