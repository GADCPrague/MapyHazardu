package cz.mapyhazardu.api.domain;

public class OpeningHours {

	private Hour from;
	private Hour to;
	
	public OpeningHours(Hour from, Hour to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public OpeningHours(int from, int to) {
		super();
		this.from = Hour.of(from);
		this.to = Hour.of(to);
	}
	
	public void setFrom(int from) {
		this.from = Hour.of(from);
	}

	public void setTo(int to) {
		this.to = Hour.of(to);
	}

	public int getFrom() {
		return from.value();
	}
	
	public int getTo() {
		return to.value();
	}
	
	
}
