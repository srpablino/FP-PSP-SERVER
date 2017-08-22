package py.org.fundacionparaguaya.pspserver.families.person.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import py.org.fundacionparaguaya.pspserver.base.BaseEntity;
import py.org.fundacionparaguaya.pspserver.families.family.domain.Family;
import py.org.fundacionparaguaya.pspserver.system.city.domain.City;
import py.org.fundacionparaguaya.pspserver.system.country.domain.Country;

/**
 * Person DAO Layer
 * 
 * <p>
 * This class represents the person mapped with the database table
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-18
 * @version 1.0
 *
 */
@Entity
@Table(name = "person", schema = "ps_families")
public class Person extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ps_families.person_person_id_seq")
    @SequenceGenerator(name="ps_families.person_person_id_seq", sequenceName="ps_families.person_person_id_seq", allocationSize=1)
	@Column(name = "person_id")
	private Long personId;
	
	private String name;
	
	private String lastname;
	
	private String identificationType;
	
	private String identificationNumber;
	
	private String personRole;
	
	@Column(name = "gender")
	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String activityPrimary;
	
	private String activitySecundary;
	
	private String phoneNumber;
	
	@ManyToOne(targetEntity = Country.class)
	@JoinColumn(name = "country")
	private Country country;
	
	@ManyToOne(targetEntity = City.class)
	@JoinColumn(name = "city")
	private City city;
	
	@ManyToOne(targetEntity = Family.class)
	@JoinColumn(name = "family_id")
	private Family family;
	
	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
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

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", name=" + name + ", lastname=" + lastname + ", identificationType="
				+ identificationType + ", identificationNumber=" + identificationNumber + ", personRole=" + personRole
				+ ", gender=" + gender + ", activityPrimary=" + activityPrimary + ", activitySecundary="
				+ activitySecundary + ", phoneNumber=" + phoneNumber + ", country=" + country + ", city=" + city + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (personId == null || obj == null || getClass() != obj.getClass())
			return false;
		Person toCompare = (Person) obj;
		return personId.equals(toCompare.personId);
	}
	
	@Override
	public int hashCode() {
		return personId == null ? 0 : personId.hashCode();
	}
	
}
