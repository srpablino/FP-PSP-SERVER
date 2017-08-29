package py.org.fundacionparaguaya.pspserver.network.dtos;

import java.io.Serializable;

import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;

public class ApplicationDTO implements Serializable {

	private static final long serialVersionUID = -3961430381031797975L;

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

}
