package py.org.fundacionparaguaya.pspserver.security.userrole.domain;

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

import py.org.fundacionparaguaya.pspserver.security.user.domain.User;

/**
 * UserRole DAO Layer
 * 
 * <p>
 * This class represents the UserRole mapped with the database table
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-17
 * @version 1.0
 *
 */
@Entity
@Table(name = "user_x_role", schema = "security")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security.user_x_role_user_x_role_id_seq")
	@SequenceGenerator(name = "security.user_x_role_user_x_role_id_seq", sequenceName = "security.user_x_role_user_x_role_id_seq", allocationSize = 1)
	@Column(name = "user_x_role_id")
	private Long userRoleId;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "role")
	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public UserRole() {
		
	}
	
	public UserRole(Long userRoleId, User user, Role role) {
		this.userRoleId = userRoleId;
		this.user = user;
		this.role = role;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
