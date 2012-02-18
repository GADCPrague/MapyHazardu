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
	
}
