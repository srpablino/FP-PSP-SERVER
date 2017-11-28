package py.org.fundacionparaguaya.pspserver.system.dtos;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

public class CountryDTO implements Serializable {

	private Long id;
	
	private String country;
	
	public CountryDTO(){}
	
	private CountryDTO(Long id, String country) {
		this.id = id;
		this.country = country;
	}

	public CountryDTO(String country) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
				.add("id", id)
				.add("country", country)
				.toString();
	}
}
