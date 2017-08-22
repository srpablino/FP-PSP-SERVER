package py.org.fundacionparaguaya.pspserver.security.entities;

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

import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;
import py.org.fundacionparaguaya.pspserver.security.constants.Role;

@Entity
@Table(name = "user_x_role", schema = "security")
public class UserRoleEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security.user_x_role_user_x_role_id_seq")
	@SequenceGenerator(name = "security.user_x_role_user_x_role_id_seq", sequenceName = "security.user_x_role_user_x_role_id_seq", allocationSize = 1)
	@Column(name = "user_x_role_id")
	private Long userRoleId;

	@ManyToOne(targetEntity = UserEntity.class)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@Column(name = "role")
	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public UserEntity getUser() {
		return userEntity;
	}

	public void setUser(UserEntity user) {
		this.userEntity = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserRoleEntity [userRoleId=" + userRoleId + ", userEntity=" + userEntity + ", role=" + role + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (userRoleId == null || obj == null || getClass() != obj.getClass())
			return false;
		UserRoleEntity toCompare = (UserRoleEntity) obj;
		return userRoleId.equals(toCompare.userRoleId);
	}
	
	@Override
	public int hashCode() {
		return userRoleId == null ? 0 : userRoleId.hashCode();
	}
	
}
