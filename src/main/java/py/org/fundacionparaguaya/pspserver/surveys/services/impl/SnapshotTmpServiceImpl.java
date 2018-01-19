package py.org.fundacionparaguaya.pspserver.surveys.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotTmp;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotTmpEntity;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotTmpMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotTmpRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotTmpService;
/**
 *
 * @author mgonzalez
 *
 */
@Service
public class SnapshotTmpServiceImpl implements SnapshotTmpService {

    private final SnapshotTmpMapper mapper;

    private final SnapshotTmpRepository repository;

    public SnapshotTmpServiceImpl(SnapshotTmpMapper mapper,
            SnapshotTmpRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public SnapshotTmp addSnapshotTmp(SnapshotTmp snapshot) {
        SnapshotTmpEntity snapshotEntity = mapper.dtoToEntity(snapshot);
        snapshotEntity = repository.save(snapshotEntity);
        return mapper.entityToDto(snapshotEntity);
    }

    @Override
    public SnapshotTmp getSnapshotTmp(Long id) {
       checkArgument(id!=null && id > 0, "Argument"
               + " was %s but expected nonnegative", id);
       return Optional.ofNullable(repository
               .findOne(id))
               .map(mapper::entityToDto)
               .orElseThrow(() ->
               new UnknownResourceException(
                       "Temporal snapshot does not exist"));
    }

    @Override
    public void deleteSnapshotTmp(Long id) {
        checkArgument(id!=null && id > 0, "Argument"
                + " was %s but expected nonnegative", id);

        Optional.ofNullable(repository.findOne(id)).ifPresent(snapshot -> {
            repository.delete(snapshot);
        });
    }

}
