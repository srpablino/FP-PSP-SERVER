package py.org.fundacionparaguaya.pspserver.families.entities;

import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;
import py.org.fundacionparaguaya.pspserver.families.constants.Gender;

public class PersonDTO {

	private Long personId;
	
	private String name;
	
	private String lastname;
	
	private String identificationType;
	
	private String identificationNumber;
	
	private String personRole;
	
	private Gender gender;
	
	private String activityPrimary;
	
	private String activitySecundary;
	
	private String phoneNumber;

	private CountryEntity country;
	
	private CityEntity city;
	
	private FamilyEntity family;

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getPersonRole() {
		return personRole;
	}

	public void setPersonRole(String personRole) {
		this.personRole = personRole;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getActivityPrimary() {
		return activityPrimary;
	}

	public void setActivityPrimary(String activityPrimary) {
		this.activityPrimary = activityPrimary;
	}

	public String getActivitySecundary() {
		return activitySecundary;
	}

	public void setActivitySecundary(String activitySecundary) {
		this.activitySecundary = activitySecundary;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public FamilyEntity getFamily() {
		return family;
	}

	public void setFamily(FamilyEntity family) {
		this.family = family;
	}
	
}
