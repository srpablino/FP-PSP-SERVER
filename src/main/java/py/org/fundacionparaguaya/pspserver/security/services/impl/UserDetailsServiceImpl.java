package py.org.fundacionparaguaya.pspserver.security.services.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.UserApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.mapper.ApplicationMapper;
import py.org.fundacionparaguaya.pspserver.network.mapper.OrganizationMapper;
import py.org.fundacionparaguaya.pspserver.network.repositories.UserApplicationRepository;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;
import py.org.fundacionparaguaya.pspserver.security.entities.UserRoleEntity;
import py.org.fundacionparaguaya.pspserver.security.repositories.UserRepository;
import py.org.fundacionparaguaya.pspserver.security.repositories.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by rodrigovillalba on 11/16/17.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepo;

    private final UserApplicationRepository userApplicationRepo;

    private final OrganizationMapper organizationMapper;

    private final ApplicationMapper applicationMapper;

    public UserDetailsServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepo, UserApplicationRepository userApplicationRepo, OrganizationMapper organizationMapper, ApplicationMapper applicationMapper) {
        this.userRepository = userRepository;
        this.userRoleRepo = userRoleRepo;
        this.userApplicationRepo = userApplicationRepo;
        this.organizationMapper = organizationMapper;
        this.applicationMapper = applicationMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findOneByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));

        List<UserRoleEntity> roles =  userRoleRepo.findByUser(user);
        UserApplicationEntity userApp = userApplicationRepo.findByUser(user).orElseGet(UserApplicationEntity::new);
        Optional<OrganizationEntity> organization = userApp.getOrganizationOpt();
        Optional<ApplicationEntity> application = userApp.getApplicationOpt();

        return UserDetailsDTO.builder()
                .username(user.getUsername())
                .password(user.getPass())
                .enabled(user.isActive())
                .application(application.map(applicationMapper::entityToDto).orElse(null))
                .organization(organization.map(organizationMapper::entityToDto).orElse(null))
                .grantedAuthorities(this.getGrantedAuthorities(roles))
                .build();
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<UserRoleEntity> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        return authorities;
    }
}
