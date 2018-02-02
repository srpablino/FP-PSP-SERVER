package py.org.fundacionparaguaya.pspserver.network.services.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.data.jpa.domain.Specifications.where;
import static py.org.fundacionparaguaya.pspserver.network.specifications.OrganizationSpecification.byFilter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyFilterDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
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

@Service
public class OrganizationServiceImpl implements OrganizationService {

   

    private static final int LIMIT_TOP_OF_INDICATOR = 5;

    private Logger LOG = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    private final FamilyService familyService;

    private final FamilyRepository familyRepository;

    private final SnapshotEconomicRepository snapshotEconomicRepo;

    private final SnapshotIndicatorMapper indicatorMapper;

    private static final String[] EXCLUDE_FIELDS = {"serialVersionUID", "id",
                    "additionalProperties", "priorities" };

    public OrganizationServiceImpl(
                    OrganizationRepository organizationRepository,
                    OrganizationMapper organizationMapper,
                    FamilyService familyService,
                    FamilyRepository familyRepository,
                    SnapshotEconomicRepository snapshotEconomicRepo,
                    SnapshotIndicatorMapper indicatorMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.familyService = familyService;
        this.familyRepository = familyRepository;
        this.snapshotEconomicRepo = snapshotEconomicRepo;
        this.indicatorMapper = indicatorMapper;
    }

    @Override
    public OrganizationDTO updateOrganization(Long organizationId,
                    OrganizationDTO organizationDTO) {
        checkArgument(organizationId > 0,
                        "Argument was %s but expected nonnegative",
                        organizationId);

        return Optional.ofNullable(
                        organizationRepository.findOne(organizationId))
                        .map(organization -> {
                            organization.setName(organizationDTO.getName());
                            organization.setDescription(
                                            organizationDTO.getDescription());
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
                        "Argument was %s but expected nonnegative",
                        organizationId);

        return Optional.ofNullable(
                        organizationRepository.findOne(organizationId))
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
                        "Argument was %s but expected nonnegative",
                        organizationId);

        Optional.ofNullable(organizationRepository.findOne(organizationId))
                        .ifPresent(organization -> {
                            organizationRepository.delete(organization);
                            LOG.debug("Deleted Organization: {}", organization);
                        });

    }

    @Override
    public Page<OrganizationDTO> listOrganizations(PageRequest pageRequest,
                    UserDetailsDTO userDetails) {
        Long applicationId = Optional.ofNullable(userDetails.getApplication())
                        .orElse(new ApplicationDTO()).getId();

        Long organizationId = Optional.ofNullable(userDetails.getOrganization())
                        .orElse(new OrganizationDTO()).getId();

        Page<OrganizationEntity> pageResponse = organizationRepository.findAll(
                        where(byFilter(applicationId, organizationId)),
                        pageRequest);

        if (pageResponse != null) {
            return pageResponse.map(
               new Converter<OrganizationEntity, OrganizationDTO>() {
                                @Override
                                public OrganizationDTO convert(
                                                OrganizationEntity source) {
                                    return organizationMapper
                                                    .entityToDto(source);
                                }
                            });
        }

        return null;
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

        dto.setDashboard(DashboardDTO
                        .of(familyService.countFamiliesByFilter(filter)));

        dto.getDashboard()
                        .setTopOfIndicators(getTopOfIndicators(organizationId));

        dto.getDashboard().setSnapshotIndicators(
                        countSnapshotIndicators(organizationId));

     

        return dto;
    }

    

    private SnapshotIndicators countSnapshotIndicators(Long organizationId) {

        List<FamilyEntity> families = familyRepository
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
        	    			   indicators.getCountRedIndicators()
        	    			   + 1);
        	    	   break;
        	       case YELLOW:
        	    	   indicators.setCountYellowIndicators(
        	    			   indicators.getCountYellowIndicators()
        	    			   + 1);
        	    	    break;
        	       case GREEN:
        	    	   indicators.setCountGreenIndicators(
        	    			   indicators.getCountGreenIndicators()
        	    			   + 1);
        	    	    break;
        	       default:
        	    	   break;
        	    }
            });
    }

    private List<TopOfIndicators> getTopOfIndicators(Long organizationId) {
        List<FamilyEntity> families = familyRepository
                        .findByOrganizationId(organizationId);

        List<SnapshotEconomicEntity> snapshotEconomics = snapshotEconomicRepo
                        .findByFamilyIn(families);

        List<SnapshotEconomicEntity> snapshotEconomicsAux = snapshotEconomics;

        List<TopOfIndicators> topOfInticators =
        		new ArrayList<TopOfIndicators>();

        for (SnapshotEconomicEntity data : snapshotEconomics) {

            Field[] fields = data.getSnapshotIndicator().getClass()
                            .getDeclaredFields();

            for (Field field : fields) {

                int green = 0;
                int yellow = 0;
                int red = 0;
                TopOfIndicators ti = new TopOfIndicators();
                
                if (!snapshotEconomicsAux.isEmpty()){
                	
                	SnapshotEconomicEntity aux =
                			snapshotEconomicsAux.get(0);
    
                    Field[] fieldsAux = aux.getSnapshotIndicator().getClass()
                                    .getDeclaredFields();

                    for (Field fieldAux : fieldsAux) {

                        if (fieldAux.getName().equals(field.getName())) {

                            ti.setIndicatorName(fieldAux.getName());

                            Object obj = null;

                            try {
                                if (!fieldAux.getName()
                                		.equals(EXCLUDE_FIELDS[0])
                                                && !fieldAux.getName()
                                                .equals(EXCLUDE_FIELDS[1])
                                                && !fieldAux.getName()
                                                .equals(EXCLUDE_FIELDS[2])
                                                && !fieldAux.getName()
                                                .equals(EXCLUDE_FIELDS[3])) {

                                    obj = PropertyUtils.getProperty(
                                                    aux.getSnapshotIndicator(),
                                                    fieldAux.getName());

                                    String value = (String) obj;

                                    switch (value) {

                                    case "GREEN":
                                        ti.setTotalGreen(++green);
                                        ti.setTotalRed(red);
                                        ti.setTotalYellow(yellow);
                                        break;
                                    case "YELLOW":
                                        ti.setTotalYellow(++yellow);
                                        ti.setTotalRed(red);
                                        ti.setTotalGreen(green);
                                        break;
                                    case "RED":
                                        ti.setTotalRed(++red);
                                        ti.setTotalYellow(yellow);
                                        ti.setTotalGreen(green);
                                        break;

                                    default:
                                        ti.setTotalRed(red);
                                        ti.setTotalYellow(yellow);
                                        ti.setTotalGreen(green);
                                        break;
                                    }

                                }

                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                throw new CustomParameterizedException("Error");
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                                throw new CustomParameterizedException("Error");
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                                throw new CustomParameterizedException("Error");
                            }

                        }

                    }

                }
                if (!ti.getIndicatorName().equals(EXCLUDE_FIELDS[0])
                                && !ti.getIndicatorName()
                                                .equals(EXCLUDE_FIELDS[1])
                                && !ti.getIndicatorName()
                                                .equals(EXCLUDE_FIELDS[2])
                                && !ti.getIndicatorName()
                                                .equals(EXCLUDE_FIELDS[3])) {
                    topOfInticators.add(ti);
                }
            }


        }

        Collections.sort(topOfInticators, new Comparator<TopOfIndicators>() {
            @Override
            public int compare(TopOfIndicators p1, TopOfIndicators p2) {
                return new CompareToBuilder()
                                .append(p2.getTotalRed(), p1.getTotalRed())
                                .append(p2.getTotalYellow(),
                                                p1.getTotalYellow())
                                .toComparison();
            }
        });
        
        if (topOfInticators.isEmpty()){
        	 return topOfInticators;
        } else {
        	return topOfInticators.subList(0, LIMIT_TOP_OF_INDICATOR);
        }
       
        

    }

}
