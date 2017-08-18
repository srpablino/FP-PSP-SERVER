package py.org.fundacionparaguaya.pspserver.system.city.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import py.org.fundacionparaguaya.pspserver.base.BaseEntity;

/**
 * City DAO Layer
 * 
 * <p>
 * This class represents the city mapped with the database table
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-18
 * @version 1.0
 *
 */
@Entity
@Table(name = "city", schema = "system")
public class City extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="system.city_city_id_seq")
    @SequenceGenerator(name="system.city_city_id_seq", sequenceName="system.city_city_id_seq", allocationSize=1)
	@Column(name = "city_id")
	private Long cityId;
	
	private String city;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", city=" + city + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (cityId == null || obj == null || getClass() != obj.getClass())
			return false;
		City toCompare = (City) obj;
		return cityId.equals(toCompare.cityId);
	}
	
	@Override
	public int hashCode() {
		return cityId == null ? 0 : cityId.hashCode();
	}

}
