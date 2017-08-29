package py.org.fundacionparaguaya.pspserver.network.entities;

import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "application", schema = "ps_network")
public class ApplicationEntity extends BaseEntity {

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
	private CountryEntity countryEntity;
	
	@ManyToOne(targetEntity = CityEntity.class)
	@JoinColumn(name = "city")
	private CityEntity cityEntity;
	
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
		return countryEntity;
	}

	public void setCountry(CountryEntity country) {
		this.countryEntity = country;
	}

	public CityEntity getCity() {
		return cityEntity;
	}

	public void setCity(CityEntity city) {
		this.cityEntity = city;
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
		return "ApplicationEntity [applicationId=" + applicationId + ", name=" + name + ", code=" + code
				+ ", description=" + description + ", isActive=" + isActive + ", countryEntity=" + countryEntity
				+ ", cityEntity=" + cityEntity + ", information=" + information + ", isHub=" + isHub + ", isDirect="
				+ isDirect + "]";
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
	
}
