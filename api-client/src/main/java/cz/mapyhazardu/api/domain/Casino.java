package cz.mapyhazardu.api.domain;

public class Casino {

	private String name;
	private GeographicCoordinate position;
	private OpeningHours openingHours;
	private Owner owner;
	private Runner runner;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public GeographicCoordinate getPosition() {
		return position;
	}
	public void setPosition(GeographicCoordinate position) {
		this.position = position;
	}
	public OpeningHours getOpeningHours() {
		return openingHours;
	}
	public void setOpeningHours(OpeningHours openingHours) {
		this.openingHours = openingHours;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public Runner getRunner() {
		return runner;
	}
	public void setRunner(Runner runner) {
		this.runner = runner;
	}
	
	

}
