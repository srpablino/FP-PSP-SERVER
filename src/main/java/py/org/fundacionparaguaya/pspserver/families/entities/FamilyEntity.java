package py.org.fundacionparaguaya.pspserver.families.entities;

import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
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
@Table(name = "family", schema = "ps_families")
public class FamilyEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ps_families.family_family_id_seq")
	@SequenceGenerator(name = "ps_families.family_family_id_seq", sequenceName = "ps_families.family_family_id_seq", allocationSize = 1)
	@Column(name = "family_id")
	private Long familyId;

	private String name;

	@ManyToOne(targetEntity = CountryEntity.class)
	@JoinColumn(name = "country")
	private CountryEntity countryEntity;

	@ManyToOne(targetEntity = CityEntity.class)
	@JoinColumn(name = "city")
	private CityEntity cityEntity;

	private String locationType;

	private String locationPositionGps;

	@ManyToOne(targetEntity = PersonEntity.class)
	@JoinColumn(name = "person_reference_id")
	private PersonEntity personEntity;

	@ManyToOne(targetEntity = ApplicationEntity.class)
	@JoinColumn(name = "application_id")
	private ApplicationEntity applicationEntity;

	@ManyToOne(targetEntity = OrganizationEntity.class)
	@JoinColumn(name = "organization_id")
	private OrganizationEntity organizationEntity;

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocationPositionGps() {
		return locationPositionGps;
	}

	public void setLocationPositionGps(String locationPositionGps) {
		this.locationPositionGps = locationPositionGps;
	}

	public PersonEntity getPersonReferenceId() {
		return personEntity;
	}

	public void setPersonReferenceId(PersonEntity personReferenceId) {
		this.personEntity = personReferenceId;
	}

	public ApplicationEntity getApplicationId() {
		return applicationEntity;
	}

	public void setApplicationId(ApplicationEntity applicationId) {
		this.applicationEntity = applicationId;
	}

	public OrganizationEntity getOrganizationId() {
		return organizationEntity;
	}

	public void setOrganizationId(OrganizationEntity organizationId) {
		this.organizationEntity = organizationId;
	}

	
	@Override
	public String toString() {
		return "FamilyEntity [familyId=" + familyId + ", name=" + name + ", countryEntity=" + countryEntity
				+ ", cityEntity=" + cityEntity + ", locationType=" + locationType + ", locationPositionGps="
				+ locationPositionGps + ", personEntity=" + personEntity + ", applicationEntity=" + applicationEntity
				+ ", organizationEntity=" + organizationEntity + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (familyId == null || obj == null || getClass() != obj.getClass())
			return false;
		FamilyEntity toCompare = (FamilyEntity) obj;
		return familyId.equals(toCompare.familyId);
	}
	
	@Override
	public int hashCode() {
		return familyId == null ? 0 : familyId.hashCode();
	}


}
