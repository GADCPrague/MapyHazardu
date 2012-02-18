package cz.mapyhazardu.api.domain;

public class GeographicCoordinate {
	
	private double latitude;
	private double longtitude;
	
	public GeographicCoordinate() {
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongtitude() {
		return longtitude;
	}

	public GeographicCoordinate setLatitude(double latitude) {
		this.latitude = latitude;
		return this;
	}

	public GeographicCoordinate setLongtitude(double longtitude) {
		this.longtitude = longtitude;
		return this;
	}
	
	
	
	
}
