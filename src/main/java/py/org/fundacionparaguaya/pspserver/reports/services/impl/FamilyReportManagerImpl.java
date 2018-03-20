package py.org.fundacionparaguaya.pspserver.reports.services.impl;

import static org.springframework.data.jpa.domain.Specifications.where;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byOrganization;
import static py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification.byApplication;
import static py.org.fundacionparaguaya.pspserver.surveys.specifications.SnapshotIndicatorSpecification.byFamily;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.specifications.FamilySpecification;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilyOrganizationReportDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilyReportFilterDTO;
import py.org.fundacionparaguaya.pspserver.reports.dtos.FamilySnapshotReportDTO;
import py.org.fundacionparaguaya.pspserver.reports.mapper.FamilyReportMapper;
import py.org.fundacionparaguaya.pspserver.reports.services.FamilyReportManager;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotIndicatorMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotIndicatorRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.specifications.SnapshotIndicatorSpecification;

/**
 *
 * @author mgonzalez
 *
 */
@Service
public class FamilyReportManagerImpl implements FamilyReportManager {

    private FamilyRepository familyRepository;

    private FamilyReportMapper familyReportMapper;

    private SnapshotIndicatorRepository snapshotRepository;

    private SnapshotIndicatorMapper snapshotMapper;
    
    private SnapshotService snapshotService;
    

    public FamilyReportManagerImpl(FamilyRepository familyRepository, FamilyReportMapper familyReportMapper,
            SnapshotIndicatorRepository snapshotRepository, SnapshotIndicatorMapper snapshotMapper,
            SnapshotService snapshotService) {
        this.familyRepository = familyRepository;
        this.familyReportMapper = familyReportMapper;
        this.snapshotRepository = snapshotRepository;
        this.snapshotMapper = snapshotMapper;
        this.snapshotService = snapshotService;
    }

    @Override
    public List<FamilyOrganizationReportDTO> listFamilyByOrganizationAndCreatedDate(FamilyReportFilterDTO filters) {

        try {

            List<FamilyEntity> families = new ArrayList<>();

            if (filters.getOrganizationId() != null) {
                families = familyRepository
                        .findAll(where(byOrganization(filters.getOrganizationId())).and(FamilySpecification.byCreatedAt(
                                getDateFormat(filters.getDateFrom()), getDateFormat(filters.getDateTo()))));

            } else if (filters.getApplicationId() != null) {
                families = familyRepository
                        .findAll(where(byApplication(filters.getApplicationId())).and(FamilySpecification.byCreatedAt(
                                getDateFormat(filters.getDateFrom()), getDateFormat(filters.getDateTo()))));
            }

            Map<OrganizationEntity, List<FamilyEntity>> groupByOrganization = families.stream()
                    .collect(Collectors.groupingBy(f -> f.getOrganization()));

            List<FamilyOrganizationReportDTO> toRet = new ArrayList<>();

            groupByOrganization.forEach((k, v) -> {
                FamilyOrganizationReportDTO fa = new FamilyOrganizationReportDTO(k.getName(), k.getCode(),
                        k.getDescription(), k.isActive());
                fa.setFamilies(familyReportMapper.entityListToDtoList(v));

                toRet.add(fa);

            });

            return toRet;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public FamilySnapshotReportDTO listSnapshotByFamily(FamilyReportFilterDTO filters) {
        FamilySnapshotReportDTO toRet = new FamilySnapshotReportDTO();
     
        if (filters.getDateFrom() != null && filters.getDateTo() != null && filters.getFamilyId() != null) {

            List<SnapshotIndicatorEntity> snapshots = snapshotRepository
                    .findAll(where(byFamily(filters.getFamilyId())).and(SnapshotIndicatorSpecification
                            .byCreatedAt(getDateFormat(filters.getDateFrom()), getDateFormat(filters.getDateTo()))));

            toRet.setId(filters.getFamilyId());

            List<SurveyData> snapshotsList = snapshots.stream().map(snapshotMapper::entityToDto)
                    .collect(Collectors.toList());
            
            toRet.setSnapshots(getSnapshotsValues(snapshotsList));

        }

        return toRet;
    }

    private String getDateFormat(String date) {
        date = date.replace("/", "-");
        date = date + " 00:00:00";

        return date;
    }
    
    private List<SnapshotIndicators> getSnapshotsValues(List<SurveyData> snapshots){
        List<SnapshotIndicators> toRet = new ArrayList<>();
        for(SurveyData data : snapshots) {
            SnapshotIndicators indicators = new SnapshotIndicators();
            indicators.setIndicatorsSurveyData(snapshotService.getIndicatorsValue(data)); 
            toRet.add(indicators);
        }
        return toRet;
    }

}
