package py.org.fundacionparaguaya.pspserver.psfamilies.dtos;

import py.org.fundacionparaguaya.pspserver.psfamilies.entities.PersonEntity;
import py.org.fundacionparaguaya.pspserver.psnetwork.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.psnetwork.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;

public class FamilyEntityDTO {

	private Long familyId;

	private String name;

	private CountryEntity country;

	private CityEntity city;

	private String locationType;

	private String locationPositionGps;

	private PersonEntity personReferenceId;

	private ApplicationEntity applicationId;

	private OrganizationEntity organizationId;

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
		return personReferenceId;
	}

	public void setPersonReferenceId(PersonEntity personReferenceId) {
		this.personReferenceId = personReferenceId;
	}

	public ApplicationEntity getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(ApplicationEntity applicationId) {
		this.applicationId = applicationId;
	}

	public OrganizationEntity getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(OrganizationEntity organizationId) {
		this.organizationId = organizationId;
	}

}
