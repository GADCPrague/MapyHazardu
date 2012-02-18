package cz.mapyhazardu.api.domain;

public class Casino {

	private String name;
	private GeographicCoordinate position;
	private String openingHoursAsText;
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
	

	public String getOpeningHoursAsText() {
		return openingHoursAsText;
	}
	public void setOpeningHoursAsText(String openingHoursAsText) {
		this.openingHoursAsText = openingHoursAsText;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((openingHoursAsText == null) ? 0 : openingHoursAsText
						.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((runner == null) ? 0 : runner.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Casino other = (Casino) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (openingHoursAsText == null) {
			if (other.openingHoursAsText != null)
				return false;
		} else if (!openingHoursAsText.equals(other.openingHoursAsText))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (runner == null) {
			if (other.runner != null)
				return false;
		} else if (!runner.equals(other.runner))
			return false;
		return true;
	}

	

}
