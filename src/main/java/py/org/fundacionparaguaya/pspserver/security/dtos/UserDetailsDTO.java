package py.org.fundacionparaguaya.pspserver.security.dtos;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by rodrigovillalba on 11/16/17.
 */
public class UserDetailsDTO implements UserDetails {

    private final String password;
    private final  String username;
    private final boolean enabled;

    private UserDetailsDTO(String username, String password, boolean enabled) {
        this.password = password;
        this.username = username;
        this.enabled = enabled;
    }

    public static UserDetailsDTO of(String username, String password, boolean enabled) {
        return new UserDetailsDTO(username, password, enabled);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // we never lock accounts
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // credentials never expire
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
