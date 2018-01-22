package py.org.fundacionparaguaya.pspserver.security.dtos;

import java.util.Date;

import com.google.common.base.MoreObjects;

public class PasswordResetTokenDTO {

	private Long id;

	private String token;

	private String temporalPassword;

	private UserDTO user;

	private Date expiryDate;

	private PasswordResetTokenDTO(Long id, String token,
			String temporalPassword, UserDTO user, Date expiryDate) {
		this.id = id;
		this.token = token;
		this.temporalPassword = temporalPassword;
		this.user = user;
		this.expiryDate = expiryDate;
	}

	public static class Builder {
		private Long id;
		private String token;
		private String temporalPassword;
		private UserDTO user;
		private Date expiryDate;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder id(String token) {
			this.token = token;
			return this;
		}

		public Builder temporalPassword(String temporalPassword) {
			this.temporalPassword = temporalPassword;
			return this;
		}

		public Builder user(UserDTO user) {
			this.user = user;
			return this;
		}

		public Builder expiryDate(Date expiryDate) {
			this.expiryDate = expiryDate;
			return this;
		}

		public PasswordResetTokenDTO build() {
			return new PasswordResetTokenDTO(id, token, temporalPassword, user,
					expiryDate);
		}

	}

	public static Builder builder() {
		return new Builder();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTemporalPassword() {
		return temporalPassword;
	}

	public void setTemporalPassword(String temporalPassword) {
		this.temporalPassword = temporalPassword;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("token", token)
				.add("temporalPassword", temporalPassword)
				.add("user", user)
				.add("expiryDate", expiryDate)
				.toString();
	}

}
