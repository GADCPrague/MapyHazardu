package cz.mapyhazardu.api.domain;

public class LegalPerson {

	private String companyNumber;

	public LegalPerson(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	
	/**
	 * @return in czech "ICO" number
	 */
	public String getCompanyNumber() {
		return companyNumber;
	}
	
}
