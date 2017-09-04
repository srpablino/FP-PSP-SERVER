package py.org.fundacionparaguaya.pspserver.families.dtos;

import com.google.common.base.MoreObjects;

import py.org.fundacionparaguaya.pspserver.families.constants.Gender;
import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;

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

	private CountryDTO country;
	
	private CityDTO city;
	
	private FamilyDTO family;
	
	public PersonDTO() {}
	
	private PersonDTO(Long personId, String name, String lastname, String identificationType,
			String identificationNumber, String personRole, Gender gender, String activityPrimary,
			String activitySecundary, String phoneNumber, CountryDTO country, CityDTO city, FamilyDTO family) {
		this.personId = personId;
		this.name = name;
		this.lastname = lastname;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.personRole = personRole;
		this.gender = gender;
		this.activityPrimary = activityPrimary;
		this.activitySecundary = activitySecundary;
		this.phoneNumber = phoneNumber;
		this.country = country;
		this.city = city;
		this.family = family;
	}

	public static class Builder {
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
		private CountryDTO country;	
		private CityDTO city;
		private FamilyDTO family;
		
		public Builder personId(Long personId) {
			this.personId = personId;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder lastname(String lastname) {
			this.lastname = lastname;
			return this;
		}
		
		public Builder identificationType(String identificationType) {
			this.identificationType = identificationType;
			return this;
		}
		
		public Builder identificationNumber(String identificationNumber) {
			this.identificationNumber = identificationNumber;
			return this;
		}
		
		public Builder personRole(String personRole) {
			this.personRole = personRole;
			return this;
		}
		
		public Builder gender(Gender gender) {
			this.gender = gender;
			return this;
		}
		
		public Builder activityPrimary(String activityPrimary) {
			this.activityPrimary = activityPrimary;
			return this;
		}
		
		public Builder activitySecundary(String activitySecundary) {
			this.activitySecundary = activitySecundary;
			return this;
		}
		
		public Builder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}
		
		public Builder country(CountryDTO country) {
			this.country = country;
			return this;
		}

		public Builder city(CityDTO city) {
			this.city = city;
			return this;
		}

		public Builder family(FamilyDTO family) {
			this.family = family;
			return this;
		}

		public PersonDTO build() {
			return new PersonDTO(personId, name, lastname, identificationType, identificationNumber, personRole, gender, activityPrimary, activitySecundary, phoneNumber, country, city, family);
		}
		
	}

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

	public CountryDTO getCountry() {
		return country;
	}

	public void setCountry(CountryDTO country) {
		this.country = country;
	}

	public CityDTO getCity() {
		return city;
	}

	public void setCity(CityDTO city) {
		this.city = city;
	}

	public FamilyDTO getFamily() {
		return family;
	}

	public void setFamily(FamilyDTO family) {
		this.family = family;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("personId", personId)
				.add("name", name)
				.add("lastname", lastname)
				.add("identificationType", identificationType)
				.add("identificationNumber", identificationNumber)
				.add("personRole", personRole)
				.add("gender", gender)
				.add("activityPrimary", activityPrimary)
				.add("activitySecundary", activitySecundary)
				.add("phoneNumber", phoneNumber)
				.add("country", country)
				.add("city", city)
				.add("family", family)
				.toString();
	}
	
}
