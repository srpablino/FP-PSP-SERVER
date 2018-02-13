package py.org.fundacionparaguaya.pspserver.families.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyMapDTO;
import py.org.fundacionparaguaya.pspserver.families.mapper.FamilyMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilySnapshotsManager;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotIndicatorPriorityRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotIndicatorRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;

@Service
public class FamilySnapshotsManagerImpl implements FamilySnapshotsManager {

    @Autowired
    private MessageSource messageSource;

    private final FamilyMapper familyMapper;

    private final FamilyRepository familyRepository;

    private final SnapshotService snapshotService;

    private final SnapshotIndicatorPriorityRepository snapshotIndicatorPriorityRepository;

    private final SnapshotIndicatorRepository snapshotIndicatorRepository;

    private final SnapshotEconomicRepository economicRepository;

    private static final int MAX_DAYS_DELETE_SNAPSHOT = 30;

    public FamilySnapshotsManagerImpl(FamilyRepository familyRepository,
            FamilyMapper familyMapper, SnapshotService snapshotService,
            SnapshotIndicatorPriorityRepository snapshotIndicatorPriorityRepository,
            SnapshotIndicatorRepository snapshotIndicatorRepository,
            SnapshotEconomicRepository economicRepository) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
        this.snapshotService = snapshotService;
        this.snapshotIndicatorPriorityRepository = snapshotIndicatorPriorityRepository;
        this.snapshotIndicatorRepository = snapshotIndicatorRepository;
        this.economicRepository = economicRepository;
    }

    @Override
    public FamilyMapDTO getFamilyMapById(Long familyId) {
        Locale locale =  LocaleContextHolder.getLocale();

        checkArgument(familyId > 0, messageSource.getMessage("argument.nonNegative", null,
                locale),
                familyId);

        FamilyMapDTO familyFile = new FamilyMapDTO();

        FamilyDTO family = Optional
                .ofNullable(familyRepository.findOne(familyId))
                .map(familyMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException(
                        messageSource.getMessage("family.notExist", null,
                                locale)));

        BeanUtils.copyProperties(family, familyFile);

        familyFile.setSnapshotIndicators(
                snapshotService.getLastSnapshotIndicatorsByFamily(familyId));
        return familyFile;
    }

    @Override
    public void deleteSnapshotByFamily(Long familyId) {
        Locale locale =  LocaleContextHolder.getLocale();
        
        checkArgument(familyId > 0, messageSource.getMessage("argument.nonNegative", null,
                locale),
                familyId);

        Optional.ofNullable(familyRepository.findOne(familyId))
                .ifPresent(family -> {
                    Optional.ofNullable(economicRepository
                            .findTopByFamilyFamilyIdOrderByIdDesc(familyId))
                            .ifPresent(snapshotEconomicEntity -> {

                                LocalDateTime now = LocalDateTime.now();
                                LocalDateTime dateOfSnapshot = snapshotEconomicEntity
                                        .getCreatedAt();
                                Period intervalPeriod = Period.between(
                                        dateOfSnapshot.toLocalDate(),
                                        now.toLocalDate());

                                if (intervalPeriod
                                        .getDays() < MAX_DAYS_DELETE_SNAPSHOT) {
                                    SnapshotEconomicEntity snapshotEconomicEntityAux = snapshotEconomicEntity;
                                    snapshotIndicatorPriorityRepository.delete(
                                            snapshotIndicatorPriorityRepository
                                                    .findBySnapshotIndicatorId(
                                                            snapshotEconomicEntity
                                                                    .getSnapshotIndicator()
                                                                    .getId()));
                                    economicRepository
                                            .delete(snapshotEconomicEntity);
                                    snapshotIndicatorRepository
                                            .delete(snapshotEconomicEntityAux
                                                    .getSnapshotIndicator());
                                }

                            });

                    family.setActive(false);
                    familyRepository.save(family);
                });
    }

}
