package py.org.fundacionparaguaya.pspserver.system.dtos;

import java.io.Serializable;

public class CityDTO implements Serializable {

	private static final long serialVersionUID = -8675740198671885154L;

	private Long cityId;
	
	private String city;

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
	
	
}
