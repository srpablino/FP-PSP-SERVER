package py.org.fundacionparaguaya.pspserver.system.dtos;

import com.google.common.base.MoreObjects;

public class CityDTO {

	private Long cityId;
	
	private String city;
	
	public CityDTO(){}
	
	private CityDTO(Long cityId, String city) {
		this.cityId = cityId;
		this.city = city; 
	}
	
	public static class Builder {
		private Long cityId;
		private String city;
		
		public Builder cityId(Long cityId) {
			this.cityId = cityId;
			return this;
		}
		public Builder city(String city) {
			this.city = city;
			return this;
		}
		public CityDTO build() {
			return new CityDTO(cityId, city);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("cityId", cityId)
				.add("city", city)
				.toString();
	}
}
