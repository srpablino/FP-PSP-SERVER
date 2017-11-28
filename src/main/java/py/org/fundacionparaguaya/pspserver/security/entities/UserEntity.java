package py.org.fundacionparaguaya.pspserver.security.entities;

import com.google.common.base.MoreObjects;
import py.org.fundacionparaguaya.pspserver.common.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "security")
public class UserEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	private String username;
	
	private String pass;
	
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		UserEntity toCompare = (UserEntity) obj;
		return id.equals(toCompare.id);
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("username", username)
				.add("pass", pass)
				.add("active", active)
				.toString();
	}
}