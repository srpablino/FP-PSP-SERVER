package py.org.fundacionparaguaya.pspserver.network.services.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static py.org.fundacionparaguaya.pspserver.surveys.specifications.SnapshotEconomicSpecification.byFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyFilterDTO;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.DashboardDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.mapper.ApplicationMapper;
import py.org.fundacionparaguaya.pspserver.network.repositories.ApplicationRepository;
import py.org.fundacionparaguaya.pspserver.network.services.ApplicationService;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;



@Service
public class ApplicationServiceImpl implements ApplicationService {

	private Logger LOG = LoggerFactory.getLogger(ApplicationServiceImpl.class);
	
	 private static final int MAX_MONTH_AGO = 2;

	private final ApplicationRepository applicationRepository;
	
	private final ApplicationMapper applicationMapper;
	
	private final FamilyService familyService;
	
	 private final SnapshotEconomicRepository snapshotEconomicRepo;
	 
	public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper,
			FamilyService familyService,SnapshotEconomicRepository snapshotEconomicRepo) {
		this.applicationRepository = applicationRepository;
		this.applicationMapper = applicationMapper;
		this.familyService = familyService;
		this.snapshotEconomicRepo = snapshotEconomicRepo;
	}
	

	@Override
	public ApplicationDTO updateApplication(Long applicationId, ApplicationDTO applicationDTO) {
		checkArgument(applicationId > 0, "Argument was %s but expected nonnegative", applicationId);

		return Optional.ofNullable(applicationRepository.findOne(applicationId))
                .map(application -> {
                    BeanUtils.copyProperties(applicationDTO, application);
                    LOG.debug("Changed Information for Application: {}", application);
                    return application;
                })
                .map(applicationMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Application does not exist"));
	}

	
	@Override
	public ApplicationDTO addApplication(ApplicationDTO applicationDTO) {
		ApplicationEntity application = new ApplicationEntity();
		BeanUtils.copyProperties(applicationDTO, application);
		ApplicationEntity newApplication = applicationRepository.save(application);
		return applicationMapper.entityToDto(newApplication);
	}

	
	@Override
	public ApplicationDTO getApplicationById(Long applicationId) {
		checkArgument(applicationId > 0, "Argument was %s but expected nonnegative", applicationId);

        return Optional.ofNullable(applicationRepository.findOne(applicationId))
                .map(applicationMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Application does not exist"));
	}

	
	@Override
	public List<ApplicationDTO> getAllApplications() {
		List<ApplicationEntity> applications = applicationRepository.findAll();
		return applicationMapper.entityListToDtoList(applications);
	}

	
	@Override
	public void deleteApplication(Long applicationId) {
		checkArgument(applicationId > 0, "Argument was %s but expected nonnegative", applicationId);

        Optional.ofNullable(applicationRepository.findOne(applicationId))
                .ifPresent(application -> {
                	applicationRepository.delete(application);
                    LOG.debug("Deleted Application: {}", application);
                });
	}
	
	@Override
    public ApplicationDTO getApplicationDashboard(Long applicationId, UserDetailsDTO details) {
	    ApplicationDTO dto = new ApplicationDTO();
	    
	    if(details.getApplication() != null && details.getApplication().getId() != null) {
	        dto = getApplicationById(details.getApplication().getId());
	    }else if(applicationId != null){
	        dto = getApplicationById(applicationId);
	    }
        
        Long organizationId = Optional.ofNullable(details.getOrganization())
                .orElse(new OrganizationDTO()).getId();
        
        FamilyFilterDTO filter = new FamilyFilterDTO(dto.getId(), organizationId);
        dto.setDashboard(DashboardDTO.of(familyService.countFamiliesByFilter(filter)));
        
        dto.getDashboard().setSnapshotTaken(countSnapshotTaken(organizationId));
        
        return dto;
	}
	
	private String countSnapshotTaken(Long organizationId) {

        LocalDate initial = LocalDate.now();
        LocalDate startToday = initial.withDayOfMonth(1);
        LocalDate endToday = initial.withDayOfMonth(initial.lengthOfMonth());

        List<SnapshotEconomicEntity> listSnapshotEconomicToday = snapshotEconomicRepo
                        .findAll(byFilter(
                                        LocalDateTime.of(startToday,
                                                        LocalTime.of(0, 0, 0)),
                                        LocalDateTime.of(endToday, LocalTime
                                                        .of(23, 59, 59))));

        SurveyData data = new SurveyData();

        for (int i = MAX_MONTH_AGO; i >= 1; i--) {

            LocalDate initial_ = LocalDate.now().minusMonths(i);

            LocalDate startToday_ = initial_.withDayOfMonth(1);
            LocalDate endToday_ = initial_
                            .withDayOfMonth(initial_.lengthOfMonth());

            List<SnapshotEconomicEntity> listSnapshotEconomicToday_ = snapshotEconomicRepo
                            .findAll(byFilter(
                                            LocalDateTime.of(startToday_,
                                                            LocalTime.of(0, 0,
                                                                            0)),
                                            LocalDateTime.of(endToday_,
                                                            LocalTime.of(23, 59,
                                                                            59))));

            if (listSnapshotEconomicToday_.size() > 0) {
                data.put(String.valueOf(initial_.getMonthValue()),
                                listSnapshotEconomicToday_.size());
            } else {
                data.put(String.valueOf(initial_.getMonthValue()), 0);
            }

        }

        if (listSnapshotEconomicToday.size() > 0) {
            data.put("TODAY", listSnapshotEconomicToday.size());
        } else {
            data.put("TODAY", 0);
        }

        return new Gson().toJson(data);

    }

}
