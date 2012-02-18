package cz.mapyhazardu.api.domain;

public class GeographicCoordinate {
	
	private double latitude;
	private double longtitude;
	
	public GeographicCoordinate() {
	}
	
	public GeographicCoordinate(double latitude, double longtitude) {
		this.latitude = latitude;
		this.longtitude = longtitude;
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
