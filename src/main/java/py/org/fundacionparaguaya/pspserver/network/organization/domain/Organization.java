package py.org.fundacionparaguaya.pspserver.network.organization.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import py.org.fundacionparaguaya.pspserver.network.application.domain.Application;
import py.org.fundacionparaguaya.pspserver.system.country.domain.Country;

/**
 * Organization DAO Layer
 * 
 * <p>
 * This class represents the Organization mapped with the database table
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-18
 * @version 1.0
 *
 */
@Entity
@Table(name = "organization", schema = "ps_network")
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ps_network.organization_organization_id_seq")
	@SequenceGenerator(name = "ps_network.organization_organization_id_seq", sequenceName = "ps_network.organization_organization_id_seq", allocationSize = 1)
	@Column(name = "organization_id")
	private Long organizationId;

	private String name;

	private Integer code;

	private String description;

	private boolean isActive;

	@ManyToOne(targetEntity = Country.class)
	@JoinColumn(name = "country")
	private Country country;

	private String information;

	@ManyToOne(targetEntity = Application.class)
	@JoinColumn(name = "application_id")
	private Application applicationId;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Application getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Application applicationId) {
		this.applicationId = applicationId;
	}

	@Override
	public String toString() {
		return "Organization [organizationId=" + organizationId + ", name=" + name + ", code=" + code + ", description="
				+ description + ", isActive=" + isActive + ", country=" + country + ", information=" + information
				+ ", applicationId=" + applicationId + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (organizationId == null || obj == null || getClass() != obj.getClass())
			return false;
		Organization toCompare = (Organization) obj;
		return organizationId.equals(toCompare.organizationId);
	}
	
	@Override
	public int hashCode() {
		return organizationId == null ? 0 : organizationId.hashCode();
	}
	
}
