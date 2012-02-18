package cz.mapyhazardu.api.domain;
import org.apache.commons.lang.Validate;


public class Hour {

	private int hour;
	
	private Hour(int hour) {
		Validate.isTrue(hour >= 1 && hour <= 24, "Hour must be number in interval 1-24");
		this.hour = hour;
	}
	
	public static Hour of(int hour) {
		return new Hour(hour);
	}

	public int value() {
		return hour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
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
		Hour other = (Hour) obj;
		if (hour != other.hour)
			return false;
		return true;
	}
	
	
	
}
