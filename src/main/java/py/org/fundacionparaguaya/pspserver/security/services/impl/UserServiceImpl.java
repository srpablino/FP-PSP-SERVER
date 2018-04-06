package py.org.fundacionparaguaya.pspserver.security.services.impl;

import com.google.common.collect.ImmutableMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.common.pagination.PspPageRequest;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.UserApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.mapper.UserApplicationMapper;
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
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static py.org.fundacionparaguaya.pspserver.network.specifications.UserApplicationSpecification.hasApplication;
import static py.org.fundacionparaguaya.pspserver.network.specifications.UserApplicationSpecification.hasOrganization;
import static py.org.fundacionparaguaya.pspserver.network.specifications.UserApplicationSpecification.userIsActive;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    private UserRoleRepository userRoleRepository;

    private ApplicationRepository applicationRepository;

    private OrganizationRepository organizationRepository;

    private UserApplicationRepository userApplicationRepository;

    private final UserMapper userMapper;

    private final UserApplicationMapper userApplicationMapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                           ApplicationRepository applicationRepository, OrganizationRepository organizationRepository,
                           UserApplicationRepository userApplicationRepository, UserMapper userMapper,
                           UserApplicationMapper userApplicationMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.applicationRepository = applicationRepository;
        this.organizationRepository = organizationRepository;
        this.userApplicationRepository = userApplicationRepository;
        this.userMapper = userMapper;
        this.userApplicationMapper = userApplicationMapper;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        userRepository
                .findOneByUsername(userDTO.getUsername())
                .ifPresent(user -> {
                    throw new CustomParameterizedException("User already exists.",
                            new ImmutableMultimap.Builder<String, String>()
                                    .put("username", user.getUsername())
                                    .build()
                                    .asMap());
                });
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userDTO, user);
        UserEntity newUser = userRepository.save(user);
        return userMapper.entityToDto(newUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        checkArgument(userId > 0, "Argument was %s but expected nonnegative", userId);

        return Optional.ofNullable(
                userRepository.findOne(userId))
                .map(userMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("User does not exist"));
    }

    @Override
    public UserDTO addUserWithRoleAndApplication(UserRoleApplicationDTO userRoleApplicationDTO,
                                                 UserDetailsDTO userDetails) {
        userRepository
                .findOneByUsername(userRoleApplicationDTO.getUsername())
                .ifPresent(user -> {
                    throw new CustomParameterizedException("User already exists.",
                            new ImmutableMultimap.Builder<String, String>()
                                    .put("username", user.getUsername())
                                    .build()
                                    .asMap());
                });

        UserEntity user = new UserEntity();
        user.setUsername(userRoleApplicationDTO.getUsername());
        user.setEmail(userRoleApplicationDTO.getEmail());
        user.setPass(new BCryptPasswordEncoder().encode(userRoleApplicationDTO.getPass()));
        user.setActive(true);
        UserEntity newUser = userRepository.save(user);

        if (userRoleApplicationDTO.getRole() != null) {
            createUserRole(newUser, userRoleApplicationDTO.getRole());
        }

        if (userRoleApplicationDTO.getOrganizationId() != null) {
            createUserOrganization(newUser, userRoleApplicationDTO);
        } else if (userRoleApplicationDTO.getApplicationId() != null) {
            createUserApplication(newUser, userRoleApplicationDTO);
        }

        return userMapper.entityToDto(newUser);
    }

    private UserRoleEntity createUserRole(UserEntity user, Role role) {
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUser(user);
        userRole.setRole(role);
        return userRoleRepository.save(userRole);
    }

    private UserApplicationEntity createUserApplication(UserEntity user,
                                                        UserRoleApplicationDTO userRoleApplicationDTO) {
        UserApplicationEntity userApplicationEntity = new UserApplicationEntity();
        userApplicationEntity.setUser(user);
        ApplicationEntity application = applicationRepository.findById(userRoleApplicationDTO.getApplicationId());
        userApplicationEntity.setApplication(application);
        return userApplicationRepository.save(userApplicationEntity);
    }

    private UserApplicationEntity createUserOrganization(UserEntity user,
                                                         UserRoleApplicationDTO userRoleApplicationDTO) {
        UserApplicationEntity userApplicationEntity = new UserApplicationEntity();
        userApplicationEntity.setUser(user);
        OrganizationEntity organization = organizationRepository.findById(userRoleApplicationDTO.getOrganizationId());
        userApplicationEntity.setOrganization(organization);
        userApplicationEntity.setApplication(organization.getApplication());
        return userApplicationRepository.save(userApplicationEntity);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return userMapper.entityListToDtoList(users);
    }

    @Override
    public void deleteUser(Long userId) {
        checkArgument(userId > 0, "Argument was %s but expected nonnegative", userId);

        Optional.ofNullable(
                userRepository.findOne(userId))
                .ifPresent(user -> {
                    user.setActive(false);
                    userRepository.save(user);
                    LOG.debug("Deleted User: {}", user);
                });
    }

    @Override
    public UserDTO updateUserByRequest(Long userTargetId, UserDTO userTargetDTO, String requesterUserName) {
        checkArgument(userTargetId > 0, "Argument was %s but expected nonnegative", userTargetId);

        UserEntity userTarget = userRepository.findOne(userTargetId);

        //check if logged user can perform the update over the targeted user.
        UserEntity responsibleUser = userRepository.findByUsername(requesterUserName);
        if (canPerformUpdateOverUser(userTarget,responsibleUser)){
            userTarget.setEmail(userTargetDTO.getEmail());
            userTarget.setActive(userTargetDTO.isActive());
            userRepository.save(userTarget);
            LOG.debug("Changed Information for User: {}", userTarget);
        }else{
            throw new RuntimeException("Sorry, you are not allowed to update this user");
        }
        return userMapper.entityToDto(userTarget);
    }

    private boolean canPerformUpdateOverUser(UserEntity targetUser, UserEntity responsibleUser){

        List<UserRoleEntity> targetUserRoleEntityList = userRoleRepository.findByUser(targetUser);

        if (targetUserRoleEntityList == null){
            throw  new RuntimeException("Currently, only users with 1 rol can be updated. " +
                    "No role were found for this user");
        }

        //there is not a predefined behavior in case the targetUser has more than 1 role for now
        if (targetUserRoleEntityList.size()>1){
            throw  new RuntimeException("Currently only users with 1 rol can be updated. " +
                    "There are more than 1 rol for this user");
        }

        //We are sure the first item is present, since the targetUserRoleEntityList is != null
        String roleUserTarget = targetUserRoleEntityList.get(0).getRole().getSecurityName();

        //find out what kind of role our targetUser has
        boolean targetIsHubAdmin = roleUserTarget.equals(Role.ROLE_HUB_ADMIN.getSecurityName());
        boolean targetIsOrgAdmin = roleUserTarget.equals(Role.ROLE_APP_ADMIN.getSecurityName());
        boolean targetIsUser = roleUserTarget
                .equals(roleUserTarget.equals(Role.ROLE_SURVEY_USER.getSecurityName()));

        //The UserApplicationEntities help to determine if the targetUser depends on the responsibleUser
        UserApplicationEntity userTargetApplicationEntity =
                userApplicationRepository.findByUser(targetUser).orElse(null);
        UserApplicationEntity userResponsibleApplicationEntity =
                userApplicationRepository
                        .findByUser(responsibleUser).orElse(null);

        //get the roles of the user responsible of the update
        List<UserRoleEntity> roleResponsibleUserList =
                this.userRoleRepository.findByUser(responsibleUser);
        String roleResponsible;

        boolean updatePossible = false;

        //One of the roles of the responsibleUser may allow him to update the targetUser
        for (UserRoleEntity userRoleEntity : roleResponsibleUserList){
            roleResponsible = userRoleEntity.getRole().getSecurityName();

            //only HUB_Users can be modified
            if (roleResponsible.equals(Role.ROLE_ROOT.getSecurityName()) && targetIsHubAdmin){

                //if responsible is root, any hubAdmin can be updated
                updatePossible = true;

            }

            //Check if the UserTarget role is inmmediatly under the level of the responsibleUser role
            if (roleResponsible.equals(Role.ROLE_HUB_ADMIN.getSecurityName()) && targetIsOrgAdmin){

                long idHubTarget = userTargetApplicationEntity.getApplication().getId();
                long idHubResponsible = userResponsibleApplicationEntity.getApplication().getId();

                //Only if they are related to the same hub, the update is posiible
                updatePossible = idHubResponsible == idHubTarget;

            }

            //Check if the UserTarget role is inmmediatly under the level of the responsibleUser role
            if (roleResponsible.equals(Role.ROLE_APP_ADMIN.getSecurityName()) && targetIsUser){
                long idOrgTarget = userTargetApplicationEntity.getOrganization().getId();
                long idOrgResponsible = userResponsibleApplicationEntity.getOrganization().getId();

                //Only if they are related to the same organization, the update is possible
                updatePossible = idOrgResponsible == idOrgTarget;
            }
        }

        return updatePossible;
    }

    public UserApplicationEntity returnFromOptional(Optional<UserApplicationEntity> optional){
        return optional.get();
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        checkArgument(userId > 0, "Argument was %s but expected nonnegative", userId);

        return Optional.ofNullable(
                userRepository.findOne(userId))
                .map(user -> {
                    BeanUtils.copyProperties(userDTO, user);
                    LOG.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(userMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("User does not exist"));
    }

    @Override
    public Page<UserDTO> listUsers(int page, int perPage, String orderBy, String sortBy, UserDetailsDTO userDetails) {
        PageRequest pageRequest = new PspPageRequest(page, perPage, orderBy, "user." + sortBy);

        Page<UserApplicationEntity> userApplicationPage = userApplicationRepository.findAll(
                    Specifications.where(hasApplication(userDetails.getApplication()))
                            .and(hasOrganization(userDetails.getOrganization()))
                            .and(userIsActive()),
                    pageRequest);

        return userApplicationPage.map(userApplicationMapper::entityToUserDto);
    }

    @Override
    public List<UserDTO> listUsers(ApplicationEntity application) {

        List<UserApplicationEntity> userApplications = userApplicationRepository.findAll(
                Specifications.where(
                        hasApplication(ApplicationDTO.builder().applicationId(application.getId()).build()))
                        .and(userIsActive()));

        return userApplications.stream().map(userApplicationMapper::entityToUserDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> listUsers(OrganizationEntity organization) {

        List<UserApplicationEntity> userApplications = userApplicationRepository.findAll(
                Specifications.where(
                        hasOrganization(OrganizationDTO.builder().id(organization.getId()).build()))
                        .and(userIsActive()));

        return userApplications.stream().map(userApplicationMapper::entityToUserDto).collect(Collectors.toList());
    }
}