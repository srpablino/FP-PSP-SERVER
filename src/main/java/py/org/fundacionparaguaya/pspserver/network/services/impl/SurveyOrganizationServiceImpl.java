package py.org.fundacionparaguaya.pspserver.network.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.SurveyOrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.SurveyOrganizationEntity;
import py.org.fundacionparaguaya.pspserver.network.mapper.SurveyOrganizationMapper;
import py.org.fundacionparaguaya.pspserver.network.repositories.ApplicationRepository;
import py.org.fundacionparaguaya.pspserver.network.repositories.OrganizationRepository;
import py.org.fundacionparaguaya.pspserver.network.repositories.SurveyOrganizationRepository;
import py.org.fundacionparaguaya.pspserver.network.services.SurveyOrganizationService;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyDefinition;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyRepository;

@Service
public class SurveyOrganizationServiceImpl
        implements SurveyOrganizationService {

    private SurveyOrganizationRepository repo;

    private SurveyOrganizationMapper mapper;

    private SurveyRepository surveyRepo;

    private OrganizationRepository organizationRepo;

    private ApplicationRepository applicationRepo;

    public SurveyOrganizationServiceImpl(SurveyOrganizationRepository repo,
            SurveyOrganizationMapper mapper, SurveyRepository surveyRepo,
            OrganizationRepository organizationRepo,
            ApplicationRepository applicationRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.surveyRepo = surveyRepo;
        this.organizationRepo = organizationRepo;
        this.applicationRepo = applicationRepo;
    }

    @Override
    public List<SurveyOrganizationDTO> getAllSurveyOrganizations() {
        return mapper.entityListToDtoList(repo.findAll());
    }

    @Override
    public SurveyOrganizationDTO addSurveyOrganization(
            SurveyOrganizationDTO surveyOrganization) {
        SurveyOrganizationEntity entity = new SurveyOrganizationEntity();
        entity.setSurvey(
                surveyRepo.findOne(surveyOrganization.getSurvey().getId()));
        entity.setOrganization(organizationRepo
                .findOne(surveyOrganization.getOrganization().getId()));
        return mapper.entityToDto(repo.save(entity));
    }

    @Override
    public List<SurveyOrganizationEntity> crudSurveyOrganization(UserDetailsDTO details,
            Long surveyId, SurveyDefinition surveyDefinition,
            SurveyEntity surveyEntity) {

        OrganizationEntity organizationEntity;
        ApplicationEntity applicationEntity;
        SurveyOrganizationEntity surveyOrganizationEntity;

        List<SurveyOrganizationEntity> surveyOrganizationEntityList =
                new ArrayList<SurveyOrganizationEntity>();

        for (OrganizationDTO organizationDTO : surveyDefinition.getOrganizations()){
            surveyOrganizationEntity = new SurveyOrganizationEntity();
            organizationEntity = organizationRepo.findById(organizationDTO.getId());
            surveyOrganizationEntity.setOrganization(organizationEntity);
            surveyOrganizationEntity.setApplication(organizationEntity.getApplication());
            surveyOrganizationEntityList.add(surveyOrganizationEntity);

        }

        for (ApplicationDTO applicationDTO : surveyDefinition.getApplications()){
            applicationEntity = applicationRepo.findById(applicationDTO.getId());
            surveyOrganizationEntity = new SurveyOrganizationEntity();
            surveyOrganizationEntity.setApplication(applicationEntity);
            surveyOrganizationEntityList.add(surveyOrganizationEntity);
        }

        return surveyOrganizationEntityList;
    }

}