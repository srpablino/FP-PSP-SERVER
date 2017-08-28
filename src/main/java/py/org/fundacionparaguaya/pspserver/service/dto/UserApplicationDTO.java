package py.org.fundacionparaguaya.pspserver.service.dto;

import py.org.fundacionparaguaya.pspserver.domain.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.domain.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.domain.UserEntity;

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
