package py.org.fundacionparaguaya.pspserver.security.services.impl;

import com.google.common.collect.ImmutableMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.UserApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.repositories.ApplicationRepository;
import py.org.fundacionparaguaya.pspserver.network.repositories.OrganizationRepository;
import py.org.fundacionparaguaya.pspserver.network.repositories.UserApplicationRepository;
import py.org.fundacionparaguaya.pspserver.security.constants.Role;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDTO;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserRoleApplicationDTO;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;
import py.org.fundacionparaguaya.pspserver.security.entities.UserRoleEntity;
import py.org.fundacionparaguaya.pspserver.security.mapper.UserMapper;
import py.org.fundacionparaguaya.pspserver.security.repositories.UserRepository;
import py.org.fundacionparaguaya.pspserver.security.repositories.UserRoleRepository;
import py.org.fundacionparaguaya.pspserver.security.services.UserService;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class UserServiceImpl implements UserService {

	private Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;

	private UserRoleRepository userRoleRepository;

	private ApplicationRepository applicationRepository;

	private OrganizationRepository organizationRepository;

	private UserApplicationRepository userApplicationRepository;

	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
						   ApplicationRepository applicationRepository, OrganizationRepository organizationRepository,
						   UserApplicationRepository userApplicationRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.applicationRepository = applicationRepository;
		this.organizationRepository = organizationRepository;
		this.userApplicationRepository = userApplicationRepository;
        this.userMapper = userMapper;
    }

	
	@Override
	public UserDTO addUser(UserDTO userDTO) {
        userRepository.findOneByUsername(userDTO.getUsername())
                .ifPresent((user) -> {
                    throw new CustomParameterizedException(
                            "User already exists.",
							new ImmutableMultimap.Builder<String, String>().
                                    put("username", user.getUsername()).
                                    build().asMap()
                    );
                });
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userDTO, user);
        UserEntity newUser = userRepository.save(user);
	    return userMapper.entityToDto(newUser);
	}
	
	
	@Override
	public UserDTO getUserById(Long userId) {
		checkArgument(userId > 0, "Argument was %s but expected nonnegative", userId);

        return Optional.ofNullable(userRepository.findOne(userId))
                .map(userMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("User does not exist"));
	}


	@Override
	public UserDTO addUserWithRoleAndApplication(UserRoleApplicationDTO userRoleApplicationDTO, UserDetailsDTO userDetails) {
		// Create User
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(userRoleApplicationDTO.getUser(), user);
		user.setPass(new BCryptPasswordEncoder().encode(user.getPass()));
		user.setActive(true);
		UserEntity newUser = userRepository.save(user);

		if (userRoleApplicationDTO.getRole() != null) {
			// Create UserRole
			UserRoleEntity userRole = new UserRoleEntity();
			userRole.setUser(newUser);
			userRole.setRole(userRoleApplicationDTO.getRole());
			userRoleRepository.save(userRole);
		}

		if (userRoleApplicationDTO.getApplication() != null) {
			// Create User-Application
			UserApplicationEntity userApplicationEntity = new UserApplicationEntity();
			userApplicationEntity.setUser(newUser);
			ApplicationEntity application = applicationRepository.findById(userRoleApplicationDTO.getApplication().getId());
			userApplicationEntity.setApplication(application);
			userApplicationRepository.save(userApplicationEntity);
		}
		else if (userRoleApplicationDTO.getOrganization() != null) {
			// Create User-Organization
			UserApplicationEntity userApplicationEntity = new UserApplicationEntity();
			userApplicationEntity.setUser(newUser);

			OrganizationEntity organization = organizationRepository.findById(userRoleApplicationDTO.getOrganization().getId());
			userApplicationEntity.setOrganization(organization);
			userApplicationEntity.setApplication(organization.getApplication());

			userApplicationRepository.save(userApplicationEntity);
		}

		if (userRoleApplicationDTO.getRole() == Role.ROLE_USER || userRoleApplicationDTO.getRole() == Role.ROLE_SURVEY_USER) {
			UserEntity loggedUser = userRepository.findOneByUsername(userDetails.getUsername()).orElseGet(UserEntity::new);
			UserApplicationEntity loggedUserApplicationEntity = userApplicationRepository.findByUser(loggedUser).orElseGet(UserApplicationEntity::new);

			UserApplicationEntity userApplicationEntity = new UserApplicationEntity();
			userApplicationEntity.setUser(newUser);
			userApplicationEntity.setApplication(loggedUserApplicationEntity.getApplication());
			userApplicationEntity.setOrganization(loggedUserApplicationEntity.getOrganization());

			userApplicationRepository.save(userApplicationEntity);
		}

		return userMapper.entityToDto(newUser);
	}


	@Override
	public List<UserDTO> getAllUsers() {
		List<UserEntity> users = userRepository.findAll();
		return userMapper.entityListToDtoList(users);
	}

	
	@Override
	public void deleteUser(Long userId) {
        checkArgument(userId > 0, "Argument was %s but expected nonnegative", userId);

        Optional.ofNullable(userRepository.findOne(userId))
                .ifPresent(user -> {
                    userRepository.delete(user);
                    LOG.debug("Deleted User: {}", user);
                });
    }

	
	@Override
	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		checkArgument(userId > 0, "Argument was %s but expected nonnegative", userId);

		return Optional.ofNullable(userRepository.findOne(userId))
                .map(user -> {
                    BeanUtils.copyProperties(userDTO, user);
                    LOG.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(userMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("User does not exist"));
    }

	
}
