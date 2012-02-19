package cz.mapyhazardu.api.domain;

public class LegalPerson {

	private String companyNumber;

	public LegalPerson(String companyNumber) {
		
		if (companyNumber == null) {
			throw new IllegalArgumentException("You have to set company number");
		}
		
		this.companyNumber = companyNumber;
	}
	
	/**
	 * @return in czech "ICO" number
	 */
	public String getCompanyNumber() {
		return companyNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((companyNumber == null) ? 0 : companyNumber.hashCode());
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
		LegalPerson other = (LegalPerson) obj;
		if (companyNumber == null) {
			if (other.companyNumber != null)
				return false;
		} else if (!companyNumber.equals(other.companyNumber))
			return false;
		return true;
	}
	
	
	
}
