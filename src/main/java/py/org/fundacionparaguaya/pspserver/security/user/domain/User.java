package py.org.fundacionparaguaya.pspserver.security.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import py.org.fundacionparaguaya.pspserver.base.BaseEntity;

/**
 * User DAO Layer
 * 
 * <p>
 * This class represents the user mapped with the database table
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-14
 * @version 1.0
 *
 */
@Entity
@Table(name = "user", schema = "security")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="security.user_user_id_seq")
    @SequenceGenerator(name="security.user_user_id_seq", sequenceName="security.user_user_id_seq", allocationSize=1)
	@Column(name = "user_id")
	private Long userId;
	
	private String username;
	
//	@JsonIgnore	
	private String pass;
	
	private boolean active;

	public User() {
		// do nothing
	}

	public User(Long userId, String username, String pass, boolean active) {
		this.userId = userId;
		this.username = username;
		this.pass = pass;
		this.active = active;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", pass=" + pass + ", active=" + active + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (userId == null || obj == null || getClass() != obj.getClass())
			return false;
		User toCompare = (User) obj;
		return userId.equals(toCompare.userId);
	}
	
	@Override
	public int hashCode() {
		return userId == null ? 0 : userId.hashCode();
	}

}