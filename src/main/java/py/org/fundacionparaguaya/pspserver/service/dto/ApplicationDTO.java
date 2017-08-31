package py.org.fundacionparaguaya.pspserver.service.dto;

import com.google.common.base.MoreObjects;

import py.org.fundacionparaguaya.pspserver.domain.CityEntity;
import py.org.fundacionparaguaya.pspserver.domain.CountryEntity;

public class ApplicationDTO {

	private Long applicationId;
	
	private String name;
	
	private String code;
	
	private String description;
	
	private boolean isActive;
	
	private CountryEntity country;
	
	private CityEntity city;
	
	private String information;
	
	private boolean isHub;
	
	private boolean isDirect;
	
	
	public ApplicationDTO() {}
	

	public ApplicationDTO(Long applicationId, String name, String code, String description, boolean isActive,
			CountryEntity country, CityEntity city, String information, boolean isHub, boolean isDirect) {
		this.applicationId = applicationId;
		this.name = name;
		this.code = code;
		this.description = description;
		this.isActive = isActive;
		this.country = country;
		this.city = city;
		this.information = information;
		this.isHub = isHub;
		this.isDirect = isDirect;
	}

	public static class Builder {
		private Long applicationId;
		private String name;
		private String code;
		private String description;
		private boolean isActive;
		private CountryEntity country;
		private CityEntity city;
		private String information;
		private boolean isHub;
		private boolean isDirect;
		
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
		
		public Builder country(CountryEntity country) {
			this.country = country;
			return this;
		}

		public Builder city(CityEntity city) {
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
		
		public Builder isDirect(boolean isDirect) {
			this.isDirect = isDirect;
			return this;
		}
		
		public ApplicationDTO build() {
			return new ApplicationDTO(applicationId, name, code, description, isActive, country,  city,  information,  isHub,  isDirect);
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

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public CityEntity getCity() {
		return city;
	}

	public void setCity(CityEntity city) {
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

	public boolean isDirect() {
		return isDirect;
	}

	public void setDirect(boolean isDirect) {
		this.isDirect = isDirect;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("applicationId", applicationId)
				.add("name", name)
				.add("code", code)
				.add("description", description)
				.add("isActive", isActive)
				.add("country", country)
				.add("city", city)
				.add("information", information)
				.add("isHub", isHub)
				.add("isDirect", isDirect)
				.toString();
	}

}
