package py.org.fundacionparaguaya.pspserver.families.family.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import py.org.fundacionparaguaya.pspserver.base.BaseEntity;
import py.org.fundacionparaguaya.pspserver.families.person.domain.Person;
import py.org.fundacionparaguaya.pspserver.network.application.domain.Application;
import py.org.fundacionparaguaya.pspserver.network.organization.domain.Organization;
import py.org.fundacionparaguaya.pspserver.system.city.domain.City;
import py.org.fundacionparaguaya.pspserver.system.country.domain.Country;

/**
 * Family DAO Layer
 * 
 * <p>
 * This class represents the family mapped with the database table
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-18
 * @version 1.0
 *
 */
@Entity
@Table(name = "family", schema = "ps_families")
public class Family extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ps_families.family_family_id_seq")
	@SequenceGenerator(name = "ps_families.family_family_id_seq", sequenceName = "ps_families.family_family_id_seq", allocationSize = 1)
	@Column(name = "family_id")
	private Long familyId;

	private String name;

	@ManyToOne(targetEntity = Country.class)
	@JoinColumn(name = "country")
	private Country country;

	@ManyToOne(targetEntity = City.class)
	@JoinColumn(name = "city")
	private City city;

	private String locationType;

	private String locationPositionGps;

	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "person_reference_id")
	private Person personReferenceId;

	@ManyToOne(targetEntity = Application.class)
	@JoinColumn(name = "application_id")
	private Application applicationId;

	@ManyToOne(targetEntity = Organization.class)
	@JoinColumn(name = "organization_id")
	private Organization organizationId;

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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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

	public Person getPersonReferenceId() {
		return personReferenceId;
	}

	public void setPersonReferenceId(Person personReferenceId) {
		this.personReferenceId = personReferenceId;
	}

	public Application getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Application applicationId) {
		this.applicationId = applicationId;
	}

	public Organization getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Organization organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		return "Family [familyId=" + familyId + ", name=" + name + ", country=" + country + ", city=" + city
				+ ", locationType=" + locationType + ", locationPositionGps=" + locationPositionGps
				+ ", personReferenceId=" + personReferenceId + ", applicationId=" + applicationId + ", organizationId="
				+ organizationId + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (familyId == null || obj == null || getClass() != obj.getClass())
			return false;
		Family toCompare = (Family) obj;
		return familyId.equals(toCompare.familyId);
	}
	
	@Override
	public int hashCode() {
		return familyId == null ? 0 : familyId.hashCode();
	}
	

}
