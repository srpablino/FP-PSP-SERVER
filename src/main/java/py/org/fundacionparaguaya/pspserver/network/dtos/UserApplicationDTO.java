package py.org.fundacionparaguaya.pspserver.network.dtos;

import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;

public class UserApplicationDTO {

	private Long userApplicationId;

	private UserEntity user;

	private ApplicationEntity application;

	private OrganizationEntity organization;

	public Long getUserApplicationId() {
		return userApplicationId;
	}

	public void setUserApplicationId(Long userApplicationId) {
		this.userApplicationId = userApplicationId;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public ApplicationEntity getApplication() {
		return application;
	}

	public void setApplication(ApplicationEntity application) {
		this.application = application;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}
	
}
