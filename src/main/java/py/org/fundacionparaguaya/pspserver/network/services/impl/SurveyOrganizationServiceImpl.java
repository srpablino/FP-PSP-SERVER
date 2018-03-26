package py.org.fundacionparaguaya.pspserver.network.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.SurveyOrganizationDTO;
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
    public void crudSurveyOrganization(UserDetailsDTO details,
            Long surveyId, SurveyDefinition surveyDefinition,
            SurveyEntity surveyEntity) {

        Long applicationId = Optional.ofNullable(details.getApplication())
                .orElse(new ApplicationDTO())
                .getId();

        List<SurveyOrganizationEntity> listSurveyOrganizations= null;

        if (surveyDefinition.getOrganizations() != null
                && surveyDefinition.getOrganizations().size() > 0) {

            //repo.deleteBySurveyIdAndApplicationIdAndOrganizationIsNotNull(surveyId, applicationId);

            surveyEntity.getSurveysOrganizations()
            .removeAll(
                    repo.findBySurveyIdAndApplicationIdAndOrganizationIsNotNull(surveyId, applicationId));

           listSurveyOrganizations
            = new ArrayList<SurveyOrganizationEntity>();

            for (OrganizationDTO organization : surveyDefinition
                    .getOrganizations()) {
                SurveyOrganizationEntity surveyOrganization = new SurveyOrganizationEntity();
                surveyOrganization.setSurvey(surveyEntity);
                surveyOrganization.setApplication(applicationRepo
                        .findById(organization.getApplication().getId()));
                surveyOrganization.setOrganization(
                        organizationRepo.findById(organization.getId()));
                //repo.save(surveyOrganization);
                listSurveyOrganizations.add(surveyOrganization);
            }
            surveyEntity.getSurveysOrganizations().addAll(listSurveyOrganizations);

        } else {
            surveyEntity.getSurveysOrganizations()
            .removeAll(
                    repo.findBySurveyIdAndApplicationIdAndOrganizationIsNotNull(surveyId, applicationId));
        }

        if (surveyDefinition.getApplications() != null
                && surveyDefinition.getApplications().size() > 0) {

            surveyEntity.getSurveysOrganizations().removeAll(repo.findBySurveyId(surveyId));

            listSurveyOrganizations
            = new ArrayList<SurveyOrganizationEntity>();

//            repo.findBySurveyId(surveyId).forEach(
//                    so -> repo.deleteBySurveyId(so.getSurvey().getId()));

            for (ApplicationDTO application : surveyDefinition
                    .getApplications()) {
                SurveyOrganizationEntity surveyOrganization = new SurveyOrganizationEntity();
                surveyOrganization.setSurvey(surveyEntity);
                surveyOrganization.setApplication(
                        applicationRepo.findById(application.getId()));
                //repo.save(surveyOrganization);
                listSurveyOrganizations.add(surveyOrganization);
            }

            surveyEntity.getSurveysOrganizations().addAll(listSurveyOrganizations);

        }
    }

}