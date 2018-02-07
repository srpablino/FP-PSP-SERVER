package py.org.fundacionparaguaya.pspserver.network.services.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.data.jpa.domain.Specifications.where;
import static py.org.fundacionparaguaya.pspserver.network.specifications.OrganizationSpecification.byFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.common.pagination.PaginableList;
import py.org.fundacionparaguaya.pspserver.common.pagination.PspPageRequest;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyFilterDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.DashboardDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.mapper.OrganizationMapper;
import py.org.fundacionparaguaya.pspserver.network.repositories.OrganizationRepository;
import py.org.fundacionparaguaya.pspserver.network.services.OrganizationService;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.TopOfIndicators;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;
import py.org.fundacionparaguaya.pspserver.surveys.enums.SurveyStoplightEnum;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotIndicatorMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.impl.SnapshotServiceImpl;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger LOG = LoggerFactory
            .getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    private final FamilyService familyService;

    private final SnapshotEconomicRepository snapshotEconomicRepo;

    private final SnapshotIndicatorMapper indicatorMapper;

    private final SnapshotServiceImpl snapshotServiceImpl;

    public OrganizationServiceImpl(
            OrganizationRepository organizationRepository,
            OrganizationMapper organizationMapper, FamilyService familyService,
            SnapshotEconomicRepository snapshotEconomicRepo,
            SnapshotIndicatorMapper indicatorMapper,
            SnapshotServiceImpl snapshotServiceImpl) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.familyService = familyService;
        this.snapshotEconomicRepo = snapshotEconomicRepo;
        this.indicatorMapper = indicatorMapper;
        this.snapshotServiceImpl = snapshotServiceImpl;
    }

    @Override
    public OrganizationDTO updateOrganization(Long organizationId,
            OrganizationDTO organizationDTO) {
        checkArgument(organizationId > 0,
                "Argument was %s but expected nonnegative", organizationId);

        return Optional
                .ofNullable(organizationRepository.findOne(organizationId))
                .map(organization -> {
                    organization.setName(organizationDTO.getName());
                    organization
                            .setDescription(organizationDTO.getDescription());
                    LOG.debug("Changed Information for Organization: {}",
                            organization);
                    return organizationRepository.save(organization);
                }).map(organizationMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException(
                        "Organization does not exist"));
    }

    @Override
    public OrganizationDTO addOrganization(OrganizationDTO organizationDTO) {
        OrganizationEntity organization = new OrganizationEntity();
        BeanUtils.copyProperties(organizationDTO, organization);
        OrganizationEntity newOrganization = organizationRepository
                .save(organization);
        return organizationMapper.entityToDto(newOrganization);
    }

    @Override
    public OrganizationDTO getOrganizationById(Long organizationId) {
        checkArgument(organizationId > 0,
                "Argument was %s but expected nonnegative", organizationId);

        return Optional
                .ofNullable(organizationRepository.findOne(organizationId))
                .map(organizationMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException(
                        "Organization does not exist"));
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
        List<OrganizationEntity> organizations = organizationRepository
                .findAll();
        return organizationMapper.entityListToDtoList(organizations);
    }

    @Override
    public void deleteOrganization(Long organizationId) {
        checkArgument(organizationId > 0,
                "Argument was %s but expected nonnegative", organizationId);

        Optional.ofNullable(organizationRepository.findOne(organizationId))
                .ifPresent(organization -> {
                    organizationRepository.delete(organization);
                    LOG.debug("Deleted Organization: {}", organization);
                });

    }

    @Override
    public OrganizationDTO getOrganizationDashboard(Long organizationId,
            UserDetailsDTO details) {
        OrganizationDTO dto = new OrganizationDTO();

        if (details.getOrganization() != null
                && details.getOrganization().getId() != null) {
            dto = getOrganizationById(details.getOrganization().getId());
        } else if (organizationId != null) {
            dto = getOrganizationById(organizationId);
        }

        Long applicationId = Optional.ofNullable(details.getApplication())
                .orElse(new ApplicationDTO()).getId();

        FamilyFilterDTO filter = new FamilyFilterDTO(applicationId,
                dto.getId());

        DashboardDTO dashboard = DashboardDTO.of(
                familyService.countFamiliesByFilter(filter), null,
                getTopOfIndicators(organizationId),
                countSnapshotIndicators(organizationId), null);

        dto.setDashboard(dashboard);

        return dto;
    }

    private List<TopOfIndicators> getTopOfIndicators(Long organizationId) {
        List<FamilyEntity> families = familyService
                .findByOrganizationId(organizationId);

        List<SnapshotEconomicEntity> snapshotEconomicsList =
                snapshotEconomicRepo.findByFamilyIn(families);

        List<SnapshotIndicatorEntity> indicatorsList =
                new ArrayList<SnapshotIndicatorEntity>();

        snapshotEconomicsList.forEach(new Consumer<SnapshotEconomicEntity>() {
            public void accept(SnapshotEconomicEntity snapshotEconomic) {
                indicatorsList.add(snapshotEconomic.getSnapshotIndicator());
            }
        });

        List<SurveyData> propertiesList = indicatorMapper
                .entityListToDtoList(indicatorsList);

        Map<String, TopOfIndicators> map =
                new HashMap<String, TopOfIndicators>();

        for (SurveyData surveyData : propertiesList) {

            surveyData.forEach((k, v) -> {
                countTopIndicators(map, k, v);
            });

        }

        List<TopOfIndicators> list = map.entrySet().stream()
                .map(e -> new TopOfIndicators(e.getValue()))
                .collect(Collectors.toList());

        return list;

    }

    private void countTopIndicators(Map<String, TopOfIndicators> map, String k,
            Object v) {

        if (map.containsKey(k)) {

            String light = (String) v;
            TopOfIndicators tOi = map.get(k);

            switch (light) {

            case "RED":
                tOi.setTotalRed(tOi.getTotalRed() + 1);
                break;
            case "YELLOW":
                tOi.setTotalYellow(tOi.getTotalYellow() + 1);
                break;
            case "GREEN":
                tOi.setTotalGreen(tOi.getTotalGreen() + 1);
                break;
            default:
                break;
            }

        } else {

            String light = (String) v;
            TopOfIndicators tOi = new TopOfIndicators();
            tOi.setIndicatorName(
                    snapshotServiceImpl.getNameFromCamelCase((String) k));

            if (light != null) {
                switch (light) {
                case "RED":
                    tOi.setTotalRed(1);
                    tOi.setTotalYellow(0);
                    tOi.setTotalGreen(0);
                    break;
                case "YELLOW":
                    tOi.setTotalYellow(1);
                    tOi.setTotalRed(0);
                    tOi.setTotalGreen(0);
                    break;
                case "GREEN":
                    tOi.setTotalGreen(1);
                    tOi.setTotalYellow(0);
                    tOi.setTotalRed(0);
                    break;
                default:
                    break;
                }

                map.put(k, tOi);
            }

        }

    }

    private SnapshotIndicators countSnapshotIndicators(Long organizationId) {

        List<FamilyEntity> families = familyService
                .findByOrganizationId(organizationId);

        List<SnapshotEconomicEntity> snapshotEconomics = snapshotEconomicRepo
                .findByFamilyIn(families);

        List<SnapshotIndicatorEntity> entityList =
                new ArrayList<SnapshotIndicatorEntity>();

        for (SnapshotEconomicEntity economics : snapshotEconomics) {
            entityList.add(economics.getSnapshotIndicator());
        }

        SnapshotIndicators indicators = new SnapshotIndicators();

        List<SurveyData> listProperties = indicatorMapper
                .entityListToDtoList(entityList);

        for (SurveyData properties : listProperties) {
            properties.forEach((k, v) -> {
                countIndicators(indicators, v);
            });
        }

        return indicators;

    }

    private void countIndicators(SnapshotIndicators indicators, Object v) {
        Optional.ofNullable(SurveyStoplightEnum.fromValue(String.valueOf(v)))
                .ifPresent(light -> {
                    switch (light) {
                    case RED:
                        indicators.setCountRedIndicators(
                                indicators.getCountRedIndicators() + 1);
                        break;
                    case YELLOW:
                        indicators.setCountYellowIndicators(
                                indicators.getCountYellowIndicators() + 1);
                        break;
                    case GREEN:
                        indicators.setCountGreenIndicators(
                                indicators.getCountGreenIndicators() + 1);
                        break;
                    default:
                        break;
                    }
                });
    }

    @Override
    public PaginableList<OrganizationDTO> listOrganizations(Long applicationId,
            Long organizationId, int page, int perPage, String orderBy,
            String sortBy) {

        PaginableList<OrganizationDTO> response;

        PageRequest pageRequest = new PspPageRequest(page, perPage, orderBy,
                sortBy);

        Page<OrganizationEntity> pageResponse = organizationRepository.findAll(
                where(byFilter(applicationId, organizationId)), pageRequest);

        if (pageResponse == null) {
            return new PaginableList<>(Collections.emptyList());
        } else {
            Page<OrganizationDTO> organizationPage = pageResponse
                    .map(new Converter<OrganizationEntity, OrganizationDTO>() {
                        @Override
                        public OrganizationDTO convert(
                                OrganizationEntity source) {
                            return organizationMapper.entityToDto(source);
                        }
                    });

            response = new PaginableList<OrganizationDTO>(organizationPage,
                    organizationPage.getContent());
        }

        return response;

    }

    @Override
    public OrganizationDTO getUserOrganization(UserDetailsDTO details,
            Long organizationId) {
        if (details.getOrganization() != null
                && details.getOrganization().getId() != null) {
            return getOrganizationById(details.getOrganization().getId());
        } else if (organizationId != null) {
            return getOrganizationById(organizationId);
        }
        return null;
    }

}
