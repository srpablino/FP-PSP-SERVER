package py.org.fundacionparaguaya.pspserver.surveys.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicatorPriority;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorPriorityEntity;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotIndicatorPriorityMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotIndicatorPriorityRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotIndicatorRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotIndicatorPriorityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 
 * @author mgonzalez
 *
 */
@Service
public class SnapshotIndicatorPriorityServiceImpl implements SnapshotIndicatorPriorityService {

    private Logger LOG = LoggerFactory.getLogger(SnapshotIndicatorPriorityServiceImpl.class);

    private SnapshotIndicatorPriorityRepository snapshotPriorityRepository;

    private SnapshotIndicatorRepository snapshotIndicatorRepository;

    private final SnapshotIndicatorPriorityMapper snapshotPriorityMapper;

    private final MessageSource messageSource;

    public SnapshotIndicatorPriorityServiceImpl(SnapshotIndicatorPriorityRepository snapshotPriorityRepository,
            SnapshotIndicatorRepository snapshotIndicatorRepository,
            SnapshotIndicatorPriorityMapper snapshotPriorityMapper, MessageSource messageSource) {
        this.snapshotPriorityRepository = snapshotPriorityRepository;
        this.snapshotIndicatorRepository = snapshotIndicatorRepository;
        this.snapshotPriorityMapper = snapshotPriorityMapper;
        this.messageSource = messageSource;
    }

    @Override
    public List<SnapshotIndicatorPriority> getSnapshotIndicatorPriorityList(Long snapshotIndicatorId) {

        checkArgument(snapshotIndicatorId > 0,
                messageSource.getMessage("argument.nonNegative", null, LocaleContextHolder.getLocale()),
                snapshotIndicatorId);

        List<SnapshotIndicatorPriorityEntity> priorities = snapshotPriorityRepository
                .findBySnapshotIndicatorId(snapshotIndicatorId);

        List<SnapshotIndicatorPriority> toRet = new ArrayList<>();
        toRet = snapshotPriorityMapper.entityListToDtoList(priorities);

        return toRet;
    }

    @Override
    public SnapshotIndicatorPriority updateSnapshotIndicatorPriority(SnapshotIndicatorPriority priority) {
        Locale locale = LocaleContextHolder.getLocale();
        checkArgument(priority.getId() > 0, messageSource.getMessage("argument.nonNegative", null, locale),
                priority.getId());

        return Optional.ofNullable(snapshotPriorityRepository.findOne(priority.getId())).map(p -> {
            p.setAction(priority.getAction());
            p.setReason(priority.getReason());
            p.setEstimatedDateAsISOString(priority.getEstimatedDate());
            LOG.debug("Changed Information for Snapshot Indicator Priority: {}", p);
            return snapshotPriorityRepository.save(p);
        }).map(snapshotPriorityMapper::entityToDto).orElseThrow(() -> new UnknownResourceException(
                messageSource.getMessage("snapshotPriority.notExist", new Object[] { priority.getId() }, locale)));
    }

    @Override
    public SnapshotIndicatorPriority addSnapshotIndicatorPriority(SnapshotIndicatorPriority priority) {

        Locale locale = LocaleContextHolder.getLocale();
        checkArgument(priority != null, messageSource.getMessage("argument.notNull", null, locale), priority);
        
        if (snapshotPriorityRepository.countAllBySnapshotIndicatorId(priority.getSnapshotIndicatorId()) >= 5) {
            throw new CustomParameterizedException(
                    messageSource.getMessage("snapshotPriority.onlyFivePriorities", null, locale));
        }

        SnapshotIndicatorPriorityEntity entity = new SnapshotIndicatorPriorityEntity();
        entity.setReason(priority.getReason());
        entity.setAction(priority.getAction());
        entity.setIndicator(priority.getIndicator());
        entity.setEstimatedDateAsISOString(priority.getEstimatedDate());
        entity.setSnapshotIndicator(snapshotIndicatorRepository.getOne(priority.getSnapshotIndicatorId()));

        SnapshotIndicatorPriorityEntity newSnapshotIndicatorPriority = snapshotPriorityRepository.save(entity);
        return snapshotPriorityMapper.entityToDto(newSnapshotIndicatorPriority);
        
    }

    @Override
    public void deletePrioritiesByIndicator(Long snapshotIndicatorId) {
        List<SnapshotIndicatorPriorityEntity> priorities = snapshotPriorityRepository
                .findBySnapshotIndicatorId(snapshotIndicatorId);

        if (priorities != null && !priorities.isEmpty()) {
            snapshotPriorityRepository.delete(priorities);
        }

    }

    @Override
    public void deleteSnapshotIndicatorPriority(Long snapshotIndicatorPriorityId) {
        checkArgument(snapshotIndicatorPriorityId > 0,
                messageSource.getMessage("argument.nonNegative", null, LocaleContextHolder.getLocale()),
                snapshotIndicatorPriorityId);

        Optional.ofNullable(snapshotPriorityRepository.findOne(snapshotIndicatorPriorityId)).ifPresent(priority -> {
            snapshotPriorityRepository.delete(priority);
            LOG.debug("Deleted Snapshot Indicator Priority: {}", priority);
        });

    }

}
