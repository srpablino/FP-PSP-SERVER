package py.org.fundacionparaguaya.pspserver.security.entities;

import com.google.common.base.MoreObjects;
import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user", schema = "security")
public class UserEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="security.user_user_id_seq")
    @SequenceGenerator(name="security.user_user_id_seq", sequenceName="security.user_user_id_seq", allocationSize=1)
	@Column(name = "user_id")
	private Long userId;
	
	private String username;
	
	private String pass;
	
	private boolean active;

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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (userId == null || obj == null || getClass() != obj.getClass())
			return false;
		UserEntity toCompare = (UserEntity) obj;
		return userId.equals(toCompare.userId);
	}
	
	@Override
	public int hashCode() {
		return userId == null ? 0 : userId.hashCode();
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("userId", userId)
				.add("username", username)
				.add("pass", pass)
				.add("active", active)
				.toString();
	}
}