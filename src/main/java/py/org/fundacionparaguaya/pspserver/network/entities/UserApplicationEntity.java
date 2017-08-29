package py.org.fundacionparaguaya.pspserver.network.entities;

import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;

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
@Table(name = "user_x_application", schema = "ps_network")
public class UserApplicationEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ps_network.user_x_application_user_x_application_id_seq")
	@SequenceGenerator(name = "ps_network.user_x_application_user_x_application_id_seq", sequenceName = "ps_network.user_x_application_user_x_application_id_seq", allocationSize = 1)
	@Column(name = "user_x_application_id")
	private Long userApplicationId;

	@ManyToOne(targetEntity = UserEntity.class)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@ManyToOne(targetEntity = ApplicationEntity.class)
	@JoinColumn(name = "application_id")
	private ApplicationEntity applicationEntity;

	@ManyToOne(targetEntity = OrganizationEntity.class)
	@JoinColumn(name = "organization_id")
	private OrganizationEntity organizationEntity;

	public Long getUserApplicationId() {
		return userApplicationId;
	}

	public void setUserApplicationId(Long userApplicationId) {
		this.userApplicationId = userApplicationId;
	}

	public UserEntity getUser() {
		return userEntity;
	}

	public void setUser(UserEntity user) {
		this.userEntity = user;
	}

	public ApplicationEntity getApplicationId() {
		return applicationEntity;
	}

	public void setApplicationId(ApplicationEntity applicationId) {
		this.applicationEntity = applicationId;
	}

	public OrganizationEntity getOrganization() {
		return organizationEntity;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organizationEntity = organization;
	}



	@Override
	public String toString() {
		return "UserApplicationEntity [userApplicationId=" + userApplicationId + ", userEntity=" + userEntity
				+ ", applicationEntity=" + applicationEntity + ", organizationEntity=" + organizationEntity + "]";
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (userApplicationId == null || obj == null || getClass() != obj.getClass())
			return false;
		UserApplicationEntity toCompare = (UserApplicationEntity) obj;
		return userApplicationId.equals(toCompare.userApplicationId);
	}
	
	
	@Override
	public int hashCode() {
		return userApplicationId == null ? 0 : userApplicationId.hashCode();
	}
	
}
