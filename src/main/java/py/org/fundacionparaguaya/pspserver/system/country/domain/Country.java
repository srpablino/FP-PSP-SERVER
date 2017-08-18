package py.org.fundacionparaguaya.pspserver.system.country.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import py.org.fundacionparaguaya.pspserver.base.BaseEntity;

/**
 * Country DAO Layer
 * 
 * <p>
 * This class represents the Country mapped with the database table
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-18
 * @version 1.0
 *
 */
@Entity
@Table(name = "country", schema = "system")
public class Country extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="system.country_country_id_seq")
    @SequenceGenerator(name="system.country_country_id_seq", sequenceName="system.country_country_id_seq", allocationSize=1)
	@Column(name = "country_id")
	private Long countryId;
	
	private String country;

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", country=" + country + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (countryId == null || obj == null || getClass() != obj.getClass())
			return false;
		Country toCompare = (Country) obj;
		return countryId.equals(toCompare.countryId);
	}
	
	@Override
	public int hashCode() {
		return countryId == null ? 0 : countryId.hashCode();
	}
	
}
