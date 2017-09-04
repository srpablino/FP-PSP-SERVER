package py.org.fundacionparaguaya.pspserver.system.dtos;

import com.google.common.base.MoreObjects;

public class CountryDTO {

	private Long countryId;
	
	private String country;
	
	public CountryDTO(){}
	
	private CountryDTO(Long countryId, String country) {
		this.countryId = countryId;
		this.country = country;
	}

	public static class Builder{
		private Long countryId;
		private String country;
		
		public Builder countryId (Long countryId){
			this.countryId = countryId;
			return this;
		}
		public Builder countryId (String country){
			this.country = country;
			return this;
		}
		public CountryDTO build() {
			return new CountryDTO(countryId, country);
		}
		
	}

	public static Builder builder() {
		return new Builder();
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("countryId", countryId)
				.add("country", country)
				.toString();
	}
}
