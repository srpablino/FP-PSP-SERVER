package py.org.fundacionparaguaya.pspserver.system.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.network.repositories.ApplicationRepository;
import py.org.fundacionparaguaya.pspserver.network.repositories.OrganizationRepository;
import py.org.fundacionparaguaya.pspserver.security.repositories.UserRepository;
import py.org.fundacionparaguaya.pspserver.system.dtos.ActivityDTO;
import py.org.fundacionparaguaya.pspserver.system.entities.ActivityEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ActivityMapper implements BaseMapper<ActivityEntity, ActivityDTO>{

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final ApplicationRepository applicationRepository;

    private final OrganizationRepository organizationRepository;

    private final FamilyRepository familyRepository;

    public ActivityMapper(ModelMapper modelMapper, UserRepository userRepository,
            ApplicationRepository applicationRepository, OrganizationRepository organizationRepository,
            FamilyRepository familyRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.organizationRepository = organizationRepository;
        this.familyRepository = familyRepository;
    }

    @Override
    public List<ActivityDTO> entityListToDtoList(List<ActivityEntity> entityList) {
        return entityList.stream()
                .filter(Objects::nonNull)
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public ActivityDTO entityToDto(ActivityEntity entity) {
        ActivityDTO dto = modelMapper.map(entity, ActivityDTO.class);
        dto.setActivityId(entity.getActivityId());
        /*dto.setApplicationId(Optional.ofNullable(entity.getApplication())
                .map(ApplicationEntity::getId).orElse(null));
        dto.setOrganizationId(Optional.ofNullable(entity.getOrganization())
                .map(OrganizationEntity::getId).orElse(null));
        dto.setFamilyId(Optional.ofNullable(entity.getFamily())
                .map(FamilyEntity::getFamilyId).orElse(null));*/
        return dto;
    }

    @Override
    public ActivityEntity dtoToEntity(ActivityDTO dto) {
        ActivityEntity entity = modelMapper.map(dto, ActivityEntity.class);

        if(dto.getUser() != null){
            entity.setUser(userRepository.getOne(dto.getUser().getUserId()));
        }
        if(dto.getApplication() != null){
            entity.setApplication(applicationRepository.getOne(dto.getApplication().getId()));
        }
        if(dto.getOrganization() != null){
            entity.setOrganization(organizationRepository.findById(dto.getOrganization().getId()));
        }
        if(dto.getFamily() != null){
            entity.setFamily(familyRepository.getOne(dto.getFamily().getFamilyId()));
        }
        return entity;
    }
}
