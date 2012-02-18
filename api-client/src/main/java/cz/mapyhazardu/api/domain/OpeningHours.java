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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		OpeningHours other = (OpeningHours) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
	
	
	
	
}
