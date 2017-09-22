package py.org.fundacionparaguaya.pspserver.network.dtos;

import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;

import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;

public class ApplicationDTO {

	private Long applicationId;
	
	@NotNull
	private String name;
	
	private String code;
	
	private String description;
	
	private boolean isActive;
	
	private CountryDTO country;
	
	private CityDTO city;
	
	private String information;
	
	private boolean isHub;
	
	private boolean isOrganization;
	
	
	public ApplicationDTO() {}
	

	private ApplicationDTO(Long applicationId, String name, String code, String description, boolean isActive,
			CountryDTO country, CityDTO city, String information, boolean isHub, boolean isOrganization) {
		this.applicationId = applicationId;
		this.name = name;
		this.code = code;
		this.description = description;
		this.isActive = isActive;
		this.country = country;
		this.city = city;
		this.information = information;
		this.isHub = isHub;
		this.isOrganization = isOrganization;
	}

	public static class Builder {
		private Long applicationId;
		private String name;
		private String code;
		private String description;
		private boolean isActive;
		private CountryDTO country;
		private CityDTO city;
		private String information;
		private boolean isHub;
		private boolean isOrganization;
		
		public Builder applicationId(Long applicationId) {
			this.applicationId = applicationId;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder code(String code) {
			this.code = code;
			return this;
		}
		
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public Builder isActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}
		
		public Builder country(CountryDTO country) {
			this.country = country;
			return this;
		}

		public Builder city(CityDTO city) {
			this.city = city;
			return this;
		}
		
		public Builder information(String information) {
			this.information = information;
			return this;
		}
		
		public Builder isHub(boolean isHub) {
			this.isHub = isHub;
			return this;
		}
		
		public Builder isOrganization(boolean isOrganization) {
			this.isOrganization = isOrganization;
			return this;
		}
		
		public ApplicationDTO build() {
			return new ApplicationDTO(applicationId, name, code, description, isActive, country,  city,  information,  isHub,  isOrganization);
		}
		
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public CountryDTO getCountry() {
		return country;
	}

	public void setCountry(CountryDTO country) {
		this.country = country;
	}

	public CityDTO getCity() {
		return city;
	}

	public void setCity(CityDTO city) {
		this.city = city;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public boolean isHub() {
		return isHub;
	}

	public void setHub(boolean isHub) {
		this.isHub = isHub;
	}

	public boolean isOrganization() {
		return isOrganization;
	}

	public void setDirect(boolean isOrganization) {
		this.isOrganization = isOrganization;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("applicationId", applicationId)
				.add("name", name)
				.add("code", code)
				.add("description", description)
				.add("isActive", isActive)
				.add("country", country.toString())
				.add("city", city.toString())
				.add("information", information)
				.add("isHub", isHub)
				.add("isOrganization", isOrganization)
				.toString();
	}

}
