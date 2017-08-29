package py.org.fundacionparaguaya.pspserver.service.dto;

import py.org.fundacionparaguaya.pspserver.service.constants.Role;
import py.org.fundacionparaguaya.pspserver.domain.UserEntity;

public class UserRoleDTO {

	private Long userRoleId;

	private UserEntity user;

	private Role role;

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
