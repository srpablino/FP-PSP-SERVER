package py.org.fundacionparaguaya.pspserver.security.userrole.domain;

import py.org.fundacionparaguaya.pspserver.security.user.domain.UserEntity;

public class UserRoleEntityDTO {

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
