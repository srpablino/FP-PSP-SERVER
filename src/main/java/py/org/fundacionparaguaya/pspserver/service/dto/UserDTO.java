package py.org.fundacionparaguaya.pspserver.service.dto;

import javax.validation.constraints.NotNull;

public class UserDTO {

	private Long userId;

	@NotNull
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
	
}
