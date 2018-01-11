package py.org.fundacionparaguaya.pspserver.security.dtos;

import com.google.common.base.MoreObjects;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.security.constants.Role;

public class UserRoleApplicationDTO {

    private UserDTO user;

    private Role role;

    private ApplicationDTO application;

    private OrganizationDTO organization;

    public UserRoleApplicationDTO() {
    }

    private UserRoleApplicationDTO(UserDTO user, Role role, ApplicationDTO application, OrganizationDTO organization) {
        this.user = user;
        this.role = role;
        this.application = application;
        this.organization = organization;
    }

    public static class Builder {
        private UserDTO user;
        private Role role;
        private ApplicationDTO application;
        private OrganizationDTO organization;

        public UserRoleApplicationDTO.Builder user(UserDTO user) {
            this.user = user;
            return this;
        }

        public UserRoleApplicationDTO.Builder role(Role role) {
            this.role = role;
            return this;
        }

        public UserRoleApplicationDTO.Builder application(ApplicationDTO application) {
            this.application = application;
            return this;
        }

        public UserRoleApplicationDTO.Builder organization(OrganizationDTO organization) {
            this.organization = organization;
            return this;
        }

        public UserRoleApplicationDTO build() {
            return new UserRoleApplicationDTO(user, role, application, organization);
        }

    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ApplicationDTO getApplication() {
        return application;
    }

    public void setApplication(ApplicationDTO application) {
        this.application = application;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("user", user)
                .add("role", role)
                .add("application", application)
                .add("organization", organization)
                .toString();
    }

}
