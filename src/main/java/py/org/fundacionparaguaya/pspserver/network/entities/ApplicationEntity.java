package py.org.fundacionparaguaya.pspserver.network.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;

@Entity
@Table(name = "application", schema = "ps_network")
public class ApplicationEntity  {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ps_network.application_application_id_seq")
    @SequenceGenerator(name="ps_network.application_application_id_seq", sequenceName="ps_network.application_application_id_seq", allocationSize=1)
	@Column(name = "application_id")
	private Long applicationId;
	
	private String name;
	
	private String code;
	
	private String description;
	
	private boolean isActive;
	
	@ManyToOne(targetEntity = CountryEntity.class)
	@JoinColumn(name = "country")
	private CountryEntity country;
	
	@ManyToOne(targetEntity = CityEntity.class)
	@JoinColumn(name = "city")
	private CityEntity city;
	
	private String information;
	
	private boolean isHub;
	
	private boolean isOrganization;

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

	public boolean isOrganization() {
		return isOrganization;
	}

	public void setDirect(boolean isOrganization) {
		this.isOrganization = isOrganization;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (applicationId == null || obj == null || getClass() != obj.getClass())
			return false;
		ApplicationEntity toCompare = (ApplicationEntity) obj;
		return applicationId.equals(toCompare.applicationId);
	}
	
	@Override
	public int hashCode() {
		return applicationId == null ? 0 : applicationId.hashCode();
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
