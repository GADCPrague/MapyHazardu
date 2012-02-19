package cz.mapyhazardu.api.impl;

public class CasinoSaveRequest {
	
	String nazev;
	String latitude;
	String longitude;
	String oteviraciDoba;
	String majitel;
	String provozovatel;
	
	public String getNazev() {
		return nazev;
	}
	public void setNazev(String nazev) {
		this.nazev = nazev;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getOteviraciDoba() {
		return oteviraciDoba;
	}
	public void setOteviraciDoba(String oteviraciDoba) {
		this.oteviraciDoba = oteviraciDoba;
	}
	
	public String getMajitel() {
		return majitel;
	}
	public void setMajitel(String majitel) {
		this.majitel = majitel;
	}
	
	public String getProvozovatel() {
		return provozovatel;
	}	
	public void setProvozovatel(String provozovatel) {
		this.provozovatel = provozovatel;
	}

	
}
