package py.org.fundacionparaguaya.pspserver.network.entities;

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

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "organization", schema = "ps_network")
public class OrganizationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ps_network.organization_organization_id_seq")
	@SequenceGenerator(name = "ps_network.organization_organization_id_seq", sequenceName = "ps_network.organization_organization_id_seq", allocationSize = 1)
	@Column(name = "organization_id")
	private Long organizationId;

	private String name;

	private Integer code;

	private String description;

	private boolean isActive;

	@ManyToOne(targetEntity = CountryEntity.class)
	@JoinColumn(name = "country")
	private CountryEntity country;

	private String information;

	@ManyToOne(targetEntity = ApplicationEntity.class)
	@JoinColumn(name = "application_id")
	private ApplicationEntity application;

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

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public ApplicationEntity getApplication() {
		return application;
	}

	public void setApplication(ApplicationEntity application) {
		this.application = application;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (organizationId == null || obj == null || getClass() != obj.getClass())
			return false;
		OrganizationEntity toCompare = (OrganizationEntity) obj;
		return organizationId.equals(toCompare.organizationId);
	}
	
	@Override
	public int hashCode() {
		return organizationId == null ? 0 : organizationId.hashCode();
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("organizationId", organizationId)
				.add("name", name)
				.add("code", code)
				.add("description", description)
				.add("isActive", isActive)
				.add("country", country.toString())
				.add("information", information)
				.add("application", application.toString())
				.toString();
	}

	
}
