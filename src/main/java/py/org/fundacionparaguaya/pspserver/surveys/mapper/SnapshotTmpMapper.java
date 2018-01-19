package py.org.fundacionparaguaya.pspserver.surveys.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.common.mapper.BaseMapper;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;
import py.org.fundacionparaguaya.pspserver.security.repositories.TermCondPolRepository;
import py.org.fundacionparaguaya.pspserver.security.repositories.UserRepository;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotTmp;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotTmpEntity;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyRepository;

/**
 *
 * @author mgonzalez
 *
 */
@Component
public class SnapshotTmpMapper implements
    BaseMapper<SnapshotTmpEntity, SnapshotTmp> {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final TermCondPolRepository termCondPolRepository;

    private final SurveyRepository surveyRepository;

    public SnapshotTmpMapper(ModelMapper modelMapper,
            UserRepository userRepository,
            TermCondPolRepository termCondPolRepository,
            SurveyRepository surveyRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.termCondPolRepository = termCondPolRepository;
        this.surveyRepository = surveyRepository;
    }

    @Override
    public List<SnapshotTmp> entityListToDtoList(
            List<SnapshotTmpEntity> entityList) {
        return entityList.stream()
                .filter(Objects::nonNull)
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SnapshotTmp entityToDto(SnapshotTmpEntity entity) {
        SnapshotTmp dto = modelMapper.map(entity, SnapshotTmp.class);
        dto = dto.createdAt(entity.getCreatedAtAsISOString()).
                surveyId(entity.getSurveyDefinition().getId());
        return dto;
    }

    @Override
    public SnapshotTmpEntity dtoToEntity(SnapshotTmp dto) {
        SnapshotTmpEntity entity = modelMapper.map(dto,
                SnapshotTmpEntity.class);

        if (dto.getUserName()!=null) {
            Optional<UserEntity> user = userRepository
                    .findOneByUsername(dto.getUserName());
            entity = entity.user(user.isPresent()? user.get() : null);
        }

        if (dto.getTermCondId()!=null) {
            entity = entity.termCond(termCondPolRepository
                    .findOne(dto.getTermCondId()));
        }

        if (dto.getPrivPolId()!=null) {
            entity = entity.privPol(termCondPolRepository
                    .findOne(dto.getPrivPolId()));
        }

        if (dto.getSurveyId()!=null) {
            entity = entity.surveyDefinition(surveyRepository
                    .findOne(dto.getSurveyId()));
        }

        return entity;
    }

}
